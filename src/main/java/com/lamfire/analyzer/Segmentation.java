/**
 * 
 */
package com.lamfire.analyzer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.lamfire.analyzer.dic.Dictionary;
import com.lamfire.analyzer.seg.CJKSeg;
import com.lamfire.analyzer.seg.LetterSeg;
import com.lamfire.analyzer.seg.QuantifierSeg;
import com.lamfire.analyzer.seg.Seg;

/**
 * 主分词器 注：Segmentation是一个lucene无关的通用分词器
 */
public final class Segmentation {

	private Reader input;
	// 默认缓冲区大小
	private static final int BUFF_SIZE = 3072;
	// 缓冲区耗尽的临界值
	private static final int BUFF_EXHAUST_CRITICAL = 48;
	// 字符窜读取缓冲
	private char[] segmentBuff;
	// 分词器上下文
	private SegContext context;
	// 分词处理器列表
	private List<Seg> segs;

	/**
	 * 主分词器构造函数 默认最细粒度切分
	 * 
	 * @param input
	 */
	public Segmentation(Reader input) {
		this(input, false);
	}

    public Segmentation(String input){
        this(new StringReader(input),false);
    }

    public Segmentation(String input, boolean isMaxWordLength){
        this(new StringReader(input),isMaxWordLength);
    }

	/**
	 * 主分词器构造函数
	 * 
	 * @param input
	 * @param isMaxWordLength
	 * 当为true时，分词器进行最大词长切分
	 */
	public Segmentation(Reader input, boolean isMaxWordLength) {
		this.input = input;
		segmentBuff = new char[BUFF_SIZE];
		context = new SegContext(segmentBuff, isMaxWordLength);
		segs = loadSegs();
	}

    public SegContext getContext(){
        return context;
    }

	/**
	 * 获取下一个语义单元
	 * 
	 * @return 没有更多的词元，则返回null
	 * @throws java.io.IOException
	 */
	public synchronized Lexeme next() {
		if (context.getResultSize() == 0) {
			// 词元池中没有处理的结果,则进行分词处理
			process();
		}

		// 读取词元池中的词元
		return buildLexeme(context.firstLexeme());
	}

	/**
	 * 分词处理
	 * 
	 * @throws java.io.IOException
	 */
	private void process(){
		/*
		 * 从reader中读取数据，填充buffer 如果reader是分次读入buffer的，那么buffer要进行移位处理
		 * 移位处理上次读入的但未处理的数据
		 */
		int available = fillBuffer(input);

		if (available <= 0) {
			context.resetContext();
			return;
		}
		// 分词处理
		int analyzedLength = 0;
		for (int buffIndex = 0; buffIndex < available; buffIndex++) {
			// 移动缓冲区指针
			context.setCursor(buffIndex);
			// 进行字符规格化（全角转半角，大写转小写处理）
			segmentBuff[buffIndex] = CharHelper.regularize(segmentBuff[buffIndex]);
			// 遍历子分词器
			for (Seg seg : segs) {
				seg.nextLexeme(segmentBuff, context);
			}
			analyzedLength++;
			/*
			 * 满足一下条件时， 1.available == BUFF_SIZE 表示buffer满载 2.buffIndex <
			 * available - 1 && buffIndex > available -
			 * BUFF_EXHAUST_CRITICAL表示当前指针处于临界区内
			 * 3.!context.isBufferLocked()表示没有segmenter在占用buffer
			 * 要中断当前循环（buffer要进行移位，并再读取数据的操作）
			 */
			if (available == BUFF_SIZE && buffIndex < available - 1 && buffIndex > available - BUFF_EXHAUST_CRITICAL && !context.isBufferLocked()) {

				break;
			}
		}

		for (Seg seg : segs) {
			seg.reset();
		}
		// System.out.println(available + " : " + buffIndex);
		// 记录最近一次分析的字符长度
		context.setLastAnalyzed(analyzedLength);
		// 同时累计已分析的字符长度
		context.setBuffOffset(context.getBuffOffset() + analyzedLength);
		// 如果使用最大切分，则过滤交叠的短词元
		if (context.isMaxWordLength()) {
			context.excludeOverlap();
		}

	}

	/**
	 * 根据context的上下文情况，填充segmentBuff
	 * 
	 * @param reader
	 * @return 返回待分析的（有效的）字串长度
	 * @throws java.io.IOException
	 */
	private int fillBuffer(Reader reader){
		int readCount = 0;
        try{
            if (context.getBuffOffset() == 0) {
                // 首次读取reader
                readCount = reader.read(segmentBuff);
            } else {
                int offset = context.getAvailable() - context.getLastAnalyzed();
                if (offset > 0) {
                    // 最近一次读取的>最近一次处理的，将未处理的字串拷贝到segmentBuff头部
                    System.arraycopy(segmentBuff, context.getLastAnalyzed(), this.segmentBuff, 0, offset);
                    readCount = offset;
                }
                // 继续读取reader ，以onceReadIn - onceAnalyzed为起始位置，继续填充segmentBuff剩余的部分
                readCount += reader.read(segmentBuff, offset, BUFF_SIZE - offset);
            }
            // 记录最后一次从Reader中读入的可用字符长度
            context.setAvailable(readCount);
        }catch (Exception e){

        }
		return readCount;
	}

	/**
	 * 取出词元集合中的下一个词元
	 * 
	 * @return Lexeme
	 */
	private Lexeme buildLexeme(Lexeme lexeme) {
		if (lexeme == null) {
			return null;
		}
		
		// 生成lexeme的词元文本
		lexeme.setLexemeText(String.valueOf(segmentBuff, lexeme.getBegin(), lexeme.getLength()));
		return lexeme;
	}

	/**
	 * 重置分词器到初始状态
	 * 
	 * @param input
	 */
	public synchronized void reset(Reader input) {
		this.input = input;
		context.resetContext();
		for (Seg seg : segs) {
			seg.reset();
		}
	}

	/**
	 * 初始化子分词器实现 （目前暂时不考虑配置扩展）
	 * 
	 * @return List<ISegmenter>
	 */
	public static List<Seg> loadSegs() {
		// 初始化词典单例
		Dictionary.getInstance();
		List<Seg> segs = new ArrayList<Seg>(4);
		// 处理数量词的子分词器
		segs.add(new QuantifierSeg());
		// 处理中文词的子分词器
		segs.add(new CJKSeg());
		// 处理字母的子分词器
		segs.add(new LetterSeg());
		return segs;
	}

    public synchronized List<String> split() {
        List<String> result = new ArrayList<String>();
        Lexeme lex = this.next();
        while(lex != null){
            result.add(lex.getLexemeText());
            lex = this.next();
        }
        return result;
    }

}

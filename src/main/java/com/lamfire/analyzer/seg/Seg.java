/**
 * 
 */
package com.lamfire.analyzer.seg;

import com.lamfire.analyzer.SegContext;

/**
 * 子分词器接口
 *
 */
public interface Seg {
	
	/**
	 * 从分析器读取下一个可能分解的词元对象
	 * @param segmentBuff 文本缓冲
	 * @param context 分词算法上下文
	 */
	void nextLexeme(char[] segmentBuff, SegContext context);
	
	/**
	 * 重置子分析器状态
	 */
	void reset();
}

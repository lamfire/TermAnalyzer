/**
 * 
 */
package com.lamfire.analyzer.dic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * Analyzer
 * 词典管理类,单例模式
 *
 */
public class Dictionary {
	/*
	 * 分词器默认字典路径 
	 */
	public static final String PATH_DIC_MAIN = "/dic/main.dic";
	public static final String PATH_DIC_SURNAME = "/dic/surname.dic";
	public static final String PATH_DIC_QUANTIFIER = "/dic/quantifier.dic";
	public static final String PATH_DIC_SUFFIX = "/dic/suffix.dic";
	public static final String PATH_DIC_PREP = "/dic/preposition.dic";
	public static final String PATH_DIC_STOP = "/dic/stopword.dic";
	
	
	/*
	 * 词典单子实例
	 */
	private static final Dictionary singleton;
	
	/*
	 * 词典初始化
	 */
	static{
		singleton = new Dictionary();
	}
	
	/*
	 * 主词典对象
	 */
	private DictSegment mainDict;
	/*
	 * 姓氏词典
	 */
	private DictSegment surnameDict;
	/*
	 * 量词词典
	 */
	private DictSegment quantifierDict;
	/*
	 * 后缀词典
	 */
	private DictSegment suffixDict;
	/*
	 * 副词，介词词典
	 */
	private DictSegment prepDict;
	/*
	 * 停止词集合
	 */
	private DictSegment stopWords;
	
	private Dictionary(){
		//初始化系统词典
		loadMainDict();
		loadSurnameDict();
		loadQuantifierDict();
		loadSuffixDict();
		loadPrepDict();
		loadStopWordDict();
	}

	/**
	 * 加载主词典
	 */
	private void loadMainDict(){
		//建立一个主词典实例
		mainDict = new DictSegment((char)0);
		//读取主词典文件
        InputStream is = Dictionary.class.getResourceAsStream(Dictionary.PATH_DIC_MAIN);
        if(is == null){
        	throw new RuntimeException("Main Dictionary not found!!!");
        }
        
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					mainDict.fillSegment(theWord.trim().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Main Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * 加载扩展词典
	 * @param extDict
	 */
	public void loadToMainDict(File extDict){
		InputStream is = null;
		try {
			//读取扩展词典文件
			is = new FileInputStream(extDict);
			//如果找不到扩展的字典，则忽略
			if(is == null){
				return;
			}
			loadToMainDict(is);
			
		} catch (IOException ioe) {
			System.err.println("Extension Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}	
	
	/**
	 * 加载扩展词典
	 * @param is
	 * @throws java.io.IOException
	 */
	public void loadToMainDict(InputStream is) throws IOException{
		if(is == null){
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
		String theWord = null;
		do {
			theWord = br.readLine();
			if (theWord != null && !"".equals(theWord.trim())) {
				//加载扩展词典数据到主内存词典中
				//System.out.println(theWord);
				mainDict.fillSegment(theWord.trim().toCharArray());
			}
		} while (theWord != null);
	}
	
	/**
	 * 加载姓氏词典
	 */
	private void loadSurnameDict(){
		//建立一个姓氏词典实例
		surnameDict = new DictSegment((char)0);
		//读取姓氏词典文件
        InputStream is = Dictionary.class.getResourceAsStream(Dictionary.PATH_DIC_SURNAME);
        if(is == null){
        	throw new RuntimeException("Surname Dictionary not found!!!");
        }
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					surnameDict.fillSegment(theWord.trim().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Surname Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 加载量词词典
	 */
	private void loadQuantifierDict(){
		//建立一个量词典实例
		quantifierDict = new DictSegment((char)0);
		//读取量词词典文件
        InputStream is = Dictionary.class.getResourceAsStream(Dictionary.PATH_DIC_QUANTIFIER);
        if(is == null){
        	throw new RuntimeException("Quantifier Dictionary not found!!!");
        }
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					quantifierDict.fillSegment(theWord.trim().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Quantifier Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 加载后缀词典
	 */
	private void loadSuffixDict(){
		//建立一个后缀词典实例
		suffixDict = new DictSegment((char)0);
		//读取量词词典文件
        InputStream is = Dictionary.class.getResourceAsStream(Dictionary.PATH_DIC_SUFFIX);
        if(is == null){
        	throw new RuntimeException("Suffix Dictionary not found!!!");
        }
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					suffixDict.fillSegment(theWord.trim().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Suffix Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}			

	/**
	 * 加载介词\副词词典
	 */
	private void loadPrepDict(){
		//建立一个介词\副词词典实例
		prepDict = new DictSegment((char)0);
		//读取量词词典文件
        InputStream is = Dictionary.class.getResourceAsStream(Dictionary.PATH_DIC_PREP);
        if(is == null){
        	throw new RuntimeException("Preposition Dictionary not found!!!");
        }
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					//System.out.println(theWord);
					prepDict.fillSegment(theWord.trim().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Preposition Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 加载停止词词典
	 */
	private void loadStopWordDict(){
		//建立一个停止词典实例
		stopWords = new DictSegment((char)0);
		//读取量词词典文件
        InputStream is = Dictionary.class.getResourceAsStream(Dictionary.PATH_DIC_STOP);
        if(is == null){
        	throw new RuntimeException("Stopword Dictionary not found!!!");
        }
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					stopWords.fillSegment(theWord.trim().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Stopword Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}		
	
	/**
	 * 加载停止词词典
	 * @param extDict
	 */
	public void loadToStopWordDict(File extDict){
		InputStream is = null;
		try {
			is = new FileInputStream(extDict);
			
			//如果找不到扩展的字典，则忽略
			if(is == null){
				return;
			}
			loadToStopWordDict(is);
		} catch (IOException ioe) {
			System.err.println("Extension Stop word Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * 加载停止词词典
	 * @param extDict
	 * @throws java.io.IOException
	 */
	public void loadToStopWordDict(InputStream is) throws IOException{
		//如果找不到扩展的字典，则忽略
		if(is == null){
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
		String theWord = null;
		do {
			theWord = br.readLine();
			if (theWord != null && !"".equals(theWord.trim())) {
				//System.out.println(theWord);
				//加载扩展停止词典数据到内存中
				stopWords.fillSegment(theWord.trim().toCharArray());
			}
		} while (theWord != null);
	}
	
	/**
	 * 词典初始化
	 * 由于IK Analyzer的词典采用Dictionary类的静态方法进行词典初始化
	 * 只有当Dictionary类被实际调用时，才会开始载入词典，
	 * 这将延长首次分词操作的时间
	 * 该方法提供了一个在应用加载阶段就初始化字典的手段
	 * 用来缩短首次分词时的时延
	 * @return Dictionary
	 */
	public static Dictionary getInstance(){
		return Dictionary.singleton;
	}
	
	/**
	 * 加载扩展的词条
	 * @param extWords Collection<String>词条列表
	 */
	public static void loadExtendWords(Collection<String> extWords){
		if(extWords != null){
			for(String extWord : extWords){
				if (extWord != null) {
					//加载扩展词条到主内存词典中
					singleton.mainDict.fillSegment(extWord.trim().toCharArray());
				}
			}
		}
	}
	
	/**
	 * 加载扩展的停止词条
	 * @param extStopWords Collection<String>词条列表
	 */
	public static void loadExtendStopWords(Collection<String> extStopWords){
		if(extStopWords != null){
			for(String extStopWord : extStopWords){
				if (extStopWord != null) {
					//加载扩展的停止词条
					singleton.stopWords.fillSegment(extStopWord.trim().toCharArray());
				}
			}
		}
	}
	
	/**
	 * 检索匹配主词典
	 * @param charArray
	 * @return Hit 匹配结果描述
	 */
	public static Hit matchInMainDict(char[] charArray){
		return singleton.mainDict.match(charArray);
	}
	
	/**
	 * 检索匹配主词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public static Hit matchInMainDict(char[] charArray , int begin, int length){
		return singleton.mainDict.match(charArray, begin, length);
	}
	
	/**
	 * 检索匹配主词典,
	 * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
	 * @param charArray
	 * @param currentIndex
	 * @param matchedHit
	 * @return Hit
	 */
	public static Hit matchWithHit(char[] charArray , int currentIndex , Hit matchedHit){
		DictSegment ds = matchedHit.getMatchedDictSegment();
		return ds.match(charArray, currentIndex, 1 , matchedHit);
	}

	/**
	 * 检索匹配姓氏词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public static Hit matchInSurnameDict(char[] charArray , int begin, int length){
		return singleton.surnameDict.match(charArray, begin, length);
	}		
	
//	/**
//	 * 
//	 * 在姓氏词典中匹配指定位置的char数组
//	 * （对传入的字串进行后缀匹配）
//	 * @param charArray
//	 * @param begin
//	 * @param end
//	 * @return
//	 */
//	public static boolean endsWithSurnameDict(char[] charArray , int begin, int length){
//		Hit hit = null;
//		for(int i = 1 ; i <= length ; i++){
//			hit = singleton._SurnameDict.match(charArray, begin + (length - i) , i);
//			if(hit.isMatch()){
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * 检索匹配量词词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public static Hit matchInQuantifierDict(char[] charArray , int begin, int length){
		return singleton.quantifierDict.match(charArray, begin, length);
	}
	
	/**
	 * 检索匹配在后缀词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public static Hit matchInSuffixDict(char[] charArray , int begin, int length){
		return singleton.suffixDict.match(charArray, begin, length);
	}
	
//	/**
//	 * 在后缀词典中匹配指定位置的char数组
//	 * （对传入的字串进行前缀匹配）
//	 * @param charArray
//	 * @param begin
//	 * @param end
//	 * @return
//	 */
//	public static boolean startsWithSuffixDict(char[] charArray , int begin, int length){
//		Hit hit = null;
//		for(int i = 1 ; i <= length ; i++){
//			hit = singleton._SuffixDict.match(charArray, begin , i);
//			if(hit.isMatch()){
//				return true;
//			}else if(hit.isUnmatch()){
//				return false;
//			}
//		}
//		return false;
//	}
	
	/**
	 * 检索匹配介词、副词词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return  Hit 匹配结果描述
	 */
	public static Hit matchInPrepDict(char[] charArray , int begin, int length){
		return singleton.prepDict.match(charArray, begin, length);
	}
	
	/**
	 * 判断是否是停止词
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return boolean
	 */
	public static boolean isStopWord(char[] charArray , int begin, int length){			
		return singleton.stopWords.match(charArray, begin, length).isMatch();
	}	
}

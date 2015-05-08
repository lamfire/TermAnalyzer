package com.lamfire.analyzer.freq;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.lamfire.analyzer.Segmentation;

/**
 * 词频分析器
 * @author lamfire
 * @date 2013-3-15
 */

public class TermFreqAnalyzer {
	private Map<String, Integer> lexemes = null;
	private String content;
	private List<TermFreq> sortedList;
    private List<String> _words;
	
	public TermFreqAnalyzer(String content)throws IOException{
		this.content = content;
	}

	/**
	 * 获得分词频
	 * @throws java.io.IOException
	 */
	public synchronized Map<String, Integer> getTermCount() throws IOException{
		if(lexemes != null){
			return lexemes;
		}
		lexemes = new TreeMap<String, Integer>();
		StringReader reader = new StringReader(this.content);
		Segmentation seg = new Segmentation(reader,true);
        _words = seg.split();
        for(String word : _words){
            Integer count = lexemes.get(word);
            if(count == null){
                lexemes.put(word,1);
            }else{
                lexemes.put(word,count +1);
            }
        }

		return lexemes;
	}

	/**
	 * 获得指定词的词频
	 * @param lexeme
	 * @return
	 * @throws java.io.IOException
	 */
	public long getTermCount(String lexeme)throws IOException{
		Integer count = getTermCount().get(lexeme);
		if(count == null){
			return 0;
		}
		return count;
	}
	
	/**
	 * 获得排序的词频
	 * @return
	 * @throws java.io.IOException
	 */
	public synchronized List<TermFreq> getTermFreqs()throws IOException{
		if(sortedList != null){
			return sortedList;
		}
		sortedList = new LinkedList<TermFreq>();
		Map<String,Integer> map = getTermCount();
        float length = _words.size();
		for(Map.Entry<String, Integer> entry : map.entrySet()){
            Integer count = entry.getValue();
			TermFreq freq = new TermFreq(entry.getKey(),count,count / length );
			sortedList.add(freq);
		}
		Collections.sort(sortedList);
		return sortedList;
	}
}

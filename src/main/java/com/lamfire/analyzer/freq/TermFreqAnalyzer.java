package com.lamfire.analyzer.freq;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.lamfire.analyzer.Segmentation;

/**
 * 词频分析器
 * @author lamfire
 * @date 2013-3-15
 */

public class TermFreqAnalyzer {
	private Map<String, Integer> termCountMap = null;
	private String content;
    private List<String> _words;
	
	public TermFreqAnalyzer(String content)throws IOException{
		this.content = content;
	}

	/**
	 * 获得分词频
	 * @throws java.io.IOException
	 */
	public synchronized Map<String, Integer> getTermCountAsMap() throws IOException{
		if(termCountMap != null){
			return termCountMap;
		}
		termCountMap = new TreeMap<String, Integer>();
		StringReader reader = new StringReader(this.content);
		Segmentation seg = new Segmentation(reader,true);
        _words = seg.split();
        for(String word : _words){
            countTerm(word);
        }

		return termCountMap;
	}

    private synchronized void countTerm(String term){
        Integer count = termCountMap.get(term);
        if(count == null){
            count = 1;
        }else{
            count++;
        }
        termCountMap.put(term, count);
    }

	/**
	 * 获得指定词的词频
	 * @param lexeme
	 * @return
	 * @throws java.io.IOException
	 */
	public long getTermCount(String lexeme)throws IOException{
		Integer count = getTermCountAsMap().get(lexeme);
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
	public synchronized List<TermFreq> getSortedTermFreqs()throws IOException{
        List<TermFreq> sortedList = new LinkedList<TermFreq>();
		Map<String,Integer> map = getTermCountAsMap();
        float length = _words.size();
		for(Map.Entry<String, Integer> entry : map.entrySet()){
            Integer count = entry.getValue();
			TermFreq freq = new TermFreq(entry.getKey(),count,count / length );
			sortedList.add(freq);
		}
		Collections.sort(sortedList);
		return sortedList;
	}

    public synchronized Map<String,Float>  getTermFreqs()throws IOException{
        Map<String,Float> map  = new HashMap<String, Float>();
        float length = _words.size();
        for(Map.Entry<String, Integer> entry : getTermCountAsMap().entrySet()){
            Integer count = entry.getValue();
            Float freq = (count.floatValue() / length );
            map.put(entry.getKey(),freq);
        }
        return map;
    }
}

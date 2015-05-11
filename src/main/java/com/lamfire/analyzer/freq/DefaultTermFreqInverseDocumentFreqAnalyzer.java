package com.lamfire.analyzer.freq;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-5-11
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTermFreqInverseDocumentFreqAnalyzer extends TermFreqInverseDocumentFreqBaseAnalyzer{
    private final Map<String, Integer> termCountMap = new HashMap<String, Integer>();

    @Override
    void setTermCount(String term, int count) {
        termCountMap.put(term,count);
    }

    int getTermCount(String term){
        Integer count =  termCountMap.get(term);
        return count == null? 0 : count;
    }

    @Override
    int getDocumentNumber() {
        return super.getAddDocumentCount();
    }
}

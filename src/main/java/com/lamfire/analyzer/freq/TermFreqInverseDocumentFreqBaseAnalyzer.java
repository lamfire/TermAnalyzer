package com.lamfire.analyzer.freq;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-5-11
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public abstract class TermFreqInverseDocumentFreqBaseAnalyzer implements TermFreqInverseDocumentFreqAnalyzer{
    private int addDocumentCount = 0;

    private synchronized void incrementTermCount(String term){
        incrementTermCount(term,1);
    }

    protected abstract void setTermCount(String term,int count);

    protected abstract int getTermCount(String term);

    protected abstract int getDocumentNumber();

    synchronized void incrementTermCount(String term,int increment){
        Integer count = getTermCount(term);
        if(count == null){
            count = increment;
        }else{
            count = count + increment;
        }
        setTermCount(term, count);
    }

    public void addDocument(String document){
        TermFreqAnalyzer tfAnalyzer = new TermFreqAnalyzer(document);
        Map<String, Integer> tcMap = tfAnalyzer.getTermCountAsMap();
        for(Map.Entry<String,Integer> e : tcMap.entrySet()){
            String term = e.getKey();
            Integer count = e.getValue();
            incrementTermCount(term,count);
        }
        this.addDocumentCount ++;
    }

    int getAddDocumentCount(){
        return this.addDocumentCount;
    }

    public Float getInverseDocumentFreq(String term){
        Integer count = getTermCount(term);
        if(count == null || count ==0){
            return 0f;
        }
        Float freq = (float)Math.log(getDocumentNumber() / count.floatValue());
        return freq;
    }



    public Map<String,Float> getTermFreqInverseDocumentFreq(String document){
        TermFreqAnalyzer tfAnalyzer = new TermFreqAnalyzer(document);
        Map<String,Float> map = tfAnalyzer.getTermFreqs();
        for(Map.Entry<String,Float> e : map.entrySet()){
            Float freq = (e.getValue() * getInverseDocumentFreq(e.getKey()));
            e.setValue(freq);
        }
        return map;
    }

    public List<TermFreq> getSortedTermFreqInverseDocumentFreq(String document){
        TermFreqAnalyzer tfAnalyzer = new TermFreqAnalyzer(document);
        List<TermFreq> list = tfAnalyzer.getSortedTermFreqs();
        for(TermFreq tf : list){
            Float idf = getInverseDocumentFreq(tf.getTerm());
            if(idf != null){
                tf.setFrequency(tf.getFrequency() * idf);
            }else{
                tf.setFrequency(1.0f);
            }
        }
        Collections.sort(list);
        return list;
    }
}

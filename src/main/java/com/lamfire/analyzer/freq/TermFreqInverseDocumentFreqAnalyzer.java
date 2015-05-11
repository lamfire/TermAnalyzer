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
public class TermFreqInverseDocumentFreqAnalyzer {

    private final Map<String, Integer> termCountMap = new HashMap<String, Integer>();
    private int documentNumber = 0;

    private synchronized void countTerm(String term){
        countTerm(term,1);
    }

    private synchronized void countTerm(String term,int increment){
        Integer count = termCountMap.get(term);
        if(count == null){
            count = increment;
        }else{
            count = count + increment;
        }
        termCountMap.put(term, count);
    }

    public void addDocument(String document) throws IOException {
        TermFreqAnalyzer tfAnalyzer = new TermFreqAnalyzer(document);
        Map<String, Integer> tcMap = tfAnalyzer.getTermCountAsMap();
        for(Map.Entry<String,Integer> e : tcMap.entrySet()){
            String term = e.getKey();
            Integer count = e.getValue();
            countTerm(term,count);
        }
        this.documentNumber ++;
    }

    public synchronized Map<String, Integer> getTermCountAsMap(){
        return this.termCountMap;
    }

    public List<TermFreq> getSortedInverseDocumentFreq(){
        LinkedList<TermFreq> sortedList = new LinkedList<TermFreq>();
        for(Map.Entry<String, Integer> entry : termCountMap.entrySet()){
            Integer count = entry.getValue();
            TermFreq freq = new TermFreq(entry.getKey(),count,(float)Math.log(documentNumber / count.floatValue()));
            System.out.println(freq);
            sortedList.add(freq);
        }
        Collections.sort(sortedList);
        return sortedList;
    }

    public Map<String,Float> getInverseDocumentFreq(){
        Map<String,Float> map  = new HashMap<String, Float>();
        for(Map.Entry<String, Integer> entry : termCountMap.entrySet()){
            Integer count = entry.getValue();
            Float freq = (float)Math.log(documentNumber / count.floatValue());
            map.put(entry.getKey(),freq);
        }
        return map;
    }

    public Map<String,Float> getTermFreqInverseDocumentFreq(String document) throws IOException {
        TermFreqAnalyzer tfAnalyzer = new TermFreqAnalyzer(document);
        Map<String,Float> idfMap = getInverseDocumentFreq();
        Map<String,Float> map = tfAnalyzer.getTermFreqs();
        for(Map.Entry<String,Float> e : map.entrySet()){
            Float freq = (e.getValue() * idfMap.get(e.getKey()));
            e.setValue(freq);
        }
        return map;
    }

    public List<TermFreq> getSortedTermFreqInverseDocumentFreq(String document) throws IOException {
        TermFreqAnalyzer tfAnalyzer = new TermFreqAnalyzer(document);
        Map<String,Float> idfMap = getInverseDocumentFreq();
        List<TermFreq> list = tfAnalyzer.getSortedTermFreqs();
        for(TermFreq tf : list){
            Float idf = idfMap.get(tf.getTerm());
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

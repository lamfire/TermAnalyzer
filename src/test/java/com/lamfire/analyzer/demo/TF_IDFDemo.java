package com.lamfire.analyzer.demo;

import com.lamfire.analyzer.freq.DefaultTermFreqInverseDocumentFreqAnalyzer;
import com.lamfire.analyzer.freq.TermFreqInverseDocumentFreqAnalyzer;
import com.lamfire.analyzer.freq.TermFreq;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-5-11
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
public class TF_IDFDemo {
    public static void main(String[] args) throws IOException {
        TermFreqInverseDocumentFreqAnalyzer idfa = new DefaultTermFreqInverseDocumentFreqAnalyzer();
        String s1 = "小林今天开开心心的去上班,却不小心迟到了";
        String s2= "今天是小明的结婚记念日,也是小林的生日";
        String s3= "小林在深圳工作,小明在广州工作,小林工作不努力";

        idfa.addDocument(s1);
        idfa.addDocument(s2);
        idfa.addDocument(s3);

        List<TermFreq>  tfidf = idfa.getSortedTermFreqInverseDocumentFreq(s3);
        for(TermFreq tf : tfidf){
            System.out.println(tf);
        }
    }
}

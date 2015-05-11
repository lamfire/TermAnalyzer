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
public interface TermFreqInverseDocumentFreqAnalyzer {

    public void addDocument(String document);

    public Float getInverseDocumentFreq(String term);

    public Map<String,Float> getTermFreqInverseDocumentFreq(String document);

    public List<TermFreq> getSortedTermFreqInverseDocumentFreq(String document);
}

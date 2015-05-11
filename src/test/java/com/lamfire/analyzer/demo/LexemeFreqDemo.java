package com.lamfire.analyzer.demo;

import java.io.IOException;
import java.util.List;

import com.lamfire.analyzer.freq.TermFreq;
import com.lamfire.analyzer.freq.TermFreqAnalyzer;

public class LexemeFreqDemo {

	public static void main(String[] args) throws IOException {
		TermFreqAnalyzer freq = new TermFreqAnalyzer(Datas.ContextString);
		List<TermFreq> list = freq.getSortedTermFreqs();
		for(TermFreq f : list){
			System.out.println(f);
		}
	}
}
                                                                                                                                              ;
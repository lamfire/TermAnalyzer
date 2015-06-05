package com.lamfire.analyzer.demo;

import com.lamfire.analyzer.TermUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-6-5
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class TermUtilsTest {

    public static void main(String[] args) {
        List<String> terms = TermUtils.maxLengthSegment(Datas.ContextString, 8);

        List<String> terms2 = TermUtils.maxLengthSegmentWithRevers(Datas.ContextString, 8);


        System.out.println("----------------------------");
        int len = terms.size();
        if(len > terms2.size()){
            len = terms2.size();
        }

        for(int i = 0; i< terms.size() ; i++){
            System.out.println(terms.get(i) +" \t\t\t\t\t\t\t\t"+ terms2.get(i));
        }
    }
}

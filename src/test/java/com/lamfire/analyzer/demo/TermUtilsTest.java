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
        List<String> terms = TermUtils.maxLengthSegmentWithRevers(Datas.ContextString, 8);

        Collections.reverse(terms);
        System.out.println("----------------------------");
        for(String s:terms){
            System.out.println(s);
        }
    }
}

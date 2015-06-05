package com.lamfire.analyzer;

import com.lamfire.analyzer.dic.Dictionary;
import com.lamfire.analyzer.dic.Hit;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-5-19
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class TermUtils {

    public static List<String> maxLengthSegment(String body,final int maxLength){
        List<String> _result = new LinkedList<String>();
        char[] array = (body).toCharArray();
        int startIndex = 0;
        while(startIndex < array.length){
            int termLength = maxLength;
            if(startIndex + maxLength > body.length()){
                termLength = body.length() - startIndex;
            }
            for(int i=termLength;i>0;i--){
                Hit hit = Dictionary.matchInMainDict(array, startIndex, i);
                if(hit.isMatch()){
                    String term = new String(array,startIndex,i);
                    _result.add(term);
                    startIndex+= i;
                    break;
                }else if(i == 1){
                    String term = new String(array,startIndex,i);
                    _result.add(term);
                    startIndex++;
                }
            }
        }
        return _result;
    }

    public static List<String> maxLengthSegmentWithRevers(String body,final int maxLength){
        List<String> _result = new LinkedList<String>();
        char[] array = (body).toCharArray();
        int startIndex = array.length ;
        while(startIndex > 0){
            int termLength = maxLength;
            if(startIndex - maxLength < 0){
                termLength = startIndex;
            }
            for(int i=termLength;i> 0;i--){
                Hit hit = Dictionary.matchInMainDict(array, startIndex - i, i);
                //String t = new String(array,startIndex - i,i);
                //System.out.println(t);
                if(hit.isMatch()){
                    String term = new String(array,startIndex - i,i);
                    _result.add(term);
                    startIndex -= i;
                    break;
                }else if(i == 1){
                    String term = new String(array,startIndex - i,1);
                    _result.add(term);
                    startIndex--;
                }
            }
        }
        return _result;
    }
}

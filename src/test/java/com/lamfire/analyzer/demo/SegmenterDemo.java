/**
 * 
 */
package com.lamfire.analyzer.demo;

import java.io.IOException;
import java.io.StringReader;

import com.lamfire.analyzer.Lexeme;
import com.lamfire.analyzer.Segmentation;
import com.lamfire.analyzer.dic.Dictionary;
import com.lamfire.analyzer.dic.Hit;

public class SegmenterDemo {

	public static void main(String[] args) throws IOException{
		
		char[] array = (Datas.ContextString).toCharArray();

        Dictionary dic = Dictionary.getInstance();

        int startIndex = 0;

        while(startIndex < array.length){
            for(int i=7;i>0;i--){
                Hit hit = Dictionary.matchInMainDict(array, startIndex, i);
                if(hit.isMatch()){
                    System.out.println(new String(array,startIndex,i));
                    startIndex+= i;
                    break;
                }else if(i == 1){



                    hit = Dictionary.matchInSurnameDict(array, startIndex, i);
                    if(hit.isMatch()){
                        System.out.println(new String(array,startIndex,i) + " ------------------ surname");
                    }else{
                        System.out.println(new String(array,startIndex,i));
                    }

                    startIndex++;
                }
            }
        }

	}
	
}

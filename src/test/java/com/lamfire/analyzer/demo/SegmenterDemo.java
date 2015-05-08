/**
 * 
 */
package com.lamfire.analyzer.demo;

import java.io.IOException;
import java.io.StringReader;

import com.lamfire.analyzer.Lexeme;
import com.lamfire.analyzer.Segmentation;

public class SegmenterDemo {

	public static void main(String[] args) throws IOException{
		
		StringReader reader = new StringReader("在广州市新塘镇有个自称叫hayash的华人算命先生,今年28岁.");
		//StringReader reader = new StringReader(testString);
		
		Segmentation seg = new Segmentation(reader, true);

		long begin = System.currentTimeMillis();   
		Lexeme lex = seg.next();
		while(lex != null){
			System.out.println(lex.getLexemeText() +"\t\t\t"+lex.getLexemeTypeName());
			lex = seg.next();
		}

        long end = System.currentTimeMillis();
        System.out.println("耗时 : " + (end - begin) + "ms");

	}
	
}

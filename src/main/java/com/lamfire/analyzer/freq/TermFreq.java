package com.lamfire.analyzer.freq;

/**
 * 词频
 * @author lamfire
 * @date 2013-3-15
 */

public class TermFreq implements Comparable<TermFreq>{
	private String lexeme;
	private int count;
    private float tf;
	
	public TermFreq(String lexeme, int freq,float tf) {
		super();
		this.lexeme = lexeme;
		this.count = freq;
        this.tf = tf;
	}
	
	

	public String getLexeme() {
		return lexeme;
	}



	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}

    public float getTf() {
        return tf;
    }

    public void setTf(float tf) {
        this.tf = tf;
    }

    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TermFreq){
			TermFreq o = (TermFreq)obj;
			return this.lexeme.equals(o.lexeme) && this.count == o.count;
		}
		return false;
	}



	@Override
	public int hashCode() {
		return this.count * this.lexeme.hashCode();
	}


	@Override
	public int compareTo(TermFreq o) {
		if(this.count > o.getCount()){
			return 1;
		}
		if(this.count < o.getCount()){
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return this.lexeme +":" + count +"/" +String.format("%.12f",tf);
	}

}

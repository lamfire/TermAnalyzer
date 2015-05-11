package com.lamfire.analyzer.freq;

/**
 * 词频
 * @author lamfire
 * @date 2013-3-15
 */

public class TermFreq implements Comparable<TermFreq>{
	private String term;
	private int count;
    private float frequency;

    public TermFreq() {

    }
	
	public TermFreq(String lexeme, int freq,float frequency) {
		super();
		this.term = lexeme;
		this.count = freq;
        this.frequency = frequency;
	}
	
	

	public String getTerm() {
		return term;
	}



	public void setTerm(String term) {
		this.term = term;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TermFreq){
			TermFreq o = (TermFreq)obj;
			return this.term.equals(o.term) && this.count == o.count;
		}
		return false;
	}



	@Override
	public int hashCode() {
		return this.count * this.term.hashCode();
	}


	@Override
	public int compareTo(TermFreq o) {
		if(this.frequency > o.getFrequency()){
			return 1;
		}
		if(this.frequency < o.getFrequency()){
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "term="+this.term +",count=" + count +",frequency=" +String.format("%.12f", frequency);
	}

}

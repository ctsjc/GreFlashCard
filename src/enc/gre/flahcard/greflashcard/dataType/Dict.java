package enc.gre.flahcard.greflashcard.dataType;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.text.format.DateFormat;

public class Dict {
	String word;
	String meaning;
	int hitCount;
	int missCount;
	Calendar accessTime=GregorianCalendar.getInstance();;
	public Dict(String word, String meaning) {
		super();
		this.word = word;
		this.meaning = meaning;
	}

	public String getMeaning() {
		return meaning;
	}
	public String getWord(){
		return word;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		
		this.hitCount = hitCount;
	}

	public int getMissCount() {
		return missCount;
	}

	public void setMissCount(int missCount) {
		this.missCount = missCount;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public Calendar getAccessTime() {
		return accessTime;
	}
	
	public void setAccessTime() {
		this.accessTime = Calendar.getInstance();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Dict){
			Dict d2 = (Dict)o;
			if(this.word == d2.word)
				return true;
			else
				return false;
		}
		return super.equals(o);
	}
	@Override
	public String toString() {
		return "Dict [word=" + word + ", meaning=" + meaning + ", hitCount="
				+ hitCount + ", missCount=" + missCount + ", accessTime="
				+ DateFormat.format("MM/dd/yy h:mmaa", accessTime) + "]";
	}
	
}

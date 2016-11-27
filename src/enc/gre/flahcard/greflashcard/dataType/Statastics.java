package enc.gre.flahcard.greflashcard.dataType;

import java.util.Calendar;

public class Statastics {
	Calendar date;
	int newWord;
	int oldWord;
	long time;
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public int getNewWord() {
		return newWord;
	}
	public void setNewWord(int newWord) {
		this.newWord = newWord;
	}
	public int getOldWord() {
		return oldWord;
	}
	public void setOldWord(int oldWord) {
		this.oldWord = oldWord;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}

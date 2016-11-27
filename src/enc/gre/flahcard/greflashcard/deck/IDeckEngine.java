package enc.gre.flahcard.greflashcard.deck;

/***
 * Interface defines the method that a DeckEngine Should implement
 * @author jiten
 *
 */
public interface IDeckEngine {
	
	/***
	 * This method is used to read content from file
	 * @param filename
	 */
	void populateFile(String filename);
	
	/***
	 * With this method we get a next word from current dictionary
	 * while getting next word
	 * you should increment the index
	 * @return
	 */
	String getWord();
	
	/***
	 * it returns the word described by index
	 * and set the dictionary index to parameter
	 * @param index
	 * @return
	 */
	String getWord(int index);
	String[] getMeaning();
	String[] getSentence();
	
	/***
	 * This method returns you the meaning without incrementing the index
	 * @return
	 */
	String getCurrentWord();
	
	/***
	 * This method returns you the meaning
	 * @return
	 */
	String[] getCurrentMeaning();
	
	
	void changeDeck(int index);
	void setDeck(String name);
}

package enc.gre.flahcard.greflashcard.deck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;
import enc.gre.flahcard.greflashcard.R;
import enc.gre.flahcard.greflashcard.ai.AIEngine;
import enc.gre.flahcard.greflashcard.dataType.Dict;

/****
 * Deck Engine is responsible for handling the deck logic
 * 
 * the Deck is collection of words
 * when a file is read it is come into the system as a <index,Dict> format
 * every file is considered as a one Deck
 * 
 * So Deck essentially contains the name, and List of Dict
 * 
 * Now user can  create a another deck
 * @author jiten
 *
 */
public class DeckEngine {
	HashMap<String,ArrayList<Dict>> decks=new HashMap<String, ArrayList<Dict>>();
	static String fastmodeDeckname;
	private static String currDeckKey;
	String key=null;
	int index=0;
	static boolean showWord=true;
	private static DeckEngine deckEngine;

	private DeckEngine(){

	}
	public static DeckEngine getInstance(){
		if(deckEngine == null)
			deckEngine = new DeckEngine();
		return deckEngine;
	}
	/**
	 * Add a new Deck to the deck set
	 * */
	public void addNewDeck(String deckname){
		decks.put(deckname, new ArrayList<Dict>());
	}
	/**
	 *  populate deck from file
	 * */
	public void populatehashMap( File sdcard,Context context) throws Exception{
		//Reading txt file from sdcard 		
		

		File sourcefile = new File(sdcard,"GreFlashCards");
		if(sourcefile != null  ){
			if(!sourcefile.exists())
			{
				if(sourcefile.mkdirs()){
					File sampleFile = new File(sourcefile, "sampleFile.txt");
					sampleFile.createNewFile();
					InputStream in = context.getResources().openRawResource(R.drawable.wordlist);
					OutputStream out = new FileOutputStream(sampleFile); 
					byte[] buf1 = new byte[1024];
					int len;
					while ((len = in.read(buf1)) > 0){
						out.write(buf1, 0, len);
					}
					in.close();
					out.close();
				}
			}
		}
		File[] files = sourcefile.listFiles();
		for(File file : files){
		if(file != null & file.exists()){ 			
			ArrayList<Dict> dictionary=new ArrayList<Dict>();
			BufferedReader reader=null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String line=null;
				String key=null;
				String value=null;
				while((line = reader.readLine())!=null ){
					StringTokenizer stringTokenizer = new StringTokenizer(line, "=");
					boolean flag=true;
					while( stringTokenizer.hasMoreElements()){
						if(flag){
							key=stringTokenizer.nextElement().toString();
							flag=false;
						}else{
							value=stringTokenizer.nextElement().toString();
						}//end of else
					}//end of while

					dictionary.add(new Dict(key, value));		
					decks.put(file.getName(), dictionary);
					setCurrDeckKey(file.getName());
				}//end of while
			}
			finally{
				if(reader !=null)
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}//end of finally
		}//end of file
		}//end of for
	}//end of populateHashMap


	/**
	 *  add a new word to the selected deck
	 * */
	public void addNewWordtoDeck(String deckname,String word,ArrayList<String>meaning, ArrayList<String> sentence){
		if(decks.containsKey(deckname)){
			ArrayList<Dict> dictionary=decks.get(deckname);
			if(dictionary==null){
				dictionary=new ArrayList<Dict>();
			}//end of dictionary null
			dictionary.add(new Dict(word, meaning.get(0)));
		}//end of if
	}
	/**
	 *  add a new word to the selected deck
	 * */
	public void addNewWordtoDeck(String deckname,String word,String meaning, String sentence){

		if(decks.containsKey(deckname)){
			ArrayList<Dict> dictionary=decks.get(deckname);
			if(dictionary==null){
				dictionary=new ArrayList<Dict>();
			}//end of dictionary null
			Dict dict = new Dict(word, meaning);
			addNewWordtoDeck(deckname, dict);
		}//end of if
	}

	/**
	 *  add a new word to the selected deck
	 * */
	public void addNewWordtoDeck(String deckname,Dict dict){
		if(decks.containsKey(deckname)){
			ArrayList<Dict> dictionary=decks.get(deckname);
			if(dictionary==null){
				dictionary=new ArrayList<Dict>();
			}//end of dictionary null
			if(!dictionary.contains(dict))
			dictionary.add(dict);
			decks.put(deckname, dictionary);
		}//end of if
	}
	/**
	 * delete word from selected deck
	 * */
	public void deleteWordFromDeck(String deckname,String word){
		if(decks.containsKey(deckname)){
			ArrayList<Dict> dictionary=decks.get(deckname);
			if(dictionary!=null && dictionary.contains(word)){
				dictionary.remove(word);
			}//end of if
		}//end of if
	}//endo of function

	/**
	 *  delete the deck from set of decks
	 * */
	public void deleteDeck(String deckname){
		if(decks.containsKey(deckname)){
			decks.remove(deckname);
		}//end of if
	}//end of method

	/**
	 * returns true : if currently word is being  displayed
	 * */
	public boolean isShowWord() {
		return DeckEngine.showWord;
	}
	/**
	 * returns the dictionary from the currently selected deck
	 * */
	public ArrayList<Dict> getDictionary() {
		if(decks.containsKey(getCurrDeckKey()))
			return decks.get(getCurrDeckKey());//dictionary;
		return null;
	}
	/**
	 * returns the word which is currently pointed out by the index
	 * this method do not change the position of  the index
	 * */
	public String currWord() {
		try{
			if(getDictionary() != null && getDictionary() .get(index) !=null)
				return getDictionary() .get(index).getWord();
		}catch(Exception e ){}
		return null;
	}//end of currWord

	/**
	 * returns the current meaning of the word
	 * **/
	public String currMeaning(){
		try{
			if(getDictionary() != null && getDictionary() .get(index) !=null)
				return getDictionary() .get(index).getMeaning();
		}catch(Exception e ){}
		return null;
	}//end of currWord

	/**
	 * if word is currently shown then by this method method will be shown and vice versa
	 * **/
	public String display() {
		Dict curWord = getDictionary() .get(index);
		if(showWord){
			showWord=true;
			return curWord.getWord();

		}else{ 
			showWord=false;
			return curWord.getMeaning();

		}
	}
	/**
	 * it display the word pointed by the index
	 * and set the flag
	 * **/
	public String displayWord( int index) {
		showWord=true;
		this.index = index;
		Dict curWord = getDictionary() .get(index);
		incrementHitCount();
		return curWord.getWord();	
	}

	public  void incrementHitCount(){
		Dict curWord = getDictionary() .get(index);
		int count = curWord.getHitCount()+1;
		curWord.setHitCount(count);
		curWord.setAccessTime();
		AIEngine.getInstance().tellAi(curWord);
		Log.e("Increment Word Count ", "Word Count is "+count);
	}
	
	/**+
	 * it displays the word and set the flag
	 * **/
	public String displayWord( ) {
		showWord=true;
		Dict curWord = getDictionary() .get(index);
		return curWord.getWord();	
	}
	/**
	 * returns the meaning of currently shown word and  set the showWord flag to false
	 * **/
	public String displayMeaning() {
		showWord=false;
		try{
			Dict curWord = getDictionary() .get(index);
			incrementMissCount();
			return  curWord.getMeaning();
		}catch (Exception e) {}
		return null;
	}
	/**
	 * @param curWord
	 */
	public void incrementMissCount() {
		Dict curWord = getDictionary() .get(index);
		int count = curWord.getMissCount()+1;
		curWord.setMissCount(count);
		AIEngine.getInstance().tellAi(curWord);
	}

	/**
	 * increments the index
	 * **/
	public void incrementIndex(){
		if( index == (getDictionary() .size()-1))
			return;
		index++;
	}

	public void decrementIndex(){
		if( index == 0)
			return;
		index--;
	}
	/**
	 * this method will change the deck
	 * set the index to o
	 * and set the flag
	 * **/
	public void changeDeck(String filename){
		index=0;
		showWord=true;
		setCurrDeckKey(filename);
		if(!decks.containsKey(getCurrDeckKey()))
			Log.e("Excepion", "Key is not present");
	}

	/**
	 * it return the current decks name
	 * **/
	public String getCurrDeckKey() {
		return currDeckKey;
	}

	/**
	 * set the deck key
	 * **/
	public void setCurrDeckKey(String currDeckKey) {
		DeckEngine.currDeckKey = currDeckKey;
	}
	/**
	 * method returns the number of words in the deck
	 * */
	public int getWordCount(){
		return getDictionary().size();
	}

	/**
	 * method returns the number of words in the deck
	 * */
	public int getWordCount(String deckname){
		return decks.get(deckname).size();
	}
	/***
	 * method returns all the listed decks
	 * @return
	 */
	public List<String> getDeckname(){
		ArrayList<String> decknames=new ArrayList<String>();
		for(java.util.Map.Entry<String,ArrayList<Dict>> en : decks.entrySet()){
			decknames.add(en.getKey().toString());
		}//end of for
		return decknames;
	}//end of getDeckName

	/***
	 * method returns all the listed decks
	 * @return
	 */
	public List<String> getDecknameWithCount(){
		ArrayList<String> decknames=new ArrayList<String>();
		for(java.util.Map.Entry<String,ArrayList<Dict>> en : decks.entrySet()){
			decknames.add(en.getKey().toString()+" ( "+en.getValue().size()+" )");
		}//end of for
		return decknames;
	}//end of getDeckName


	private Dict getCurrDict(){
		return getDictionary() .get(index);
	}
	public int getHitCount() {
		return getCurrDict().getHitCount();
	}

	public int getMissCount() {
		return getCurrDict().getMissCount();
	}
	public static String getFastmodeDeckname() {
		return fastmodeDeckname;
	}
	public static void setFastmodeDeckname(String fastmodeDeckname) {
		DeckEngine.fastmodeDeckname = fastmodeDeckname;
	}
	
	public void removeWordFromDeck(String deckname, Dict dict){
		if(decks.containsKey(deckname)){
			ArrayList<Dict> dictionary=decks.get(deckname);
			if(dictionary.contains(dict))
				dictionary.remove(dict);
		}//end of if
	}
}//end of class
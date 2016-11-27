package enc.gre.flahcard.greflashcard.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.MotionEvent;
import android.widget.Toast;
import enc.gre.flahcard.greflashcard.MainActivity;
import enc.gre.flahcard.greflashcard.dataType.Dict;
import enc.gre.flahcard.greflashcard.deck.DeckActivity;
import enc.gre.flahcard.greflashcard.deck.DeckEngine;

public class MainActivityLogic {
	boolean fastMode=false;
	 
	public boolean isFastMode() {
		return fastMode;
	}


	public void setFastMode(boolean fastMode) {
		this.fastMode = fastMode;
	}
	public static final String SEARCH_ACTION   = "colordict.intent.action.SEARCH";
	public static final String EXTRA_QUERY    = "EXTRA_QUERY";
	public static final String EXTRA_FULLSCREEN = "EXTRA_FULLSCREEN";
	private File sdcard;
	HashMap<String,ArrayList<Dict>> decks=new HashMap<String, ArrayList<Dict>>();
	TextFormatter formatter=new TextFormatter();
	private static MainActivityLogic activityLogic;

	DeckEngine deckEngine=DeckEngine.getInstance();
	public boolean isShowWord() {
		return deckEngine.isShowWord();
	}
	
	
	public ArrayList<Dict> getDictionary() {
		return deckEngine.getDictionary();
	}
	
	private MainActivityLogic() {
		sdcard = Environment.getExternalStorageDirectory();
	}
	
	public static MainActivityLogic getInstance(){
		if(activityLogic == null)
			activityLogic=new MainActivityLogic();
		return activityLogic;
	}
	
	public void populatehashMap(MainActivity context) throws Exception{
		deckEngine.populatehashMap(sdcard, context);
	}//end of populateHashMap
	
	
	public String display() {
		return deckEngine.display();
	}

	public String displayWord( int index) {
		return deckEngine.displayWord(index);
	}
	
	public void showPrevWord(){
		deckEngine.decrementIndex();
		deckEngine.incrementHitCount();
		deckEngine. displayWord();
	}
	
	public void showNextWord(){
		deckEngine.incrementIndex();
		deckEngine.incrementHitCount();
		deckEngine. displayWord();
	}
	private void Axis_X(float velocityX, float velocityY, MainActivity activity){
		//If currently we are showing word 
		if(isShowWord()){
			if( velocityX > 0 ){// +ve X Left to Right
				
				showPrevWord();
			}else{
				showNextWord();
			}
			 return;
		}//end of showWord
		//if currently meaning is displayed 
		// Well now simply display word
		else{
			if( velocityX > 0 ){
				deckEngine.incrementIndex();
				deckEngine.displayMeaning();
				return ;
				
			}else{
				showColorDict(activity);
				deckEngine.displayMeaning();
				return ;
			}
		}
	}
	
	private void Axis_Y(float velocityX, float velocityY, MainActivity activity){
		// if currently word is display
		if(deckEngine.isShowWord()){
			if( velocityY > 0 ){// +ve Y Top  to Bottom
				if(isFastMode()){
					DeckActivity.addToDeck(DeckEngine.getFastmodeDeckname());
					Toast.makeText(activity,"Word Added into the "+DeckEngine.getFastmodeDeckname(),Toast.LENGTH_SHORT).show();
				}
				else{
				showAddtoDeck(activity);
				}
				 return;
			}else{
				// Axis.BOTTOM_TO_TOP
				deckEngine.displayMeaning();
				return ;
			}
		}//end of if
		else {// means currently we are displaying meaning
			if( velocityY > 0 ){
				deckEngine.displayWord();
				 return;
			}else{
				// Axis.BOTTOM_TO_TOP
				deckEngine.displayWord();
				 return;
			}//end of else
		}//end of else
	}
	
	public void displayLogic(MotionEvent e1, float velocityX, float velocityY, MainActivity activity) {
		System.out.println("\n=========================\n");
		// perform X
		if( Math.abs(velocityX) > Math.abs(velocityY) ){
			Axis_X(velocityX,velocityY,activity);
			return ;
		}//end of if 
		// else perform Y
		else{
			 Axis_Y(velocityX, velocityY, activity);
			return;
		}//end of else
	}//end of function
	public static boolean isIntentAvailable(MainActivity context, Intent intent) {
		 final PackageManager packageManager = context.getPackageManager();
		 List<?> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		 return list.size() > 0; 
		}
	public void showColorDict(MainActivity activity){
		Intent intent = new Intent(SEARCH_ACTION);
		intent.putExtra(EXTRA_QUERY, deckEngine.currWord()); //Search Query
		intent.putExtra(EXTRA_FULLSCREEN, false); //
		if(isIntentAvailable(activity, intent)){
		activity.startActivity(intent);
		}
		else
		{
			Toast.makeText(activity.getApplicationContext(), "ColorDict Dictionary not found\nDownload from app store\nLongman is prefered ", Toast.LENGTH_SHORT).show();
		}
	}//end of showColorDict
	
	public void showAddtoDeck(Activity activity){
		Intent intent = new Intent(activity, DeckActivity.class);
		activity.startActivity(intent);
	}//end of showAddtoDeck
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
}//end of class

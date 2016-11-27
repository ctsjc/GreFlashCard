package enc.gre.flahcard.greflashcard;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import enc.gre.flahcard.greflashcard.R.id;
import enc.gre.flahcard.greflashcard.ai.AIEngine;
import enc.gre.flahcard.greflashcard.alarm.AlarmActivity;
import enc.gre.flahcard.greflashcard.dataType.Dict;
import enc.gre.flahcard.greflashcard.deck.DeckEngine;
import enc.gre.flahcard.greflashcard.deck.DeckMgmtActivity;
import enc.gre.flahcard.greflashcard.internal.MainActivityLogic;
import enc.gre.flahcard.greflashcard.internal.TextFormatter;

public class MainActivity extends Activity implements OnGestureListener,OnDoubleTapListener, OnTouchListener{
	GestureDetector detector;	
	//	TextView textView;
	WebView webView;
	MainActivityLogic activityLogic=MainActivityLogic.getInstance();
	TextFormatter textFormatter = new TextFormatter();
	ListView listView ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("Flash Card");
		this.setTitleColor(Color.GRAY);
		detector = new GestureDetector(this,this);
		String keyname = DeckEngine.getInstance().getCurrDeckKey();
		if(keyname == null){
			try{
				activityLogic.populatehashMap(this);
			}catch (Exception e) {Toast.makeText(getApplicationContext(), "Wrong File Dude !!\n "+e.getMessage(), Toast.LENGTH_SHORT).show(); finish();
			}
		}
		webView=(WebView)findViewById(R.id.webView1);
		webView.setVerticalScrollBarEnabled(false);
		webView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				detector.onTouchEvent(event);
				return false;
			}
		});


		listView = (ListView) findViewById(R.id.mylist);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,	int position, long id) {
				activityLogic.displayWord(position);
				textFormatter.setText( webView);
				parent.setVisibility(View.GONE);
			}//end of Set
		}); //end of list view listner

		textFormatter.setText( webView);
	}//end of onCreate


	public boolean onDown(MotionEvent e) {
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		activityLogic.displayLogic(e1, velocityX, velocityY,this);
		boolean show = activityLogic.isShowWord();
		textFormatter.setText( webView);
		Log.e("Activity", String.valueOf( show));
		return false;
	}


	public boolean onTouchEvent(MotionEvent me){ 
		this.detector.onTouchEvent(me);
		return true;
	}

	public void onLongPress(MotionEvent e) {
		listView.setVisibility(View.VISIBLE);
		ArrayList<String> values =new  ArrayList<String>();
		for(Dict  dict : activityLogic.getDictionary()){
			values.add(dict.getWord());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);
		listView.setAdapter(adapter); 
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onShowPress(MotionEvent e) {

	}

	public boolean onSingleTapUp(MotionEvent e) {
		activityLogic.display();
		textFormatter.setText( webView);
		return false;
	}

	public boolean onDoubleTap(MotionEvent e) {
		activityLogic.display();
		textFormatter.setText( webView);
		return false;
	}

	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}

	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent =null;
		switch (item.getItemId()) {
		case R.id.itmRemainder:
			intent = new Intent(this, AlarmActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.itmHelp:
			return true;
		case R.id.itmDeck:
			intent = new Intent(this, DeckMgmtActivity.class);
			this.startActivity(intent);

		case R.id.itmAi:
			AIEngine.getInstance().initialize();
			return true;
		case id.itExit:
			finish();
			return true;
		case id.itmIntel:
			activityLogic.setFastMode(true);
			return true;
		case id.itmPopulate:
			try {
				activityLogic.populatehashMap(this);
			} catch (Exception e) {				e.printStackTrace();		}
			return true;
		case id.itmAll:
			textFormatter.showTable( webView);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// handling the volumn keys 
	//==================
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
	//-----------------------------

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e("onKeyDown 2 ","Im Pressed "+event.getKeyCode());
		if(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN){
			Log.e("onKeyDown 2 ","Im Pressed KEYCODE_VOLUME_DOWN "+event.getKeyCode());
			activityLogic.showPrevWord();
			textFormatter.setText( webView);
			return true;
		}
		if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP){
			activityLogic.showNextWord();
			textFormatter.setText( webView);
			Log.e("onKeyDown 2 ","Im Pressed KEYCODE_VOLUME_UP "+event.getKeyCode());
			return true;
		}
		else 
			return false;
    }
}//end of class

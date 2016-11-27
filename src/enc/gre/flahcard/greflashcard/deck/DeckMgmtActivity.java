package enc.gre.flahcard.greflashcard.deck;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import enc.gre.flahcard.greflashcard.MainActivity;
import enc.gre.flahcard.greflashcard.R;

/***\
 * this is simple deck
 * you are adding it into the deck
 * */
public class DeckMgmtActivity extends ListActivity {
	DeckEngine deckEngine=DeckEngine.getInstance();
	String deckSelected;
	EditText  txtNewDeck;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deckmgmt);
		populateList();

		getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,		long arg3) {
				deckSelected =   getListView().getItemAtPosition(arg2).toString();
				int l = deckSelected.indexOf('(');
				deckSelected = deckSelected.substring(0, l).trim();
				txtNewDeck.setText(deckSelected);
			}
		});
		txtNewDeck = (EditText) findViewById(R.id.txtNewDeck);
		txtNewDeck.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				txtNewDeck.setText("");
			}
		});

		txtNewDeck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				txtNewDeck.setText("");				
			}
		});
	}//end of onCreate

	/**
	 * 
	 */
	private void populateList() {
		List<String> values = deckEngine.getDecknameWithCount();
		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	public void onClick(View view) {
		String deckName =null;
		@SuppressWarnings("unchecked")
		ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
		switch (view.getId()) {
		case R.id.btnAdd:
			deckName=txtNewDeck.getText().toString();
			if(deckName !=null && !deckName.equalsIgnoreCase("")){
				DeckEngine.setFastmodeDeckname(deckName);
				DeckEngine.getInstance().addNewDeck(deckName);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				populateList();
				adapter.notifyDataSetChanged();
			}
			break;

		case R.id.btnDelete:
			deckName=txtNewDeck.getText().toString();
			if(deckName !=null && !deckName.equalsIgnoreCase("")){
				DeckEngine.getInstance().deleteDeck(deckSelected);
				txtNewDeck.setText("");	
				populateList();
				adapter.notifyDataSetChanged();
			}
			break;

		case R.id.btnSet:
			deckName=txtNewDeck.getText().toString(); 
			int count = DeckEngine.getInstance().getWordCount(deckName);
			if(deckName !=null && !deckName.equalsIgnoreCase("") && count != 0){
				DeckEngine.getInstance().changeDeck(deckSelected);
				adapter.notifyDataSetChanged();
				this.startActivity(new Intent(this,MainActivity.class));
			}
			break;
		}//end of switch case
	}//end of onClick
}//end of class 

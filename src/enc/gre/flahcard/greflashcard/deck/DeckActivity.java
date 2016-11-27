package enc.gre.flahcard.greflashcard.deck;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import enc.gre.flahcard.greflashcard.MainActivity;
import enc.gre.flahcard.greflashcard.R;
import enc.gre.flahcard.greflashcard.R.id;

/***\
 * this is simple deck
 * you are adding it into the deck
 * */
public class DeckActivity extends ListActivity {
	RadioButton radioButton;
	Button btnOk;
	Button btnCancel;

	TextView textView;
	TextView txtStat;
	String deckSelected;
	boolean flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);

		btnOk = (Button) findViewById(id.btnOk);
		btnCancel=(Button) findViewById(id.btnCancel);
		radioButton = (RadioButton) findViewById(id.rbnOk);
		txtStat=(TextView) findViewById(id.txtStat);
		txtStat.setText("Seen :: "+ DeckEngine.getInstance().getHitCount()+"\nMiss :: "+ DeckEngine.getInstance().getMissCount());
		showList();

		getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,	int position, long id) {
				deckSelected =  getListView().getItemAtPosition(position).toString();
				int l = deckSelected.indexOf('(');
				deckSelected = deckSelected.substring(0, l).trim();
				textView.setText(deckSelected);
			}//end of Set
		}); //end of list view listner

		radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					flag= isChecked;
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if( flag && deckSelected != null ){
					addToDeck(deckSelected);
					goMain();
				}//end of if 
			}//end of method
		});

		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				goMain();				
			}
		});//end of btnCancel


		textView=(TextView) findViewById(id.txtNewDeck);
	}//end of on/create

	void goMain(){
		this.startActivity(new Intent(this, MainActivity.class));
	}

	void showList(){
		ArrayList<String> values =(ArrayList<String>) DeckEngine.getInstance().getDecknameWithCount();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);
		getListView().setAdapter(adapter); 
	}
	/**
	 * method add the word to the deck
	 * */
	public static void addToDeck(String deckname){
		DeckEngine deck = DeckEngine.getInstance();
		deck.addNewWordtoDeck(deckname,deck.currWord() , deck.currMeaning(), null);
	}//end of addToDeck
}//end of class

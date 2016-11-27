package enc.gre.flahcard.greflashcard.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import enc.gre.flahcard.greflashcard.MainActivity;
import enc.gre.flahcard.greflashcard.R;
import enc.gre.flahcard.greflashcard.R.id;

public class AlarmActivity extends Activity {
	AlarmManager am;
	TextView timeView;
	SeekBar timeBar;
	CheckBox checkBox;
	Button startButton;
	Button stopButton;
	int setAlarm;
	boolean flag;

	final static int AlarmId = 123456;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		timeView = (TextView) findViewById(id.textView1);
		timeBar = (SeekBar) findViewById(id.seekBar1);
		checkBox = (CheckBox) findViewById(id.checkBox1);
		startButton=(Button) findViewById(id.button1);
		timeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			public void onStartTrackingTouch(SeekBar seekBar) {


			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				int timeSpan = progress /15;
				// TODO Improve the logic u looser
				switch (timeSpan) {
				case 0:
					setAlarm =5;
					timeView.setText("Remainder set for 5 min");
					break;
				case 1:
					setAlarm =15;
					timeView.setText("Remainder set for 15 min");
					break;	
				case 2:
					setAlarm =30;
					timeView.setText("Remainder set for 30 min");
					break;
				case 3:
					setAlarm =60;
					timeView.setText("Remainder set for 1 hr");
					break;
				case 4:
					setAlarm =120;
					timeView.setText("Remainder set for 2 hrs");
					break;	
				case 5:
					setAlarm =240;
					timeView.setText("Remainder set for 4 hrs");
					break;
				case 6:
					setAlarm =360;
					timeView.setText("Remainder set for 6 hrs");
					break;	
				default:
					setAlarm=1000;
					break;
				}
			}
		});

		checkBox.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					flag=true;
				}
				else 
					flag=false;
			}
		});
		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(setAlarm != 0) {
					if(flag){
						setRepeatingAlarm();
						goMain();
					}//end pf if
					else{
						setOneTimeAlarm();
						goMain();
					}//end of else
				}//end of if
			}//end of method
		});//end of listner;


		stopButton = (Button) findViewById(id.button2);
		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				stopAlarm();
				goMain();
			}
		});// end of button stop event handler


	}

	public void goMain(){
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);

	}
	public void setOneTimeAlarm() {

		Intent intent = new Intent(this, TimeAlarm.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmId,
				intent, PendingIntent.FLAG_ONE_SHOT);

		am.set(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + (setAlarm* 60*1000), pendingIntent);
	}

	public void setRepeatingAlarm() {
		Intent intent = new Intent(this, TimeAlarm.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmId,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				(setAlarm* 60* 1000), pendingIntent);
	}

	public void stopAlarm(){

		Intent intent  = new Intent(this, TimeAlarm.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmId, intent, 0);
		am.cancel(pendingIntent);

	}//end of stopAlarm
}

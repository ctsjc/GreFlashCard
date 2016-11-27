package enc.gre.flahcard.greflashcard.alarm;

import enc.gre.flahcard.greflashcard.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeAlarm extends BroadcastReceiver {

	NotificationManager nm;

	@Override
	public void onReceive(Context context, Intent intent) {
		nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		CharSequence from = "Gre Study Reminder";
		CharSequence message = "One More Word for American Dream....";
		Intent intent2 = new Intent(context,AlarmActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent2, 0);
		// deprecated method
		
//		 Notification noti = new Notification.Builder(context)
//         .setContentTitle(from)
//         .setContentText(message)
//         .setAutoCancel(true)
//         .setDefaults(Notification.DEFAULT_SOUND)
//         .build();
		
		
		Notification notif = new Notification(R.drawable.ic_launcher,    "Flash Card"/*name that comes on the bar*/, System.currentTimeMillis());
		notif.setLatestEventInfo(context, from, message, contentIntent);
		notif.flags =  Notification.FLAG_AUTO_CANCEL;
		notif.defaults=Notification.DEFAULT_ALL ;
		
		nm.notify(1, notif);
		
	}
}


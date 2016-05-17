import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(context.getString("NOTIFICATION"));
        int id = intent.getIntExtra(context.getString("NOTIFICATION_ID"),0);
        notificationManager.notify(id, notification);
    }
}

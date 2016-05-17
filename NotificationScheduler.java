import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.example.Activities.MainActivity;
import com.example.Object;
import com.example.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationScheduler
{
    private Object object;
    private int id;
    private Context mContext;
    private AlarmManager alarmManager;

    public NotificationScheduler(Context mContext, Object object)
    {
        this.mContext = mContext;
        this.object = object;
        this.id = Integer.parseInt(object.getId());
        this.alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public Notification getDefaultNotification(int id, Object object)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(mContext, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.mipmap.ic_launcher_filmsfy)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("New Notification from Object")
                .setContentText(object.getTitle() + " " + "has a message")
                .setTicker("New Notification")
                .setContentIntent(pi);

        return builder.build();
    }

    public void scheduleNotification()
    {
        Notification notification = getDefaultNotification(id, film);
        Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
        notificationIntent.putExtra("NOTIFICATION_ID", id);
        notificationIntent.putExtra("NOTIFICATION", notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        SimpleDateFormat fmtIn = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = fmtIn.parse(object.getDate());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        long delay = date.getTime() - System.currentTimeMillis();
        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public void cancelNotification()
    {
        Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}

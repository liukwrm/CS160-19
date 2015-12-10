
        package group19.cs160.scoreradar;

        import android.app.Notification;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.NotificationCompat;
        import android.support.v4.app.NotificationManagerCompat;
        import android.util.Log;

        import com.google.android.gms.wearable.WearableListenerService;
        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.lang.reflect.Type;
        import java.util.ArrayList;

        import group19.cs160.scoreradar.R;
        import pl.tajchert.buswear.EventBus;

public class ListenerService extends WearableListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        // EventBus.getDefault().register(this);
    }

//    public void onEvent(String response) {
//        if (response.startsWith("WearUpdate")) {
//            Log.d("in listener-onEvent", "Here in event for notification");
//            response = response.substring(10);
//            Gson gson = new Gson();
//            Type listOfTemp = new TypeToken<ArrayList<Game>>() {
//            }.getType();
//            ArrayList<Game> gameList = gson.fromJson(response, listOfTemp);
//
//
//            int notificationId = 001;
//            // Build intent for notification content
//            Intent viewIntent = new Intent(this, MainActivity.class);
//            //viewIntent.putExtra("gameList", response);
//            PendingIntent viewPendingIntent =
//                    PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
//
//            NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle("New Score Update From ScoreRadar")
//                            .setContentText("Click here to see your games!")
//                            .setContentIntent(viewPendingIntent)
//                            .addAction(R.mipmap.ic_launcher,
//                                    "Opening ScoreRadar", viewPendingIntent);
//                            //.extend(new NotificationCompat.WearableExtender().setDisplayIntent(viewPendingIntent));
//
//            // Get an instance of the NotificationManager service
//            NotificationManagerCompat notificationManager =
//                    NotificationManagerCompat.from(this);
//
//            // Build the notification and issues it with notification manager.
//            notificationManager.notify(notificationId, notificationBuilder.build());
//        }
//    }

}
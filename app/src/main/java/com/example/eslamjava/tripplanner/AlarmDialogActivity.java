package com.example.eslamjava.tripplanner;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import dto.TripData;
import sqliteDB.DB_Adapter;

import static android.R.string.cancel;

public class AlarmDialogActivity extends AppCompatActivity {

    Button startBtn;
    Button laterBtn;
    Button cancelBtn;
    TextView tripNameTxtV;
    TextView fromTxtV;
    TextView toTxtV;
    int tripId;
    Ringtone r;
    DB_Adapter adabter;
    TripData trData;
    NotificationCompat.Builder builder;
    NotificationManager notificationManager;
    RemoteViews remoteViews;
    Context context;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_alarm_dialog);
        context = this;
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(this, notification);
        r.play();

        Vibrator vibrator = (Vibrator) this
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);
        adabter = new DB_Adapter(this);
        Intent i = getIntent();
        tripId = i.getIntExtra("tripId", 0);
        Log.e("edit", "alarm : " + tripId);

        trData = adabter.getCertaineTripData(tripId);
        Log.e("edit", "onClick: " + trData.getTripName());

        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_layout);
        startBtn = (Button) dialog.findViewById(R.id.startDialogtn);
        laterBtn = (Button) dialog.findViewById(R.id.laterDialogBtn);
        cancelBtn = (Button) dialog.findViewById(R.id.canceleDialogBtn);
        tripNameTxtV = (TextView) dialog.findViewById(R.id.dialogTripName);
        fromTxtV = (TextView) dialog.findViewById(R.id.dialogFrom);
        toTxtV = (TextView) dialog.findViewById(R.id.dialogTo);

        fromTxtV.setText(trData.getStartPointAddress());
        tripNameTxtV.setText(trData.getTripName());
        toTxtV.setText(trData.getDestinationAddress());

        //========================================================

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + trData.getStartPointLatitude() + "," + trData.getStartPointLongtude() + "&daddr=" + trData.getDestinationLatitude() + "," + trData.getDestinationLongtude()));
        Intent cancelIntent = new Intent(this, CancelActivity.class);
        cancelIntent.putExtra("id", trData.getId());


        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);

        remoteViews.setTextViewText(R.id.notificationTripNameTxtV, "Your trip " + trData.getTripName());

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_start, pendingIntent);

        final PendingIntent cancelpendingIntent = PendingIntent.getActivity(this, 0, cancelIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_cancel, cancelpendingIntent);

        //========================================================


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adabter.updateTripStatus(tripId, CreatNewTripActivity.CANCELED);
                r.stop();
                finish();

            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + trData.getStartPointLatitude() + "," + trData.getStartPointLongtude() + "&daddr=" + trData.getDestinationLatitude() + "," + trData.getDestinationLongtude()));
                adabter.updateTripStatus(tripId, CreatNewTripActivity.FINISHED);
                r.stop();
                startActivity(intent);

                finish();
            }
        });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.stop();

                builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.lancuh)
                        .setAutoCancel(true)
                        .setCustomContentView(remoteViews).mActions.clear();
                notificationManager.notify(trData.getId(), builder.build());
                //notificationManager.cancel(tripId);
                finish();


            }
        });

        dialog.setTitle("The Trip");
        dialog.show();
        //=======================================

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adabter.updateTripStatus(tripId, CreatNewTripActivity.CANCELED);
        r.stop();
        finish();
    }
}

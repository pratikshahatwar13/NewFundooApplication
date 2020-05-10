package com.bridgelabzs.add_note_page.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bridgelabzs.add_note_page.AddNoteActivity;

public class StartNotificationReceiver extends BroadcastReceiver {
    private String futureDate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        AddNoteActivity.scheduleJob(context,futureDate);
    }
}

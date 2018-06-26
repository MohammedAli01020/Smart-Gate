package com.example.mohamed.smartgate.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.mohamed.smartgate.R;
import com.example.mohamed.smartgate.data.MilitaryContract;

public final class SetupDataUtils {

    private static boolean sInitialized;
    @SuppressLint("StaticFieldLeak")
    synchronized public static void initialize(final Context context) {

        if (sInitialized) return;
        sInitialized = true;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

                if (!preferences.contains("anter1")) {
                    Intent intent = new Intent(context, UsersIntentService.class);
                    intent.setAction(UsersIntentService.ACTION_CREATE_USERS);
                    context.startService(intent);
                }

                return null;
            }
        }.execute();

    }


    static ContentValues[] getUsers() {

        ContentValues[] contentValues = new ContentValues[5];

        // First user
        ContentValues user1 = new ContentValues();
        user1.put(MilitaryContract.UsersEntry.COLUMN_NAME, "Mohamed Anter");
        user1.put(MilitaryContract.UsersEntry.COLUMN_USERNAME, "anter1");
        user1.put(MilitaryContract.UsersEntry.COLUMN_PHOTO, R.drawable.user_anter);
        contentValues[0] = user1;

        // Second user
        ContentValues user2 = new ContentValues();
        user2.put(MilitaryContract.UsersEntry.COLUMN_NAME, "Sabry Goda");
        user2.put(MilitaryContract.UsersEntry.COLUMN_USERNAME, "sabry1");
        user2.put(MilitaryContract.UsersEntry.COLUMN_PHOTO, R.drawable.user_sabry);
        contentValues[1] = user2;

        // Third user
        ContentValues user3 = new ContentValues();
        user3.put(MilitaryContract.UsersEntry.COLUMN_NAME, "Samir Farag");
        user3.put(MilitaryContract.UsersEntry.COLUMN_USERNAME, "smair1");
        user3.put(MilitaryContract.UsersEntry.COLUMN_PHOTO, R.drawable.user_samir);
        contentValues[2] = user3;

        // Forth user
        ContentValues user4 = new ContentValues();
        user4.put(MilitaryContract.UsersEntry.COLUMN_NAME, "Mohamed Khaled");
        user4.put(MilitaryContract.UsersEntry.COLUMN_USERNAME, "khaled1");
        user4.put(MilitaryContract.UsersEntry.COLUMN_PHOTO, R.drawable.user_khaled);
        contentValues[3] = user4;

        // Fifth user
        ContentValues user5 = new ContentValues();
        user5.put(MilitaryContract.UsersEntry.COLUMN_NAME, "Dr Diaa");
        user5.put(MilitaryContract.UsersEntry.COLUMN_USERNAME, "diaa1");
        user5.put(MilitaryContract.UsersEntry.COLUMN_PHOTO, R.drawable.user_diaa);
        contentValues[4] = user5;

        return contentValues;
    }

}

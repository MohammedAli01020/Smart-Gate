package com.example.mohamed.smartgate.utils;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.mohamed.smartgate.data.MilitaryProvider;


public class UsersIntentService extends IntentService {
    public static final String ACTION_CREATE_USERS = "action-create-users";

    public UsersIntentService() {
        super("UsersIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        String action = intent.getAction();

        assert action != null;
        if (action.equals(ACTION_CREATE_USERS)) {
            createUsers(this);
            initializeDatabase(this);
        }

    }

    synchronized private void initializeDatabase(Context context) {

        try {
            ContentValues[] values = SetupDataUtils.getUsers();
            context.getContentResolver().delete(MilitaryProvider.Users.USERS_URI,
                    null,
                    null);

            if (values != null && values.length != 0) {
                context.getContentResolver().bulkInsert(MilitaryProvider.Users.USERS_URI, values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized private void createUsers(Context context) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("anter1", "123");
        editor.putString("anter2", "321");

        editor.putString("sabry1", "123");
        editor.putString("sabry2", "321");

        editor.putString("samir1", "123");
        editor.putString("samir2", "321");

        editor.putString("khaled1", "123");
        editor.putString("khaled2", "321");

        editor.putString("diaa1", "123");
        editor.putString("diaa2", "321");

        editor.apply();
    }
}

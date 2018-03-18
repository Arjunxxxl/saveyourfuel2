package com.saveyourfuel.saveyourfuel.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by root on 18/3/18.
 */

public class instanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("firebaseService", "Refreshed token: " + refreshedToken);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString("firebaseToken", refreshedToken).apply();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}

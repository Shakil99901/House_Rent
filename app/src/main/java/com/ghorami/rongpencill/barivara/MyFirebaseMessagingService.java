package com.ghorami.rongpencill.barivara;


import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    // Implement the onNewToken method to handle token updates

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
    }

    private static final String TAG = "MyFirebaseMsgService";

}


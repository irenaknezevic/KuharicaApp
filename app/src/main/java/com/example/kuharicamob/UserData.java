package com.example.kuharicamob;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {

    public static String GetUserID(Context context){
        String userId = "";
        String MY_PREFERENCES = "USER_DATA";
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, context.MODE_PRIVATE);

        if(!sharedPreferences.getString("user_id", "emptyString").matches("emptyString"))
        {
            userId = sharedPreferences.getString("user_id", "emptyString");
        }

        return userId;
    }
}

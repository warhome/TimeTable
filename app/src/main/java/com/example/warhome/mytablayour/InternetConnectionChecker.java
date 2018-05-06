package com.example.warhome.mytablayour;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class InternetConnectionChecker {
    public static boolean checkConnection(@NonNull Context context) {
        return ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}

package es.uv.uvlive.utils;

import android.os.Build;


public class SystemUtils {

    private SystemUtils() {
        throw new RuntimeException("Class can't be instatiated");
    }

    public static String getVersionNumber(){
        return Build.VERSION.RELEASE;
    }

}

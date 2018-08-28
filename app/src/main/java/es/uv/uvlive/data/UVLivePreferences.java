package es.uv.uvlive.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.Map;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Merchant;
import es.uv.uvlive.session.Student;
import es.uv.uvlive.session.Teacher;
import es.uv.uvlive.session.User;

/**
 * Created by atraverf on 17/11/15.
 * Esta clase centraliza el acceso a las preferencias del dispositivo
 */
public class UVLivePreferences {

    private static final String BASE_KEY = "es.uv.uvlive.UVLiveSharedPreferences.";
    private static final String STRING_DEFAULT_VALUE = BASE_KEY + "DEFAULT_VALUE";
    private static final int INT_DEFAULT_VALUE = -1;
    private static final String PASSWORD = "Uv~2017.UVl1v€";

    private static final String USER_KEY = BASE_KEY + "USER_KEY";
    private static final String USER_TYPE_KEY = BASE_KEY + "USER_TYPE_KEY";
    private static final String PUSH_TOKEN_KEY = BASE_KEY + "PUSH_TOKEN_KEY";
    private static final String DEEKLINK_PARAMS_KEY = BASE_KEY + "DEEPLINK_PARAMS_KEY";

    private static final Gson GSON_CREATOR = new GsonBuilder().create();

    private static UVLivePreferences sUvLivePreferences;

    private final SharedPreferences mSharedPreferences;

    public static UVLivePreferences getInstance() {
        return getInstance(UVLiveApplication.getInstance());
    }

    public static UVLivePreferences getInstance(Context context) {
        if (sUvLivePreferences == null) {
            sUvLivePreferences = new UVLivePreferences(context);
        }
        return sUvLivePreferences;
    }

    public UVLivePreferences(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Map<String,?> getAll() {
        Map<String, ?> allEntries = mSharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        return allEntries;
    }

    public void saveUser(User user) {
        String serializedUser = GSON_CREATOR.toJson(user);
        try {
            String emcryptedUser = AESCrypt.encrypt(PASSWORD, serializedUser);
            saveString(USER_KEY,emcryptedUser);
            saveString(USER_TYPE_KEY,user.getClazz());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public User getUser() {
        String encryptedUser = getString(USER_KEY);
        if (encryptedUser != null) {
            try {
                String serializedUser = AESCrypt.decrypt(PASSWORD, encryptedUser);
                String type = getString(USER_TYPE_KEY);
                if (Student.class.getName().equals(type)) {
                    return GSON_CREATOR.fromJson(serializedUser, Student.class);
                } else if (Teacher.class.getName().equals(type)) {
                    return GSON_CREATOR.fromJson(serializedUser, Teacher.class);
                } else if (Admin.class.getName().equals(type)) {
                    return GSON_CREATOR.fromJson(serializedUser, Admin.class);
                } else {
                    return GSON_CREATOR.fromJson(serializedUser, Merchant.class);
                }
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveDeeplinkParams(int idConversation) {
        saveInt(DEEKLINK_PARAMS_KEY, idConversation);
    }

    public int getDeeplinkParams() {
        return getInt(DEEKLINK_PARAMS_KEY);
    }

    public void removeDeeplinkParams() {
        removeElement(DEEKLINK_PARAMS_KEY);
    }

    public void removeUser() {
        removeElement(USER_KEY);
    }

    public void savePushToken(String pushToken) {
        saveString(PUSH_TOKEN_KEY,pushToken);
    }

    @Nullable
    public String getPushToken() {
        return getString(PUSH_TOKEN_KEY);
    }

    public void removePushToken() {
        removeElement(PUSH_TOKEN_KEY);
    }


    /*
    * Private methods
    * */

    private void removeElement(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    @Nullable
    private String getString(String key) {
        String token = mSharedPreferences.getString(key, STRING_DEFAULT_VALUE);
        return STRING_DEFAULT_VALUE.equals(token) ? null : token;
    }

    private void saveString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    private int getInt(String key) {
        return mSharedPreferences.getInt(key, INT_DEFAULT_VALUE);
    }

    private void saveInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }
}

package es.uv.uvlive.data;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.session.User;

/**
 * Created by atraverf on 17/11/15.
 * Esta clase centraliza el acceso a las preferencias del dispositivo
 */
public class UVLivePreferences {

    private static final String BASE_KEY = "es.uv.uvlive.UVLiveSharedPreferences.";
    private static final String STRING_DEFAULT_VALUE = BASE_KEY + "DEFAULT_VALUE";
    private static final String PASSWORD = "Uv~2017.UVl1v€";
    private static final String USER_KEY = BASE_KEY + "USER_KEY";

    private static final Gson GSON_CREATOR = new GsonBuilder().create();

    private static UVLivePreferences sUvLivePreferences;

    private final SharedPreferences mSharedPreferences;

    public static UVLivePreferences getInstance() {
        if (sUvLivePreferences == null) {
            sUvLivePreferences = new UVLivePreferences();
        }
        return sUvLivePreferences;
    }

    public UVLivePreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(UVLiveApplication.getInstance());
    }

    public void saveUser(User user) {
        String serializedUser = GSON_CREATOR.toJson(user);
        try {
            String emcryptedUser = AESCrypt.encrypt(PASSWORD, serializedUser);
            saveString(USER_KEY,emcryptedUser);
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
                return GSON_CREATOR.fromJson(serializedUser, User.class);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void removeUser() {
        removeElement(USER_KEY);
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

}

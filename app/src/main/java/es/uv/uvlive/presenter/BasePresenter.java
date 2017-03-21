package es.uv.uvlive.presenter;

import android.content.SharedPreferences;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.session.User;

/**
 * Created by alextfos on 07/12/2016.
 */

public class BasePresenter {
    protected static User currentUser;

    protected void saveUser() {
        UVLivePreferences.getInstance().saveUser(currentUser);
    }

    protected void loadUser() {
        currentUser = UVLivePreferences.getInstance().getUser();
    }

}

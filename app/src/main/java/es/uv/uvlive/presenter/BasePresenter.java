package es.uv.uvlive.presenter;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.session.User;

public class BasePresenter {
    protected static User currentUser;

    protected void saveUser() {
        UVLivePreferences.getInstance().saveUser(currentUser);
    }

    protected void loadUser() {
        currentUser =  UVLivePreferences.getInstance().getUser();
    }

}

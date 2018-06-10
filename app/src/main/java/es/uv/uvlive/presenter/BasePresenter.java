package es.uv.uvlive.presenter;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.session.Session;
import es.uv.uvlive.session.User;

public abstract class BasePresenter {

    protected static void saveUser(LoginPresenter.LoginTypes loginType, String token, String ownerField) {
        getSession().loginSuccessfull(loginType, token, ownerField);
        UVLivePreferences.getInstance().saveUser(getSession().getCurrentUser());
    }

    protected static void loadUser() {
        getSession().loadUser();
    }

    protected static User getUser() {
        return Session.getInstance().getCurrentUser();
    }

    protected static Session getSession() {
        return Session.getInstance();
    }
}

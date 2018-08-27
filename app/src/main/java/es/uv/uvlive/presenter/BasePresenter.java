package es.uv.uvlive.presenter;

import android.support.annotation.CallSuper;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.session.Session;
import es.uv.uvlive.session.User;

public abstract class BasePresenter {

    private static List<BasePresenter> registeredPresenters = new ArrayList<>();

    public BasePresenter() {

    }

    protected static void saveUser(LoginPresenter.LoginTypes loginType, String token, String ownerField) {
        getSession().loginSuccessfull(loginType, token, ownerField);
        UVLivePreferences.getInstance().saveUser(getSession().getCurrentUser());
    }

    protected static void loadUser() {
        getSession().loadUser();
    }

    @CallSuper
    public void onStart() {
        registeredPresenters.add(this);
    }

    @CallSuper
    public void onStop() {
        registeredPresenters.remove(this);
    }

    protected void notifyConversationListReceived() {
        // TODO
    }

    protected void notifyMessagesReceived() {
        // TODO
    }

    protected static User getUser() {
        return Session.getInstance().getCurrentUser();
    }

    protected static Session getSession() {
        return Session.getInstance();
    }
}

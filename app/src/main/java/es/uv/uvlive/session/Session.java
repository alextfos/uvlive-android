package es.uv.uvlive.session;

import java.util.ArrayList;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.presenter.LoginPresenter;

public class Session {
    private static Session session;

    private User currentUser;

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    private Session() {
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void loginSuccessfull(LoginPresenter.LoginTypes loginType, String token, String ownerField) {
        switch (loginType) {
            case Student:
                currentUser = new Student();
                break;
            case Teacher:
                currentUser = new Teacher();
                break;
            case Admin:
                currentUser = new Admin();
                break;
            case Merchant:
                currentUser = new Merchant();
        }
        currentUser.setToken(token);
        currentUser.setOwnerName(ownerField);
    }

    public void logout() {
        currentUser = null;
    }

    public void loadUser() {
        currentUser = UVLivePreferences.getInstance().getUser();
    }
}

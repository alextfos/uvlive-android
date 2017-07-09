package es.uv.uvlive.presenter;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Merchant;
import es.uv.uvlive.session.Student;
import es.uv.uvlive.session.Teacher;
import es.uv.uvlive.session.User;

public class BasePresenter {
    protected static User currentUser;

    protected void saveUser() {
        UVLivePreferences.getInstance().saveUser(currentUser);
    }

    protected void loadUser() {
        currentUser = UVLivePreferences.getInstance().getUser();
    }
}

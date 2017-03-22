package es.uv.uvlive.presenter;

import android.content.SharedPreferences;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Student;
import es.uv.uvlive.session.Teacher;
import es.uv.uvlive.ui.actions.MainActions;

/**
 * Created by alextfos on 21/01/2017.
 */

public class MainPresenter extends BasePresenter {
    private MainActions mainActions;

    public MainPresenter(MainActions mainActions) {
        this.mainActions=mainActions;
    }

    public void loadSession() {
        //TODO if currentUser is null
        if (Admin.class.getName().equals(currentUser.getClazz())) {
            mainActions.loadAdminMenu();
        } else if (Student.class.getName().equals(currentUser.getClazz())
                || Teacher.class.getName().equals(currentUser.getClazz())) {
            mainActions.loadConversations();
        }
    }

    public void logout() {
        currentUser = null;
        GsonRequest.setToken(null);
        UVLivePreferences.getInstance().removeUser();
    }
}

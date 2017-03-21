package es.uv.uvlive.presenter;

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
        if (currentUser instanceof Admin) {
            mainActions.loadAdminMenu();
        } else if (currentUser instanceof Teacher
                || currentUser instanceof Student) {
            mainActions.loadConversations();
        }
    }
}

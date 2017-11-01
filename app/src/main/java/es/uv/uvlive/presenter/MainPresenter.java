package es.uv.uvlive.presenter;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Merchant;
import es.uv.uvlive.session.RolUV;
import es.uv.uvlive.session.Student;
import es.uv.uvlive.session.Teacher;
import es.uv.uvlive.ui.actions.MainActions;


public class MainPresenter extends BasePresenter {
    private MainActions mainActions;

    public MainPresenter(MainActions mainActions) {
        this.mainActions=mainActions;
    }

    public void loadSession() {
        //TODO if currentUser is null
        if (currentUser instanceof Admin) {
            mainActions.loadAdminMenu();
        } else if (currentUser instanceof RolUV) {
            mainActions.loadConversations();
        } else if (currentUser instanceof Merchant) {
            mainActions.loadMerchantPanel();
        }
    }

    public LoginPresenter.LoginTypes getLoginType() {
        LoginPresenter.LoginTypes loginType = null;
        if (currentUser instanceof Admin) {
            loginType = LoginPresenter.LoginTypes.Admin;
        } else if (currentUser instanceof Teacher) {
            loginType = LoginPresenter.LoginTypes.Teacher;
        } else if (currentUser instanceof Student) {
            loginType = LoginPresenter.LoginTypes.Student;
        }else if (currentUser instanceof Merchant) {
            loginType = LoginPresenter.LoginTypes.Merchant;
        }
        return loginType;
    }

    public void logout() {
        currentUser = null;
        GsonRequest.setToken(null);
        UVLivePreferences.getInstance().removeUser();
    }

    public boolean isRolUV() {
        return currentUser instanceof RolUV;
    }

    public boolean isTeacher() {
        return currentUser instanceof Teacher;
    }

    public String getUsername() {
        return currentUser.getOwnerName();
    }
}

package es.uv.uvlive.presenter;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Merchant;
import es.uv.uvlive.session.RolUV;
import es.uv.uvlive.session.Session;
import es.uv.uvlive.session.Student;
import es.uv.uvlive.session.Teacher;
import es.uv.uvlive.ui.actions.MainActions;


public class MainPresenter extends BasePresenter {
    private MainActions mainActions;

    public MainPresenter(MainActions mainActions) {
        this.mainActions = mainActions;
    }

    public void loadSession() {
        if (getUser() instanceof Admin) {
            mainActions.loadAdminMenu();
        } else if (getUser() instanceof RolUV) {
            mainActions.loadConversations();
        } else if (getUser() instanceof Merchant) {
            mainActions.loadMerchantPanel();
        }
    }

    public LoginPresenter.LoginTypes getLoginType() {
        LoginPresenter.LoginTypes loginType = null;
        if (getUser() instanceof Admin) {
            loginType = LoginPresenter.LoginTypes.Admin;
        } else if (getUser() instanceof Teacher) {
            loginType = LoginPresenter.LoginTypes.Teacher;
        } else if (getUser() instanceof Student) {
            loginType = LoginPresenter.LoginTypes.Student;
        }else if (getUser() instanceof Merchant) {
            loginType = LoginPresenter.LoginTypes.Merchant;
        }
        return loginType;
    }

    public void logout() {
        Session.getInstance().logout();
        GsonRequest.setToken(null);
        UVLivePreferences.getInstance().removeUser();
    }

    public boolean isRolUV() {
        return getUser() instanceof RolUV;
    }

    public boolean isTeacher() {
        return getUser() instanceof Teacher;
    }

    public String getUsername() {
        return getUser().getOwnerName();
    }
}

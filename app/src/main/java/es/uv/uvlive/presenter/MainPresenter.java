package es.uv.uvlive.presenter;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Merchant;
import es.uv.uvlive.session.RolUV;
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

    public void logout() {
        currentUser = null;
        GsonRequest.setToken(null);
        UVLivePreferences.getInstance().removeUser();
    }

    public boolean isRolUV() {
        return currentUser instanceof RolUV;
    }
}

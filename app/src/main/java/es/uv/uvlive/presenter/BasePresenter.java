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
    public void onCreate() {
        registeredPresenters.add(this);
    }

    @CallSuper
    public void onDestroy() {
        registeredPresenters.remove(this);
    }

    public static boolean notifyConversationListReceived() {
        boolean sended = false;

        if (registeredPresenters != null && !registeredPresenters.isEmpty()) {
            for (BasePresenter basePresenter : registeredPresenters) {
                if (basePresenter instanceof ConversationsPresenter) {
                    ((ConversationsPresenter) basePresenter).updateConversations();
                    sended = true;
                    break;
                }
            }
        }

        return sended;
    }

    public static boolean notifyMessagesReceived(int idConversation) {
        boolean sended = false;

        if (registeredPresenters != null && !registeredPresenters.isEmpty()) {
            for (BasePresenter basePresenter : registeredPresenters) {
                if (basePresenter instanceof MessagesPresenter && ((MessagesPresenter) basePresenter).getIdConversation() == idConversation) {
                    ((MessagesPresenter) basePresenter).getFollowingMessages();
                }
            }
        }

        return sended;
    }

    protected static User getUser() {
        return Session.getInstance().getCurrentUser();
    }

    protected static Session getSession() {
        return Session.getInstance();
    }
}

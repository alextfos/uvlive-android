package es.uv.uvlive.presenter;

import java.util.List;

import es.uv.uvlive.mappers.ConversationMapper;
import es.uv.uvlive.session.BusinessCallback;
import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.session.Conversation;
import es.uv.uvlive.session.RolUV;
import es.uv.uvlive.session.Session;
import es.uv.uvlive.ui.actions.ConversationsActions;

public class ConversationsPresenter extends BasePresenter {

    private ConversationsActions conversationsActions;

    public ConversationsPresenter(ConversationsActions conversationsActions) {
        this.conversationsActions = conversationsActions;
    }

    public void getLocalConversations() {
        ((RolUV)Session.getInstance().getCurrentUser()).loadConversations(new BusinessCallback<List<Conversation>>() {
            @Override
            public void onDataReceived(List<Conversation> result) {
                conversationsActions.onConversationsReceived(ConversationMapper.getConversationModelListFromConversationList(result));
            }

            @Override
            public void onError(BusinessError businessError) {
                conversationsActions.onError(businessError);
            }
        });
    }

    public void updateConversations() {
        ((RolUV)Session.getInstance().getCurrentUser()).updateConversations(new BusinessCallback<List<Conversation>>() {
            @Override
            public void onDataReceived(List<Conversation> result) {
                conversationsActions.onConversationsReceived(ConversationMapper.getConversationModelListFromConversationList(result));
            }

            @Override
            public void onError(BusinessError businessError) {
                conversationsActions.onError(businessError);
            }
        });
    }
}

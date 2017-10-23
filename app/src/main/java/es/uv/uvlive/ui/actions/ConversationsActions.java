package es.uv.uvlive.ui.actions;

import java.util.List;

import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.session.ConversationModel;

public interface ConversationsActions extends BaseActions {
    void onConversationsReceived(List<ConversationModel> conversationModelList);
    void getConversations();
}

package es.uv.uvlive.ui.actions;

import java.util.List;

import es.uv.uvlive.session.Conversation;
import es.uv.uvlive.ui.models.ConversationModel;

public interface ConversationsActions extends BaseActions {
    void onConversationsReceived(List<ConversationModel> conversationModelList);
    void getConversations();
}

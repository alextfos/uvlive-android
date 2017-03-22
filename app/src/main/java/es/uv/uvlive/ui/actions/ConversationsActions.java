package es.uv.uvlive.ui.actions;

import java.util.List;

import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.session.ConversationModel;

/**
 * Created by alextfos on 07/12/2016.
 */

public interface ConversationsActions {
    void onConversationsReceived(List<ConversationModel> conversationModelList);
}

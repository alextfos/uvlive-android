package es.uv.uvlive.ui.actions;

import es.uv.uvlive.data.gateway.response.ConversationsListResponse;

/**
 * Created by alextfos on 07/12/2016.
 */

public interface ConversationsActions {
    void onConversationsReceived(ConversationsListResponse conversationsListResponse);
}

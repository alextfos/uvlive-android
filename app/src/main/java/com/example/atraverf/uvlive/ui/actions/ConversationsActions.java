package com.example.atraverf.uvlive.ui.actions;

import com.example.atraverf.uvlive.data.gateway.response.ConversationsListResponse;

/**
 * Created by alextfos on 07/12/2016.
 */

public interface ConversationsActions {
    void onConversationsReceived(ConversationsListResponse conversationsListResponse);
}

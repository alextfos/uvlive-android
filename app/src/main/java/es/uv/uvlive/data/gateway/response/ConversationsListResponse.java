package es.uv.uvlive.data.gateway.response;

import java.util.ArrayList;

public class ConversationsListResponse {

    private ArrayList<ConversationResponse> conversations;

    public ArrayList<ConversationResponse> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<ConversationResponse> conversations) {
        this.conversations = conversations;
    }
}

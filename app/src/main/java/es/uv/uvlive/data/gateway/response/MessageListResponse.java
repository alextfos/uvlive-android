package es.uv.uvlive.data.gateway.response;

import java.util.ArrayList;


public class MessageListResponse {
    private ArrayList<MessageResponse> messageResponse;

    public ArrayList<MessageResponse> getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(ArrayList<MessageResponse> messageResponse) {
        this.messageResponse = messageResponse;
    }
}

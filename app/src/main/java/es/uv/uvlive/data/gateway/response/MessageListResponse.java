package es.uv.uvlive.data.gateway.response;

import java.util.ArrayList;


public class MessageListResponse extends BaseResponse {
    private ArrayList<MessageResponse> messages;

    public ArrayList<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageResponse> messages) {
        this.messages = messages;
    }
}

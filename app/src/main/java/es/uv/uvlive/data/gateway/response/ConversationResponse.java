package es.uv.uvlive.data.gateway.response;

public class ConversationResponse extends BaseResponse {

    private int idConversation;
    private String name;

    public int getId(){
        return idConversation;
    }

    public String getName(){
        return name;
    }

    public void setId(int idConversation) {
        this.idConversation = idConversation;
    }

    public void setName(String name) {
        this.name = name;
    }
}

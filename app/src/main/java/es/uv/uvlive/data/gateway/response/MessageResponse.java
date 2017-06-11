package es.uv.uvlive.data.gateway.response;

public class MessageResponse {

    private int idMessage;
    private String text;
    private String timestamp;

    public String getText() {
        return text;
    }

    public void setMessage(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

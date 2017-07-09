package es.uv.uvlive.data.gateway.response;

public class MessageResponse {

    private int idMessage;
    private String text;
    private int timestamp;
    private String owner;

    public String getText() {
        return text;
    }

    public void setMessage(String text) {
        this.text = text;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

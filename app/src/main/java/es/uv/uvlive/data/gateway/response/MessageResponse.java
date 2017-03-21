package es.uv.uvlive.data.gateway.response;

/**
 * Created by alextfos on 01/03/2017.
 */

public class MessageResponse {
    private String message;
    private long timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

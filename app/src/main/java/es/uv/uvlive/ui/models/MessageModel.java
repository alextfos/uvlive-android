package es.uv.uvlive.ui.models;


public class MessageModel {
    private boolean sended;
    private boolean mine;
    private String owner;
    private String message;
    private String date;
    private boolean blocked;

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MessageModel
                && message != null && message.equals(((MessageModel) obj).message) &&
                owner != null && owner.equals(((MessageModel) obj).owner);
    }
}

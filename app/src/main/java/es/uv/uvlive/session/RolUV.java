package es.uv.uvlive.session;

public abstract class RolUV extends User {

    private String pushToken;

    public RolUV() {
        clazz = getClass().getName();
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}

package es.uv.uvlive.data.gateway.form;

public class LoginForm extends BaseForm {

    private String userName;
    private String password;
    private String loginType;
    private String pushToken;

    public void setUser(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTypeLogin(String type) {
        this.loginType = type;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}

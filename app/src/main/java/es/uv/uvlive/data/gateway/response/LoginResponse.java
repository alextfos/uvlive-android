package es.uv.uvlive.data.gateway.response;

public class LoginResponse extends BaseResponse {

    private String token;
    private String ownerField;

    public String getOwnerField() {
        return ownerField;
    }

    public void setOwnerField(String ownerField) {
        this.ownerField = ownerField;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

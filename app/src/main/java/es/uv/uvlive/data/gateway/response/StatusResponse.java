package es.uv.uvlive.data.gateway.response;

public class StatusResponse extends BaseResponse {

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

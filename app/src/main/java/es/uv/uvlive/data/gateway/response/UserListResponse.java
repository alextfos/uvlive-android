package es.uv.uvlive.data.gateway.response;

import java.util.List;

public class UserListResponse extends BaseResponse {
    private List<UserResponse> users;

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }
}

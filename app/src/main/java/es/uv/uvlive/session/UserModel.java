package es.uv.uvlive.session;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.data.gateway.response.UserResponse;

public class UserModel {
    private int userId;
    private String name;

    public static @NonNull List<UserModel> transform(@Nullable List<UserResponse> userResponseList) {
        ArrayList<UserModel> userModels = new ArrayList<>();

        if (userResponseList != null) {
            for (UserResponse userResponse : userResponseList) {
                userModels.add(new UserModel(userResponse));
            }
        }

        return userModels;
    }

    public UserModel(UserResponse userResponse) {
        userId = userResponse.getUserId();
        name = userResponse.getFirstname() + " " + userResponse.getLastname();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

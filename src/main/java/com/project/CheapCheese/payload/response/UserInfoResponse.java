package com.project.CheapCheese.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoResponse {

    private int idUser;

    private String username;

    private String email;

    private List<String> role;

    public UserInfoResponse() {}

    public UserInfoResponse(int id, String username, String email, List<String> role) {
        this.idUser = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}

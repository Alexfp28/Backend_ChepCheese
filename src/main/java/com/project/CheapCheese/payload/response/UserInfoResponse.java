package com.project.CheapCheese.payload.response;

import java.math.BigInteger;
import java.util.List;

public class UserInfoResponse {

    private BigInteger id;

    private String username;

    private String email;

    private List<String> role;

    public UserInfoResponse() {}

    public UserInfoResponse(BigInteger id, String username, String email, List<String> role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}

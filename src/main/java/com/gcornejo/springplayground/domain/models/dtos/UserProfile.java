package com.gcornejo.springplayground.domain.models.dtos;

public class UserProfile {

    private String username;

    private String company;

    public UserProfile(String name, String company) {
        this.username = name;
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

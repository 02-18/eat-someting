package com.eat.pojo;

import lombok.Data;

@Data
public class User {

    private String id;
    private String nickname;
    private String username;
    private String password;
    private String cookStyle;
    private String requirements;
    private String allergy;
    private String avatar;
}

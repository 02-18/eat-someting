package com.eat.pojo;

import lombok.Data;

@Data
public class Step {

    private String id;
    private String stepName;
    private String menuId;
    private String menuName;
    private int orderNum;

}

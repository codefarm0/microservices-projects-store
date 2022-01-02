package com.gl.userservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity(name = "users")
@Table(name ="users")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private Integer mobile;
    private String password;
    private Date insertDate;
    private Date expireDate;
}

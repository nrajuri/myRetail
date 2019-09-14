package com.codetest.app.myretail.response;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author nrajuri
 */
@Document(collection = "users")
@Data
public class Users {

    @Id
    private String id;
    private String username;
    private String password;

    @DBRef(lazy = false)
    private List<Role> roles;

    /**
     * Default constructor (no args).
     */
    public Users() {
        // TODO Auto-generated constructor stub
    }

    /**
     * constructor
     *
     * @param id
     * @param username
     * @param password
     */
    public Users(String id, String username, String password, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
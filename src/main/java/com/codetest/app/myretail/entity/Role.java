package com.codetest.app.myretail.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author nrajuri
 */
@Document(collection = "role")
@Data
public class Role {

    @Id
    private String id;
    private String name;

    /**
     * Default constructor.
     */
    public Role() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Parameterized constructor.
     *
     * @param id
     * @param name
     */
    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

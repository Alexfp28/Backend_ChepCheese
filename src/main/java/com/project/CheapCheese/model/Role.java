package com.project.CheapCheese.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

}
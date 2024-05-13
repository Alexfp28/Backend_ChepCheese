package com.project.CheapCheese.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Data
@Document(collection = "supermarkets")
public class Supermarket {
    @Id
    private BigInteger _id;
    private String nombre;
    private String imagen;
    private String descripcion;

    public Supermarket( String nombre, String imagen, String descripcion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

}

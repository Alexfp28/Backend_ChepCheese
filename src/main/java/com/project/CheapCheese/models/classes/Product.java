package com.project.CheapCheese.models.classes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;


@Data
@Document(collection = "products")
public class Product {

    @Id
    private BigInteger _id;

    private String nombre;
    private String imagen;
    private double precio;
    private String tipo;
    private String tienda;

    public Product( String nombre, String imagen, double precio, String tipo, String tienda) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
        this.tipo = tipo;
        this.tienda = tienda;
    }

}


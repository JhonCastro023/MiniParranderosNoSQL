package uniandes.edu.co.demo.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "puntos")
public class Punto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitud;
    private double longitud;
    private String direccion;
    private String ciudad;

    public Punto() {}

    // Getters & Setters...
}

package uniandes.edu.co.demo.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int puntuacion;
    private String comentario;

    @ManyToOne
    private User autor;

    @ManyToOne
    private User receptor;

    @ManyToOne
    private Servicio servicio;

    public Review() {}

    // Getters & Setters...
}

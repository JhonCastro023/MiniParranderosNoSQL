package uniandes.edu.co.demo.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String marca;
    private String modelo;
    private String color;

    @Column(unique = true)
    private String placa;

    private String ciudadPlaca;
    private int capacidadPasajeros;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private User conductor;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<Disponibilidad> disponibilidades;

    public Vehicle() {}

    // Getters & Setters...
}

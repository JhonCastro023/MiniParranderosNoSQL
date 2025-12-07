package uniandes.edu.co.demo.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String correo;

    private String celular;

    @Column(unique = true)
    private String cedula;

    // SERVICIO o CONDUCTOR
    private String tipo;

    // Métodos de pago (solo para servicio)
    private String tarjetaNumero;
    private String tarjetaNombre;
    private String tarjetaVencimiento;
    private String tarjetaCodigo;

    // Vehículos (solo si es conductor)
    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL)
    private List<Vehicle> vehiculos;

    public User() {}

    // Getters & Setters...
}

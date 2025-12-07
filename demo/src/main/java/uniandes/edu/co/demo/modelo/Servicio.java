package uniandes.edu.co.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String nivel;
    private double distancia;
    private double costo;

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuarioServicio;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private User conductor;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehicle vehiculo;

    @OneToOne(cascade = CascadeType.ALL)
    private Punto puntoInicio;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Punto> puntosLlegada;

    public Servicio() {}

    // Getters & Setters...
}

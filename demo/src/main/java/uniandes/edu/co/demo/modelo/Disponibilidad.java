package uniandes.edu.co.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.DayOfWeek;

@Entity
@Table(name = "disponibilidades")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dia;

    private LocalTime horaInicio;
    private LocalTime horaFin;

    private String tipoServicio;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehicle vehiculo;

    public Disponibilidad() {}

    // Getters & Setters...
}

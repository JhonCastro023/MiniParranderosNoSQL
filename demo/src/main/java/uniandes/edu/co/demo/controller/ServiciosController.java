package uniandes.edu.co.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.*;
import uniandes.edu.co.demo.repository.*;

@RestController
@RequestMapping("/alpescab")
public class ServiciosController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    /* ================================
         RF1 - REGISTRAR USUARIO SERVICIO
       ================================ */
    @PostMapping("/usuarios/servicio/registrar")
    public ResponseEntity<String> registrarUsuarioServicio(@RequestBody User user) {
        user.setTipo("servicio");
        userRepository.save(user);
        return ResponseEntity.ok("Usuario de servicios registrado");
    }

    /* ================================
        RF2 - REGISTRAR USUARIO CONDUCTOR
       ================================ */
    @PostMapping("/usuarios/conductor/registrar")
    public ResponseEntity<String> registrarConductor(@RequestBody User user) {
        user.setTipo("conductor");
        userRepository.save(user);
        return ResponseEntity.ok("Conductor registrado");
    }

    /* ==========================================
        RF3 - REGISTRAR VEHÍCULO PARA UN CONDUCTOR
       ========================================== */
    @PostMapping("/vehiculos/registrar/{idConductor}")
    public ResponseEntity<String> registrarVehiculo(
            @PathVariable String idConductor,
            @RequestBody Vehicle vehicle) {

        vehicle.setIdOwner(idConductor);
        vehicleRepository.save(vehicle);

        return ResponseEntity.ok("Vehículo registrado para el conductor");
    }

    /* =====================================================
        RF4 - REGISTRAR DISPONIBILIDAD DE UN CONDUCTOR
       ===================================================== */
    @PostMapping("/disponibilidad/registrar/{idConductor}/{idVehiculo}")
    public ResponseEntity<String> registrarDisponibilidad(
            @PathVariable String idConductor,
            @PathVariable String idVehiculo,
            @RequestBody Disponibilidad disp) {

        // validar superposición
        List<Disponibilidad> existentes = disponibilidadRepository.buscarPorConductor(idConductor);

        for (Disponibilidad d : existentes) {
            if (disp.seSuperpone(d)) {
                return ResponseEntity.badRequest()
                        .body("Error: La disponibilidad se superpone con una existente.");
            }
        }

        disp.setIdConductor(idConductor);
        disp.setIdVehiculo(idVehiculo);
        disponibilidadRepository.save(disp);

        return ResponseEntity.ok("Disponibilidad registrada");
    }

    /* =====================================================
         RF5 - MODIFICAR DISPONIBILIDAD
       ===================================================== */
    @PutMapping("/disponibilidad/modificar/{idDisp}")
    public ResponseEntity<String> modificarDisponibilidad(
            @PathVariable String idDisp,
            @RequestBody Disponibilidad nueva) {

        Disponibilidad actual = disponibilidadRepository.findById(idDisp).orElse(null);

        if (actual == null) {
            return ResponseEntity.badRequest().body("No existe la disponibilidad.");
        }

        // validar superposición
        List<Disponibilidad> existentes = disponibilidadRepository.buscarPorConductor(actual.getIdConductor());

        for (Disponibilidad d : existentes) {
            if (!d.getId().equals(idDisp) && nueva.seSuperpone(d)) {
                return ResponseEntity.badRequest()
                        .body("Error: Se superpone con otra disponibilidad.");
            }
        }

        nueva.setId(idDisp);
        disponibilidadRepository.save(nueva);
        return ResponseEntity.ok("Disponibilidad modificada.");
    }

    /* ===============================================
         RF6 - SOLICITAR UN SERVICIO
       =============================================== */
    @PostMapping("/servicios/solicitar/{idUsuario}")
    public ResponseEntity<String> solicitarServicio(
            @PathVariable String idUsuario,
            @RequestBody Servicio servicio) {

        servicio.setIdUsuario(idUsuario);
        servicio.setEstado("Asignando");

        // asignar conductor automáticamente
        User conductorAsignado = userRepository.asignarConductor(servicio);
        if (conductorAsignado == null) {
            return ResponseEntity.badRequest().body("No hay conductores disponibles cerca.");
        }

        servicio.setIdConductor(conductorAsignado.getId());
        servicio.setEstado("Asignado");

        servicioRepository.save(servicio);
        return ResponseEntity.ok("Servicio creado y conductor asignado.");
    }

    /* ===============================================
         RF7 - REGISTRAR VIAJE FINALIZADO
       =============================================== */
    @PutMapping("/servicios/finalizar/{idServicio}")
    public ResponseEntity<String> finalizarServicio(
            @PathVariable String idServicio,
            @RequestBody Servicio datos) {

        Servicio s = servicioRepository.findById(idServicio).orElse(null);

        if (s == null) {
            return ResponseEntity.badRequest().body("Servicio no existe.");
        }

        s.setHoraInicio(datos.getHoraInicio());
        s.setHoraFin(datos.getHoraFin());
        s.setCosto(datos.getCosto());
        s.setEstado("Finalizado");

        servicioRepository.save(s);
        return ResponseEntity.ok("Viaje registrado correctamente.");
    }

    /* ===============================================
         RFC1 - HISTÓRICO DE SERVICIOS DE UN USUARIO
       =============================================== */
    @GetMapping("/consultas/historico/{idUsuario}")
    public List<Servicio> historico(@PathVariable String idUsuario) {
        return servicioRepository.buscarPorUsuario(idUsuario);
    }

    /* ===============================================
         RFC2 - TOP 20 CONDUCTORES CON MÁS SERVICIOS
       =============================================== */
    @GetMapping("/consultas/top-conductores")
    public List<Document> topConductores() {
        return servicioRepository.topConductores();
    }

    /* ===============================================
         RFC3 - USO DE SERVICIOS POR CIUDAD Y FECHA
       =============================================== */
    @GetMapping("/consultas/uso")
    public List<Document> usoServicios(
            @RequestParam String ciudad,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        return servicioRepository.usoServicios(ciudad, fechaInicio, fechaFin);
    }

}

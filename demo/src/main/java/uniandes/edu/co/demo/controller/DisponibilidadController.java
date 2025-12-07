package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Disponibilidad;
import uniandes.edu.co.demo.repository.DisponibilidadRepository;

import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadRepository repository;

    @PostMapping("/new/save")
    public ResponseEntity<String> crear(@RequestBody Disponibilidad d) {
        try {
            repository.save(d);
            return new ResponseEntity<>("Disponibilidad creada", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizar(@PathVariable String id, @RequestBody Disponibilidad d) {
        try {
            d.setId(id);
            repository.save(d);
            return ResponseEntity.ok("Disponibilidad actualizada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Disponibilidad>> obtenerTodas() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disponibilidad> obtenerPorId(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Disponibilidad eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

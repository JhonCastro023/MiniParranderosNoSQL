package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Servicio;
import uniandes.edu.co.demo.repository.ServicioRepository;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository repository;

    @PostMapping("/new/save")
    public ResponseEntity<String> crear(@RequestBody Servicio s) {
        try {
            repository.save(s);
            return new ResponseEntity<>("Servicio creado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizar(@PathVariable String id, @RequestBody Servicio s) {
        try {
            s.setId(id);
            repository.save(s);
            return ResponseEntity.ok("Servicio actualizado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Servicio>> obtenerTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Servicio eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}


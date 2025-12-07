package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Vehicle;
import uniandes.edu.co.demo.repository.VehicleRepository;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleRepository repository;

    @PostMapping("/new/save")
    public ResponseEntity<String> crear(@RequestBody Vehicle v) {
        try {
            repository.save(v);
            return new ResponseEntity<>("Vehículo creado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizar(@PathVariable String id, @RequestBody Vehicle v) {
        try {
            v.setId(id);
            repository.save(v);
            return ResponseEntity.ok("Vehículo actualizado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Vehicle>> obtenerTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> obtenerPorId(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Vehículo eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

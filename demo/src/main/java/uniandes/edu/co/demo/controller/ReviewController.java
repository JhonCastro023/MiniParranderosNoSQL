package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.demo.modelo.Review;
import uniandes.edu.co.demo.repository.ReviewRepository;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository repository;

    @PostMapping("/new/save")
    public ResponseEntity<String> crear(@RequestBody Review r) {
        try {
            repository.save(r);
            return new ResponseEntity<>("Review creada", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizar(@PathVariable String id, @RequestBody Review r) {
        try {
            r.setId(id);
            repository.save(r);
            return ResponseEntity.ok("Review actualizada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Review>> obtenerTodas() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> obtenerPorId(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Review eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

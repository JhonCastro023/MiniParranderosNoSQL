package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle, Integer> {

    @Query("{}")
    List<Vehicle> buscarTodos();

    @Query("{_id: ?0}")
    List<Vehicle> buscarPorId(int id);

    @Query("{ $insert: { _id: ?0, plate: ?1, type: ?2, userId: ?3 } }")
    void insertar(int id, String plate, String type, int userId);

    @Query("{ _id: ?0 }")
    @Update("{ $set: { plate: ?1, type: ?2, userId: ?3 }}")
    void actualizar(int id, String plate, String type, int userId);

    @Query(value = "{_id: ?0}", delete = true)
    void eliminar(int id);

    @Query(value = "{ userId: ?0 }")
    List<Vehicle> buscarPorUsuario(int userId);
}

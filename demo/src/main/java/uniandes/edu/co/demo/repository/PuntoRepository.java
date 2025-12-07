package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.Punto;

public interface PuntoRepository extends MongoRepository<Punto, Integer> {

    @Query("{}")
    List<Punto> buscarTodos();

    @Query("{ _id: ?0 }")
    List<Punto> buscarPorId(int id);

    @Query("{ ciudad: ?0 }")
    List<Punto> buscarPorCiudad(String ciudad);

    @Query("{ $insert: { _id: ?0, latitud: ?1, longitud: ?2, direccion: ?3, ciudad: ?4 } }")
    void insertar(int id, double latitud, double longitud, String direccion, String ciudad);

    @Query("{ _id: ?0 }")
    @Update("{ $set: { latitud: ?1, longitud: ?2, direccion: ?3, ciudad: ?4 } }")
    void actualizar(int id, double latitud, double longitud, String direccion, String ciudad);

    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarPorId(int id);
}

package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.Review;

public interface ReviewRepository extends MongoRepository<Review, Integer> {

    @Query("{}")
    List<Review> buscarTodas();

    @Query("{ _id: ?0 }")
    List<Review> buscarPorId(int id);

    // Reviews dirigidas a un receptor (por ejemplo, conductor)
    @Query("{ 'receptor.$id': ?0 }")
    List<Review> buscarPorReceptor(int receptorId);

    // Reviews creadas por un autor
    @Query("{ 'autor.$id': ?0 }")
    List<Review> buscarPorAutor(int autorId);

    // Reviews asociadas a un servicio
    @Query("{ 'servicio.$id': ?0 }")
    List<Review> buscarPorServicio(int servicioId);

    @Query("{ $insert: { _id: ?0, puntuacion: ?1, comentario: ?2, autorId: ?3, receptorId: ?4, servicioId: ?5 } }")
    void insertar(int id, int puntuacion, String comentario, int autorId, int receptorId, int servicioId);

    @Query("{ _id: ?0 }")
    @Update("{ $set: { puntuacion: ?1, comentario: ?2 } }")
    void actualizar(int id, int puntuacion, String comentario);

    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarPorId(int id);
}


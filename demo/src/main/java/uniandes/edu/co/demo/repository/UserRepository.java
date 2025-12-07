package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.User;

public interface UserRepository extends MongoRepository<User, Integer> {

    @Query(value = "{}", fields = "{ 'tarjetaNumero': 0, 'tarjetaCodigo': 0 }")
    List<User> buscarTodos();

    @Query("{_id: ?0}")
    List<User> buscarPorId(int id);

    @Query("{ email: ?0 }")
    List<User> buscarPorCorreo(String correo);

    @Query("{ cedula: ?0 }")
    List<User> buscarPorCedula(String cedula);

    @Query("{ tipo: ?0 }")
    List<User> buscarPorTipo(String tipo); 

    @Query("{ $insert: { _id: ?0, nombre: ?1, correo: ?2, celular: ?3, cedula: ?4, tipo: ?5, tarjetaNumero: ?6, tarjetaNombre: ?7, tarjetaVencimiento: ?8, tarjetaCodigo: ?9 } }")
    void insertar(int id, String nombre, String correo, String celular, String cedula, String tipo,
                  String tarjetaNumero, String tarjetaNombre, String tarjetaVencimiento, String tarjetaCodigo);

    @Query("{ _id: ?0 }")
    @Update("{ $set: { nombre: ?1, correo: ?2, celular: ?3, cedula: ?4, tipo: ?5, tarjetaNumero: ?6, tarjetaNombre: ?7, tarjetaVencimiento: ?8, tarjetaCodigo: ?9 } }")
    void actualizar(int id, String nombre, String correo, String celular, String cedula, String tipo,
                    String tarjetaNumero, String tarjetaNombre, String tarjetaVencimiento, String tarjetaCodigo);

    @Query(value = "{_id: ?0}", delete = true)
    void eliminarPorId(int id);
}

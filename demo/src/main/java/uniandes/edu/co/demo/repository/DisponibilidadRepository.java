package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.Disponibilidad;

public interface DisponibilidadRepository extends MongoRepository<Disponibilidad, Integer> {

    @Query("{}")
    List<Disponibilidad> buscarTodas();

    @Query("{ _id: ?0 }")
    List<Disponibilidad> buscarPorId(int id);

    // Buscar disponibilidades por vehiculo
    @Query("{ 'vehiculo.$id': ?0 }")
    List<Disponibilidad> buscarPorVehiculo(int vehiculoId);

    // Buscar por día de la semana (ej. "MONDAY")
    @Query("{ dia: ?0 }")
    List<Disponibilidad> buscarPorDia(String dia);

    @Query("{ $insert: { _id: ?0, dia: ?1, horaInicio: ?2, horaFin: ?3, tipoServicio: ?4, vehiculoId: ?5 } }")
    void insertar(int id, String dia, String horaInicio, String horaFin, String tipoServicio, int vehiculoId);

    @Query("{ _id: ?0 }")
    @Update("{ $set: { dia: ?1, horaInicio: ?2, horaFin: ?3, tipoServicio: ?4, vehiculoId: ?5 } }")
    void actualizar(int id, String dia, String horaInicio, String horaFin, String tipoServicio, int vehiculoId);

    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarPorId(int id);
}

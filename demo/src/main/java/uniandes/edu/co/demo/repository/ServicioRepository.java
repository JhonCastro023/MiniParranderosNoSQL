package uniandes.edu.co.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.demo.modelo.Servicio;

public interface ServicioRepository extends MongoRepository<Servicio, Integer> {

    @Query("{}")
    List<Servicio> buscarTodos();

    @Query("{ _id: ?0 }")
    List<Servicio> buscarPorId(int id);

    // Buscar servicios por usuario solicitante
    @Query("{ 'usuarioServicio.$id': ?0 }")
    List<Servicio> buscarPorUsuario(int usuarioId);

    // Buscar servicios por conductor
    @Query("{ 'conductor.$id': ?0 }")
    List<Servicio> buscarPorConductor(int conductorId);

    // Buscar servicios en un rango de fechas (asumiendo campo horaInicio)
    @Query("{ horaInicio: { $gte: ?0, $lte: ?1 } }")
    List<Servicio> buscarPorRangoFechas(String fechaInicioIso, String fechaFinIso);

    // Insertar (ejemplo básico; adapta campos según tu modelo real)
    @Query("{ $insert: { _id: ?0, tipo: ?1, nivel: ?2, distancia: ?3, costo: ?4, horaInicio: ?5, horaFin: ?6, usuarioServicioId: ?7, conductorId: ?8, vehiculoId: ?9, puntoInicioId: ?10 } }")
    void insertar(int id, String tipo, String nivel, double distancia, double costo, String horaInicio, String horaFin, int usuarioServicioId, Integer conductorId, Integer vehiculoId, Integer puntoInicioId);

    @Query("{ _id: ?0 }")
    @Update("{ $set: { tipo: ?1, nivel: ?2, distancia: ?3, costo: ?4, horaInicio: ?5, horaFin: ?6, usuarioServicioId: ?7, conductorId: ?8, vehiculoId: ?9 } }")
    void actualizar(int id, String tipo, String nivel, double distancia, double costo, String horaInicio, String horaFin, int usuarioServicioId, Integer conductorId, Integer vehiculoId);

    // Actualizar estado (ej. status: "assigned" | "in_progress" | "completed")
    @Query("{ _id: ?0 }")
    @Update("{ $set: { status: ?1 } }")
    void actualizarEstado(int id, String status);

    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarPorId(int id);
}

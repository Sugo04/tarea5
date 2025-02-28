package ad.hibernate.hmarort.dao.implementacion;

import ad.hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.hibernate.hmarort.models.ZonaEnvio;
import ad.hibernate.hmarort.utils.SessionManager;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementación de la interfaz DAOZonaEnvio utilizando Hibernate como ORM.
 * Esta clase proporciona métodos para realizar operaciones CRUD sobre la entidad ZonaEnvio
 * y operaciones adicionales específicas del negocio.
 * 
 * @author hmarort
 * @version 1.0
 * @see DAOZonaEnvio
 * @see ZonaEnvio
 * @see SessionManager
 */
public class DAOZonaEnvioImpl implements DAOZonaEnvio {
    /**
     * Logger para registrar eventos y errores durante la ejecución.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOZonaEnvioImpl.class);
    
    /**
     * Gestor de sesiones de Hibernate que proporciona acceso a las sesiones
     * y maneja las transacciones.
     */
    private final SessionManager sessionManager;

    /**
     * Constructor por defecto que inicializa el gestor de sesiones.
     */
    public DAOZonaEnvioImpl() {
        this.sessionManager = SessionManager.getInstance();
        LOGGER.debug("DAOZonaEnvioImpl inicializado correctamente");
    }

    /**
     * Agrega una nueva zona de envío a la base de datos.
     *
     * @param zonaEnvio El objeto ZonaEnvio a persistir
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void agregarZonaEnvio(ZonaEnvio zonaEnvio) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Insertando nueva zona de envío: {}", zonaEnvio.getNombreZona());
                sesion.persist(zonaEnvio);
                LOGGER.info("Zona de envío insertada exitosamente con ID: {}", zonaEnvio.getIdZona());
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al insertar zona de envío en la base de datos", excepcion);
            throw new SQLException("Error al insertar zona de envío: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera una zona de envío específica por su identificador.
     *
     * @param id Identificador único de la zona de envío a buscar
     * @return El objeto ZonaEnvio encontrado o null si no existe
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public ZonaEnvio obtenerZonaEnvioPorId(int id) throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Buscando zona de envío con identificador: {}", id);
                ZonaEnvio zonaEncontrada = sesion.get(ZonaEnvio.class, id);
                if (zonaEncontrada != null) {
                    LOGGER.debug("Zona de envío encontrada: {}", zonaEncontrada.getNombreZona());
                } else {
                    LOGGER.debug("No se encontró ninguna zona de envío con ID: {}", id);
                }
                return zonaEncontrada;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al buscar zona de envío por ID en la base de datos", excepcion);
            throw new SQLException("Error al buscar zona de envío: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera todas las zonas de envío almacenadas en la base de datos.
     *
     * @return Lista de todas las zonas de envío
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public List<ZonaEnvio> obtenerTodasLasZonas() throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Obteniendo listado completo de zonas de envío");
                Query<ZonaEnvio> consulta = sesion.createQuery("FROM ZonaEnvio", ZonaEnvio.class);
                List<ZonaEnvio> listaZonas = consulta.getResultList();
                LOGGER.debug("Se encontraron {} zonas de envío en total", listaZonas.size());
                return listaZonas;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al obtener todas las zonas de envío de la base de datos", excepcion);
            throw new SQLException("Error al obtener zonas de envío: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Actualiza la información de una zona de envío existente en la base de datos.
     *
     * @param zonaEnvio El objeto ZonaEnvio con la información actualizada
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void actualizarZonaEnvio(ZonaEnvio zonaEnvio) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Actualizando zona de envío con ID: {}", zonaEnvio.getIdZona());
                sesion.merge(zonaEnvio);
                LOGGER.info("Zona de envío actualizada correctamente: {}", zonaEnvio.getNombreZona());
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al actualizar información de la zona de envío", excepcion);
            throw new SQLException("Error al actualizar zona de envío: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Elimina una zona de envío de la base de datos según su identificador.
     *
     * @param id Identificador único de la zona de envío a eliminar
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void eliminarZonaEnvioPorId(int id) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Eliminando zona de envío con identificador: {}", id);
                ZonaEnvio zonaAEliminar = sesion.get(ZonaEnvio.class, id);
                if (zonaAEliminar != null) {
                    sesion.remove(zonaAEliminar);
                    LOGGER.info("Zona de envío eliminada exitosamente con ID: {}", id);
                } else {
                    LOGGER.warn("No se encontró zona de envío para eliminar con ID: {}", id);
                    throw new RuntimeException("La zona de envío con ID " + id + " no existe en la base de datos");
                }
            });
        } catch (RuntimeException excepcion) {
            LOGGER.error("Error al eliminar zona de envío de la base de datos", excepcion);
            throw new SQLException("Error al eliminar zona de envío: " + excepcion.getMessage(), excepcion);
        }
    }
}
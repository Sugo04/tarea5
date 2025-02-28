package ad.hibernate.hmarort.dao.implementacion;

import ad.hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.hibernate.hmarort.models.ZonaEnvio;
import ad.hibernate.hmarort.utils.SessionManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class DAOZonaEnvioImpl implements DAOZonaEnvio {
    private static final Logger logger = LoggerFactory.getLogger(DAOZonaEnvioImpl.class);
    private final SessionManager sessionManager;

    public DAOZonaEnvioImpl() {
        this.sessionManager = SessionManager.getInstance();
    }

    @Override
    public void agregarZonaEnvio(ZonaEnvio zonaEnvio) throws SQLException {
        try {
            sessionManager.execute(session -> {
                logger.debug("Insertando zona de envío: {}", zonaEnvio.getNombreZona());
                session.persist(zonaEnvio);
                logger.info("Zona de envío insertada con ID: {}", zonaEnvio.getIdZona());
            });
        } catch (Exception e) {
            logger.error("Error al insertar zona de envío", e);
            throw new SQLException("Error al insertar zona de envío: " + e.getMessage(), e);
        }
    }

    @Override
    public ZonaEnvio obtenerZonaEnvioPorId(int id) throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                logger.debug("Buscando zona de envío con ID: {}", id);
                ZonaEnvio zona = session.get(ZonaEnvio.class, id);
                if (zona != null) {
                    logger.debug("Zona de envío encontrada: {}", zona.getNombreZona());
                } else {
                    logger.debug("No se encontró zona de envío con ID: {}", id);
                }
                return zona;
            });
        } catch (Exception e) {
            logger.error("Error al buscar zona de envío por ID", e);
            throw new SQLException("Error al buscar zona de envío: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ZonaEnvio> obtenerTodasLasZonas() throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                logger.debug("Obteniendo todas las zonas de envío");
                Query<ZonaEnvio> query = session.createQuery("FROM ZonaEnvio", ZonaEnvio.class);
                List<ZonaEnvio> zonas = query.getResultList();
                logger.debug("Se encontraron {} zonas de envío", zonas.size());
                return zonas;
            });
        } catch (Exception e) {
            logger.error("Error al obtener todas las zonas de envío", e);
            throw new SQLException("Error al obtener zonas de envío: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarZonaEnvio(ZonaEnvio zonaEnvio) throws SQLException {
        try {
            sessionManager.execute(session -> {
                logger.debug("Actualizando zona de envío con ID: {}", zonaEnvio.getIdZona());
                session.merge(zonaEnvio);
                logger.info("Zona de envío actualizada: {}", zonaEnvio.getNombreZona());
            });
        } catch (Exception e) {
            logger.error("Error al actualizar zona de envío", e);
            throw new SQLException("Error al actualizar zona de envío: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarZonaEnvioPorId(int id) throws SQLException {
        try {
            sessionManager.execute(session -> {
                logger.debug("Eliminando zona de envío con ID: {}", id);
                ZonaEnvio zona = session.get(ZonaEnvio.class, id);
                if (zona != null) {
                    session.remove(zona);
                    logger.info("Zona de envío eliminada con ID: {}", id);
                } else {
                    logger.warn("No se encontró zona de envío para eliminar con ID: {}", id);
                    throw new RuntimeException("La zona de envío con ID " + id + " no existe");
                }
            });
        } catch (RuntimeException e) {
            logger.error("Error al eliminar zona de envío", e);
            throw new SQLException("Error al eliminar zona de envío: " + e.getMessage(), e);
        }
    }
}
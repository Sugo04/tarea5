package ad.hibernate.hmarort.dao.implementacion;

import ad.hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.hibernate.hmarort.models.Pedido;
import ad.hibernate.hmarort.utils.SessionManager;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de DAOPedido utilizando Hibernate.
 */
public class DAOPedidoImpl implements DAOPedido {
    private static final Logger logger = LoggerFactory.getLogger(DAOPedidoImpl.class);
    private final SessionManager sessionManager;

    public DAOPedidoImpl() {
        this.sessionManager = SessionManager.getInstance();
        logger.debug("HibernateDAOPedido inicializado");
    }

    @Override
    public void agregarPedido(Pedido pedido) throws SQLException {
        try {
            sessionManager.execute(session -> {
                logger.debug("Insertando pedido para cliente con ID: {}", pedido.getIdCliente());
                session.persist(pedido);
                logger.info("Pedido insertado con ID: {}", pedido.getIdPedido());
            });
        } catch (Exception e) {
            logger.error("Error al insertar pedido", e);
            throw new SQLException("Error al insertar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public Pedido obtenerPedidoPorId(int id) throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                logger.debug("Buscando pedido con ID: {}", id);
                Pedido pedido = session.get(Pedido.class, id);
                if (pedido != null) {
                    logger.debug("Pedido encontrado con ID: {}", id);
                } else {
                    logger.debug("No se encontró pedido con ID: {}", id);
                }
                return pedido;
            });
        } catch (Exception e) {
            logger.error("Error al buscar pedido por ID", e);
            throw new SQLException("Error al buscar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> obtenerTodosLosPedidos() throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                logger.debug("Obteniendo todos los pedidos");
                Query<Pedido> query = session.createQuery("FROM Pedido", Pedido.class);
                List<Pedido> pedidos = query.getResultList();
                logger.debug("Se encontraron {} pedidos", pedidos.size());
                return pedidos;
            });
        } catch (Exception e) {
            logger.error("Error al obtener todos los pedidos", e);
            throw new SQLException("Error al obtener pedidos: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarPedido(Pedido pedido) throws SQLException {
        try {
            sessionManager.execute(session -> {
                logger.debug("Actualizando pedido con ID: {}", pedido.getIdPedido());
                session.merge(pedido);
                logger.info("Pedido actualizado con ID: {}", pedido.getIdPedido());
            });
        } catch (Exception e) {
            logger.error("Error al actualizar pedido", e);
            throw new SQLException("Error al actualizar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarPedido(int id) throws SQLException {
        try {
            sessionManager.execute(session -> {
                logger.debug("Eliminando pedido con ID: {}", id);
                Pedido pedido = session.get(Pedido.class, id);
                if (pedido != null) {
                    session.remove(pedido);
                    logger.info("Pedido eliminado con ID: {}", id);
                } else {
                    logger.warn("No se encontró pedido para eliminar con ID: {}", id);
                    throw new RuntimeException("El pedido con ID " + id + " no existe");
                }
            });
        } catch (RuntimeException e) {
            logger.error("Error al eliminar pedido", e);
            throw new SQLException("Error al eliminar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(int idCliente) throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                logger.debug("Obteniendo pedidos para cliente con ID: {}", idCliente);
                Query<Pedido> query = session.createQuery("FROM Pedido WHERE idCliente = :idCliente", Pedido.class);
                query.setParameter("idCliente", idCliente);
                List<Pedido> pedidos = query.getResultList();
                logger.debug("Se encontraron {} pedidos para el cliente", pedidos.size());
                return pedidos;
            });
        } catch (Exception e) {
            logger.error("Error al obtener pedidos por cliente", e);
            throw new SQLException("Error al obtener pedidos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorFecha(LocalDate fecha) throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                logger.debug("Obteniendo pedidos para la fecha: {}", fecha);
                Query<Pedido> query = session.createQuery("FROM Pedido WHERE fecha = :fecha", Pedido.class);
                query.setParameter("fecha", fecha);
                List<Pedido> pedidos = query.getResultList();
                logger.debug("Se encontraron {} pedidos en la fecha especificada", pedidos.size());
                return pedidos;
            });
        } catch (Exception e) {
            logger.error("Error al obtener pedidos por fecha", e);
            throw new SQLException("Error al obtener pedidos: " + e.getMessage(), e);
        }
    }

    @Override
    public double calcularTotalFacturadoPorCliente(int idCliente) throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                logger.debug("Calculando total facturado para cliente con ID: {}", idCliente);
                Query<Double> query = session.createQuery("SELECT SUM(importe) FROM Pedido WHERE idCliente = :idCliente", Double.class);
                query.setParameter("idCliente", idCliente);
                Double total = query.uniqueResult();
                return total != null ? total : 0.0;
            });
        } catch (Exception e) {
            logger.error("Error al calcular total facturado", e);
            throw new SQLException("Error al calcular total facturado: " + e.getMessage(), e);
        }
    }
}
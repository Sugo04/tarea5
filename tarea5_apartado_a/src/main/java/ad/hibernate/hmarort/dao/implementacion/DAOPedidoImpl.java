package ad.hibernate.hmarort.dao.implementacion;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ad.hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.hibernate.hmarort.models.Pedido;
import ad.hibernate.hmarort.utils.SessionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de la interfaz DAOPedido utilizando Hibernate como ORM.
 * Esta clase proporciona métodos para realizar operaciones CRUD sobre la entidad Pedido
 * y operaciones adicionales específicas del negocio.
 * 
 * @author hmarort
 * @version 1.0
 * @see DAOPedido
 * @see Pedido
 * @see SessionManager
 */
public class DAOPedidoImpl implements DAOPedido {
    /**
     * Logger para registrar eventos y errores durante la ejecución.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOPedidoImpl.class);
    
    /**
     * Gestor de sesiones de Hibernate que proporciona acceso a las sesiones
     * y maneja las transacciones.
     */
    private final SessionManager sessionManager;

    /**
     * Constructor por defecto que inicializa el gestor de sesiones.
     */
    public DAOPedidoImpl() {
        this.sessionManager = SessionManager.getInstance();
        LOGGER.debug("DAOPedidoImpl inicializado correctamente");
    }

    /**
     * Agrega un nuevo pedido a la base de datos.
     *
     * @param pedido El objeto Pedido a persistir
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void agregarPedido(Pedido pedido) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Insertando nuevo pedido para cliente con ID: {}", pedido.getIdCliente());
                sesion.persist(pedido);
                LOGGER.info("Pedido insertado exitosamente con ID: {}", pedido.getIdPedido());
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al insertar pedido en la base de datos", excepcion);
            throw new SQLException("Error al insertar pedido: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera un pedido específico por su identificador.
     *
     * @param id Identificador único del pedido a buscar
     * @return El objeto Pedido encontrado o null si no existe
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public Pedido obtenerPedidoPorId(int id) throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Buscando pedido con identificador: {}", id);
                Pedido pedidoEncontrado = sesion.get(Pedido.class, id);
                if (pedidoEncontrado != null) {
                    LOGGER.debug("Pedido encontrado con ID: {}", id);
                } else {
                    LOGGER.debug("No se encontró ningún pedido con ID: {}", id);
                }
                return pedidoEncontrado;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al buscar pedido por ID en la base de datos", excepcion);
            throw new SQLException("Error al buscar pedido: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera todos los pedidos almacenados en la base de datos.
     *
     * @return Lista de todos los pedidos
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public List<Pedido> obtenerTodosLosPedidos() throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Obteniendo listado completo de pedidos");
                Query<Pedido> consulta = sesion.createQuery("FROM Pedido", Pedido.class);
                List<Pedido> listaPedidos = consulta.getResultList();
                LOGGER.debug("Se encontraron {} pedidos en total", listaPedidos.size());
                return listaPedidos;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al obtener todos los pedidos de la base de datos", excepcion);
            throw new SQLException("Error al obtener pedidos: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Actualiza la información de un pedido existente en la base de datos.
     *
     * @param pedido El objeto Pedido con la información actualizada
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void actualizarPedido(Pedido pedido) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Actualizando pedido con ID: {}", pedido.getIdPedido());
                sesion.merge(pedido);
                LOGGER.info("Pedido actualizado correctamente con ID: {}", pedido.getIdPedido());
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al actualizar información del pedido", excepcion);
            throw new SQLException("Error al actualizar pedido: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Elimina un pedido de la base de datos según su identificador.
     *
     * @param id Identificador único del pedido a eliminar
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void eliminarPedido(int id) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Eliminando pedido con identificador: {}", id);
                Pedido pedidoAEliminar = sesion.get(Pedido.class, id);
                if (pedidoAEliminar != null) {
                    sesion.remove(pedidoAEliminar);
                    LOGGER.info("Pedido eliminado exitosamente con ID: {}", id);
                } else {
                    LOGGER.warn("No se encontró pedido para eliminar con ID: {}", id);
                    throw new RuntimeException("El pedido con ID " + id + " no existe en la base de datos");
                }
            });
        } catch (RuntimeException excepcion) {
            LOGGER.error("Error al eliminar pedido de la base de datos", excepcion);
            throw new SQLException("Error al eliminar pedido: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera todos los pedidos realizados por un cliente específico.
     *
     * @param idCliente Identificador único del cliente
     * @return Lista de pedidos asociados al cliente especificado
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public List<Pedido> obtenerPedidosPorCliente(int idCliente) throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Obteniendo pedidos para cliente con identificador: {}", idCliente);
                Query<Pedido> consulta = sesion.createQuery("FROM Pedido WHERE idCliente = :idCliente", Pedido.class);
                consulta.setParameter("idCliente", idCliente);
                List<Pedido> pedidosCliente = consulta.getResultList();
                LOGGER.debug("Se encontraron {} pedidos para el cliente", pedidosCliente.size());
                return pedidosCliente;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al obtener pedidos por cliente en la base de datos", excepcion);
            throw new SQLException("Error al obtener pedidos: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera todos los pedidos realizados en una fecha específica.
     *
     * @param fecha Fecha de los pedidos a buscar
     * @return Lista de pedidos realizados en la fecha especificada
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public List<Pedido> obtenerPedidosPorFecha(LocalDate fecha) throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Obteniendo pedidos para la fecha: {}", fecha);
                Query<Pedido> consulta = sesion.createQuery("FROM Pedido WHERE fecha = :fecha", Pedido.class);
                consulta.setParameter("fecha", fecha);
                List<Pedido> pedidosFecha = consulta.getResultList();
                LOGGER.debug("Se encontraron {} pedidos en la fecha especificada", pedidosFecha.size());
                return pedidosFecha;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al obtener pedidos por fecha en la base de datos", excepcion);
            throw new SQLException("Error al obtener pedidos: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Calcula el importe total facturado a un cliente específico.
     *
     * @param idCliente Identificador único del cliente
     * @return Importe total facturado al cliente
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public double calcularTotalFacturadoPorCliente(int idCliente) throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Calculando total facturado para cliente con ID: {}", idCliente);
                Query<Double> consulta = sesion.createQuery("SELECT SUM(importe) FROM Pedido WHERE idCliente = :idCliente", Double.class);
                consulta.setParameter("idCliente", idCliente);
                Double totalFacturado = consulta.uniqueResult();
                double resultadoFinal = totalFacturado != null ? totalFacturado : 0.0;
                LOGGER.debug("Total facturado al cliente {}: {} €", idCliente, resultadoFinal);
                return resultadoFinal;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al calcular total facturado al cliente", excepcion);
            throw new SQLException("Error al calcular total facturado: " + excepcion.getMessage(), excepcion);
        }
    }
}
package ad.hibernate.hmarort.dao.implementacion;

import ad.hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.hibernate.hmarort.models.Cliente;
import ad.hibernate.hmarort.utils.SessionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementación de la interfaz DAOCliente utilizando Hibernate como ORM.
 * Esta clase proporciona métodos para realizar operaciones CRUD sobre la entidad Cliente
 * y operaciones adicionales específicas del negocio.
 * 
 * @author hmarort
 * @version 1.0
 * @see DAOCliente
 * @see Cliente
 * @see SessionManager
 */
public class DAOClienteImpl implements DAOCliente {
    /**
     * Logger para registrar eventos y errores durante la ejecución.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOClienteImpl.class);
    
    /**
     * Gestor de sesiones de Hibernate que proporciona acceso a las sesiones
     * y maneja las transacciones.
     */
    private final SessionManager sessionManager;

    /**
     * Constructor por defecto que inicializa el gestor de sesiones.
     */
    public DAOClienteImpl() {
        this.sessionManager = SessionManager.getInstance();
        LOGGER.debug("DAOClienteImpl inicializado correctamente");
    }

    /**
     * Agrega un nuevo cliente a la base de datos.
     *
     * @param cliente El objeto Cliente a persistir
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void agregarCliente(Cliente cliente) throws SQLException {
        Transaction transaccion = null;
        try {
            Session sesion = sessionManager.getCurrentSession();
            transaccion = sesion.beginTransaction();
            LOGGER.debug("Insertando nuevo cliente: {}", cliente.getNombre());
            sesion.persist(cliente);
            transaccion.commit();
            LOGGER.info("Cliente insertado exitosamente con ID: {}", cliente.getIdCliente());
        } catch (Exception excepcion) {
            if (transaccion != null && transaccion.isActive()) {
                transaccion.rollback();
            }
            LOGGER.error("Error al insertar cliente en la base de datos", excepcion);
            throw new SQLException("Error al insertar cliente: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera un cliente específico por su identificador.
     *
     * @param id Identificador único del cliente a buscar
     * @return El objeto Cliente encontrado o null si no existe
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public Cliente obtenerClientePorId(int id) throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                LOGGER.debug("Buscando cliente con ID: {}", id);
                Cliente cliente = session.get(Cliente.class, id);
                if (cliente != null) {
                    LOGGER.debug("Cliente encontrado: {}", cliente.getNombre());
                } else {
                    LOGGER.debug("No se encontró cliente con ID: {}", id);
                }
                return cliente;
            });
        } catch (Exception e) {
            LOGGER.error("Error al buscar cliente por ID", e);
            throw new SQLException("Error al buscar cliente: " + e.getMessage(), e);
        }
    }

    /**
     * Recupera todos los clientes almacenados en la base de datos.
     *
     * @return Lista de todos los clientes
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public List<Cliente> obtenerTodosLosClientes() throws SQLException {
        Transaction transaccion = null;
        try {
            Session sesion = sessionManager.getCurrentSession();
            transaccion = sesion.beginTransaction(); // Asegurar que la transacción está activa

            LOGGER.debug("Obteniendo listado completo de clientes");
            Query<Cliente> consulta = sesion.createQuery("FROM Cliente", Cliente.class);
            List<Cliente> listaClientes = consulta.getResultList();

            transaccion.commit(); 

            LOGGER.debug("Se encontraron {} clientes en total", listaClientes.size());
            return listaClientes;
        } catch (Exception excepcion) {
            if (transaccion != null && transaccion.isActive()) {
                transaccion.rollback(); // Revertir si hay un error
            }
            LOGGER.error("Error al obtener todos los clientes de la base de datos", excepcion);
            throw new SQLException("Error al obtener clientes: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Actualiza la información de un cliente existente en la base de datos.
     *
     * @param cliente El objeto Cliente con la información actualizada
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void actualizarInformacionCliente(Cliente cliente) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Actualizando cliente con ID: {}", cliente.getIdCliente());
                sesion.merge(cliente);
                LOGGER.info("Cliente actualizado correctamente: {}", cliente.getNombre());
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al actualizar información del cliente", excepcion);
            throw new SQLException("Error al actualizar cliente: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Elimina un cliente de la base de datos según su identificador.
     *
     * @param id Identificador único del cliente a eliminar
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public void eliminarClientePorId(int id) throws SQLException {
        try {
            sessionManager.execute(sesion -> {
                LOGGER.debug("Eliminando cliente con identificador: {}", id);
                Cliente clienteAEliminar = sesion.get(Cliente.class, id);
                if (clienteAEliminar != null) {
                    sesion.remove(clienteAEliminar);
                    LOGGER.info("Cliente eliminado exitosamente con ID: {}", id);
                } else {
                    LOGGER.warn("No se encontró cliente para eliminar con ID: {}", id);
                    throw new RuntimeException("El cliente con ID " + id + " no existe en la base de datos");
                }
            });
        } catch (RuntimeException excepcion) {
            LOGGER.error("Error al eliminar cliente de la base de datos", excepcion);
            throw new SQLException("Error al eliminar cliente: " + excepcion.getMessage(), excepcion);
        }
    }

    /**
     * Recupera todos los clientes que pertenecen a una zona específica.
     *
     * @param idZona Identificador de la zona a filtrar
     * @return Lista de clientes que pertenecen a la zona especificada
     * @throws SQLException Si ocurre un error durante la operación de base de datos
     */
    @Override
    public List<Cliente> obtenerClientePorZona(int idZona) throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Buscando clientes en zona con identificador: {}", idZona);
                Query<Cliente> consulta = sesion.createQuery(
                        "FROM Cliente WHERE idZona = :idZona", Cliente.class);
                consulta.setParameter("idZona", idZona);
                List<Cliente> clientesZona = consulta.getResultList();
                LOGGER.debug("Se encontraron {} clientes en la zona {}", clientesZona.size(), idZona);
                return clientesZona;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al buscar clientes por zona en la base de datos", excepcion);
            throw new SQLException("Error al buscar clientes por zona: " + excepcion.getMessage(), excepcion);
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
    public double calcularFacturacionTotalCliente(int idCliente) throws SQLException {
        try {
            return sessionManager.executeWithResult(sesion -> {
                LOGGER.debug("Calculando facturación total para cliente con ID: {}", idCliente);
                Query<Double> consulta = sesion.createQuery(
                        "SELECT SUM(p.importeTotal) FROM Pedido p WHERE p.idCliente = :idCliente", Double.class);
                consulta.setParameter("idCliente", idCliente);
                Double totalFacturacion = consulta.uniqueResult();
                double resultadoFinal = (totalFacturacion != null) ? totalFacturacion : 0.0;
                LOGGER.debug("Total facturado al cliente {}: {} €", idCliente, resultadoFinal);
                return resultadoFinal;
            });
        } catch (Exception excepcion) {
            LOGGER.error("Error al calcular facturación total del cliente", excepcion);
            throw new SQLException("Error al calcular total facturado: " + excepcion.getMessage(), excepcion);
        }
    }
}
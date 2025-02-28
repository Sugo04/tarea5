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
 * Implementación de DAOCliente utilizando Hibernate y SessionManager.
 */
public class DAOClienteImpl implements DAOCliente {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOClienteImpl.class);
    private final SessionManager sessionManager;

    public DAOClienteImpl() {
        this.sessionManager = SessionManager.getInstance();
        LOGGER.debug("DAOClienteImpl inicializado");
    }

    @Override
    public void agregarCliente(Cliente cliente) throws SQLException {
        Transaction transaction = null;
        try {
            Session session = sessionManager.getCurrentSession();
            transaction = session.beginTransaction();
            LOGGER.debug("Insertando cliente: {}", cliente.getNombre());
            session.persist(cliente);
            transaction.commit();
            LOGGER.info("Cliente insertado con ID: {}", cliente.getId_Cliente());
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.error("Error al insertar cliente", e);
            throw new SQLException("Error al insertar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente obtenerClientePorId(int id) throws SQLException {
        try {
            Session session = sessionManager.getCurrentSession();
            LOGGER.debug("Buscando cliente con ID: {}", id);
            Cliente cliente = session.get(Cliente.class, id);
            if (cliente != null) {
                LOGGER.debug("Cliente encontrado: {}", cliente.getNombre());
            } else {
                LOGGER.debug("No se encontró cliente con ID: {}", id);
            }
            return cliente;
        } catch (Exception e) {
            LOGGER.error("Error al buscar cliente por ID", e);
            throw new SQLException("Error al buscar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() throws SQLException {
        Transaction transaction = null;
        try {
            Session session = sessionManager.getCurrentSession();
            transaction = session.beginTransaction(); // 🔹 Asegurar que la transacción está activa

            LOGGER.debug("Obteniendo todos los clientes");
            Query<Cliente> query = session.createQuery("FROM Cliente", Cliente.class);
            List<Cliente> clientes = query.getResultList();

            transaction.commit(); 

            LOGGER.debug("Se encontraron {} clientes", clientes.size());
            return clientes;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // 🔹 Revertir si hay un error
            }
            LOGGER.error("Error al obtener todos los clientes", e);
            throw new SQLException("Error al obtener clientes: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarInformacionCliente(Cliente cliente) throws SQLException {
        Transaction transaction = null;
        try {
            Session session = sessionManager.getCurrentSession();
            transaction = session.beginTransaction();
            LOGGER.debug("Actualizando cliente con ID: {}", cliente.getId_Cliente());
            session.merge(cliente);
            transaction.commit();
            LOGGER.info("Cliente actualizado: {}", cliente.getNombre());
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.error("Error al actualizar cliente", e);
            throw new SQLException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarClientePorId(int id) throws SQLException {
        Transaction transaction = null;
        try {
            Session session = sessionManager.getCurrentSession();
            transaction = session.beginTransaction();
            LOGGER.debug("Eliminando cliente con ID: {}", id);
            Cliente cliente = session.get(Cliente.class, id);
            if (cliente != null) {
                session.remove(cliente);
                transaction.commit();
                LOGGER.info("Cliente eliminado con ID: {}", id);
            } else {
                LOGGER.warn("No se encontró cliente para eliminar con ID: {}", id);
                throw new SQLException("El cliente con ID " + id + " no existe");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.error("Error al eliminar cliente", e);
            throw new SQLException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cliente> obtenerClientePorZona(int idZona) throws SQLException {
        try {
            Session session = sessionManager.getCurrentSession();
            LOGGER.debug("Buscando clientes en la zona: {}", idZona);
            Query<Cliente> query = session.createQuery("FROM Cliente WHERE idZonaEnvio = :idZona", Cliente.class);
            query.setParameter("idZona", idZona);
            List<Cliente> clientes = query.getResultList();
            LOGGER.debug("Se encontraron {} clientes en la zona {}", clientes.size(), idZona);
            return clientes;
        } catch (Exception e) {
            LOGGER.error("Error al obtener clientes por zona", e);
            throw new SQLException("Error al obtener clientes por zona: " + e.getMessage(), e);
        }
    }

    @Override
    public double calcularFacturacionTotalCliente(int idCliente) throws SQLException {
        try {
            return sessionManager.executeWithResult(session -> {
                LOGGER.debug("Calculando total gastado por cliente con ID: {}", idCliente);
                Query<Double> query = session.createQuery(
                        "SELECT SUM(p.importeTotal) FROM Pedido p WHERE p.idCliente = :idCliente", Double.class);
                query.setParameter("idCliente", idCliente);
                Double total = query.uniqueResult();
                double result = (total != null) ? total : 0.0;
                LOGGER.debug("Total gastado por cliente {}: {}", idCliente, result);
                return result;
            });
        } catch (Exception e) {
            LOGGER.error("Error al calcular total gastado por cliente", e);
            throw new SQLException("Error al calcular total gastado: " + e.getMessage(), e);
        }
    }
    
}
package ad.conectores_hibernate.hmarort.factory;

import java.sql.Connection;
import ad.conectores_hibernate.hmarort.dao.implementacion.hibernate.DAOClienteImpl;
import ad.conectores_hibernate.hmarort.dao.implementacion.hibernate.DAOPedidoImpl;
import ad.conectores_hibernate.hmarort.dao.implementacion.hibernate.DAOZonaEnvioImpl;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.conectores_hibernate.hmarort.database_config.DatabaseConfig;

/**
 * Implementación concreta de {@link DAOFactory} que proporciona DAOs basados en Hibernate.
 * 
 * Esta factoría crea objetos de acceso a datos (DAOs) que utilizan Hibernate como
 * tecnología de persistencia. Permite acceder a las entidades del modelo de datos
 * a través de la API de Hibernate en lugar de SQL directo.
 * 
 * @author hmarort
 * @version 1.0
 * @see DAOFactory
 * @see DatabaseConfig
 */
public class HibernateDAOFactory extends DAOFactory {
    /**
     * Constructor que inicializa la factoría con una configuración de base de datos.
     * 
     * Aunque Hibernate maneja internamente sus conexiones a través del SessionFactory,
     * se mantiene la configuración para consistencia con la arquitectura general.
     *
     * @param databaseConfig Configuración de conexión a la base de datos, que debe ser
     *                      de tipo compatible con Hibernate
     */
    public HibernateDAOFactory(DatabaseConfig databaseConfig) {
        super(databaseConfig);
    }

    /**
     * Crea una instancia del DAO para la entidad Cliente que utiliza Hibernate.
     * 
     * La implementación devuelta gestiona operaciones CRUD y consultas sobre la
     * entidad Cliente utilizando la API de Hibernate.
     *
     * @return Una implementación de {@link DAOCliente} basada en Hibernate
     */
    @Override
    public DAOCliente createClienteDAO() {
        return new DAOClienteImpl();
    }

    /**
     * Crea una instancia del DAO para la entidad Pedido que utiliza Hibernate.
     * 
     * La implementación devuelta gestiona operaciones CRUD y consultas sobre la
     * entidad Pedido utilizando la API de Hibernate.
     *
     * @return Una implementación de {@link DAOPedido} basada en Hibernate
     */
    @Override
    public DAOPedido createPedidoDAO() {
        return new DAOPedidoImpl();
    }

    /**
     * Crea una instancia del DAO para la entidad ZonaEnvio que utiliza Hibernate.
     * 
     * La implementación devuelta gestiona operaciones CRUD y consultas sobre la
     * entidad ZonaEnvio utilizando la API de Hibernate.
     *
     * @return Una implementación de {@link DAOZonaEnvio} basada en Hibernate
     */
    @Override
    public DAOZonaEnvio createZonaEnvioDAO() {
        return new DAOZonaEnvioImpl();
    }

    /**
     * Obtiene una conexión a la base de datos utilizando la configuración de Hibernate.
     * 
     * Este método es principalmente para mantener compatibilidad con la arquitectura general.
     * En aplicaciones que utilizan Hibernate directamente, normalmente se trabaja con
     * {@link org.hibernate.Session} en lugar de conexiones JDBC directas.
     *
     * @return Una conexión JDBC a la base de datos subyacente
     * @throws Exception Si ocurre un error al obtener la conexión
     */
    @Override
    public Connection getConnection() throws Exception {
        // Con Hibernate normalmente no necesitamos acceder directamente a la conexión JDBC
        // Pero implementamos este método para cumplir con la interfaz
        return databaseConfig.getConnection();
    }
}
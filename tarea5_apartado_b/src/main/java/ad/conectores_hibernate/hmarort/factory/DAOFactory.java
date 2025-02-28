package ad.conectores_hibernate.hmarort.factory;

import java.sql.Connection;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.conectores_hibernate.hmarort.database_config.DatabaseConfig;
import ad.conectores_hibernate.hmarort.database_config.DatabaseType;

/**
 * Factoría abstracta para la creación de objetos de acceso a datos (DAO).
 * 
 * Esta clase implementa el patrón de diseño Abstract Factory, proporcionando
 * una interfaz común para crear familias de DAOs relacionados sin especificar
 * sus clases concretas. Permite cambiar entre diferentes implementaciones de
 * persistencia manteniendo la misma interfaz.
 * 
 * @author hmarort
 * @version 1.0
 * @see DatabaseConfig
 * @see DatabaseType
 */
public abstract class DAOFactory {
    /**
     * Configuración de la base de datos utilizada por esta factoría.
     * Contiene los parámetros necesarios para establecer conexiones.
     */
    protected DatabaseConfig databaseConfig;

    /**
     * Constructor protegido para inicializar la configuración de la base de datos.
     * 
     * Este constructor está protegido para asegurar que solo las subclases puedan
     * instanciar la factoría directamente.
     *
     * @param databaseConfig La configuración de la base de datos a utilizar
     */
    protected DAOFactory(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    /**
     * Crea y devuelve una instancia concreta de {@link DAOFactory} según el tipo
     * de base de datos especificado.
     * 
     * Este método implementa el patrón Factory Method para la creación de factorías
     * específicas según el motor de base de datos requerido.
     *
     * @param dbType Tipo de base de datos a utilizar (SQLITE, MYSQL, POSTGRESQL, HIBERNATE)
     * @param config Configuración con los parámetros de conexión a la base de datos
     * @return Una instancia concreta de {@link DAOFactory} correspondiente al tipo especificado
     * @throws UnsupportedOperationException Si se solicita un tipo de base de datos no implementado
     */
    public static DAOFactory getDAOFactory(DatabaseType dbType, DatabaseConfig config) {
        return switch (dbType) {
            case SQLITE -> new SQLiteDAOFactory(config);
            case MYSQL -> throw new UnsupportedOperationException("MySQL no implementado aún");
            case POSTGRESQL -> throw new UnsupportedOperationException("PostgreSQL no implementado aún");
            case HIBERNATE -> new HibernateDAOFactory(config);
        };
    }

    /**
     * Crea y devuelve una nueva instancia de DAO para la entidad Cliente.
     * 
     * @return Una implementación concreta de {@link DAOCliente} según el tipo de factoría
     */
    public abstract DAOCliente createClienteDAO();

    /**
     * Crea y devuelve una nueva instancia de DAO para la entidad Pedido.
     * 
     * @return Una implementación concreta de {@link DAOPedido} según el tipo de factoría
     */
    public abstract DAOPedido createPedidoDAO();

    /**
     * Crea y devuelve una nueva instancia de DAO para la entidad ZonaEnvio.
     * 
     * @return Una implementación concreta de {@link DAOZonaEnvio} según el tipo de factoría
     */
    public abstract DAOZonaEnvio createZonaEnvioDAO();

    /**
     * Obtiene una conexión a la base de datos configurada.
     * 
     * Este método delega la obtención de la conexión al objeto DatabaseConfig,
     * proporcionando un punto único de acceso a las conexiones para todos los DAOs
     * creados por esta factoría.
     *
     * @return Una conexión activa a la base de datos
     * @throws Exception Si ocurre algún error durante la obtención de la conexión
     */
    public Connection getConnection() throws Exception {
        return databaseConfig.getConnection();
    }
}
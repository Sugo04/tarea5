package ad.hibernate.hmarort.factory;

import ad.hibernate.hmarort.dao.implementacion.DAOClienteImpl;
import ad.hibernate.hmarort.dao.implementacion.DAOPedidoImpl;
import ad.hibernate.hmarort.dao.implementacion.DAOZonaEnvioImpl;
import ad.hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.hibernate.hmarort.database_config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factoría singleton para la creación y gestión centralizada de DAOs con Hibernate.
 * 
 * Esta clase implementa el patrón de diseño Singleton para proporcionar un punto
 * único de acceso a los objetos DAO en toda la aplicación. Gestiona la creación
 * y reutilización de los DAOs para optimizar el uso de recursos.
 * 
 * <p>A diferencia del enfoque basado en herencia de {@link ad.conectores_hibernate.hmarort.factory.DAOFactory},
 * esta implementación utiliza un enfoque basado en composición y acceso mediante métodos getter.</p>
 * 
 * @author hmarort
 * @version 1.0
 * @see DatabaseConfig
 * @see DAOCliente
 * @see DAOPedido
 * @see DAOZonaEnvio
 */
public class DAOFactory {
    private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);
    
    /** Instancia única del singleton */
    private static DAOFactory instance;
    
    /** DAO para operaciones con la entidad Cliente */
    private final DAOCliente clienteDAO;
    
    /** DAO para operaciones con la entidad Pedido */
    private final DAOPedido pedidoDAO;
    
    /** DAO para operaciones con la entidad ZonaEnvio */
    private final DAOZonaEnvio zonaEnvioDAO;

    /**
     * Constructor privado que inicializa las implementaciones de DAO.
     * 
     * Este constructor es privado para asegurar que solo se pueda crear
     * una instancia a través del método {@link #getInstance(DatabaseConfig)}.
     * 
     * @param dbConfig Configuración de la base de datos para los DAOs
     */
    private DAOFactory(DatabaseConfig dbConfig) {
        logger.debug("Inicializando DAOFactory con configuración de base de datos");
        this.clienteDAO = new DAOClienteImpl();
        this.pedidoDAO = new DAOPedidoImpl();
        this.zonaEnvioDAO = new DAOZonaEnvioImpl();
        logger.info("DAOFactory inicializado correctamente");
    }

    /**
     * Obtiene la única instancia de DAOFactory, creándola si no existe.
     * 
     * Este método implementa el patrón Singleton con inicialización lazy y
     * sincronización para entornos multi-hilo. La primera llamada crea la
     * instancia, las posteriores devuelven la instancia ya creada.
     *
     * @param dbConfig Configuración de la base de datos a utilizar
     * @return La instancia única de DAOFactory
     */
    public static synchronized DAOFactory getInstance(DatabaseConfig dbConfig) {
        if (instance == null) {
            instance = new DAOFactory(dbConfig);
        }
        return instance;
    }

    /**
     * Obtiene el DAO para gestionar operaciones con la entidad Cliente.
     * 
     * Este DAO proporciona métodos para crear, leer, actualizar y eliminar
     * registros de Cliente, así como consultas específicas relacionadas.
     *
     * @return La instancia del DAO para Cliente
     */
    public DAOCliente getClienteDAO() {
        return clienteDAO;
    }

    /**
     * Obtiene el DAO para gestionar operaciones con la entidad Pedido.
     * 
     * Este DAO proporciona métodos para crear, leer, actualizar y eliminar
     * registros de Pedido, así como consultas específicas relacionadas.
     *
     * @return La instancia del DAO para Pedido
     */
    public DAOPedido getPedidoDAO() {
        return pedidoDAO;
    }

    /**
     * Obtiene el DAO para gestionar operaciones con la entidad ZonaEnvio.
     * 
     * Este DAO proporciona métodos para crear, leer, actualizar y eliminar
     * registros de ZonaEnvio, así como consultas específicas relacionadas.
     *
     * @return La instancia del DAO para ZonaEnvio
     */
    public DAOZonaEnvio getZonaEnvioDAO() {
        return zonaEnvioDAO;
    }
}
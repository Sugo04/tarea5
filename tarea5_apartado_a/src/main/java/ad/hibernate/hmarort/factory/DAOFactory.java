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
 * Factoría singleton para crear instancias de DAOs utilizando Hibernate.
 */
public class DAOFactory {
    private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);
    private static DAOFactory instance;
    
    private final DAOCliente clienteDAO;
    private final DAOPedido pedidoDAO;
    private final DAOZonaEnvio zonaEnvioDAO;

    /**
     * Constructor privado que inicializa los DAOs con la configuración de base de datos.
     */
    private DAOFactory(DatabaseConfig dbConfig) {
        logger.debug("Inicializando DAOFactory con configuración de base de datos");
        this.clienteDAO = new DAOClienteImpl();
        this.pedidoDAO = new DAOPedidoImpl();
        this.zonaEnvioDAO = new DAOZonaEnvioImpl();
        logger.info("DAOFactory inicializado correctamente");
    }

    /**
     * Obtiene la única instancia de DAOFactory con la configuración de base de datos.
     *
     * @param dbConfig Configuración de la base de datos
     * @return La instancia de DAOFactory
     */
    public static synchronized DAOFactory getInstance(DatabaseConfig dbConfig) {
        if (instance == null) {
            instance = new DAOFactory(dbConfig);
        }
        return instance;
    }

    /**
     * Obtiene un DAO para gestionar clientes.
     *
     * @return Una instancia de DAOCliente
     */
    public DAOCliente getClienteDAO() {
        return clienteDAO;
    }

    /**
     * Obtiene un DAO para gestionar pedidos.
     *
     * @return Una instancia de DAOPedido
     */
    public DAOPedido getPedidoDAO() {
        return pedidoDAO;
    }

    /**
     * Obtiene un DAO para gestionar zonas de envío.
     *
     * @return Una instancia de DAOZonaEnvio
     */
    public DAOZonaEnvio getZonaEnvioDAO() {
        return zonaEnvioDAO;
    }
}
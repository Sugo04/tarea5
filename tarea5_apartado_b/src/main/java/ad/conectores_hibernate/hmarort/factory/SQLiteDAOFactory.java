package ad.conectores_hibernate.hmarort.factory;

import ad.conectores_hibernate.hmarort.dao.implementacion.sqlite.SQLiteDAOCliente;
import ad.conectores_hibernate.hmarort.dao.implementacion.sqlite.SQLiteDAOPedido;
import ad.conectores_hibernate.hmarort.dao.implementacion.sqlite.SQLiteDAOZonaEnvio;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.conectores_hibernate.hmarort.database_config.DatabaseConfig;

public class SQLiteDAOFactory extends DAOFactory{

    /**
     * Constructor protegido que recibe la configuración de la base de datos.
     * @param databaseConfig
     */
    public SQLiteDAOFactory(DatabaseConfig databaseConfig) {
        super(databaseConfig);
    }

    /**
     * Crea un objeto DAOCliente.
     * @return
     */
    @Override
    public DAOCliente createClienteDAO() {
        return new SQLiteDAOCliente(this.databaseConfig);
    }

    /**
     * Crea un objeto DAOPedido.
     * @return
     */
    @Override
    public DAOPedido createPedidoDAO() {
        return new SQLiteDAOPedido(this.databaseConfig);
    }

    /**
     * Crea un objeto DAOZonaEnvio.
     * @return
     */
    @Override
    public DAOZonaEnvio createZonaEnvioDAO() {
        return new SQLiteDAOZonaEnvio(this.databaseConfig);
    }
}

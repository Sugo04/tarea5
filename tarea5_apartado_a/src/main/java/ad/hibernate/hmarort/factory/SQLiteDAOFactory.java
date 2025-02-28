package ad.hibernate.hmarort.factory;

import ad.hibernate.hmarort.dao.implementacion.sqlite.SQLiteDAOCliente;
import ad.hibernate.hmarort.dao.implementacion.sqlite.SQLiteDAOPedido;
import ad.hibernate.hmarort.dao.implementacion.sqlite.SQLiteDAOZonaEnvio;
import ad.hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.hibernate.hmarort.database_config.DatabaseConfig;

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
        return new SQLiteDAOCliente(this.dbConfig);
    }

    /**
     * Crea un objeto DAOPedido.
     * @return
     */
    @Override
    public DAOPedido createPedidoDAO() {
        return new SQLiteDAOPedido(this.dbConfig);
    }

    /**
     * Crea un objeto DAOZonaEnvio.
     * @return
     */
    @Override
    public DAOZonaEnvio createZonaEnvioDAO() {
        return new SQLiteDAOZonaEnvio(this.dbConfig);
    }
}

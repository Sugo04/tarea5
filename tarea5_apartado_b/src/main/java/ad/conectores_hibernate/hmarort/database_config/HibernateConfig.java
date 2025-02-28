package ad.conectores_hibernate.hmarort.database_config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ad.conectores_hibernate.hmarort.utils.HibernateUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Configuración de base de datos para Hibernate.
 * 
 * Esta clase proporciona una implementación de {@link DatabaseConfig} utilizando Hibernate como
 * proveedor de persistencia. Actúa como adaptador entre el sistema de acceso a datos genérico
 * y la configuración específica de Hibernate.
 * 
 * @author hmarort
 * @version 1.0
 */
public class HibernateConfig implements DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
    private final String url;
    private final String username;
    private final String password;
    private final int maxPoolSize;
    private final int minPoolSize;
    private final SessionFactory sessionFactory;

    /**
     * Constructor que inicializa la configuración de Hibernate con parámetros completos.
     * 
     * @param url URL de conexión a la base de datos (usado principalmente para información)
     * @param username Nombre de usuario para la conexión (puede ser vacío para SQLite)
     * @param password Contraseña para la conexión (puede ser vacía para SQLite)
     * @param maxPoolSize Tamaño máximo del pool de conexiones (informativo en Hibernate)
     * @param minPoolSize Tamaño mínimo del pool de conexiones (informativo en Hibernate)
     */
    public HibernateConfig(String url, String username, String password, int maxPoolSize, int minPoolSize) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        this.minPoolSize = minPoolSize;
       
        // Inicializar la SessionFactory de Hibernate
        this.sessionFactory = HibernateUtil.getSessionFactory();
        logger.info("Configuración de Hibernate inicializada");
    }

    /**
     * Constructor simplificado para bases de datos SQLite.
     * 
     * Utiliza valores predeterminados para username, password y tamaños de pool.
     * 
     * @param url URL de la base de datos SQLite (ejemplo: jdbc:sqlite:mibasededatos.db)
     */
    public HibernateConfig(String url) {
        this(url, "", "", 10, 1);
    }

    /**
     * Obtiene una conexión JDBC a la base de datos utilizando Hibernate.
     * 
     * En Hibernate normalmente se trabaja con sesiones en lugar de conexiones directas,
     * pero este método proporciona compatibilidad con la interfaz {@link DatabaseConfig}.
     * La conexión se obtiene a través de una sesión de Hibernate.
     * 
     * @return Una conexión JDBC obtenida de una sesión de Hibernate
     * @throws SQLException Si ocurre un error al obtener la conexión
     */
    @Override
    public Connection getConnection() throws SQLException {
        try {
            Session session = sessionFactory.openSession();
            return session.doReturningWork(connection -> connection);
        } catch (Exception e) {
            logger.error("Error al obtener conexión desde Hibernate", e);
            throw new SQLException("Error al obtener conexión desde Hibernate", e);
        }
    }

    /**
     * Obtiene el DataSource subyacente.
     * 
     * Este método está implementado para cumplir con la interfaz {@link DatabaseConfig},
     * pero Hibernate no utiliza DataSource directamente en esta implementación.
     * 
     * @return null ya que Hibernate gestiona las conexiones internamente
     */
    @Override
    public DataSource getDataSource() {
        return null;
    }

    /**
     * Cierra la SessionFactory de Hibernate y libera los recursos asociados.
     * 
     * Este método debe ser llamado cuando la aplicación finaliza para asegurar
     * que todos los recursos de conexión sean liberados correctamente.
     */
    @Override
    public void closeP() {
        HibernateUtil.shutdown();
    }

    /**
     * Obtiene la URL de conexión a la base de datos.
     * 
     * @return URL de la base de datos configurada
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Obtiene el nombre de usuario configurado para la conexión.
     * 
     * @return Nombre de usuario para la conexión a la base de datos
     */
    @Override
    public String getClienName() {
        return username;
    }

    /**
     * Obtiene la contraseña configurada para la conexión.
     * 
     * @return Contraseña para la conexión a la base de datos
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el tamaño máximo configurado para el pool de conexiones.
     * 
     * @return Tamaño máximo del pool de conexiones
     */
    @Override
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     * Obtiene el tamaño mínimo configurado para el pool de conexiones.
     * 
     * @return Tamaño mínimo del pool de conexiones
     */
    @Override
    public int getMinPoolSize() {
        return minPoolSize;
    }
}
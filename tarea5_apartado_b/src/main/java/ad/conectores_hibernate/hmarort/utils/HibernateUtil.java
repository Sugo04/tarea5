/**
 * Clase de utilidad para manejar la {@code SessionFactory} de Hibernate.
 * Implementa el patrón Singleton para garantizar una única instancia de {@code SessionFactory}.
 */
package ad.conectores_hibernate.hmarort.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    
    /**
     * Logger para registrar eventos y errores relacionados con la inicialización de Hibernate.
     */
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    
    /**
     * Única instancia de {@code SessionFactory} utilizada en la aplicación.
     */
    private static SessionFactory sessionFactory;

    /**
     * Constructor privado para evitar la instanciación directa.
     */
    private HibernateUtil() {
    }

    /**
     * Obtiene la única instancia de {@code SessionFactory}, creándola si no existe.
     *
     * @return una instancia única de {@code SessionFactory}.
     * @throws ExceptionInInitializerError si ocurre un error durante la inicialización de Hibernate.
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                logger.info("Inicializando SessionFactory de Hibernate");
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory();
                logger.info("SessionFactory inicializada correctamente");
            } catch (Exception e) {
                logger.error("Error al inicializar SessionFactory", e);
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

    /**
     * Cierra la {@code SessionFactory} si está abierta.
     * Se recomienda llamar a este método al finalizar la aplicación para liberar recursos.
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            logger.info("Cerrando SessionFactory");
            sessionFactory.close();
            logger.info("SessionFactory cerrada correctamente");
        }
    }
}

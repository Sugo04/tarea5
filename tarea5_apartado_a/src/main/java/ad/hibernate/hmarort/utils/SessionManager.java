/**
 * Clase para gestionar sesiones y transacciones de Hibernate.
 * Implementa el patrón Singleton para garantizar una única instancia de {@code SessionFactory}.
 */
package ad.hibernate.hmarort.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class SessionManager {
    
    /**
     * Logger para registrar eventos y errores relacionados con la gestión de sesiones y transacciones.
     */
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    
    /**
     * Instancia única de {@code SessionManager} para aplicar el patrón Singleton.
     */
    private static SessionManager instance;
    
    /**
     * Fábrica de sesiones de Hibernate.
     */
    private final SessionFactory sessionFactory;

    /**
     * Constructor privado para inicializar la {@code SessionFactory}.
     *
     * @throws ExceptionInInitializerError si ocurre un error durante la configuración de Hibernate.
     */
    private SessionManager() {
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

    /**
     * Obtiene la única instancia de {@code SessionManager}, creándola si no existe.
     *
     * @return la instancia única de {@code SessionManager}.
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Obtiene la {@code SessionFactory} utilizada por este administrador de sesiones.
     *
     * @return la {@code SessionFactory} actual.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Obtiene la sesión actual de Hibernate.
     *
     * @return la sesión actual gestionada por Hibernate.
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Ejecuta una operación dentro de una transacción y devuelve un resultado.
     *
     * @param <T>       el tipo del resultado esperado.
     * @param operation la operación a ejecutar dentro de la transacción.
     * @return el resultado de la operación.
     * @throws RuntimeException si ocurre un error durante la transacción.
     */
    public <T> T executeWithResult(Function<Session, T> operation) {
        Transaction transaction = null;
        try {
            Session session = getCurrentSession();
            transaction = session.beginTransaction();
            T result = operation.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    logger.error("Error al hacer rollback", rollbackEx);
                }
            }
            logger.error("Error durante la transacción", e);
            throw new RuntimeException("Error durante la transacción", e);
        }
    }

    /**
     * Ejecuta una operación dentro de una transacción sin devolver un resultado.
     *
     * @param operation la operación a ejecutar dentro de la transacción.
     * @throws RuntimeException si ocurre un error durante la transacción.
     */
    public void execute(Consumer<Session> operation) {
        Transaction transaction = null;
        try {
            Session session = getCurrentSession();
            transaction = session.beginTransaction();
            operation.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    logger.error("Error al hacer rollback", rollbackEx);
                }
            }
            logger.error("Error durante la transacción", e);
            throw new RuntimeException("Error durante la transacción", e);
        }
    }

    /**
     * Cierra la {@code SessionFactory} si está abierta.
     * Se recomienda llamar a este método al finalizar la aplicación para liberar recursos.
     */
    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            logger.info("Cerrando SessionFactory");
            sessionFactory.close();
            logger.info("SessionFactory cerrada correctamente");
        }
    }
}

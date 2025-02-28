package ad.hibernate.hmarort.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Clase para gestionar sesiones y transacciones de Hibernate.
 * Implementa el patrón Singleton para garantizar una única instancia de SessionFactory.
 */
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static SessionManager instance;
    private final SessionFactory sessionFactory;

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

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // ⚠️ Cambiamos de `openSession()` a `getCurrentSession()`
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public <T> T executeWithResult(Function<Session, T> operation) {
        Transaction transaction = null;
        try {
            Session session = getCurrentSession();  // Usamos getCurrentSession()
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

    public void execute(Consumer<Session> operation) {
        Transaction transaction = null;
        try {
            Session session = getCurrentSession();  // Usamos getCurrentSession()
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

    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            logger.info("Cerrando SessionFactory");
            sessionFactory.close();
            logger.info("SessionFactory cerrada correctamente");
        }
    }
}

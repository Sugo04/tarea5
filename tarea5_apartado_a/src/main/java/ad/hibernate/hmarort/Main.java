package ad.hibernate.hmarort;

import ad.hibernate.hmarort.ui.UI;
import ad.hibernate.hmarort.ui.UIFactory;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal de la aplicación de gestión de pedidos.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Método principal que inicia la ejecución de la aplicación.
     *
     * @param args Argumentos de línea de comandos proporcionados por el usuario
     */
    public static void main(String[] args) {
        logger.info("Iniciando aplicación de gestión de pedidos");
        
        try {
            // Configuración de opciones
            logger.debug("Configurando opciones de línea de comandos");
            Options options = new Options();
            options.addOption("i", "interfaz", true, "Interfaz a usar (manual/automatica)");
            
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            
            // Obtener la interfaz seleccionada
            String interfaz = cmd.getOptionValue("i", args.length > 0 && "manual".equalsIgnoreCase(args[0]) ? "manual" : "automatica");
            logger.info("Interfaz seleccionada: {}", interfaz);
            
            // Crear la interfaz correspondiente
            UI ui = UIFactory.crearUI(new String[]{interfaz});
            logger.debug("Tipo de interfaz instanciada: {}", ui.getClass().getSimpleName());
            
            // Iniciar la interfaz
            ui.iniciar();
            logger.info("Aplicación finalizada exitosamente");

        } catch (ParseException e) {
            logger.error("Error al parsear argumentos de línea de comandos: {}", e.getMessage());
            System.err.println("Error al parsear argumentos: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error fatal en la aplicación", e);
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
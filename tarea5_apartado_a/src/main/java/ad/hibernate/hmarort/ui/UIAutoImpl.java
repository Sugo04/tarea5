package ad.hibernate.hmarort.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ad.hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.hibernate.hmarort.database_config.DatabaseConfig;
import ad.hibernate.hmarort.database_config.DatabaseConfigFactory;
import ad.hibernate.hmarort.database_config.DatabaseProperties;
import ad.hibernate.hmarort.database_config.DatabaseType;
import ad.hibernate.hmarort.factory.DAOFactory;
import ad.hibernate.hmarort.models.Cliente;
import ad.hibernate.hmarort.models.Pedido;
import ad.hibernate.hmarort.models.ZonaEnvio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementación automática de la interfaz de usuario que genera datos de prueba.
 */
public class UIAutoImpl implements UI {
    private static final Logger logger = LoggerFactory.getLogger(UIAutoImpl.class);

    private DAOCliente daoCliente;
    private DAOPedido daoPedido;
    private DAOZonaEnvio daoZonaEnvio;
    private Random random;
    private DatabaseConfig dbConfig;

    /**
     * Constructor que inicializa la configuración de la base de datos y los DAOs.
     */
    public UIAutoImpl() {
        DatabaseProperties properties = new DatabaseProperties.Builder()
                .url("src/main/resources/pedidos.db")
                .build();

        dbConfig = DatabaseConfigFactory.createConfig(DatabaseType.SQLITE, properties);

        DAOFactory factory = DAOFactory.getInstance(dbConfig);

        daoCliente = factory.getClienteDAO();
        daoPedido = factory.getPedidoDAO();
        daoZonaEnvio = factory.getZonaEnvioDAO();
        
        random = new Random();
    }

    /**
     * Inicia el proceso automático de generación de datos y reportes.
     */
    @Override
    public void iniciar() {
        try {
            generarDatosAutomaticos();
            generarInformesAutomaticos();
        } catch (Exception e) {
            logger.error("Error en la ejecución automática", e);
            mostrarError("Error en proceso automático: " + e.getMessage());
        }
    }

    private void generarDatosAutomaticos() throws Exception {
        logger.info("[GENERACIÓN AUTOMÁTICA DE DATOS]");

        // Generar zonas de envío
        List<ZonaEnvio> zonas = IntStream.range(1, 4)
                .mapToObj(i -> new ZonaEnvio(0, "Zona " + i, random.nextDouble(10, 50)))
                .collect(Collectors.toList());

        for (ZonaEnvio zona : zonas) {
            daoZonaEnvio.agregarZonaEnvio(zona);
        }
        logger.info("✓ Zonas de envío generadas");

        // Generar clientes
        List<Cliente> clientes = IntStream.range(1, 6)
                .mapToObj(i -> new Cliente(0,
                        "Cliente " + i,
                        "cliente" + i + "@example.com",
                        "123456789" + i,
                        zonas.get(random.nextInt(zonas.size())).getIdZona()))
                .collect(Collectors.toList());

        for (Cliente cliente : clientes) {
            daoCliente.agregarCliente(cliente);
        }
        logger.info("✓ Clientes generados");

        // Generar pedidos
        for (Cliente cliente : clientes) {
            int numeroPedidos = random.nextInt(1, 4);
            for (int j = 0; j < numeroPedidos; j++) {
                Pedido pedido = new Pedido(0,
                        LocalDate.now().minusDays(random.nextInt(30)),
                        random.nextDouble(50, 500),
                        cliente.getId_Cliente());
                daoPedido.agregarPedido(pedido);
            }
        }
        logger.info("✓ Pedidos generados");
    }

    private void generarInformesAutomaticos() throws Exception {
        logger.info("[INFORME AUTOMÁTICO]");
        List<Cliente> clientes = daoCliente.obtenerTodosLosClientes();

        for (Cliente cliente : clientes) {
            List<Pedido> pedidos = daoPedido.obtenerPedidosPorCliente(cliente.getId_Cliente());
            double totalFacturado = daoCliente.calcularFacturacionTotalCliente(cliente.getId_Cliente());

            logger.info("\n--- Resumen Cliente ---");
            logger.info("Nombre: {}", cliente.getNombre());
            logger.info("Total Pedidos: {}", pedidos.size());
            logger.info("Total Facturado: {} €", totalFacturado);
        }
    }

    @Override
    public int mostrarMenu() { return 0; }

    @Override
    public void gestionarClientes() {}

    @Override
    public void gestionarPedidos() {}

    @Override
    public void consultarZonasEnvio() throws Exception {}

    @Override
    public void consultarPedidosCliente() throws Exception {}

    @Override
    public void mostrarMensaje(String mensaje) { logger.info(mensaje); }

    @Override
    public void mostrarError(String mensaje) { logger.error(mensaje); }
}
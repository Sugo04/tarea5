package ad.conectores_hibernate.hmarort.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ad.conectores_hibernate.hmarort.dao.interfaces.DAOCliente;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOPedido;
import ad.conectores_hibernate.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.conectores_hibernate.hmarort.database_config.DatabaseConfig;
import ad.conectores_hibernate.hmarort.database_config.DatabaseConfigFactory;
import ad.conectores_hibernate.hmarort.database_config.DatabaseProperties;
import ad.conectores_hibernate.hmarort.database_config.DatabaseType;
import ad.conectores_hibernate.hmarort.factory.DAOFactory;
import ad.conectores_hibernate.hmarort.models.Cliente;
import ad.conectores_hibernate.hmarort.models.Pedido;
import ad.conectores_hibernate.hmarort.models.ZonaEnvio;

/**
 * Implementación manual de la interfaz de usuario que permite la interacción
 * directa con el usuario a través de la consola.
 */
public class UIManualImpl implements UI {
    private DAOCliente daoCliente;
    private DAOPedido daoPedido;
    private DAOZonaEnvio daoZonaEnvio;
    private Scanner scanner;
    private DatabaseConfig dbConfig;

    /**
     * Constructor que inicializa la configuración de la base de datos, los DAOs y
     * el scanner.
     */
    public UIManualImpl() {
        DatabaseProperties properties = new DatabaseProperties.Builder()
                .url("src/main/resources/pedidos.db")
                .build();

        dbConfig = DatabaseConfigFactory.createConfig(DatabaseType.SQLITE, properties);

        DAOFactory factory = DAOFactory.getDAOFactory(DatabaseType.SQLITE, dbConfig);

        daoCliente = factory.createClienteDAO();
        daoPedido = factory.createPedidoDAO();
        daoZonaEnvio = factory.createZonaEnvioDAO();
        scanner = new Scanner(System.in);
    }

    /**
     * Inicia el bucle principal de interacción con el usuario.
     */
    @Override
    public void iniciar() {
        while (true) {
            int opcion = mostrarMenu();
            try {
                switch (opcion) {
                    case 1 -> gestionarClientes();
                    case 2 -> gestionarPedidos();
                    case 3 -> consultarZonasEnvio();
                    case 4 -> consultarPedidosCliente();
                    case 0 -> {
                        mostrarMensaje("\n📍 Saliendo del sistema...");
                        return;
                    }
                }
            } catch (Exception e) {
                mostrarError("❌ Error: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra el menú principal y retorna la opción seleccionada por el usuario.
     * 
     * @return La opción seleccionada
     * @throws Exception Si ocurre un error al leer la entrada del usuario
     */
    @Override
    public int mostrarMenu() {
        System.out.println("\n╔═══════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN      ║");
        System.out.println("╠═══════════════════════════╣");
        System.out.println("║ 1. Gestionar Clientes     ║");
        System.out.println("║ 2. Gestionar Pedidos      ║");
        System.out.println("║ 3. Consultar Zonas Envío  ║");
        System.out.println("║ 4. Pedidos por Cliente    ║");
        System.out.println("║ 0. Salir                  ║");
        System.out.println("╚═══════════════════════════╝");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    /**
     * Gestiona las operaciones CRUD de clientes.
     * 
     * @throws Exception Si ocurre un error durante la gestión de clientes
     */
    @Override
    public void gestionarClientes() throws Exception {
        System.out.println("\n--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Agregar Cliente");
        System.out.println("2. Modificar Cliente");
        System.out.println("3. Eliminar Cliente");
        System.out.println("4. Listar Clientes");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        switch (opcion) {
            case 1 -> agregarCliente();
            case 2 -> modificarCliente();
            case 3 -> eliminarCliente();
            case 4 -> listarClientes();
        }
    }

    /**
     * Agrega un nuevo cliente a la base de datos.
     * 
     * @throws Exception
     */
    private void agregarCliente() throws Exception {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("ID Zona Envío: ");
        int idZona = scanner.nextInt();

        Cliente cliente = new Cliente(0, nombre, email, telefono, idZona);
        daoCliente.agregarCliente(cliente);
        mostrarMensaje("Cliente agregado exitosamente");
    }

    /**
     * Modifica un cliente existente en la base de datos.
     * 
     * @throws Exception
     */
    private void modificarCliente() throws Exception {
        System.out.print("ID de Cliente a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Cliente cliente = daoCliente.obtenerClientePorId(id);
        if (cliente == null) {
            mostrarError("Cliente no encontrado");
            return;
        }

        System.out.print("Nuevo nombre (enter para mantener actual): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty())
            cliente.setNombre(nombre);

        System.out.print("Nuevo email (enter para mantener actual): ");
        String email = scanner.nextLine();
        if (!email.isEmpty())
            cliente.setEmail(email);

        System.out.print("Nuevo teléfono (enter para mantener actual): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty())
            cliente.setTelefono(telefono);

        daoCliente.actualizarInformacionCliente(cliente);
        mostrarMensaje("Cliente modificado exitosamente");
    }

    /**
     * Elimina un cliente de la base de datos.
     * 
     * @throws Exception
     */
    private void eliminarCliente() throws Exception {
        System.out.print("ID de Cliente a eliminar: ");
        int id = scanner.nextInt();
        daoCliente.eliminarClientePorId(id);
        mostrarMensaje("Cliente eliminado exitosamente");
    }

    /**
     * Lista todos los clientes de la base de datos.
     * 
     * @throws Exception
     */
    private void listarClientes() throws Exception {
        List<Cliente> clientes = daoCliente.obtenerTodosLosClientes();
        clientes.forEach(c -> System.out.println(
                "ID: " + c.getId() +
                        ", Nombre: " + c.getNombre() +
                        ", Email: " + c.getEmail()));
    }

    /**
     * Gestiona las operaciones CRUD de pedidos.
     * 
     * @throws Exception
     */
    @Override
    public void gestionarPedidos() throws Exception {
        System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
        System.out.println("1. Agregar Pedido");
        System.out.println("2. Modificar Pedido");
        System.out.println("3. Eliminar Pedido");
        System.out.println("4. Listar Pedidos");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> agregarPedido();
            case 2 -> modificarPedido();
            case 3 -> eliminarPedido();
            case 4 -> listarPedidos();
        }
    }

    /**
     * Agrega un nuevo pedido a la base de datos.
     * 
     * @throws Exception
     */
    private void agregarPedido() throws Exception {
        System.out.print("ID de Cliente: ");
        int idCliente = scanner.nextInt();
        System.out.print("Importe del Pedido: ");
        double importe = scanner.nextDouble();

        Pedido pedido = new Pedido(0, LocalDate.now(), importe, idCliente);
        daoPedido.agregarPedido(pedido);
        mostrarMensaje("Pedido agregado exitosamente");
    }

    /**
     * Modifica un pedido existente en la base de datos.
     * 
     * @throws Exception
     */
    private void modificarPedido() throws Exception {
        System.out.print("ID de Pedido a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Pedido pedido = daoPedido.obtenerPedidoPorId(id);
        if (pedido == null) {
            mostrarError("Pedido no encontrado");
            return;
        }

        System.out.print("Nuevo importe (enter para mantener actual): ");
        String importeStr = scanner.nextLine();
        if (!importeStr.isEmpty()) {
            pedido.setImporte(Double.parseDouble(importeStr));
        }

        daoPedido.actualizarPedido(pedido);
        mostrarMensaje("Pedido modificado exitosamente");
    }

    /**
     * Elimina un pedido de la base de datos.
     * 
     * @throws Exception
     */
    private void eliminarPedido() throws Exception {
        System.out.print("ID de Pedido a eliminar: ");
        int id = scanner.nextInt();
        daoPedido.eliminarPedido(id);
        mostrarMensaje("Pedido eliminado exitosamente");
    }

    /**
     * Lista todos los pedidos de la base de datos.
     * 
     * @throws Exception
     */
    private void listarPedidos() throws Exception {
        List<Pedido> pedidos = daoPedido.obtenerTodosLosPedidos();
        pedidos.forEach(p -> System.out.println(
                "ID: " + p.getId() +
                        ", Fecha: " + p.getFecha() +
                        ", Importe: " + p.getImporte() +
                        ", Cliente ID: " + p.getIdCliente()));
    }

    /**
     * Muestra información sobre las zonas de envío disponibles.
     * 
     * @throws Exception
     */
    @Override
    public void consultarZonasEnvio() throws Exception {
        List<ZonaEnvio> zonas = daoZonaEnvio.obtenerTodasLasZonas();
        zonas.forEach(z -> System.out.println(
                "ID: " + z.getId() +
                        ", Nombre: " + z.getNombre() +
                        ", Precio: " + z.getPrecio()));
    }

    /**
     * Consulta y muestra los pedidos de un cliente específico.
     * 
     * @throws Exception
     */
    @Override
    public void consultarPedidosCliente() throws Exception {
        System.out.print("ID de Cliente: ");
        int idCliente = scanner.nextInt();

        List<Pedido> pedidos = daoPedido.obtenerPedidosPorCliente(idCliente);
        double totalFacturado = daoCliente.calcularFacturacionTotalCliente(idCliente);

        System.out.println("Pedidos del Cliente:");
        pedidos.forEach(p -> System.out.println(
                "ID: " + p.getId() +
                        ", Fecha: " + p.getFecha() +
                        ", Importe: " + p.getImporte()));
        System.out.println("Total Facturado: " + totalFacturado);
    }

    /**
     * Muestra un mensaje en la consola.
     * 
     * @param mensaje El mensaje a mostrar
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("✅ " + mensaje);
    }

    /**
     * Muestra un mensaje de error en la consola.
     * 
     * @param mensaje El mensaje de error a mostrar
     */
    @Override
    public void mostrarError(String mensaje) {
        System.err.println("❌ " + mensaje);
    }
}
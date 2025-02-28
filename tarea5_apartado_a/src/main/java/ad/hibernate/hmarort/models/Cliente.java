package ad.hibernate.hmarort.models;
import jakarta.persistence.*;

/**
 * Clase que representa un cliente en la base de datos.
 * 
 * <p>Esta entidad almacena la información básica de los clientes
 * como su identificador, nombre, email, teléfono y la zona a la que pertenecen.
 * Es utilizada para mapear la tabla "Clientes" en la base de datos.</p>
 * 
 * @author Sistema de Gestión de Clientes
 * @version 1.0
 * @since 2025-02-28
 */
@Entity
@Table(name = "Clientes")
public class Cliente {
    
    /**
     * Identificador único del cliente.
     * Se genera automáticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int idCliente;
    
    /**
     * Nombre completo del cliente.
     * Este campo es obligatorio y tiene una longitud máxima de 100 caracteres.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    /**
     * Dirección de correo electrónico del cliente.
     * Este campo es obligatorio y tiene una longitud máxima de 100 caracteres.
     */
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    
    /**
     * Número de teléfono del cliente.
     * Tiene una longitud máxima de 15 caracteres y puede ser nulo.
     */
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    /**
     * Identificador de la zona geográfica a la que pertenece el cliente.
     * Este campo es obligatorio y hace referencia a la entidad Zona.
     */
    @Column(name = "id_zona", nullable = false)
    private int idZona;
    
    /**
     * Constructor completo para crear un nuevo cliente con todos sus atributos.
     *
     * @param idCliente Identificador único del cliente
     * @param nombre Nombre completo del cliente
     * @param email Dirección de correo electrónico del cliente
     * @param telefono Número de teléfono del cliente
     * @param idZona Identificador de la zona geográfica a la que pertenece el cliente
     */
    public Cliente(int idCliente, String nombre, String email, String telefono, int idZona) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.idZona = idZona;
    }
    
    /**
     * Constructor vacío requerido por JPA.
     * Inicializa una instancia de Cliente sin asignar valores a sus atributos.
     */
    public Cliente() {
    }
    
    /**
     * Obtiene el identificador único del cliente.
     *
     * @return Identificador único del cliente
     */
    public int getIdCliente() {
        return idCliente;
    }
    
    /**
     * Establece el identificador único del cliente.
     *
     * @param idCliente El nuevo identificador del cliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    /**
     * Obtiene el nombre completo del cliente.
     *
     * @return Nombre completo del cliente
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del cliente, eliminando espacios en blanco al inicio y al final.
     * Este valor no debe estar vacío y no debe superar los 100 caracteres.
     *
     * @param nombre El nuevo nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre.trim();
    }
    
    /**
     * Obtiene la dirección de correo electrónico del cliente.
     *
     * @return Dirección de correo electrónico del cliente
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Establece la dirección de correo electrónico del cliente, eliminando espacios en blanco al inicio y al final.
     * Este valor no debe estar vacío y debe tener un formato de email válido.
     *
     * @param email La nueva dirección de correo electrónico del cliente
     */
    public void setEmail(String email) {
        this.email = email.trim();
    }
    
    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return Número de teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }
    
    /**
     * Establece el número de teléfono del cliente, eliminando espacios en blanco al inicio y al final.
     * Idealmente, este valor debe contener 9 dígitos para números de teléfono españoles.
     *
     * @param telefono El nuevo número de teléfono del cliente
     */
    public void setTelefono(String telefono) {
        if (telefono != null) {
            telefono = telefono.trim();
        }
        this.telefono = telefono;
    }
    
    /**
     * Obtiene el identificador de la zona geográfica a la que pertenece el cliente.
     *
     * @return Identificador de la zona geográfica
     */
    public int getIdZona() {
        return idZona;
    }
    
    /**
     * Establece el identificador de la zona geográfica a la que pertenece el cliente.
     * Este valor debe ser un número positivo.
     *
     * @param idZona El nuevo identificador de la zona geográfica
     */
    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }
    
    /**
     * Devuelve una representación en forma de cadena de texto del cliente.
     * La representación incluye el nombre del cliente y su dirección de correo electrónico.
     *
     * @return Cadena de texto con el formato "nombre (email)"
     */
    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}
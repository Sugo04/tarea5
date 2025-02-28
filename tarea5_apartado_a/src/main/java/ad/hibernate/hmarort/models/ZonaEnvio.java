/**
 * Clase que representa una zona de envío en un sistema de acceso a datos.
 * 
 * <p>Esta entidad mapea la tabla "Zonas_Envio" en la base de datos y almacena
 * información sobre las diferentes zonas geográficas de envío, incluyendo
 * su identificador único, nombre descriptivo y la tarifa asociada para envíos
 * a dicha zona.</p>
 *
 * @author Sistema de Gestión de Envíos
 * @version 1.0
 * @since 2025-02-28
 */
package ad.hibernate.hmarort.models;
import jakarta.persistence.*;

@Entity
@Table(name = "Zonas_Envio")
public class ZonaEnvio {
    
    /**
     * Identificador único de la zona de envío.
     * 
     * <p>Este campo es la clave primaria de la entidad y se genera automáticamente
     * mediante una estrategia de identidad en la base de datos.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zona")
    private int idZona;
   
    /**
     * Nombre descriptivo de la zona de envío.
     * 
     * <p>Este campo es obligatorio y tiene una longitud máxima de 100 caracteres.
     * Identifica de manera clara y única cada zona geográfica de envío.</p>
     */
    @Column(name = "nombre_zona", nullable = false, length = 100)
    private String nombreZona;
   
    /**
     * Tarifa asociada al envío a esta zona.
     * 
     * <p>Este campo es obligatorio y representa el costo base para realizar
     * envíos a la zona especificada. El valor debe ser positivo y no puede
     * exceder 999.99 unidades monetarias.</p>
     */
    @Column(name = "tarifa_envio", nullable = false)
    private double tarifaEnvio;

    /**
     * Constructor que inicializa una nueva instancia de ZonaEnvio con los valores proporcionados.
     * 
     * <p>Crea una zona de envío completa con todos sus atributos.</p>
     *
     * @param idZona        El identificador único de la zona de envío
     * @param nombreZona    El nombre descriptivo de la zona de envío
     * @param tarifaEnvio   La tarifa asociada al envío a esta zona
     */
    public ZonaEnvio(int idZona, String nombreZona, double tarifaEnvio) {
        this.idZona = idZona;
        this.nombreZona = nombreZona;
        this.tarifaEnvio = tarifaEnvio;
    }

    /**
     * Constructor vacío que crea una instancia de ZonaEnvio sin inicializar valores.
     * 
     * <p>Este constructor es requerido por JPA para la creación de instancias
     * durante la carga de datos desde la base de datos.</p>
     */
    public ZonaEnvio() {
    }

    /**
     * Obtiene el identificador único de la zona de envío.
     *
     * @return El identificador único de la zona de envío
     */
    public int getIdZona() {
        return idZona;
    }

    /**
     * Establece el identificador único de la zona de envío.
     *
     * @param idZona El nuevo identificador único de la zona de envío
     */
    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    /**
     * Obtiene el nombre descriptivo de la zona de envío.
     *
     * @return El nombre descriptivo de la zona de envío
     */
    public String getNombreZona() {
        return nombreZona;
    }

    /**
     * Establece el nombre descriptivo de la zona de envío, realizando validaciones.
     * 
     * <p>Este método valida que:
     * <ul>
     *   <li>El nombre no sea nulo ni esté vacío</li>
     *   <li>El nombre no exceda los 100 caracteres</li>
     * </ul>
     * Además, elimina los espacios en blanco al inicio y al final.</p>
     *
     * @param nombreZona El nuevo nombre descriptivo de la zona de envío
     * @throws IllegalArgumentException Si el nombre es nulo, vacío o excede la longitud permitida
     */
    public void setNombreZona(String nombreZona) {
        if (nombreZona == null || nombreZona.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de zona no puede estar vacío");
        }
        if (nombreZona.length() > 100) {
            throw new IllegalArgumentException("El nombre de zona no puede exceder 100 caracteres");
        }
        this.nombreZona = nombreZona.trim();
    }

    /**
     * Obtiene la tarifa asociada al envío a esta zona.
     *
     * @return La tarifa asociada al envío a esta zona
     */
    public double getTarifaEnvio() {
        return tarifaEnvio;
    }

    /**
     * Establece la tarifa asociada al envío a esta zona, realizando validaciones.
     * 
     * <p>Este método valida que:
     * <ul>
     *   <li>La tarifa no sea negativa</li>
     *   <li>La tarifa no exceda el valor máximo permitido de 999.99</li>
     * </ul></p>
     *
     * @param tarifaEnvio La nueva tarifa asociada al envío a esta zona
     * @throws IllegalArgumentException Si la tarifa es negativa o excede el valor máximo permitido
     */
    public void setTarifaEnvio(double tarifaEnvio) {
        if (tarifaEnvio < 0) {
            throw new IllegalArgumentException("La tarifa no puede ser negativa");
        }
        if (tarifaEnvio > 999.99) {
            throw new IllegalArgumentException("La tarifa excede el máximo permitido");
        }
        this.tarifaEnvio = tarifaEnvio;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto ZonaEnvio.
     * 
     * <p>La representación incluye el nombre de la zona y su tarifa asociada
     * con formato de dos decimales y el símbolo de euro.</p>
     *
     * @return Una cadena con el formato "nombreZona (Tarifa: XX.XX€)"
     */
    @Override
    public String toString() {
        return String.format("%s (Tarifa: %.2f€)", nombreZona, tarifaEnvio);
    }
}
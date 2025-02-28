package ad.conectores_hibernate.hmarort.ui;

import ad.conectores_hibernate.hmarort.database_config.DatabaseType;

public class UIFactory {
    /**
     * Enumeración que define los tipos de interfaz de usuario disponibles.
     */
    public enum TipoUI {
        AUTO,
        MANUAL
    }

    /**
     * Crea una instancia de UI según el tipo especificado.
     * 
     * @param tipo El tipo de interfaz de usuario a crear
     * @return La instancia de UI creada
     */
    public static UI creaUI(String tipo, DatabaseType typ) {
        switch (tipo) {
            case "MANUAL":
                return new UIManualImpl(typ);
            case "AUTO":
                return new UIAutoImpl(typ);
            default:
                return null;
        }
    }
}

# Proyecto: tarea5_apartado_b

## Descripción
Este proyecto es una implementación de un sistema de gestión de pedidos utilizando Hibernate y SQLite como opciones de persistencia. Implementa el patrón DAO (Data Access Object) para gestionar clientes, pedidos y zonas de envío, con soporte para dos tipos de almacenamiento: SQLite y Hibernate.

La información de este README se obtuvo mediante el análisis del código con Repomix, asegurando una visión detallada de la estructura del proyecto.

## Estructura del Proyecto
```
tarea5_apartado_b/
  .vscode/               # Configuración del entorno de desarrollo
    launch.json          # Configuraciones de depuración
    settings.json        # Configuraciones del entorno
  src/
    main/
      java/
        ad/
          conectores_hibernate/
            hmarort/
              dao/                  # Implementaciones DAO para Hibernate y SQLite
                implementacion/
                  hibernate/        # DAO usando Hibernate
                  sqlite/           # DAO usando SQLite
                interfaces/         # Interfaces DAO
              database_config/       # Configuración de bases de datos y fábrica de configuraciones
              factory/               # Implementación del patrón Factory para DAOs
              models/                # Entidades del sistema: Cliente, Pedido, ZonaEnvio
              ui/                    # Implementación de la interfaz de usuario (automática y manual)
              utils/                 # Utilidades del sistema como SessionManager y QueryUtil
              Main.java               # Punto de entrada del programa
      resources/
        hibernate.cfg.xml             # Configuración de Hibernate
        logback.xml                    # Configuración de logs
        pedidos.sql                     # Script SQL de inicialización de la base de datos
  pom.xml                               # Archivo de configuración para Maven
```

## Instalación y Configuración
1. **Clonar el repositorio**
   ```bash
   git clone <repositorio>
   cd tarea5_apartado_b
   ```

2. **Configurar la base de datos**
   - Para SQLite: Asegurarse de que `pedidos.sql` está disponible en `src/main/resources/`.
   - Para Hibernate: Configurar `hibernate.cfg.xml` en `src/main/resources/` con los datos de conexión adecuados.

3. **Compilar y ejecutar**
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="ad.conectores_hibernate.hmarort.Main"
   ```

## Uso
El programa puede ejecutarse en dos modos:
- **Modo Automático**: Procesa datos automáticamente sin intervención del usuario.
- **Modo Manual**: Permite la interacción del usuario para gestionar pedidos y clientes.

Ejemplo de ejecución con SQLite en modo automático:
```bash
java -jar target/tarea5_apartado_b.jar -i AUTO -db sqlite
```

Ejemplo de ejecución con Hibernate en modo manual:
```bash
java -jar target/tarea5_apartado_b.jar -i MANUAL -db hibernate
```

## Tecnologías Utilizadas
- **Java 11+**
- **Hibernate** como ORM
- **SQLite** como base de datos alternativa
- **Maven** para la gestión de dependencias
- **JUnit** para pruebas unitarias
- **SLF4J + Logback** para logging

## Autores
- **hmarort** (Autor principal)

## Licencia
Este proyecto está bajo la licencia MIT. Puedes modificarlo y distribuirlo según los términos de la licencia.


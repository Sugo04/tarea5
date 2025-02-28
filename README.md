# Ejercicio 5.1 - Héctor Martín Ortega

    Tome el ejercicio sobre pedidos de clientes que se resolvió en la unidad dedicada a conectores y resuélvalo usando Hibernate respetando escrupulosamente la interfaz que se ideó en la resolución de aquel ejercicio o, lo que es lo mismo, para un usuario final no debería haber ninguna diferencia entre la ejecución de una u otra aplicación, porque verá ambas iguales.

    Debe resolver dos veces el ejercicio usando dos estrategias distintas:

        Modifique el código del programa para acomodarse a la API de Hibernate/JPA.

        Respete escrupulosamente el programa ya escrito y limítese a:

            Anotar las clases del modelo para que pueda interpretarlas Hibernate.

            Escriba la traducción entre la API que usó en el ejercicio citado y la de Hibernate/JPA, a fin de que no haya que cambiar ninguna línea del programa (salvo las importaciones, claro está).

La estructura del proyecto ha terminado siendo la siguiente:
```
C:.
├───.vscode
├───tarea5_apartado_a
│   ├───.vscode
│   ├───docs
│   │   └───apidocs
│   │       ├───ad
│   │       │   └───hibernate
│   │       │       └───hmarort
│   │       │           ├───class-use
│   │       │           ├───dao
│   │       │           │   ├───implementacion
│   │       │           │   │   └───class-use
│   │       │           │   └───interfaces
│   │       │           │       └───class-use
│   │       │           ├───database_config
│   │       │           │   └───class-use
│   │       │           ├───factory
│   │       │           │   └───class-use
│   │       │           ├───models
│   │       │           │   └───class-use
│   │       │           ├───ui
│   │       │           │   └───class-use
│   │       │           └───utils
│   │       │               └───class-use
│   │       ├───legal
│   │       ├───resource-files
│   │       │   └───fonts
│   │       └───script-files
│   ├───public
│   │   └───loggers
│   ├───src
│   │   └───main
│   │       ├───java
│   │       │   └───ad
│   │       │       └───hibernate
│   │       │           └───hmarort
│   │       │               ├───dao
│   │       │               │   ├───implementacion
│   │       │               │   └───interfaces
│   │       │               ├───database_config
│   │       │               ├───factory
│   │       │               ├───models
│   │       │               ├───ui
│   │       │               └───utils
│   │       └───resources
│   └───target
│       ├───classes
│       │   └───ad
│       │       └───hibernate
│       │           └───hmarort
│       │               ├───dao
│       │               │   ├───implementacion
│       │               │   └───interfaces
│       │               ├───database_config
│       │               ├───factory
│       │               ├───models
│       │               ├───ui
│       │               └───utils
│       ├───javadoc-bundle-options
│       └───test-classes
└───tarea5_apartado_b
    ├───.vscode
    ├───docs
    │   └───apidocs
    │       ├───ad
    │       │   └───conectores_hibernate
    │       │       └───hmarort
    │       │           ├───class-use
    │       │           ├───dao
    │       │           │   ├───implementacion
    │       │           │   │   ├───hibernate
    │       │           │   │   │   └───class-use
    │       │           │   │   └───sqlite
    │       │           │   │       └───class-use
    │       │           │   └───interfaces
    │       │           │       └───class-use
    │       │           ├───database_config
    │       │           │   └───class-use
    │       │           ├───factory
    │       │           │   └───class-use
    │       │           ├───models
    │       │           │   └───class-use
    │       │           ├───ui
    │       │           │   └───class-use
    │       │           └───utils
    │       │               └───class-use
    │       ├───legal
    │       ├───resource-files
    │       │   └───fonts
    │       └───script-files
    ├───public
    │   └───loggers
    ├───src
    │   └───main
    │       ├───java
    │       │   └───ad
    │       │       └───conectores_hibernate
    │       │           └───hmarort
    │       │               ├───dao
    │       │               │   ├───implementacion
    │       │               │   │   ├───hibernate
    │       │               │   │   └───sqlite
    │       │               │   └───interfaces
    │       │               ├───database_config
    │       │               ├───factory
    │       │               ├───models
    │       │               ├───ui
    │       │               └───utils
    │       └───resources
    └───target
        ├───classes
        │   └───ad
        │       └───conectores_hibernate
        │           └───hmarort
        │               ├───dao
        │               │   ├───implementacion
        │               │   │   ├───hibernate
        │               │   │   └───sqlite
        │               │   └───interfaces
        │               ├───database_config
        │               ├───factory
        │               ├───models
        │               ├───ui
        │               └───utils
        ├───javadoc-bundle-options
        └───test-classes
```
El proyecto se encuentra dividido en dos proyectos aunque tecnicamente estando todo bien hecho, el 2º cuenta con el primero realmente. Pero prefiero se precabido y subir ambos.
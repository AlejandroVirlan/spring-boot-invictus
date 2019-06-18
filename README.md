# API REST Invictus en desarrollo con Spring Framework 5

Este proyecto en desarrollo consiste en una API REST para una asociación de juegos de mesa,   
con el fin de tener una aplicación desarrollada en Angular por la parte del cliente que se conecte   
a esta API realizada con Spring Framework 5.

### FUNCIONES
---
* Tener un control de los socios que se han registrado e inician sesión.
* Poder controlar las reservas de los juegos de mesa que se soliciten.
* Tener un listado del libro de socios.
* Revisar los gastos y beneficios generados.

### IMPLEMENTACIONES
---
* Login de socios con JWT (Json Web Token)
* Logout
* Registro de socios
* CRUDs:
    * Usuarios
    * Socios
    * Juegos de mesa
    * Reservas
    * Transacciones

### REQUISITOS
---
Para que el proyecto pueda ejecutarse y no le falte ninguna dependencia, hay que tener instalado:

* **[JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).**
* **[Spring Tools 4](https://spring.io/tools).**
* **[Lombok](https://projectlombok.org/download).**

### INSTRUCCIONES
---
Antes de ejecutar el proyecto, debe de crearse la base de datos.

En este caso es una base de datos en MySQL llamada **db_invictus**.

Editar los siguientes valores en el archivo de configuración del proyecto (_application.properties_)  
que se encuentran en la siguiente ubicación :

> spring-boot-invictus\src\main\resources\application.properties

por los propios que tenga en su ordenardor. 

Valores a editar (señalados en **negrita**) :

Nombre de usuario de la base de datos MySQL  
spring.datasource.username=**root**  

Contraseña de la base de datos MySQL  
spring.datasource.password=**admin**  

Opción para crear la base de datos, insertar registros, actualizar registros  
spring.jpa.hibernate.ddl-auto=**update**

NOTA: Si es la primera vez que se ejecuta el proyecto, hay que cambiar la opción **update** de la siguiente línea **spring.jpa.hibernate.ddl-auto** por la opción **create**. Una vez que se haya ejecutado, cambiar a la opción **update**
para mantener los registros y actualizarlos.

Una vez configurado todos los parámetros necesarios para que funcione la aplicación, puede ejecutarla  
y comenzar a hacer pruebas con una herramienta que permite realizar peticiones HTTP a cualquier API,  
como por ejemplo ["_Postman_"](https://www.getpostman.com/).

Al ejecutarla, se creará la base de datos con registros de ejemplo:

* usuario: Usuario1, contraseña: 12345678. Roles (Administrador y usuario).
* usuario: Usuario2, contraseña: 12345678. Roles (Usuario).

### PRUEBAS
---
Para poder realizar las pruebas en la API, tienes que estar autenticado antes, ya que sólo se puede  
acceder al registro y al login sin estar autenticado.  

Sino aparecerá el siguiente error de no estar autorizado:

```json
{  
    "timestamp": "2019-01-31T16:56:04.039+0000",  
    "status": 401,  
    "error": "Unauthorized",  
    "message": "Error -> No autorizado",  
    "path": "/api/usuarios"  
}
```

Para evitar esto, lo que debemos hacer primero es crear un socio en formato **JSON**, para ello configuramos el párametro **application/json**   
de la cabecera "_Content-Type_" y luego por petición **POST** llamamos a la siguiente URL: `http://localhost:8094/api/auth/signup`.   
La estructura de datos para crear un socio y el resultado de las configuraciones, se muestran en la siguiente imagen:

Registro/Sign Up (POST `http://localhost:8094/api/auth/signup`)

![Registro](https://github.com/AlejandroVirlan/spring-boot-invictus/blob/master/registro.png)


lo que debemos hacer es copiar el **_token de autenticación_** que nos devuelva la petición al loguearnos   
y ponerlo en el parámetro de la cabecera "_Authentication_" junto a la palabra "**_Bearer_**" (Esquema de autenticación HTTP) y un espacio,   
además del parámetro de **application/json** de la cabecera "_Content-Type_", como se muestra en las siguientes imágenes: 

Login (POST `http://localhost:8094/api/auth/signin`)

![Login](https://github.com/AlejandroVirlan/spring-boot-invictus/blob/master/login.png)

Listado de juegos de mesa (GET `http://localhost:8094/api/juegos`)

![Token](https://github.com/AlejandroVirlan/spring-boot-invictus/blob/master/token.png)
 
Por ahora estas son todas las URL disponibles para realizar las pruebas de los CRUDs:

|                  | GET ALL                            | GET                                            | POST                               | PUT                                            | DELETE                                         |
| -----------      | -----------                        | -----------                                    |-----------                         | -----------                                    | -----------                                    |
| Usuarios         | `http://localhost:8094/api/usuarios`| `http://localhost:8094/api/usuarios/{idusuario}`| `http://localhost:8094/api/usuarios`| `http://localhost:8094/api/usuarios/{idusuario}` | * X |
| Socios           | `http://localhost:8094/api/socios`  | `http://localhost:8094/api/socios/{idsocio}`    | `http://localhost:8094/api/socios`  | `http://localhost:8094/api/socios/{idsocio}`</br>** `http://localhost:8094/api/socios/socio/{idsocio}` |* X|
| Juegos           | `http://localhost:8094/api/juegos`  | `http://localhost:8094/api/juegos/{idjuego}`    | `http://localhost:8094/api/juegos`  | `http://localhost:8094/api/juegos/{idjuego}`</br>** `http://localhost:8094/api/juegos/juego/{idjuego}` | * X |
| Reservas         | `http://localhost:8094/api/reservas`| `http://localhost:8094/api/reservas/{idreserva}`| `http://localhost:8094/api/reservas/socio/{idsocio}/juego/{idjuego}`| `http://localhost:8094/api/reservas/{idreserva}` | `http://localhost:8094/api/reservas/{idreserva}` |
| Transacciones    | `http://localhost:8094/api/transacciones`</br>`http://localhost:8094/api/transacciones/fechas` | `http://localhost:8094/api/transacciones/{idtransaccion}` | `http://localhost:8094/api/transacciones/socio/{idsocio}` | `http://localhost:8094/api/transacciones/{idtransaccion}` | `http://localhost:8094/api/transacciones/{idtransaccion}`    |


*: No hay ninguna URL en el apartado de DELETE (marcadas con X) de las clases Usuarios, Socios y Juegos porque no se deben de borrar,   
&nbsp;&nbsp;&nbsp;debido a que si se borra un socio hay que borrar todas sus relaciones y habría que borrar los registros de transacciones de ese socio,    
&nbsp;&nbsp;&nbsp;y por lo tanto las cuentas ya no serían reales, por eso hay un atributo "_enabled_" que indica si el socio se ha dado de baja o no,    
&nbsp;&nbsp;&nbsp;para que todo lo que haya realizado ese socio se quede guardado, por si en algún futuro, volviera a darse de alta.

&nbsp;&nbsp;&nbsp;El motivo de la clase Juegos se debe al almacenamiento de registros históricos de los mismos para que así se puedan volver a recuperar,    
&nbsp;&nbsp;&nbsp;por ese motivo existe un atributo "_vendido_", para indicar a los socios, si está disponible o no para poder reservarlo, pero si quieren     
&nbsp;&nbsp;&nbsp;obtener información sobre alguno, puede hacerlo sin problema. Además, es mejor conservar sus datos como registros históricos, por si en un     
&nbsp;&nbsp;&nbsp;futuro se realizan implementaciones con fines de estudios estadísticos.

**: En el apartado PUT de la clase Socios (`http://localhost:8094/api/socios/socio/{idsocio}`) y de la clase Juegos (`http://localhost:8094/api/juegos/juego/{idjuego}`)    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;existen estas URL para poder dar de baja a socios modificando el valor del atributo "_enabled_" y para marcar juegos como vendidos modificando el valor         
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;del atributo "_vendido_".   

La siguiente URL (GET `http://localhost:8094/api/transacciones/fechas`) es para buscar las transacciones en la base de datos entre dos fechas, pasando un json    
como el que aparece a continuación, con el formato de fecha (yyyy-MM-dd):

```json
{
	"fechaInicio": "2019-01-15",
	"fechaFin": "2019-01-16"
}
```

Al estar en desarrollo, habrán cambios que irán siendo documentados y actualizados, entre ellos en la clase Usuarios, porque varias operaciones   
para esta clase, se realizan a la vez a través de la clase Socio y se trata más bien como una clase de pruebas. 

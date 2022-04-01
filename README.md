# Aprende Spring Cloud Netflix

## Introducción
Los microservicios están de moda y múltiples empresas han optado por usarlos, gracias a un enfoque más flexible y escalable para el desarrollo e implementación de aplicaciones que siguen creciendo rápidamente.

Pero como todo gran avance técnologico, la implementación de aplicaciones que utilizan la arquitectura de microservicios puede generar grandes desafíos como el descubrimiento de servicios, el equilibrio de carga, la tolerancia a fallas, etc.

Netflix Open Source Software (Netflix OSS) incluye un conjunto de herramientas / componentes de software creados por Netflix para resolver los problemas antes mencionados. 

Así que, explicaremos de manera rápida cómo se puede integrar Netflix Zuul (API Gateway), Netflix Eureka (para detección de servicios), Netflix Ribbon (para balanceo de carga) y Netflix Hystrix (para monitoreo y tolerancia a fallas) a nuestra arquitectura de microservicios.


## Monolíticos vs Microservicios

### Arquitectura monolítica
Las aplicaciones monolíticas o sistemas monolíticos, tienen todas o la mayoría de sus funcionalidades en un único proceso o contenedor y está formada por capas internas o bibliotecas. Aunque son fáciles de desarrollar, una aplicación que aglutina toda su funcionalidad no es la mejor opción, en el caso de que se tengan aspiraciones de crecimiento complejo, más usuarios, más desarrolladores…

Además, debes tener en cuenta que un gran inconveniente que caracteriza a las aplicaciones monolíticas, es que en el momento que se quiera realizar un nuevo despliegue, se debería relanzar todo el sistema de nuevo.

Ventajas de los monolitos

 - Fácil desarrollo
 - Pruebas sencillas, con todo lo que necesita en un solo paquete, desde la interfaz de usuario hasta la base de datos.
 - Fácil implementación, todo lo que tiene que hacer es empaquetar todo el proyecto e implementar un archivo binario
 - Fácil de escalar horizontalmente, agregando varias instancias en varias máquinas.

Desventajas de los monolitos

- La aplicación puede volverse muy grande, muy rápidamente, y puede ser difícil para un desarrollador mantener una imagen mental de lo que está haciendo la aplicación.
- El tamaño de la aplicación puede ralentizar los tiempos de inicio.
- En cada iteración, debe implementar toda la aplicación, incluso si solo hay cambios en una pequeña parte de la aplicación.
- Cada cambio puede introducir más errores. El impacto no se entiende completamente debido a la imagen mental borrosa.
- Confiabilidad: un error de ruptura en una parte de la aplicación puede derribar toda la aplicación y no solo una funcionalidad.
- A medida que el proyecto se vuelve más grande y el equipo aumenta, a veces se hace difícil para los desarrolladores trabajar de forma independiente y sin conflictos.
- Los monolitos no son flexibles en términos de diversidad de equipos o tecnologías utilizadas y son escépticos acerca de la adopción de nuevas tecnologías o la actualización de bibliotecas, ya que cualquier cambio afectará a toda la aplicación a un costo tremendo.

### Arquitectura de Microservicios

Una arquitectura de microservicios consta de una colección de servicios autónomos y pequeños. Cada uno de servicio es independiente y debe implementar una funcionalidad de negocio individual dentro de un contexto delimitado. Un contexto delimitado es una división natural de una empresa y proporciona un límite explícito dentro del cual existe un modelo de dominio.

Netflix fue uno de los primeros en adoptar microservicios. Allá por 2009, la empresa comenzó la transición de un modelo de desarrollo tradicional con 100 ingenieros que producían una aplicación monolítica de alquiler de DVD a una arquitectura de microservicios con varios equipos pequeños que se encargaban del desarrollo integral de cientos de microservicios en tándem. Hicieron todo esto para mantenerse al día con su creciente demanda. Esto resultó en que los ingenieros de Netflix implementaran código miles de veces al día.

![Building Microservices with Spring Boot & Netflix OSS now](https://www.pentalog.com/wp-content/uploads/2020/05/Building-Microservices-with-Spring-Boot-Netflix-OSS-now.jpg)

> Cuando un microservicio falla, los desarrolladores intervienen para solucionarlo sin afectar la integridad de todo el sistema.

Ventajas de los microservicios

 - Reducción de la complejidad de una aplicación al dividirla en problemas más pequeños. (Divide y vencerás)
 - Requiere equipos independientes y flexibles libres para elegir el mejor stack de tecnología que mejor se adapte a ellos y al producto.
 - Se integra fácilmente con entornos CI/CD.
 - Se puede escalar de forma independiente. Por lo tanto, si la empresa necesita escalar solo una función, los microservicios pueden escalar solo esa parte, no todo el monolito.
 - Cuando ocurre un error, afectará solo a uno o algunos sistemas, en lugar de a toda la aplicación.

Desventajas de los microservicios

 - Complejidad agregada con comunicación entre servicios, patrón de base de datos separado y pruebas.
 - Las pruebas de integración pueden ser más difíciles.
 - Es más difícil implementar cambios en varios módulos debido a la coordinación entre varios equipos.
 - La implementación es más difícil porque hay varios binarios, ***pero este proceso se facilita con las infraestructuras de nube modernas*.**

## ¿Qué es Netflix OSS?

Netflix OSS son una serie de herramientas / componentes de software para poder desarrollar de una manera fácil y rápida aplicaciones y servicios que implementen alguno de los patrones más comunes que se usan en sistemas distribuidos:

-   Configuración distribuida.
-   Registro y autoreconocimiento de servicios.
-   Enrutado.
-   Llamadas servicio a servicio.
-   Balanceo de carga.
-   Control de ruptura de comunicación con los servicios.
-   Clusterización.
-   Mensajería distribuida.

Lograr todos estos elementos que permitan orquestar toda una red de microservicios es complejo. Las herramientas de Netflix OSS lo convierten en una tarea sencilla.

Los ingenieros de Netflix desarrollaron un conjunto de soluciones para implementar cada una de esas características, para ello se basaron en la reutilización de mucho código existente, el resultado final es la solución de código abierto [Spring Cloud Netflix.](http://cloud.spring.io/spring-cloud-netflix)

Algunos componentes que revisaremos son:

-   **Descubrimiento de servicios y Eureka**

[Eureka](https://github.com/Netflix/eureka/wiki) es un servidor para el registro y localización de microservicios, balanceo de carga y tolerancia a fallos.  **La función de Eureka es registrar las diferentes instancias de microservicios existentes, su localización, estado, metadatos...**

### ¿Cómo funciona?

Cada microservicio, durante su arranque, se comunicará con el servidor Eureka para notificar que está disponible, dónde está situado, sus metadatos… De esta forma  **Eureka mantendrá en su registro la información de todos los microservicios del ecosistema**.

El nuevo microservicio continuará notificando a Eureka su estado cada 30 segundos, lo que denominan ‘heartbeats’. Si después de tres periodos Eureka no recibe notificación de dicho microservicio, lo eliminará del registro. De la misma forma una vez vueltos a recibir tres notificaciones considerará el servicio disponible de nuevo.

Cada cliente de Eureka podrá también recuperar el registro para localizar otros microservicios con los que necesite comunicarse. Dicha información de registro se cachea en el cliente.

Eureka  **se puede configurar para funcionar en modo cluster donde varias instancias “peer” intercambiarán su información**. Esto, junto al cacheo de la información de registro en los clientes da a Eureka una alta tolerancia a fallos.

-   **Balanceo de carga y Ribbon**


[Ribbon](https://github.com/Netflix/ribbon/wiki)  es una librería diseñada para la comunicación entre procesos en la nube que realiza balanceo de carga en el lado cliente. Cada uno de los balanceadores es parte de un conjunto de componentes que trabajan juntos para comunicarse con un servidor remoto bajo demanda.

Uno de los puntos clave de Ribbon es la posibilidad de integrarse con Eureka para el descubrimiento de las diferentes instancias de las que dispone un microservicio.

### ¿Cómo funciona?

Cuando necesitamos invocar un microservicio, en primera instancia definiremos nuestra petición a dicho microservicio identificándolo por el nombre con el que se registra en Eureka. No será necesario pues identificar una instancia concreta del microservicio, ni la máquina en la que se encuentra, su IP o puerto.

Ribbon utilizará el nombre de microservicio que hemos indicado y consultará el registro de Eureka para recuperar cuantas instancias hay de ese microservicio y en qué máquinas se encuentran.

Con dicha información Ribbon ejecutará el algoritmo de balanceo de carga que tenga definido (Round Robin por defecto) para determinar a qué instancia del microservicio invocar.

Una vez decidida que instancia invocar, Ribbon encapsulará la petición dentro de un “Hystrix Command” y la realizará. La gestión de la petición será entonces realizada por Hystrix.

-   **Tolerancia a fallos y Hystrix**


[Hystrix](https://github.com/Netflix/Hystrix/wiki) es una librería que implemente el patrón  [CircuitBreaker](http://martinfowler.com/bliki/CircuitBreaker.html). Hystrix nos permite gestionar las interacciones entre servicios en sistemas distribuidos añadiendo lógica de latencia y tolerancia a fallos.

Su finalidad es mejorar la fiabilidad global del sistema, para ello Hystrix aísla los puntos de acceso de los microservicios, impidiendo así los fallos en cascada a través de los diferentes componentes de la aplicación, proporcionando alternativas de “fallback”, gestionando timeouts, pools de hilos…

### ¿Cómo funciona?

Hystrix encapsula las peticiones a sistemas “externos” para gestionar diversos aspectos de éstas tales como: timeouts, estadísticas de éxito y fallo, semáforos, lógica de gestión de error, propagación de errores en cascada...

Así por ejemplo si una petición a un servicio alcanza un cierto límite (20 fallos en 5 segundos por defecto), Hystrix abrirá el circuito de forma que no se realizarán más peticiones a dicho servicio, lo que impedirá la propagación de los errores en cascada.

-   **Patrón de diseño de API Gateway y Zuul**

[Zuul](https://github.com/Netflix/zuul/wiki)  es un “edge service” que  **permite enrutado dinámico, balanceo de carga, monitorización y securización de peticiones**. A efectos prácticos Zuul es un servidor compuesto por filtros, cada uno de los cuales está enfocado a una funcionalidad como pueden ser las anteriormente mencionadas.

### ¿Cómo funciona?

En una arquitectura de microservicios normalmente  **Zuul será configurado como el punto de entrada al ecosistema de microservicios y será el encargado de enrutar, balancear, securizar… las peticiones que reciban los microservicios**.

Cada petición enviada a Zuul pasará por los filtros que lo componen que en función de las características de la petición pueden por ejemplo rechazarla por motivos de seguridad, registrarla con finalidades de monitorización, enrutarla a una determinada instancia de un microservicio… según los filtros configurados o los que nosotros mismos hayamos implementado.

Por defecto, Zuul utiliza Ribbon para localizar a través de Eureka las instancias de microservicio a la que derivar las peticiones que ejecutará dentro de un “Hystrix Command”, integrando así todos los componentes de la arquitectura y aprovechando todas la ventajas que proporciona el ecosistema spring­cloud.

**Zuul también nos permite configurar el enrutamiento a los microservicios a través de properties**, de forma que un microservicio no tendrá que ser invocado necesariamente por el nombre con el que ha sido registrado en Eureka.

Sin embargo esta funcionalidad de enrutamiento no utiliza los otros elementos de la arquitectura como Ribbon o Hystrix.

## Ejemplo de Spring Cloud Netflix

Después de ver algunos conceptos básicos acerca de los microservicios y Netflix OSS, veremos como poder aplicar lo teórico a lo práctico.

### Requisitos
Para poder ejecutar y probar el ejemplo de este repositorio, necesitamos tener instalado algunas herramientas:

1. **JDK 11**
2. **El IDE de su preferencia**
> Microsoft ofrece un paquete de codificación para Java el cual nos ayuda a configurar rápidamente, instalando VS Code, el kit de desarrollo de Java (JDK) y las extensiones esenciales de Java. [Getting Started with Java in Visual Studio Code](https://code.visualstudio.com/docs/java/java-tutorial).
> 
> Además, requerimos algunas extensiones adicionales para poder ejecutar y/o debuguear programas hechos con Spring Boot en nuestro VS Code [Spring Boot support in Visual Studio Code](https://code.visualstudio.com/docs/java/java-spring-boot).

3. **Postman**

>**Postman** Es un cliente HTTP que nos da la posibilidad de testear 'HTTP requests' a través de una interfaz gráfica de usuario, por medio de la cual obtendremos diferentes tipos de respuesta
>[Download Postman | Get Started for Free](https://www.postman.com/downloads/)

### Introducción al ejemplo
Hay dos servicios principales: **el servicio de películas (movie-service) y dos servicios de reseñas (review-service-8083 y review-service-8086)**, que implementarán una biblioteca de películas simple y un sistema de reseñas que contiene reseñas de películas.

Además de los servicios principales, también hay dos más: **servicio de descubrimiento (discovery-service) y puerta de enlace (api-gateway)**.

Usaremos Spring Cloud Netflix, que integra Netflix OSS con Spring Boot, lo que permite la configuración automática para las tecnologías que se encuentran en el stack de Netflix.

### Arquitectura del ejemplo

La arquitecura propuesta para este ejemplo fue basada en el patrón la API Gateway.

Dónde: 

1. Comenzamos con el **CLIENTE**, en este caso será Postman, el cual nos ayudará a mandar peticiones HTTP a nuestros microservicios.
2. En segundo lugar tenemos el servicio **API GATEWAY**, el cual nos permitirá centralizar y enrutar todas las peticiones desde el exterior hacia cada microservicio, actuando como un proxy o punto único que enruta una petición que llega hacia los microservicios correspondientes.
3. En tercer lugar tenemos el **DISCOVERY SERVICE**, el cual tendrá el rol de servidor para el registro y localización de microservicios.
4. Finalmente tenemos los microservicios principales o de dominio:
	- **MOVIE SERVICE** el cual almacenará algunas películas de ejemplo en una base de datos embebida (**H2**). Teniendo como proposito simular la biblioteca de películas simple.
	- **REVIEW SERVICE** en este caso, tendremos dos proyectos de review-service en diferentes carpetas (8083 y 8806). Esto para simular dos instancias de REVIEW SERVICE y poder balancear las peticiones con Ribbon, Eureka y Feign.
Dichos proyectos tiene como proposito simular el sistema de reseñas que contiene reseñas de las películas de MOVIE SERVICE, y de igual manera, agregar dichas reseñas en una base de datos embebida (**H2**).
	
![Arquitectura para ejemplo](https://github.com/geezylucas/aprende-spring-cloud-netflix/blob/master/arquit-netflix.drawio.png?raw=true)

# Referencias

 1. [Microservicios Spring Cloud: Arquitectura (1/2) - Paradigma (paradigmadigital.com)](https://www.paradigmadigital.com/dev/quien-es-quien-en-la-arquitectura-de-microservicios-spring-cloud-12/)
    
  2. [Microservicios Spring Cloud: Hystrix, Ribbon y Zuul - Paradigma (paradigmadigital.com)](https://www.paradigmadigital.com/dev/quien-es-quien-en-la-arquitectura-de-microservicios-spring-cloud-22/)
    
  3. [Building Microservices with Spring Boot & Netflix OSS - Pentalog](https://www.pentalog.com/blog/it-development-technology/microservices-spring-boot-netflix-oss)
    
   4. [Microservices with Netflix OSS, Spring Boot, and non-JVM applications | by Samodha Pallewatta | Medium](https://medium.com/@skpallewatta92/microservices-with-netflix-oss-spring-boot-and-non-jvm-applications-2d762768921a)

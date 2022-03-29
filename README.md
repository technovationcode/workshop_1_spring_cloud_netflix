# Aprende Spring Cloud Netflix

## Introducción
Los microservicios están de moda y múltiples empresas han optado por usarlos, gracias a un enfoque más flexible y escalable para el desarrollo e implementación de aplicaciones que siguen creciendo rápidamente.

Pero como todo gran avance técnologico, la implementación de aplicaciones que utilizan la arquitectura de microservicios puede generar grandes desafíos como el descubrimiento de servicios, el equilibrio de carga, la tolerancia a fallas, etc.

Netflix Open Source Software (Netflix OSS) incluye un conjunto de herramientas / componentes de software creados por Netflix para resolver los problemas antes mencionados. 

Así que, explicaremos de manera rápida cómo se puede integrar Netflix Zuul (API Gateway), Netflix Eureka (para detección de servicios), Netflix Ribbon (para balanceo de carga) y Netflix Hystrix (para monitoreo y tolerancia a fallas) a nuestra arquitectura de microservicios.


## Monolíticos vs Microservicios

### Arquitectura monolítica
Las aplicaciones monolíticas o sistemas monolíticos, tienen todas o la mayoría de sus funcionalidades en un único proceso o contenedor y está formada por capas internas o bibliotecas. Aunque son fáciles de desarrollar, una aplicación que aglutina toda su funcionalidad no es la mejor opción, en el caso de que se tengan aspiraciones de crecimiento complejas, más usuarios, más desarrolladores…

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

Las herramientas específicas que resuelve el problema son:

-   **Service Discovery y Netflix Eureka**

Es un microservicio REST (Representational State Transfer) que se utiliza con el objetivo de registrar y localizar los microservicios existentes en el ecosistema, informar de su localización, estado y alguna métrica operacional. Facilita el balanceo de la carga entre las instancias de los microservicios y la tolerancia a fallos.

En Netflix, es un componente crítico en la distribución de la carga en capas intermedias. En resumen la función de Eureka es registrar las diferentes instancias de microservicios existentes, su localización, estado, metadatos…

-   **Load balancing y Netflix Ribbon**

Ribbon proporciona principalmente algoritmos de equilibrio de carga del lado del cliente, es una librería para la Inter Comunicación de Procesos (IPC), conocido también como llamadas a procedimientos remotos (RPC). El modelo de uso primario implica llamadas REST con soporte para varios esquemas de serialización. Ribbon proporciona las siguientes funcionalidades:

-   Balanceo de carga
-   Tolerancia a fallos
-   Soporte para múltiples protocolos de forma asíncrona o reactiva (HTTP, TCP, UDP)
-   Permite cacheo y procesamiento por lotes
-   Integración con servidores de descubrimiento como Eureka o Consul

Cuando Ribbon recibe una petición a un servicio externo, lo primero que hace es consultar al servidor de Eureka, como se encuentra dicho micro, y decidirá a qué instancia de las disponibles dirigir la petición entrante.

![netflix y microservicios](https://lh3.googleusercontent.com/aubes0y1QW7_BQFRpFVNXSxOcNsFZgvattDu95i1fV_BzzBhUp9fN4ogfkcfmFS__ntSMKS_xyP8sSX65FCouZwzL_rFDL8B19kaRKQf4qYqUoLqfjwymEJLaQPYFtZM0jxyAdgO)

-   **Fault tolerance y Netflix Hystrix**

Hystrix es una biblioteca de latencia y tolerancia a fallos diseñada para aislar puntos de acceso a sistemas remotos, servicios y bibliotecas de terceros, detener el fallo en cascada y permitir la resiliencia en sistemas distribuidos complejos donde los errores son inevitables.

Por lo general, para los sistemas desarrollados con arquitectura de microservicios, hay muchos microservicios involucrados. Estos microservicios colaboran entre sí.

![netflix y microservicios](https://lh5.googleusercontent.com/vsMeSIBc9EmPkIGaNBQyHeXWApixyQdFw4NrnzSNnhmH28k45sK5c4Fvj1eJswgUCtC8a8tZO_bQLlLmT8R8LJ3bckeAufDM6dksTfkNSsYiRKehqDV3T5HxFZ2g6txWCs4V38Ni)

-   **API Gateway Design Pattern y Netflix Zuul**

Es la puerta de entrada para todas las solicitudes desde dispositivos y sitios web al backend de la aplicación de transmisión de Netflix. Como aplicación de servicio perimetral, Zuul está diseñada para permitir el enrutamiento dinámico, el monitoreo, la resistencia y la seguridad. También tiene la capacidad de enrutar solicitudes a varios grupos de Amazon Auto Scaling según corresponda.

Zuul utiliza una gama de diferentes tipos de filtros que nos permiten aplicar la funcionalidad de forma rápida y ágil a nuestro servicio de borde. Estos filtros nos ayudan a realizar las siguientes funciones:

-   Autenticación y seguridad: identifica los requisitos de autenticación para cada recurso y rechaza las solicitudes que no los satisfacen.
-   Información y supervisión: seguimiento de datos y estadísticas importantes en el borde para brindarnos una visión precisa de la producción.
-   Enrutamiento dinámico: enrutamiento dinámico de solicitudes a diferentes clústeres de backend según sea necesario.
-   Stress Testing: aumentar gradualmente el tráfico a un clúster para medir el rendimiento.
-   Load Shedding: asignación de capacidad para cada tipo de solicitud y eliminación de solicitudes que superan el límite.
-   Manejo de respuestas estáticas: generar algunas respuestas directamente en el borde en lugar de reenviarlas a un clúster interno
-   Resiliencia multirregional: enrutamiento de solicitudes a través de las regiones de AWS para diversificar nuestro uso de ELB y acercar nuestra ventaja a nuestros miembros.

![netflix y microservicios](https://lh3.googleusercontent.com/FZST3ltIZLImjBDN3vYoRwOpqEUzRzecq5hedOWZG36QG4-oVqMW6NlBHnrLPFIOrOqcA1pfRlu5gvCcejyZJOkglZkD9IauvkEPdPRkuPXuqCzZnlsYIwX-npffKzxZfiGr2LJC)


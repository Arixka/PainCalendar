# PainCalendar - Guia de arquitectura y buenas practicas

Este README es una guia de aprendizaje y referencia para entender el por que de las decisiones del backend y la arquitectura hexagonal aplicada en este proyecto.

## Objetivos del proyecto
- Backend real con JDK 17 + Spring Boot 3.x
- Arquitectura hexagonal estricta (domain / application / infrastructure)
- Separacion clara entre dominio, web, persistencia y configuracion
- Codigo mantenible, testeable y preparado para crecer

## Principios clave (y por que)
- Dominio sin frameworks: el dominio no depende de Spring ni de librerias externas para mantener reglas de negocio puras y portables.
- Puertos y adaptadores: el dominio define interfaces (puertos) y la infraestructura aporta implementaciones (adaptadores). Esto reduce acoplamiento.
- Wiring explicito: en lugar de anotar servicios en application, se usa @Configuration + @Bean para dejar claro como se ensamblan las piezas.
- DTOs web separados: la capa web no habla con el dominio directamente; mapea un DTO valido a un Command de dominio.
- Validaciones en la capa web: se valida con Jakarta Validation antes de entrar al dominio. El dominio mantiene invariantes criticas.

## Ejemplo guiado: crear un pain record (POST /pain-records)

Este ejemplo recorre el flujo real de una request en el proyecto y explica por que cada paso existe.

1. **DTO web con validaciones**
   - Archivo: `infrastructure/web/dto/CreatePainRecordRequest.java`
   - Por que: el DTO representa el contrato HTTP, objeto de entrada que valida el formato y las reglas basicas antes de entrar al core. Asi evitamos que datos invalidos lleguen a la logica de negocio.
   - Mejora frente a alternativas: si usas el command de dominio directo en el controller, acoplas la API al core y pierdes libertad para evolucionar el contrato HTTP.

2. **Mapper web -> command**
   - Archivo: `infrastructure/web/mapper/`
   - Por que: el mapper es la frontera explicita entre web y dominio. Ahi se traduce el DTO a un command inmutable que espera el useCase.
   - Mejora frente a alternativas: evita que la capa web conozca detalles del dominio y mantiene el command limpio y estable. 
   De esta manera si mañana cambia el contrato HTTP, solo cambias el DTO y el mapper, sin tocar el dominio.


3. **Use case (port in) en application**
   - Archivo: `application/service/`
   - Por que: la orquestacion vive en application para que el dominio se mantenga puro y la logica de flujo sea testeable sin Spring.
   - Mejora frente a alternativas: centraliza el flujo y facilita pruebas unitarias, en lugar de dispersar logica en controllers o adapters.

4. **Persistencia via port out + adapter**
   - Archivos: `domain/port/out/` y `infrastructure/persistence/adapter/`
   - Por que: el dominio define la dependencia y la infraestructura la implementa. Asi puedes cambiar la tecnologia sin tocar el core.
   - Mejora frente a alternativas: reduce acoplamiento y permite testear el core con dobles.

5. **Respuesta REST correcta**
   - Controller devuelve `201 Created` y `Location`.
   - Por que: la API sigue semantica HTTP real, lo que facilita integraciones y consistencia.

## Mapa de responsabilidades

|       Capa      |        Responsabilidad      |         En este proyecto          |            Dependencias               |
| --------------- | --------------------------- | --------------------------------- | ------------------------------------- |
| Dominio         | Logica de negocio y modelos | `domain/model/`, `domain/port/`   | No depende de nada                    |
| Aplicacion      | Orquestacion y casos de uso | `application/service/`            | Depende solo de `domain/`             |
| Infraestructura | Detalles tecnicos           | `infrastructure/`                 | Depende de `application/` y `domain/` |

## Puertos y adaptadores

|  Concepto |         Ubicacion           |                       Rol                     |
| --------- | --------------------------- | --------------------------------------------- |
| Port In   | `domain/port/in/`           | Interfaces que exponen los casos de uso       |
| Port Out  | `domain/port/out/`          | Interfaces que definen dependencias externas  |
| Use Cases | `application/service/`      | Implementan Port In                           |
| Adapters  | `infrastructure/*/adapter/` | Implementan Port Out                          |

Nota: Spring vive solo en infraestructura. El dominio y la aplicacion son POJOs.

## Estructura de paquetes
- m.siverio.paincalendar
  - painrecord
    - domain
      - model
      - port
        - in
        - out
    - application
      - service
    - infrastructure
      - web
        - controller
        - dto
        - mapper
      - persistence
      - config
  - medication (misma estructura)

## Flujo de una request (ejemplo POST /pain-records)
1. Web Controller recibe un DTO con @Valid.
2. Mapper transforma DTO -> Command del dominio.
3. Use Case (puerto in) se ejecuta en application.
4. Persistencia (puerto out) se usa mediante un adapter.
5. Respuesta REST con semantica correcta (201 Created + Location).



## Diagrama de Secuencia: Creación de Registro (La Magia de la Inyección)

![alt text](<diagrama post.svg>)



## Diagrama de flujo


![alt text](<diagrama de flujo.svg>)

## Diagrama de capas


![alt text](<flujo arquitectura.svg>)

## Por que no usar @Service en application
En arquitectura hexagonal, application no debe depender del framework. Usar @Service introduce acoplamiento directo a Spring. En su lugar:
- La clase de application es POJO.
- La configuracion de Spring vive en infrastructure/config y hace el wiring.

## DTOs y Commands
- DTO (web): representa el contrato HTTP y contiene validaciones.
- Command (dominio): representa una intencion de negocio y es inmutable.
- Mapper: traduce DTO -> Command en la frontera de la capa web.

## Validaciones
- Web: Jakarta Validation asegura formato y reglas basicas (null, rangos, tamanos).
- Dominio: mantiene invariantes criticas y reglas de negocio (nunca confiar solo en web).

## REST correcto
- POST create devuelve 201 Created y header Location.
- Evitamos respuestas ambiguas (200 OK sin Location) para cumplir semantica HTTP real.

## Testing
- Controller: @WebMvcTest + MockMvc valida contrato HTTP y wiring web.
- Application: pruebas unitarias con Mockito para reglas del use case.
- Dominio: pruebas puras sin Spring.

## Convenciones del proyecto
- Java 21, Spring Boot 3.x
- JUnit 5 + Mockito
- Separacion estricta de capas

---

Este README evolucionara con el proyecto y documentara cada decision importante.

## Licencia

Este proyecto es propiedad exclusiva de su autor.
Todos los derechos están reservados. No se permite la reproducción, distribución, modificación ni uso comercial o privado de este código sin el permiso explícito y por escrito del autor.

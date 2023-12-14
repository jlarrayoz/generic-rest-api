<!-- TOC start (generated with https://github.com/derlin/bitdowntoc) -->

- [generic-rest-api](#generic-rest-api)
    * [Introducción](#introducción)
        + [BaseGenericRepository](#basegenericrepository)
        + [BaseGenericService](#basegenericservice)
        + [BaseGenericRestController](#basegenericrestcontroller)
    * [Instalación del starter](#instalación-del-starter)

<!-- TOC end -->

<!-- TOC --><a name="generic-rest-api"></a>
# generic-rest-api


<!-- TOC --><a name="introducción"></a>
## Introducción
Spring Boot starter que permite crear una api rest basada en generics

Este proyecto define 3 clases básicas:
<ol>
    <li>BaseGenericRepository</li>
    <li>BaseGenericService</li>
    <li>BaseGenericRestController</li>
</ol>

<!-- TOC --><a name="basegenericrepository"></a>
### BaseGenericRepository

Esta clase hereda de JpaRepository (Spring DATA JPA). La idea es mediante un generador poder generar esta clase y aprovechar
las bondades del mini DSL de spring para generar únicamente el nombre del método deseado y que spring haga el resto.

<!-- TOC --><a name="basegenericservice"></a>
### BaseGenericService

Servicio genérico báse, el cual utiliza un reposiotry (Del mismo tipo genérico T e ID).
El servicio genérico de momento expone una cantidad acotada de métodos, a efecto de realizar la prueba de concepto.

<!-- TOC --><a name="basegenericrestcontroller"></a>
### BaseGenericRestController

Controlador REST que utiliza un servicio y repo genérico sobre el mismo tipo T e ID.
Este controlador expone métodos básicos de momento.

Se le agrego la feature de conversión automática entre DTO y Entity (Sin mayor intervención del usuario).
Se utilizó la librería ModelMapper para su implementación.

Ejemplo:

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public T create(@GenericDTO T entity) {
    return service.save(entity);
}
```

La anotación @GenericDTO en conjunto con el nombre del método (se debe llamar create en este caso)
y con la Entity correspondiente (@GenericDtoType, anotación que especifica el DTO que se debe utilizar para el create y el update)

A futuro: Se podría incrementar lo desarrollado y que la anotación @GenericDtoType permita definir que DTO se devuelve en cada método del repo, service, rest.


<!-- TOC --><a name="instalación-del-starter"></a>
## Instalación del starter

Para poder utilizar este starter desde otro proyecto spring se debe insalar en el repo maven local.

Para eso ejecutar la siguiente linea de comandos parado en la raiz del proyecto:

```bash
maven install
```

También se puede ejecutar el install de maven desde el IDE directamente.


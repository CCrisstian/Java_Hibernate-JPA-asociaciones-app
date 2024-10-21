<h1 align="center">Anotaciones @ManyToOne, @OneToMany, @OneToOne y @ManyToMany</h1>
<p>Las anotaciones @ManyToOne, @OneToMany, @OneToOne y @ManyToMany en JPA e Hibernate se utilizan para definir las relaciones entre entidades en un modelo de datos. Estas relaciones especifican cómo están conectadas las entidades entre sí en la base de datos y permiten que Hibernate gestione automáticamente las asociaciones.</p>

- `@ManyToOne`
  - Representa una relación en la que <b>muchos registros de una entidad están relacionados con un solo registro de otra entidad</b>.
  - En términos de base de datos, esto suele corresponder a una <b>clave foránea</b> en la tabla que tiene la relación "muchos".
  - `@JoinColumn(name = "id_cliente")` especifica que la columna `id_cliente` en la tabla `facturas` se usará como la clave foránea para establecer la relación con la tabla `clientes`.

```java
@Entity
@Table(name = "clientes")
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) /*Id Auto_Incremental*/
  private long id;

  private String nombre;
  private String apellido;

  @Column(name = "forma_pago")
  private String formaPago;

  @Embedded
  private Auditoria audit = new Auditoria();
}
```
```java
@Entity
@Table(name = "facturas")
public class Factura {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String descripcion;
  private Long total;

  @ManyToOne
  @JoinColumn(name = "id_cliente")
  private Cliente cliente;
}
```

- `@OneToMany`
  - Representa una relación en la que <b>un registro de una entidad está relacionado con muchos registros de otra entidad</b>.
  - Es la contraparte de `@ManyToOne`. En la base de datos, se implementa mediante una clave foránea en la tabla "muchos".
  - La relación puede ser unidireccional o bidireccional. En una relación bidireccional, se debe usar la anotación `mappedBy` en el lado "uno" para indicar la propiedad que mapea la relación.
```java
    
```

- `@OneToOne`
  - Representa una relación en la que <b>un registro de una entidad está relacionado con un solo registro de otra entidad</b>.
  - En la base de datos, esto puede implementarse con una clave foránea o una columna única.
    ```java
    
    ```
- `@ManyToMany`
  - Representa una relación en la que <b>muchos registros de una entidad están relacionados con muchos registros de otra entidad</b>.
  - En la base de datos, esto se implementa mediante una <b>tabla intermedia</b> que contiene las claves foráneas de ambas entidades.

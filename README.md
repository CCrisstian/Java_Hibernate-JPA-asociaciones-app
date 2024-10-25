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
  - Un objeto de la clase `Cliente` puede tener una lista de múltiples objetos de la clase `Direccion`.
  - `cascade = CascadeType.ALL`: Especifica que las operaciones de persistencia (guardar, actualizar, eliminar, etc.) que se realicen en la entidad principal (`Cliente`) se propaguen automáticamente a las entidades relacionadas (`Direccion`).
  - `orphanRemoval = true`: Significa que si se elimina una `Direccion` de la lista de direccions del `Cliente`, también se eliminará de la base de datos.
  - La anotación `@JoinColumn` especifica la columna que actuará como clave foránea en la tabla `direcciones`.
  - `@JoinColumn(name = "id_cliente")` indica que la columna `id_cliente` en la tabla `direcciones` se utilizará para almacenar el identificador del `cliente` asociado.
  - La anotación `@JoinTable` se utiliza para definir una tabla de unión explícita que gestionará la relación entre las tablas `clientes` y `direcciones`.
  - `name = "tbl_clientes_direcciones"`: Indica el nombre de la tabla de unión, en este caso `tbl_clientes_direcciones`.
  - `joinColumns = @JoinColumn(name = "id_cliente")`: Especifica la columna en la tabla de unión que actuará como clave foránea hacia la tabla `clientes`. Cada registro en `tbl_clientes_direcciones` tendrá un valor de `id_cliente` que se refiere al `cliente` correspondiente.
  - `inverseJoinColumns = @JoinColumn(name = "id_direccion")`: Define la columna en la tabla de unión que será la clave foránea hacia la tabla `direcciones`. Cada registro en `tbl_clientes_direcciones` tendrá un valor de `id_direccion` que se refiere a la dirección correspondiente.
  - `uniqueConstraints = @UniqueConstraint(columnNames = {"id_direccion"})`: Asegura que cada `id_direccion` en la tabla de unión sea único, lo que significa que una misma dirección no se puede asociar a más de un cliente.
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//  @JoinColumn(name = "id_cliente")
    @JoinTable(
        name = "tbl_clientes_direcciones", // Nombre de la tabla de unión
        joinColumns = @JoinColumn(name = "id_cliente"), // Clave foránea hacia la tabla Cliente
        inverseJoinColumns = @JoinColumn(name = "id_direccion"), // Clave foránea hacia la tabla Direccion
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_direccion"}) // Restricción de unicidad
    )
    private List<Direccion> direccions;
}
```
```java
@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private Integer numero;
}
```


- `@OneToMany` "Bidireccional"
  - En una relación bidireccional `@OneToMany`, ambas entidades están al tanto de la relación y se mantiene una referencia en ambas direcciones. En este caso, un `Cliente` puede tener muchas `Factura`s, y cada `Factura` está asociada con un `Cliente`.
  - Relación Uno a Muchos (`@OneToMany`) en `Cliente`:
    -  La anotación `@OneToMany` en la clase `Cliente` indica que un cliente puede tener varias facturas asociadas.
    -  La propiedad `mappedBy = "cliente"` se utiliza para definir el lado "no propietario" de la relación, lo que significa que la tabla `clientes` no tendrá la clave foránea. En su lugar, la clave foránea se maneja desde la entidad `Factura`.
    -  `cascade = CascadeType.ALL` y `orphanRemoval = true` especifican que las operaciones en la entidad principal (`Cliente`) se propaguen a las `Factura`s asociadas, y si se elimina una factura de la lista, se elimina también de la base de datos.
  - Relación Muchos a Uno (`@ManyToOne`) en `Factura`:
    - La anotación `@ManyToOne` en la clase `Factura` indica que muchas facturas pueden estar asociadas con un solo cliente.
    - `@JoinColumn(name = "id_cliente")` define la columna en la tabla `facturas` que actúa como clave foránea, apuntando al campo `id` de la tabla `clientes`. Esto establece la relación en la base de datos.
    - La clase `Factura` es el lado "propietario" de la relación, ya que contiene la clave foránea que vincula la factura con el cliente.
  - Funcionamiento de la relación bidireccional:
    - Desde la clase `Cliente`, se puede acceder a la lista de `Factura`s asociadas.
    - Desde la clase `Factura`, se puede acceder al `Cliente` al que pertenece la factura.
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
    private List<Factura> facturas;
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

    public Factura() {
    }
}
```

- `@OneToOne`
  - Representa una relación en la que <b>un registro de una entidad está relacionado con un solo registro de otra entidad</b>.
  - En la base de datos, esto puede implementarse con una clave foránea o una columna única.
    ```java
    
    ```
- `@ManyToMany`
  - Representa una relación en la que <b>muchos registros de una entidad están relacionados con muchos registros de otra entidad</b>.
  - En la base de datos, esto se implementa mediante una <b>tabla intermedia</b> que contiene las claves foráneas de ambas entidades.

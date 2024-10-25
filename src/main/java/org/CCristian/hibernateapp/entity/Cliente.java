package org.CCristian.hibernateapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
//    @JoinColumn(name = "id_cliente")
    @JoinTable(name = "tbl_clientes_direcciones"
    , joinColumns = @JoinColumn(name = "id_cliente")
    , inverseJoinColumns = @JoinColumn(name = "id_direccion")
    , uniqueConstraints = @UniqueConstraint(columnNames = {"id_direccion"}))
    private List<Direccion> direccions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
    private List<Factura> facturas;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
    private ClienteDetalle detalle;

    public Cliente() {
        facturas = new ArrayList<>();
        direccions = new ArrayList<>();
    }

    public Cliente(String nombre, String apellido) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Cliente(long id, String nombre, String apellido, String formaPago) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.formaPago = formaPago;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public List<Direccion> getDireccions() {
        return direccions;
    }

    public Auditoria getAudit() {
        return audit;
    }

    public void setAudit(Auditoria audit) {
        this.audit = audit;
    }

    public void setDireccions(List<Direccion> direccions) {
        this.direccions = direccions;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public ClienteDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(ClienteDetalle detalle) {
        this.detalle = detalle;
    }

    public Cliente addFactura(Factura factura){
        this.facturas.add(factura);
        factura.setCliente(this);
        return this;
    }

    public void removeFactura(Factura factura) {
        this.facturas.remove(factura);
        factura.setCliente(null);
    }

    public void addDetalle(ClienteDetalle detalle) {
        this.detalle = detalle;
        detalle.setCliente(this);
    }

    public void removeDetalle (){
        detalle.setCliente(null);
        this.detalle = null;
    }

    @Override
    public String toString() {
        LocalDateTime creado = this.audit != null? audit.getCreadoEn() : null;
        LocalDateTime editado = this.audit != null? audit.getEditadoEn() : null;
        return "------------------------ Cliente "+id+" ------------------------"+
                "\nid= " + id +
                ", nombre= " + nombre +
                ", apellido= " + apellido +
                ", formaPago= " + formaPago +
                ", creadoEn= " + creado +
                ", editadoEn= " + editado+
                ", direcciones = " + direccions +
                ", facturas = " + facturas +
                ", detalles = " + detalle;
    }



}

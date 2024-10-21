package org.CCristian.hibernateapp;

import jakarta.persistence.EntityManager;
import org.CCristian.hibernateapp.entity.Cliente;
import org.CCristian.hibernateapp.entity.Factura;
import org.CCristian.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesManyToOne {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            /*Se crea un nuevo cliente*/
            Cliente cliente = new Cliente("Cata","Edu");
            cliente.setFormaPago("credito");
            /*SE guarda el cliente en la Base de Datos*/
            em.persist(cliente);

            /*Se crea la factura*/
            Factura factura = new Factura("compras de oficina",1000L);

            /*Estableciendo la relación @ManyToOne con un cliente que existe en la Base de Datos*/
            factura.setCliente(cliente);
            em.persist(factura);
            System.out.println(factura.getCliente());

            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}

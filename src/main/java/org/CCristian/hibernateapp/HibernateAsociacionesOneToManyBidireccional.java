package org.CCristian.hibernateapp;

import jakarta.persistence.EntityManager;
import org.CCristian.hibernateapp.entity.Cliente;
import org.CCristian.hibernateapp.entity.Factura;
import org.CCristian.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToManyBidireccional {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Cliente cliente = new Cliente("Cata","Edu");
            cliente.setFormaPago("paypal");

            Factura f1 = new Factura("compras de supermercado",5000L);
            Factura f2 = new Factura("compras de tecnología",7000L);

//            cliente.getFacturas().add(f1);
//            cliente.getFacturas().add(f2);
//            f1.setCliente(cliente);
//            f2.setCliente(cliente);

            cliente.addFactura(f1).addFactura(f2);

            em.persist(cliente);

            em.getTransaction().commit();
            System.out.println(cliente);

            em.getTransaction().begin();

            Factura f3 = new Factura("compras de supermercado",5000L);
            f3.setId(1L);
            cliente.removeFactura(f3);

            em.getTransaction().commit();
            System.out.println(cliente);
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

    }
}

package org.CCristian.hibernateapp;

import jakarta.persistence.EntityManager;
import org.CCristian.hibernateapp.entity.Cliente;
import org.CCristian.hibernateapp.entity.Direccion;
import org.CCristian.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToMany {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Cliente cliente = new Cliente("Cata","Edu");
            cliente.setFormaPago("mercado pago");

            Direccion d1 = new Direccion("el vergel",123);
            Direccion d2 = new Direccion("vasco de gama",456);

            cliente.getDireccions().add(d1);
            cliente.getDireccions().add(d2);
            em.persist(cliente);

            em.getTransaction().commit();
            System.out.println(cliente);

            em.getTransaction().begin();

            cliente = em.find(Cliente.class, cliente.getId());
            cliente.getDireccions().remove(d1);

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

package org.CCristian.hibernateapp;

import jakarta.persistence.EntityManager;
import org.CCristian.hibernateapp.entity.Alumno;
import org.CCristian.hibernateapp.entity.Curso;
import org.CCristian.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesManyToManyFindBidireccional {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Alumno alumno1 = em.find(Alumno.class, 1L);
            Alumno alumno2 = em.find(Alumno.class, 2L);

            Curso curso1 = em.find(Curso.class, 1L);
            Curso curso2 = em.find(Curso.class, 2L);


            alumno1.addCurso(curso1);
            alumno1.addCurso(curso2);
            alumno2.addCurso(curso1);

            em.getTransaction().commit();

            System.out.println(alumno1);
            System.out.println(alumno2);

            em.getTransaction().begin();

            Curso c2 = new Curso("Curso Java EE 9", "Andres");
            c2.setId(2L);

            alumno1.removeCurso(c2);

            em.getTransaction().commit();
            System.out.println(alumno1);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}

package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.config.ObjectDBManager;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EnvioRepository {

    @Autowired
    private ObjectDBManager dbManager;

    public List<EnvioACasa> findByIdParada(long idParada) {
        EntityManager em = dbManager.getEntityManager();
        TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :idParada", EnvioACasa.class);
        query.setParameter("idParada", idParada);
        return query.getResultList();
    }

    public List<EnvioACasa> findByUrgenteTrue() {
        EntityManager em = dbManager.getEntityManager();
        TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.urgente = true", EnvioACasa.class);
        return query.getResultList();
    }

    public List<EnvioACasa> findAll() {
        EntityManager em = dbManager.getEntityManager();
        TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e", EnvioACasa.class);
        return query.getResultList();
    }
    
    public void save(EnvioACasa envio) {
        EntityManager em = dbManager.getEntityManager();
        em.getTransaction().begin();
        em.persist(envio);
        em.getTransaction().commit();
    }
    
    public void delete(EnvioACasa envio) {
        EntityManager em = dbManager.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(envio) ? envio : em.merge(envio));
        em.getTransaction().commit();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.Maquinaria;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author anacastillo
 */
public class MaquinariaJpaController implements Serializable {

    public MaquinariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maquinaria maquinaria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(maquinaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maquinaria maquinaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            maquinaria = em.merge(maquinaria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = maquinaria.getIdMaquinaria();
                if (findMaquinaria(id) == null) {
                    throw new NonexistentEntityException("The maquinaria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maquinaria maquinaria;
            try {
                maquinaria = em.getReference(Maquinaria.class, id);
                maquinaria.getIdMaquinaria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maquinaria with id " + id + " no longer exists.", enfe);
            }
            em.remove(maquinaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maquinaria> findMaquinariaEntities() {
        return findMaquinariaEntities(true, -1, -1);
    }

    public List<Maquinaria> findMaquinariaEntities(int maxResults, int firstResult) {
        return findMaquinariaEntities(false, maxResults, firstResult);
    }

    private List<Maquinaria> findMaquinariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maquinaria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Maquinaria findMaquinaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maquinaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaquinariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maquinaria> rt = cq.from(Maquinaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

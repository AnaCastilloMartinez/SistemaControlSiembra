/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.ControlFertilizado;
import entidades.Insumo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class InsumoJpaController implements Serializable {

    public InsumoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Insumo insumo) {
        if (insumo.getControlFertilizadoList() == null) {
            insumo.setControlFertilizadoList(new ArrayList<ControlFertilizado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ControlFertilizado> attachedControlFertilizadoList = new ArrayList<ControlFertilizado>();
            for (ControlFertilizado controlFertilizadoListControlFertilizadoToAttach : insumo.getControlFertilizadoList()) {
                controlFertilizadoListControlFertilizadoToAttach = em.getReference(controlFertilizadoListControlFertilizadoToAttach.getClass(), controlFertilizadoListControlFertilizadoToAttach.getIdFertilizado());
                attachedControlFertilizadoList.add(controlFertilizadoListControlFertilizadoToAttach);
            }
            insumo.setControlFertilizadoList(attachedControlFertilizadoList);
            em.persist(insumo);
            for (ControlFertilizado controlFertilizadoListControlFertilizado : insumo.getControlFertilizadoList()) {
                Insumo oldIdInsumoOfControlFertilizadoListControlFertilizado = controlFertilizadoListControlFertilizado.getIdInsumo();
                controlFertilizadoListControlFertilizado.setIdInsumo(insumo);
                controlFertilizadoListControlFertilizado = em.merge(controlFertilizadoListControlFertilizado);
                if (oldIdInsumoOfControlFertilizadoListControlFertilizado != null) {
                    oldIdInsumoOfControlFertilizadoListControlFertilizado.getControlFertilizadoList().remove(controlFertilizadoListControlFertilizado);
                    oldIdInsumoOfControlFertilizadoListControlFertilizado = em.merge(oldIdInsumoOfControlFertilizadoListControlFertilizado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Insumo insumo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Insumo persistentInsumo = em.find(Insumo.class, insumo.getIdInsumo());
            List<ControlFertilizado> controlFertilizadoListOld = persistentInsumo.getControlFertilizadoList();
            List<ControlFertilizado> controlFertilizadoListNew = insumo.getControlFertilizadoList();
            List<String> illegalOrphanMessages = null;
            for (ControlFertilizado controlFertilizadoListOldControlFertilizado : controlFertilizadoListOld) {
                if (!controlFertilizadoListNew.contains(controlFertilizadoListOldControlFertilizado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlFertilizado " + controlFertilizadoListOldControlFertilizado + " since its idInsumo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ControlFertilizado> attachedControlFertilizadoListNew = new ArrayList<ControlFertilizado>();
            for (ControlFertilizado controlFertilizadoListNewControlFertilizadoToAttach : controlFertilizadoListNew) {
                controlFertilizadoListNewControlFertilizadoToAttach = em.getReference(controlFertilizadoListNewControlFertilizadoToAttach.getClass(), controlFertilizadoListNewControlFertilizadoToAttach.getIdFertilizado());
                attachedControlFertilizadoListNew.add(controlFertilizadoListNewControlFertilizadoToAttach);
            }
            controlFertilizadoListNew = attachedControlFertilizadoListNew;
            insumo.setControlFertilizadoList(controlFertilizadoListNew);
            insumo = em.merge(insumo);
            for (ControlFertilizado controlFertilizadoListNewControlFertilizado : controlFertilizadoListNew) {
                if (!controlFertilizadoListOld.contains(controlFertilizadoListNewControlFertilizado)) {
                    Insumo oldIdInsumoOfControlFertilizadoListNewControlFertilizado = controlFertilizadoListNewControlFertilizado.getIdInsumo();
                    controlFertilizadoListNewControlFertilizado.setIdInsumo(insumo);
                    controlFertilizadoListNewControlFertilizado = em.merge(controlFertilizadoListNewControlFertilizado);
                    if (oldIdInsumoOfControlFertilizadoListNewControlFertilizado != null && !oldIdInsumoOfControlFertilizadoListNewControlFertilizado.equals(insumo)) {
                        oldIdInsumoOfControlFertilizadoListNewControlFertilizado.getControlFertilizadoList().remove(controlFertilizadoListNewControlFertilizado);
                        oldIdInsumoOfControlFertilizadoListNewControlFertilizado = em.merge(oldIdInsumoOfControlFertilizadoListNewControlFertilizado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = insumo.getIdInsumo();
                if (findInsumo(id) == null) {
                    throw new NonexistentEntityException("The insumo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Insumo insumo;
            try {
                insumo = em.getReference(Insumo.class, id);
                insumo.getIdInsumo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insumo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ControlFertilizado> controlFertilizadoListOrphanCheck = insumo.getControlFertilizadoList();
            for (ControlFertilizado controlFertilizadoListOrphanCheckControlFertilizado : controlFertilizadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Insumo (" + insumo + ") cannot be destroyed since the ControlFertilizado " + controlFertilizadoListOrphanCheckControlFertilizado + " in its controlFertilizadoList field has a non-nullable idInsumo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(insumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Insumo> findInsumoEntities() {
        return findInsumoEntities(true, -1, -1);
    }

    public List<Insumo> findInsumoEntities(int maxResults, int firstResult) {
        return findInsumoEntities(false, maxResults, firstResult);
    }

    private List<Insumo> findInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Insumo.class));
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

    public Insumo findInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Insumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Insumo> rt = cq.from(Insumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import entidades.DetalleFertilizado;
import entidades.Hectarea;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class DetalleFertilizadoJpaController implements Serializable {

    public DetalleFertilizadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFertilizado detalleFertilizado) {
        if (detalleFertilizado.getControlFertilizadoList() == null) {
            detalleFertilizado.setControlFertilizadoList(new ArrayList<ControlFertilizado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlFertilizado idFertilizado = detalleFertilizado.getIdFertilizado();
            if (idFertilizado != null) {
                idFertilizado = em.getReference(idFertilizado.getClass(), idFertilizado.getIdFertilizado());
                detalleFertilizado.setIdFertilizado(idFertilizado);
            }
            Hectarea idHectarea = detalleFertilizado.getIdHectarea();
            if (idHectarea != null) {
                idHectarea = em.getReference(idHectarea.getClass(), idHectarea.getIdHectarea());
                detalleFertilizado.setIdHectarea(idHectarea);
            }
            List<ControlFertilizado> attachedControlFertilizadoList = new ArrayList<ControlFertilizado>();
            for (ControlFertilizado controlFertilizadoListControlFertilizadoToAttach : detalleFertilizado.getControlFertilizadoList()) {
                controlFertilizadoListControlFertilizadoToAttach = em.getReference(controlFertilizadoListControlFertilizadoToAttach.getClass(), controlFertilizadoListControlFertilizadoToAttach.getIdFertilizado());
                attachedControlFertilizadoList.add(controlFertilizadoListControlFertilizadoToAttach);
            }
            detalleFertilizado.setControlFertilizadoList(attachedControlFertilizadoList);
            em.persist(detalleFertilizado);
            if (idFertilizado != null) {
                idFertilizado.getDetalleFertilizadoList().add(detalleFertilizado);
                idFertilizado = em.merge(idFertilizado);
            }
            if (idHectarea != null) {
                idHectarea.getDetalleFertilizadoList().add(detalleFertilizado);
                idHectarea = em.merge(idHectarea);
            }
            for (ControlFertilizado controlFertilizadoListControlFertilizado : detalleFertilizado.getControlFertilizadoList()) {
                DetalleFertilizado oldIdDetalleFertilizadoOfControlFertilizadoListControlFertilizado = controlFertilizadoListControlFertilizado.getIdDetalleFertilizado();
                controlFertilizadoListControlFertilizado.setIdDetalleFertilizado(detalleFertilizado);
                controlFertilizadoListControlFertilizado = em.merge(controlFertilizadoListControlFertilizado);
                if (oldIdDetalleFertilizadoOfControlFertilizadoListControlFertilizado != null) {
                    oldIdDetalleFertilizadoOfControlFertilizadoListControlFertilizado.getControlFertilizadoList().remove(controlFertilizadoListControlFertilizado);
                    oldIdDetalleFertilizadoOfControlFertilizadoListControlFertilizado = em.merge(oldIdDetalleFertilizadoOfControlFertilizadoListControlFertilizado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleFertilizado detalleFertilizado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFertilizado persistentDetalleFertilizado = em.find(DetalleFertilizado.class, detalleFertilizado.getIdDetalleFertilizado());
            ControlFertilizado idFertilizadoOld = persistentDetalleFertilizado.getIdFertilizado();
            ControlFertilizado idFertilizadoNew = detalleFertilizado.getIdFertilizado();
            Hectarea idHectareaOld = persistentDetalleFertilizado.getIdHectarea();
            Hectarea idHectareaNew = detalleFertilizado.getIdHectarea();
            List<ControlFertilizado> controlFertilizadoListOld = persistentDetalleFertilizado.getControlFertilizadoList();
            List<ControlFertilizado> controlFertilizadoListNew = detalleFertilizado.getControlFertilizadoList();
            List<String> illegalOrphanMessages = null;
            for (ControlFertilizado controlFertilizadoListOldControlFertilizado : controlFertilizadoListOld) {
                if (!controlFertilizadoListNew.contains(controlFertilizadoListOldControlFertilizado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlFertilizado " + controlFertilizadoListOldControlFertilizado + " since its idDetalleFertilizado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFertilizadoNew != null) {
                idFertilizadoNew = em.getReference(idFertilizadoNew.getClass(), idFertilizadoNew.getIdFertilizado());
                detalleFertilizado.setIdFertilizado(idFertilizadoNew);
            }
            if (idHectareaNew != null) {
                idHectareaNew = em.getReference(idHectareaNew.getClass(), idHectareaNew.getIdHectarea());
                detalleFertilizado.setIdHectarea(idHectareaNew);
            }
            List<ControlFertilizado> attachedControlFertilizadoListNew = new ArrayList<ControlFertilizado>();
            for (ControlFertilizado controlFertilizadoListNewControlFertilizadoToAttach : controlFertilizadoListNew) {
                controlFertilizadoListNewControlFertilizadoToAttach = em.getReference(controlFertilizadoListNewControlFertilizadoToAttach.getClass(), controlFertilizadoListNewControlFertilizadoToAttach.getIdFertilizado());
                attachedControlFertilizadoListNew.add(controlFertilizadoListNewControlFertilizadoToAttach);
            }
            controlFertilizadoListNew = attachedControlFertilizadoListNew;
            detalleFertilizado.setControlFertilizadoList(controlFertilizadoListNew);
            detalleFertilizado = em.merge(detalleFertilizado);
            if (idFertilizadoOld != null && !idFertilizadoOld.equals(idFertilizadoNew)) {
                idFertilizadoOld.getDetalleFertilizadoList().remove(detalleFertilizado);
                idFertilizadoOld = em.merge(idFertilizadoOld);
            }
            if (idFertilizadoNew != null && !idFertilizadoNew.equals(idFertilizadoOld)) {
                idFertilizadoNew.getDetalleFertilizadoList().add(detalleFertilizado);
                idFertilizadoNew = em.merge(idFertilizadoNew);
            }
            if (idHectareaOld != null && !idHectareaOld.equals(idHectareaNew)) {
                idHectareaOld.getDetalleFertilizadoList().remove(detalleFertilizado);
                idHectareaOld = em.merge(idHectareaOld);
            }
            if (idHectareaNew != null && !idHectareaNew.equals(idHectareaOld)) {
                idHectareaNew.getDetalleFertilizadoList().add(detalleFertilizado);
                idHectareaNew = em.merge(idHectareaNew);
            }
            for (ControlFertilizado controlFertilizadoListNewControlFertilizado : controlFertilizadoListNew) {
                if (!controlFertilizadoListOld.contains(controlFertilizadoListNewControlFertilizado)) {
                    DetalleFertilizado oldIdDetalleFertilizadoOfControlFertilizadoListNewControlFertilizado = controlFertilizadoListNewControlFertilizado.getIdDetalleFertilizado();
                    controlFertilizadoListNewControlFertilizado.setIdDetalleFertilizado(detalleFertilizado);
                    controlFertilizadoListNewControlFertilizado = em.merge(controlFertilizadoListNewControlFertilizado);
                    if (oldIdDetalleFertilizadoOfControlFertilizadoListNewControlFertilizado != null && !oldIdDetalleFertilizadoOfControlFertilizadoListNewControlFertilizado.equals(detalleFertilizado)) {
                        oldIdDetalleFertilizadoOfControlFertilizadoListNewControlFertilizado.getControlFertilizadoList().remove(controlFertilizadoListNewControlFertilizado);
                        oldIdDetalleFertilizadoOfControlFertilizadoListNewControlFertilizado = em.merge(oldIdDetalleFertilizadoOfControlFertilizadoListNewControlFertilizado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleFertilizado.getIdDetalleFertilizado();
                if (findDetalleFertilizado(id) == null) {
                    throw new NonexistentEntityException("The detalleFertilizado with id " + id + " no longer exists.");
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
            DetalleFertilizado detalleFertilizado;
            try {
                detalleFertilizado = em.getReference(DetalleFertilizado.class, id);
                detalleFertilizado.getIdDetalleFertilizado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleFertilizado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ControlFertilizado> controlFertilizadoListOrphanCheck = detalleFertilizado.getControlFertilizadoList();
            for (ControlFertilizado controlFertilizadoListOrphanCheckControlFertilizado : controlFertilizadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleFertilizado (" + detalleFertilizado + ") cannot be destroyed since the ControlFertilizado " + controlFertilizadoListOrphanCheckControlFertilizado + " in its controlFertilizadoList field has a non-nullable idDetalleFertilizado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ControlFertilizado idFertilizado = detalleFertilizado.getIdFertilizado();
            if (idFertilizado != null) {
                idFertilizado.getDetalleFertilizadoList().remove(detalleFertilizado);
                idFertilizado = em.merge(idFertilizado);
            }
            Hectarea idHectarea = detalleFertilizado.getIdHectarea();
            if (idHectarea != null) {
                idHectarea.getDetalleFertilizadoList().remove(detalleFertilizado);
                idHectarea = em.merge(idHectarea);
            }
            em.remove(detalleFertilizado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleFertilizado> findDetalleFertilizadoEntities() {
        return findDetalleFertilizadoEntities(true, -1, -1);
    }

    public List<DetalleFertilizado> findDetalleFertilizadoEntities(int maxResults, int firstResult) {
        return findDetalleFertilizadoEntities(false, maxResults, firstResult);
    }

    private List<DetalleFertilizado> findDetalleFertilizadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleFertilizado.class));
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

    public DetalleFertilizado findDetalleFertilizado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleFertilizado.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleFertilizadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleFertilizado> rt = cq.from(DetalleFertilizado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

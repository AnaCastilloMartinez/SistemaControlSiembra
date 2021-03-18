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
import entidades.ControlRiego;
import entidades.DetalleRiego;
import entidades.Hectarea;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class DetalleRiegoJpaController implements Serializable {

    public DetalleRiegoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleRiego detalleRiego) throws IllegalOrphanException {
        if (detalleRiego.getControlRiegoList() == null) {
            detalleRiego.setControlRiegoList(new ArrayList<ControlRiego>());
        }
        List<String> illegalOrphanMessages = null;
        ControlRiego idRiegoOrphanCheck = detalleRiego.getIdRiego();
        if (idRiegoOrphanCheck != null) {
            DetalleRiego oldIdDetalleRiegoOfIdRiego = idRiegoOrphanCheck.getIdDetalleRiego();
            if (oldIdDetalleRiegoOfIdRiego != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The ControlRiego " + idRiegoOrphanCheck + " already has an item of type DetalleRiego whose idRiego column cannot be null. Please make another selection for the idRiego field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlRiego idRiego = detalleRiego.getIdRiego();
            if (idRiego != null) {
                idRiego = em.getReference(idRiego.getClass(), idRiego.getIdRiego());
                detalleRiego.setIdRiego(idRiego);
            }
            Hectarea idHectarea = detalleRiego.getIdHectarea();
            if (idHectarea != null) {
                idHectarea = em.getReference(idHectarea.getClass(), idHectarea.getIdHectarea());
                detalleRiego.setIdHectarea(idHectarea);
            }
            List<ControlRiego> attachedControlRiegoList = new ArrayList<ControlRiego>();
            for (ControlRiego controlRiegoListControlRiegoToAttach : detalleRiego.getControlRiegoList()) {
                controlRiegoListControlRiegoToAttach = em.getReference(controlRiegoListControlRiegoToAttach.getClass(), controlRiegoListControlRiegoToAttach.getIdRiego());
                attachedControlRiegoList.add(controlRiegoListControlRiegoToAttach);
            }
            detalleRiego.setControlRiegoList(attachedControlRiegoList);
            em.persist(detalleRiego);
            if (idRiego != null) {
                idRiego.setIdDetalleRiego(detalleRiego);
                idRiego = em.merge(idRiego);
            }
            if (idHectarea != null) {
                idHectarea.getDetalleRiegoList().add(detalleRiego);
                idHectarea = em.merge(idHectarea);
            }
            for (ControlRiego controlRiegoListControlRiego : detalleRiego.getControlRiegoList()) {
                DetalleRiego oldIdDetalleRiegoOfControlRiegoListControlRiego = controlRiegoListControlRiego.getIdDetalleRiego();
                controlRiegoListControlRiego.setIdDetalleRiego(detalleRiego);
                controlRiegoListControlRiego = em.merge(controlRiegoListControlRiego);
                if (oldIdDetalleRiegoOfControlRiegoListControlRiego != null) {
                    oldIdDetalleRiegoOfControlRiegoListControlRiego.getControlRiegoList().remove(controlRiegoListControlRiego);
                    oldIdDetalleRiegoOfControlRiegoListControlRiego = em.merge(oldIdDetalleRiegoOfControlRiegoListControlRiego);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleRiego detalleRiego) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleRiego persistentDetalleRiego = em.find(DetalleRiego.class, detalleRiego.getIdDetalleRiego());
            ControlRiego idRiegoOld = persistentDetalleRiego.getIdRiego();
            ControlRiego idRiegoNew = detalleRiego.getIdRiego();
            Hectarea idHectareaOld = persistentDetalleRiego.getIdHectarea();
            Hectarea idHectareaNew = detalleRiego.getIdHectarea();
            List<ControlRiego> controlRiegoListOld = persistentDetalleRiego.getControlRiegoList();
            List<ControlRiego> controlRiegoListNew = detalleRiego.getControlRiegoList();
            List<String> illegalOrphanMessages = null;
            if (idRiegoOld != null && !idRiegoOld.equals(idRiegoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ControlRiego " + idRiegoOld + " since its idDetalleRiego field is not nullable.");
            }
            if (idRiegoNew != null && !idRiegoNew.equals(idRiegoOld)) {
                DetalleRiego oldIdDetalleRiegoOfIdRiego = idRiegoNew.getIdDetalleRiego();
                if (oldIdDetalleRiegoOfIdRiego != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The ControlRiego " + idRiegoNew + " already has an item of type DetalleRiego whose idRiego column cannot be null. Please make another selection for the idRiego field.");
                }
            }
            for (ControlRiego controlRiegoListOldControlRiego : controlRiegoListOld) {
                if (!controlRiegoListNew.contains(controlRiegoListOldControlRiego)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlRiego " + controlRiegoListOldControlRiego + " since its idDetalleRiego field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idRiegoNew != null) {
                idRiegoNew = em.getReference(idRiegoNew.getClass(), idRiegoNew.getIdRiego());
                detalleRiego.setIdRiego(idRiegoNew);
            }
            if (idHectareaNew != null) {
                idHectareaNew = em.getReference(idHectareaNew.getClass(), idHectareaNew.getIdHectarea());
                detalleRiego.setIdHectarea(idHectareaNew);
            }
            List<ControlRiego> attachedControlRiegoListNew = new ArrayList<ControlRiego>();
            for (ControlRiego controlRiegoListNewControlRiegoToAttach : controlRiegoListNew) {
                controlRiegoListNewControlRiegoToAttach = em.getReference(controlRiegoListNewControlRiegoToAttach.getClass(), controlRiegoListNewControlRiegoToAttach.getIdRiego());
                attachedControlRiegoListNew.add(controlRiegoListNewControlRiegoToAttach);
            }
            controlRiegoListNew = attachedControlRiegoListNew;
            detalleRiego.setControlRiegoList(controlRiegoListNew);
            detalleRiego = em.merge(detalleRiego);
            if (idRiegoNew != null && !idRiegoNew.equals(idRiegoOld)) {
                idRiegoNew.setIdDetalleRiego(detalleRiego);
                idRiegoNew = em.merge(idRiegoNew);
            }
            if (idHectareaOld != null && !idHectareaOld.equals(idHectareaNew)) {
                idHectareaOld.getDetalleRiegoList().remove(detalleRiego);
                idHectareaOld = em.merge(idHectareaOld);
            }
            if (idHectareaNew != null && !idHectareaNew.equals(idHectareaOld)) {
                idHectareaNew.getDetalleRiegoList().add(detalleRiego);
                idHectareaNew = em.merge(idHectareaNew);
            }
            for (ControlRiego controlRiegoListNewControlRiego : controlRiegoListNew) {
                if (!controlRiegoListOld.contains(controlRiegoListNewControlRiego)) {
                    DetalleRiego oldIdDetalleRiegoOfControlRiegoListNewControlRiego = controlRiegoListNewControlRiego.getIdDetalleRiego();
                    controlRiegoListNewControlRiego.setIdDetalleRiego(detalleRiego);
                    controlRiegoListNewControlRiego = em.merge(controlRiegoListNewControlRiego);
                    if (oldIdDetalleRiegoOfControlRiegoListNewControlRiego != null && !oldIdDetalleRiegoOfControlRiegoListNewControlRiego.equals(detalleRiego)) {
                        oldIdDetalleRiegoOfControlRiegoListNewControlRiego.getControlRiegoList().remove(controlRiegoListNewControlRiego);
                        oldIdDetalleRiegoOfControlRiegoListNewControlRiego = em.merge(oldIdDetalleRiegoOfControlRiegoListNewControlRiego);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleRiego.getIdDetalleRiego();
                if (findDetalleRiego(id) == null) {
                    throw new NonexistentEntityException("The detalleRiego with id " + id + " no longer exists.");
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
            DetalleRiego detalleRiego;
            try {
                detalleRiego = em.getReference(DetalleRiego.class, id);
                detalleRiego.getIdDetalleRiego();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleRiego with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ControlRiego idRiegoOrphanCheck = detalleRiego.getIdRiego();
            if (idRiegoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleRiego (" + detalleRiego + ") cannot be destroyed since the ControlRiego " + idRiegoOrphanCheck + " in its idRiego field has a non-nullable idDetalleRiego field.");
            }
            List<ControlRiego> controlRiegoListOrphanCheck = detalleRiego.getControlRiegoList();
            for (ControlRiego controlRiegoListOrphanCheckControlRiego : controlRiegoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleRiego (" + detalleRiego + ") cannot be destroyed since the ControlRiego " + controlRiegoListOrphanCheckControlRiego + " in its controlRiegoList field has a non-nullable idDetalleRiego field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Hectarea idHectarea = detalleRiego.getIdHectarea();
            if (idHectarea != null) {
                idHectarea.getDetalleRiegoList().remove(detalleRiego);
                idHectarea = em.merge(idHectarea);
            }
            em.remove(detalleRiego);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleRiego> findDetalleRiegoEntities() {
        return findDetalleRiegoEntities(true, -1, -1);
    }

    public List<DetalleRiego> findDetalleRiegoEntities(int maxResults, int firstResult) {
        return findDetalleRiegoEntities(false, maxResults, firstResult);
    }

    private List<DetalleRiego> findDetalleRiegoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleRiego.class));
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

    public DetalleRiego findDetalleRiego(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleRiego.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleRiegoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleRiego> rt = cq.from(DetalleRiego.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

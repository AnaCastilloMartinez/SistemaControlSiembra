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
import entidades.ControlDeshierbe;
import entidades.DetalleDeshierbe;
import entidades.Hectarea;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class DetalleDeshierbeJpaController implements Serializable {

    public DetalleDeshierbeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleDeshierbe detalleDeshierbe) throws IllegalOrphanException {
        if (detalleDeshierbe.getControlDeshierbeList() == null) {
            detalleDeshierbe.setControlDeshierbeList(new ArrayList<ControlDeshierbe>());
        }
        List<String> illegalOrphanMessages = null;
        ControlDeshierbe idDeshierbeOrphanCheck = detalleDeshierbe.getIdDeshierbe();
        if (idDeshierbeOrphanCheck != null) {
            DetalleDeshierbe oldIdDetalleDeshierbeOfIdDeshierbe = idDeshierbeOrphanCheck.getIdDetalleDeshierbe();
            if (oldIdDetalleDeshierbeOfIdDeshierbe != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The ControlDeshierbe " + idDeshierbeOrphanCheck + " already has an item of type DetalleDeshierbe whose idDeshierbe column cannot be null. Please make another selection for the idDeshierbe field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlDeshierbe idDeshierbe = detalleDeshierbe.getIdDeshierbe();
            if (idDeshierbe != null) {
                idDeshierbe = em.getReference(idDeshierbe.getClass(), idDeshierbe.getIdDeshierbe());
                detalleDeshierbe.setIdDeshierbe(idDeshierbe);
            }
            Hectarea idHectarea = detalleDeshierbe.getIdHectarea();
            if (idHectarea != null) {
                idHectarea = em.getReference(idHectarea.getClass(), idHectarea.getIdHectarea());
                detalleDeshierbe.setIdHectarea(idHectarea);
            }
            List<ControlDeshierbe> attachedControlDeshierbeList = new ArrayList<ControlDeshierbe>();
            for (ControlDeshierbe controlDeshierbeListControlDeshierbeToAttach : detalleDeshierbe.getControlDeshierbeList()) {
                controlDeshierbeListControlDeshierbeToAttach = em.getReference(controlDeshierbeListControlDeshierbeToAttach.getClass(), controlDeshierbeListControlDeshierbeToAttach.getIdDeshierbe());
                attachedControlDeshierbeList.add(controlDeshierbeListControlDeshierbeToAttach);
            }
            detalleDeshierbe.setControlDeshierbeList(attachedControlDeshierbeList);
            em.persist(detalleDeshierbe);
            if (idDeshierbe != null) {
                idDeshierbe.setIdDetalleDeshierbe(detalleDeshierbe);
                idDeshierbe = em.merge(idDeshierbe);
            }
            if (idHectarea != null) {
                idHectarea.getDetalleDeshierbeList().add(detalleDeshierbe);
                idHectarea = em.merge(idHectarea);
            }
            for (ControlDeshierbe controlDeshierbeListControlDeshierbe : detalleDeshierbe.getControlDeshierbeList()) {
                DetalleDeshierbe oldIdDetalleDeshierbeOfControlDeshierbeListControlDeshierbe = controlDeshierbeListControlDeshierbe.getIdDetalleDeshierbe();
                controlDeshierbeListControlDeshierbe.setIdDetalleDeshierbe(detalleDeshierbe);
                controlDeshierbeListControlDeshierbe = em.merge(controlDeshierbeListControlDeshierbe);
                if (oldIdDetalleDeshierbeOfControlDeshierbeListControlDeshierbe != null) {
                    oldIdDetalleDeshierbeOfControlDeshierbeListControlDeshierbe.getControlDeshierbeList().remove(controlDeshierbeListControlDeshierbe);
                    oldIdDetalleDeshierbeOfControlDeshierbeListControlDeshierbe = em.merge(oldIdDetalleDeshierbeOfControlDeshierbeListControlDeshierbe);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleDeshierbe detalleDeshierbe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleDeshierbe persistentDetalleDeshierbe = em.find(DetalleDeshierbe.class, detalleDeshierbe.getIdDetalleDeshierbe());
            ControlDeshierbe idDeshierbeOld = persistentDetalleDeshierbe.getIdDeshierbe();
            ControlDeshierbe idDeshierbeNew = detalleDeshierbe.getIdDeshierbe();
            Hectarea idHectareaOld = persistentDetalleDeshierbe.getIdHectarea();
            Hectarea idHectareaNew = detalleDeshierbe.getIdHectarea();
            List<ControlDeshierbe> controlDeshierbeListOld = persistentDetalleDeshierbe.getControlDeshierbeList();
            List<ControlDeshierbe> controlDeshierbeListNew = detalleDeshierbe.getControlDeshierbeList();
            List<String> illegalOrphanMessages = null;
            if (idDeshierbeOld != null && !idDeshierbeOld.equals(idDeshierbeNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ControlDeshierbe " + idDeshierbeOld + " since its idDetalleDeshierbe field is not nullable.");
            }
            if (idDeshierbeNew != null && !idDeshierbeNew.equals(idDeshierbeOld)) {
                DetalleDeshierbe oldIdDetalleDeshierbeOfIdDeshierbe = idDeshierbeNew.getIdDetalleDeshierbe();
                if (oldIdDetalleDeshierbeOfIdDeshierbe != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The ControlDeshierbe " + idDeshierbeNew + " already has an item of type DetalleDeshierbe whose idDeshierbe column cannot be null. Please make another selection for the idDeshierbe field.");
                }
            }
            for (ControlDeshierbe controlDeshierbeListOldControlDeshierbe : controlDeshierbeListOld) {
                if (!controlDeshierbeListNew.contains(controlDeshierbeListOldControlDeshierbe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlDeshierbe " + controlDeshierbeListOldControlDeshierbe + " since its idDetalleDeshierbe field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDeshierbeNew != null) {
                idDeshierbeNew = em.getReference(idDeshierbeNew.getClass(), idDeshierbeNew.getIdDeshierbe());
                detalleDeshierbe.setIdDeshierbe(idDeshierbeNew);
            }
            if (idHectareaNew != null) {
                idHectareaNew = em.getReference(idHectareaNew.getClass(), idHectareaNew.getIdHectarea());
                detalleDeshierbe.setIdHectarea(idHectareaNew);
            }
            List<ControlDeshierbe> attachedControlDeshierbeListNew = new ArrayList<ControlDeshierbe>();
            for (ControlDeshierbe controlDeshierbeListNewControlDeshierbeToAttach : controlDeshierbeListNew) {
                controlDeshierbeListNewControlDeshierbeToAttach = em.getReference(controlDeshierbeListNewControlDeshierbeToAttach.getClass(), controlDeshierbeListNewControlDeshierbeToAttach.getIdDeshierbe());
                attachedControlDeshierbeListNew.add(controlDeshierbeListNewControlDeshierbeToAttach);
            }
            controlDeshierbeListNew = attachedControlDeshierbeListNew;
            detalleDeshierbe.setControlDeshierbeList(controlDeshierbeListNew);
            detalleDeshierbe = em.merge(detalleDeshierbe);
            if (idDeshierbeNew != null && !idDeshierbeNew.equals(idDeshierbeOld)) {
                idDeshierbeNew.setIdDetalleDeshierbe(detalleDeshierbe);
                idDeshierbeNew = em.merge(idDeshierbeNew);
            }
            if (idHectareaOld != null && !idHectareaOld.equals(idHectareaNew)) {
                idHectareaOld.getDetalleDeshierbeList().remove(detalleDeshierbe);
                idHectareaOld = em.merge(idHectareaOld);
            }
            if (idHectareaNew != null && !idHectareaNew.equals(idHectareaOld)) {
                idHectareaNew.getDetalleDeshierbeList().add(detalleDeshierbe);
                idHectareaNew = em.merge(idHectareaNew);
            }
            for (ControlDeshierbe controlDeshierbeListNewControlDeshierbe : controlDeshierbeListNew) {
                if (!controlDeshierbeListOld.contains(controlDeshierbeListNewControlDeshierbe)) {
                    DetalleDeshierbe oldIdDetalleDeshierbeOfControlDeshierbeListNewControlDeshierbe = controlDeshierbeListNewControlDeshierbe.getIdDetalleDeshierbe();
                    controlDeshierbeListNewControlDeshierbe.setIdDetalleDeshierbe(detalleDeshierbe);
                    controlDeshierbeListNewControlDeshierbe = em.merge(controlDeshierbeListNewControlDeshierbe);
                    if (oldIdDetalleDeshierbeOfControlDeshierbeListNewControlDeshierbe != null && !oldIdDetalleDeshierbeOfControlDeshierbeListNewControlDeshierbe.equals(detalleDeshierbe)) {
                        oldIdDetalleDeshierbeOfControlDeshierbeListNewControlDeshierbe.getControlDeshierbeList().remove(controlDeshierbeListNewControlDeshierbe);
                        oldIdDetalleDeshierbeOfControlDeshierbeListNewControlDeshierbe = em.merge(oldIdDetalleDeshierbeOfControlDeshierbeListNewControlDeshierbe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleDeshierbe.getIdDetalleDeshierbe();
                if (findDetalleDeshierbe(id) == null) {
                    throw new NonexistentEntityException("The detalleDeshierbe with id " + id + " no longer exists.");
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
            DetalleDeshierbe detalleDeshierbe;
            try {
                detalleDeshierbe = em.getReference(DetalleDeshierbe.class, id);
                detalleDeshierbe.getIdDetalleDeshierbe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleDeshierbe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ControlDeshierbe idDeshierbeOrphanCheck = detalleDeshierbe.getIdDeshierbe();
            if (idDeshierbeOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleDeshierbe (" + detalleDeshierbe + ") cannot be destroyed since the ControlDeshierbe " + idDeshierbeOrphanCheck + " in its idDeshierbe field has a non-nullable idDetalleDeshierbe field.");
            }
            List<ControlDeshierbe> controlDeshierbeListOrphanCheck = detalleDeshierbe.getControlDeshierbeList();
            for (ControlDeshierbe controlDeshierbeListOrphanCheckControlDeshierbe : controlDeshierbeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleDeshierbe (" + detalleDeshierbe + ") cannot be destroyed since the ControlDeshierbe " + controlDeshierbeListOrphanCheckControlDeshierbe + " in its controlDeshierbeList field has a non-nullable idDetalleDeshierbe field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Hectarea idHectarea = detalleDeshierbe.getIdHectarea();
            if (idHectarea != null) {
                idHectarea.getDetalleDeshierbeList().remove(detalleDeshierbe);
                idHectarea = em.merge(idHectarea);
            }
            em.remove(detalleDeshierbe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleDeshierbe> findDetalleDeshierbeEntities() {
        return findDetalleDeshierbeEntities(true, -1, -1);
    }

    public List<DetalleDeshierbe> findDetalleDeshierbeEntities(int maxResults, int firstResult) {
        return findDetalleDeshierbeEntities(false, maxResults, firstResult);
    }

    private List<DetalleDeshierbe> findDetalleDeshierbeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleDeshierbe.class));
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

    public DetalleDeshierbe findDetalleDeshierbe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleDeshierbe.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleDeshierbeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleDeshierbe> rt = cq.from(DetalleDeshierbe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

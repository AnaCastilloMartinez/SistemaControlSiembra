/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.ControlDeshierbe;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.DetalleDeshierbe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class ControlDeshierbeJpaController implements Serializable {

    public ControlDeshierbeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlDeshierbe controlDeshierbe) {
        if (controlDeshierbe.getDetalleDeshierbeList() == null) {
            controlDeshierbe.setDetalleDeshierbeList(new ArrayList<DetalleDeshierbe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleDeshierbe idDetalleDeshierbe = controlDeshierbe.getIdDetalleDeshierbe();
            if (idDetalleDeshierbe != null) {
                idDetalleDeshierbe = em.getReference(idDetalleDeshierbe.getClass(), idDetalleDeshierbe.getIdDetalleDeshierbe());
                controlDeshierbe.setIdDetalleDeshierbe(idDetalleDeshierbe);
            }
            List<DetalleDeshierbe> attachedDetalleDeshierbeList = new ArrayList<DetalleDeshierbe>();
            for (DetalleDeshierbe detalleDeshierbeListDetalleDeshierbeToAttach : controlDeshierbe.getDetalleDeshierbeList()) {
                detalleDeshierbeListDetalleDeshierbeToAttach = em.getReference(detalleDeshierbeListDetalleDeshierbeToAttach.getClass(), detalleDeshierbeListDetalleDeshierbeToAttach.getIdDetalleDeshierbe());
                attachedDetalleDeshierbeList.add(detalleDeshierbeListDetalleDeshierbeToAttach);
            }
            controlDeshierbe.setDetalleDeshierbeList(attachedDetalleDeshierbeList);
            em.persist(controlDeshierbe);
            if (idDetalleDeshierbe != null) {
                idDetalleDeshierbe.getControlDeshierbeList().add(controlDeshierbe);
                idDetalleDeshierbe = em.merge(idDetalleDeshierbe);
            }
            for (DetalleDeshierbe detalleDeshierbeListDetalleDeshierbe : controlDeshierbe.getDetalleDeshierbeList()) {
                ControlDeshierbe oldIdDeshierbeOfDetalleDeshierbeListDetalleDeshierbe = detalleDeshierbeListDetalleDeshierbe.getIdDeshierbe();
                detalleDeshierbeListDetalleDeshierbe.setIdDeshierbe(controlDeshierbe);
                detalleDeshierbeListDetalleDeshierbe = em.merge(detalleDeshierbeListDetalleDeshierbe);
                if (oldIdDeshierbeOfDetalleDeshierbeListDetalleDeshierbe != null) {
                    oldIdDeshierbeOfDetalleDeshierbeListDetalleDeshierbe.getDetalleDeshierbeList().remove(detalleDeshierbeListDetalleDeshierbe);
                    oldIdDeshierbeOfDetalleDeshierbeListDetalleDeshierbe = em.merge(oldIdDeshierbeOfDetalleDeshierbeListDetalleDeshierbe);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlDeshierbe controlDeshierbe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlDeshierbe persistentControlDeshierbe = em.find(ControlDeshierbe.class, controlDeshierbe.getIdDeshierbe());
            DetalleDeshierbe idDetalleDeshierbeOld = persistentControlDeshierbe.getIdDetalleDeshierbe();
            DetalleDeshierbe idDetalleDeshierbeNew = controlDeshierbe.getIdDetalleDeshierbe();
            List<DetalleDeshierbe> detalleDeshierbeListOld = persistentControlDeshierbe.getDetalleDeshierbeList();
            List<DetalleDeshierbe> detalleDeshierbeListNew = controlDeshierbe.getDetalleDeshierbeList();
            List<String> illegalOrphanMessages = null;
            for (DetalleDeshierbe detalleDeshierbeListOldDetalleDeshierbe : detalleDeshierbeListOld) {
                if (!detalleDeshierbeListNew.contains(detalleDeshierbeListOldDetalleDeshierbe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleDeshierbe " + detalleDeshierbeListOldDetalleDeshierbe + " since its idDeshierbe field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDetalleDeshierbeNew != null) {
                idDetalleDeshierbeNew = em.getReference(idDetalleDeshierbeNew.getClass(), idDetalleDeshierbeNew.getIdDetalleDeshierbe());
                controlDeshierbe.setIdDetalleDeshierbe(idDetalleDeshierbeNew);
            }
            List<DetalleDeshierbe> attachedDetalleDeshierbeListNew = new ArrayList<DetalleDeshierbe>();
            for (DetalleDeshierbe detalleDeshierbeListNewDetalleDeshierbeToAttach : detalleDeshierbeListNew) {
                detalleDeshierbeListNewDetalleDeshierbeToAttach = em.getReference(detalleDeshierbeListNewDetalleDeshierbeToAttach.getClass(), detalleDeshierbeListNewDetalleDeshierbeToAttach.getIdDetalleDeshierbe());
                attachedDetalleDeshierbeListNew.add(detalleDeshierbeListNewDetalleDeshierbeToAttach);
            }
            detalleDeshierbeListNew = attachedDetalleDeshierbeListNew;
            controlDeshierbe.setDetalleDeshierbeList(detalleDeshierbeListNew);
            controlDeshierbe = em.merge(controlDeshierbe);
            if (idDetalleDeshierbeOld != null && !idDetalleDeshierbeOld.equals(idDetalleDeshierbeNew)) {
                idDetalleDeshierbeOld.getControlDeshierbeList().remove(controlDeshierbe);
                idDetalleDeshierbeOld = em.merge(idDetalleDeshierbeOld);
            }
            if (idDetalleDeshierbeNew != null && !idDetalleDeshierbeNew.equals(idDetalleDeshierbeOld)) {
                idDetalleDeshierbeNew.getControlDeshierbeList().add(controlDeshierbe);
                idDetalleDeshierbeNew = em.merge(idDetalleDeshierbeNew);
            }
            for (DetalleDeshierbe detalleDeshierbeListNewDetalleDeshierbe : detalleDeshierbeListNew) {
                if (!detalleDeshierbeListOld.contains(detalleDeshierbeListNewDetalleDeshierbe)) {
                    ControlDeshierbe oldIdDeshierbeOfDetalleDeshierbeListNewDetalleDeshierbe = detalleDeshierbeListNewDetalleDeshierbe.getIdDeshierbe();
                    detalleDeshierbeListNewDetalleDeshierbe.setIdDeshierbe(controlDeshierbe);
                    detalleDeshierbeListNewDetalleDeshierbe = em.merge(detalleDeshierbeListNewDetalleDeshierbe);
                    if (oldIdDeshierbeOfDetalleDeshierbeListNewDetalleDeshierbe != null && !oldIdDeshierbeOfDetalleDeshierbeListNewDetalleDeshierbe.equals(controlDeshierbe)) {
                        oldIdDeshierbeOfDetalleDeshierbeListNewDetalleDeshierbe.getDetalleDeshierbeList().remove(detalleDeshierbeListNewDetalleDeshierbe);
                        oldIdDeshierbeOfDetalleDeshierbeListNewDetalleDeshierbe = em.merge(oldIdDeshierbeOfDetalleDeshierbeListNewDetalleDeshierbe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlDeshierbe.getIdDeshierbe();
                if (findControlDeshierbe(id) == null) {
                    throw new NonexistentEntityException("The controlDeshierbe with id " + id + " no longer exists.");
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
            ControlDeshierbe controlDeshierbe;
            try {
                controlDeshierbe = em.getReference(ControlDeshierbe.class, id);
                controlDeshierbe.getIdDeshierbe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlDeshierbe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleDeshierbe> detalleDeshierbeListOrphanCheck = controlDeshierbe.getDetalleDeshierbeList();
            for (DetalleDeshierbe detalleDeshierbeListOrphanCheckDetalleDeshierbe : detalleDeshierbeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ControlDeshierbe (" + controlDeshierbe + ") cannot be destroyed since the DetalleDeshierbe " + detalleDeshierbeListOrphanCheckDetalleDeshierbe + " in its detalleDeshierbeList field has a non-nullable idDeshierbe field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            DetalleDeshierbe idDetalleDeshierbe = controlDeshierbe.getIdDetalleDeshierbe();
            if (idDetalleDeshierbe != null) {
                idDetalleDeshierbe.getControlDeshierbeList().remove(controlDeshierbe);
                idDetalleDeshierbe = em.merge(idDetalleDeshierbe);
            }
            em.remove(controlDeshierbe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlDeshierbe> findControlDeshierbeEntities() {
        return findControlDeshierbeEntities(true, -1, -1);
    }

    public List<ControlDeshierbe> findControlDeshierbeEntities(int maxResults, int firstResult) {
        return findControlDeshierbeEntities(false, maxResults, firstResult);
    }

    private List<ControlDeshierbe> findControlDeshierbeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlDeshierbe.class));
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

    public ControlDeshierbe findControlDeshierbe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlDeshierbe.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlDeshierbeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlDeshierbe> rt = cq.from(ControlDeshierbe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

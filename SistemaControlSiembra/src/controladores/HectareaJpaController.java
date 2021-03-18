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
import entidades.DetalleFertilizado;
import java.util.ArrayList;
import java.util.List;
import entidades.DetalleDeshierbe;
import entidades.DetalleRiego;
import entidades.Hectarea;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class HectareaJpaController implements Serializable {

    public HectareaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hectarea hectarea) {
        if (hectarea.getDetalleFertilizadoList() == null) {
            hectarea.setDetalleFertilizadoList(new ArrayList<DetalleFertilizado>());
        }
        if (hectarea.getDetalleDeshierbeList() == null) {
            hectarea.setDetalleDeshierbeList(new ArrayList<DetalleDeshierbe>());
        }
        if (hectarea.getDetalleRiegoList() == null) {
            hectarea.setDetalleRiegoList(new ArrayList<DetalleRiego>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleFertilizado> attachedDetalleFertilizadoList = new ArrayList<DetalleFertilizado>();
            for (DetalleFertilizado detalleFertilizadoListDetalleFertilizadoToAttach : hectarea.getDetalleFertilizadoList()) {
                detalleFertilizadoListDetalleFertilizadoToAttach = em.getReference(detalleFertilizadoListDetalleFertilizadoToAttach.getClass(), detalleFertilizadoListDetalleFertilizadoToAttach.getIdDetalleFertilizado());
                attachedDetalleFertilizadoList.add(detalleFertilizadoListDetalleFertilizadoToAttach);
            }
            hectarea.setDetalleFertilizadoList(attachedDetalleFertilizadoList);
            List<DetalleDeshierbe> attachedDetalleDeshierbeList = new ArrayList<DetalleDeshierbe>();
            for (DetalleDeshierbe detalleDeshierbeListDetalleDeshierbeToAttach : hectarea.getDetalleDeshierbeList()) {
                detalleDeshierbeListDetalleDeshierbeToAttach = em.getReference(detalleDeshierbeListDetalleDeshierbeToAttach.getClass(), detalleDeshierbeListDetalleDeshierbeToAttach.getIdDetalleDeshierbe());
                attachedDetalleDeshierbeList.add(detalleDeshierbeListDetalleDeshierbeToAttach);
            }
            hectarea.setDetalleDeshierbeList(attachedDetalleDeshierbeList);
            List<DetalleRiego> attachedDetalleRiegoList = new ArrayList<DetalleRiego>();
            for (DetalleRiego detalleRiegoListDetalleRiegoToAttach : hectarea.getDetalleRiegoList()) {
                detalleRiegoListDetalleRiegoToAttach = em.getReference(detalleRiegoListDetalleRiegoToAttach.getClass(), detalleRiegoListDetalleRiegoToAttach.getIdDetalleRiego());
                attachedDetalleRiegoList.add(detalleRiegoListDetalleRiegoToAttach);
            }
            hectarea.setDetalleRiegoList(attachedDetalleRiegoList);
            em.persist(hectarea);
            for (DetalleFertilizado detalleFertilizadoListDetalleFertilizado : hectarea.getDetalleFertilizadoList()) {
                Hectarea oldIdHectareaOfDetalleFertilizadoListDetalleFertilizado = detalleFertilizadoListDetalleFertilizado.getIdHectarea();
                detalleFertilizadoListDetalleFertilizado.setIdHectarea(hectarea);
                detalleFertilizadoListDetalleFertilizado = em.merge(detalleFertilizadoListDetalleFertilizado);
                if (oldIdHectareaOfDetalleFertilizadoListDetalleFertilizado != null) {
                    oldIdHectareaOfDetalleFertilizadoListDetalleFertilizado.getDetalleFertilizadoList().remove(detalleFertilizadoListDetalleFertilizado);
                    oldIdHectareaOfDetalleFertilizadoListDetalleFertilizado = em.merge(oldIdHectareaOfDetalleFertilizadoListDetalleFertilizado);
                }
            }
            for (DetalleDeshierbe detalleDeshierbeListDetalleDeshierbe : hectarea.getDetalleDeshierbeList()) {
                Hectarea oldIdHectareaOfDetalleDeshierbeListDetalleDeshierbe = detalleDeshierbeListDetalleDeshierbe.getIdHectarea();
                detalleDeshierbeListDetalleDeshierbe.setIdHectarea(hectarea);
                detalleDeshierbeListDetalleDeshierbe = em.merge(detalleDeshierbeListDetalleDeshierbe);
                if (oldIdHectareaOfDetalleDeshierbeListDetalleDeshierbe != null) {
                    oldIdHectareaOfDetalleDeshierbeListDetalleDeshierbe.getDetalleDeshierbeList().remove(detalleDeshierbeListDetalleDeshierbe);
                    oldIdHectareaOfDetalleDeshierbeListDetalleDeshierbe = em.merge(oldIdHectareaOfDetalleDeshierbeListDetalleDeshierbe);
                }
            }
            for (DetalleRiego detalleRiegoListDetalleRiego : hectarea.getDetalleRiegoList()) {
                Hectarea oldIdHectareaOfDetalleRiegoListDetalleRiego = detalleRiegoListDetalleRiego.getIdHectarea();
                detalleRiegoListDetalleRiego.setIdHectarea(hectarea);
                detalleRiegoListDetalleRiego = em.merge(detalleRiegoListDetalleRiego);
                if (oldIdHectareaOfDetalleRiegoListDetalleRiego != null) {
                    oldIdHectareaOfDetalleRiegoListDetalleRiego.getDetalleRiegoList().remove(detalleRiegoListDetalleRiego);
                    oldIdHectareaOfDetalleRiegoListDetalleRiego = em.merge(oldIdHectareaOfDetalleRiegoListDetalleRiego);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hectarea hectarea) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hectarea persistentHectarea = em.find(Hectarea.class, hectarea.getIdHectarea());
            List<DetalleFertilizado> detalleFertilizadoListOld = persistentHectarea.getDetalleFertilizadoList();
            List<DetalleFertilizado> detalleFertilizadoListNew = hectarea.getDetalleFertilizadoList();
            List<DetalleDeshierbe> detalleDeshierbeListOld = persistentHectarea.getDetalleDeshierbeList();
            List<DetalleDeshierbe> detalleDeshierbeListNew = hectarea.getDetalleDeshierbeList();
            List<DetalleRiego> detalleRiegoListOld = persistentHectarea.getDetalleRiegoList();
            List<DetalleRiego> detalleRiegoListNew = hectarea.getDetalleRiegoList();
            List<String> illegalOrphanMessages = null;
            for (DetalleFertilizado detalleFertilizadoListOldDetalleFertilizado : detalleFertilizadoListOld) {
                if (!detalleFertilizadoListNew.contains(detalleFertilizadoListOldDetalleFertilizado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFertilizado " + detalleFertilizadoListOldDetalleFertilizado + " since its idHectarea field is not nullable.");
                }
            }
            for (DetalleDeshierbe detalleDeshierbeListOldDetalleDeshierbe : detalleDeshierbeListOld) {
                if (!detalleDeshierbeListNew.contains(detalleDeshierbeListOldDetalleDeshierbe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleDeshierbe " + detalleDeshierbeListOldDetalleDeshierbe + " since its idHectarea field is not nullable.");
                }
            }
            for (DetalleRiego detalleRiegoListOldDetalleRiego : detalleRiegoListOld) {
                if (!detalleRiegoListNew.contains(detalleRiegoListOldDetalleRiego)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleRiego " + detalleRiegoListOldDetalleRiego + " since its idHectarea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DetalleFertilizado> attachedDetalleFertilizadoListNew = new ArrayList<DetalleFertilizado>();
            for (DetalleFertilizado detalleFertilizadoListNewDetalleFertilizadoToAttach : detalleFertilizadoListNew) {
                detalleFertilizadoListNewDetalleFertilizadoToAttach = em.getReference(detalleFertilizadoListNewDetalleFertilizadoToAttach.getClass(), detalleFertilizadoListNewDetalleFertilizadoToAttach.getIdDetalleFertilizado());
                attachedDetalleFertilizadoListNew.add(detalleFertilizadoListNewDetalleFertilizadoToAttach);
            }
            detalleFertilizadoListNew = attachedDetalleFertilizadoListNew;
            hectarea.setDetalleFertilizadoList(detalleFertilizadoListNew);
            List<DetalleDeshierbe> attachedDetalleDeshierbeListNew = new ArrayList<DetalleDeshierbe>();
            for (DetalleDeshierbe detalleDeshierbeListNewDetalleDeshierbeToAttach : detalleDeshierbeListNew) {
                detalleDeshierbeListNewDetalleDeshierbeToAttach = em.getReference(detalleDeshierbeListNewDetalleDeshierbeToAttach.getClass(), detalleDeshierbeListNewDetalleDeshierbeToAttach.getIdDetalleDeshierbe());
                attachedDetalleDeshierbeListNew.add(detalleDeshierbeListNewDetalleDeshierbeToAttach);
            }
            detalleDeshierbeListNew = attachedDetalleDeshierbeListNew;
            hectarea.setDetalleDeshierbeList(detalleDeshierbeListNew);
            List<DetalleRiego> attachedDetalleRiegoListNew = new ArrayList<DetalleRiego>();
            for (DetalleRiego detalleRiegoListNewDetalleRiegoToAttach : detalleRiegoListNew) {
                detalleRiegoListNewDetalleRiegoToAttach = em.getReference(detalleRiegoListNewDetalleRiegoToAttach.getClass(), detalleRiegoListNewDetalleRiegoToAttach.getIdDetalleRiego());
                attachedDetalleRiegoListNew.add(detalleRiegoListNewDetalleRiegoToAttach);
            }
            detalleRiegoListNew = attachedDetalleRiegoListNew;
            hectarea.setDetalleRiegoList(detalleRiegoListNew);
            hectarea = em.merge(hectarea);
            for (DetalleFertilizado detalleFertilizadoListNewDetalleFertilizado : detalleFertilizadoListNew) {
                if (!detalleFertilizadoListOld.contains(detalleFertilizadoListNewDetalleFertilizado)) {
                    Hectarea oldIdHectareaOfDetalleFertilizadoListNewDetalleFertilizado = detalleFertilizadoListNewDetalleFertilizado.getIdHectarea();
                    detalleFertilizadoListNewDetalleFertilizado.setIdHectarea(hectarea);
                    detalleFertilizadoListNewDetalleFertilizado = em.merge(detalleFertilizadoListNewDetalleFertilizado);
                    if (oldIdHectareaOfDetalleFertilizadoListNewDetalleFertilizado != null && !oldIdHectareaOfDetalleFertilizadoListNewDetalleFertilizado.equals(hectarea)) {
                        oldIdHectareaOfDetalleFertilizadoListNewDetalleFertilizado.getDetalleFertilizadoList().remove(detalleFertilizadoListNewDetalleFertilizado);
                        oldIdHectareaOfDetalleFertilizadoListNewDetalleFertilizado = em.merge(oldIdHectareaOfDetalleFertilizadoListNewDetalleFertilizado);
                    }
                }
            }
            for (DetalleDeshierbe detalleDeshierbeListNewDetalleDeshierbe : detalleDeshierbeListNew) {
                if (!detalleDeshierbeListOld.contains(detalleDeshierbeListNewDetalleDeshierbe)) {
                    Hectarea oldIdHectareaOfDetalleDeshierbeListNewDetalleDeshierbe = detalleDeshierbeListNewDetalleDeshierbe.getIdHectarea();
                    detalleDeshierbeListNewDetalleDeshierbe.setIdHectarea(hectarea);
                    detalleDeshierbeListNewDetalleDeshierbe = em.merge(detalleDeshierbeListNewDetalleDeshierbe);
                    if (oldIdHectareaOfDetalleDeshierbeListNewDetalleDeshierbe != null && !oldIdHectareaOfDetalleDeshierbeListNewDetalleDeshierbe.equals(hectarea)) {
                        oldIdHectareaOfDetalleDeshierbeListNewDetalleDeshierbe.getDetalleDeshierbeList().remove(detalleDeshierbeListNewDetalleDeshierbe);
                        oldIdHectareaOfDetalleDeshierbeListNewDetalleDeshierbe = em.merge(oldIdHectareaOfDetalleDeshierbeListNewDetalleDeshierbe);
                    }
                }
            }
            for (DetalleRiego detalleRiegoListNewDetalleRiego : detalleRiegoListNew) {
                if (!detalleRiegoListOld.contains(detalleRiegoListNewDetalleRiego)) {
                    Hectarea oldIdHectareaOfDetalleRiegoListNewDetalleRiego = detalleRiegoListNewDetalleRiego.getIdHectarea();
                    detalleRiegoListNewDetalleRiego.setIdHectarea(hectarea);
                    detalleRiegoListNewDetalleRiego = em.merge(detalleRiegoListNewDetalleRiego);
                    if (oldIdHectareaOfDetalleRiegoListNewDetalleRiego != null && !oldIdHectareaOfDetalleRiegoListNewDetalleRiego.equals(hectarea)) {
                        oldIdHectareaOfDetalleRiegoListNewDetalleRiego.getDetalleRiegoList().remove(detalleRiegoListNewDetalleRiego);
                        oldIdHectareaOfDetalleRiegoListNewDetalleRiego = em.merge(oldIdHectareaOfDetalleRiegoListNewDetalleRiego);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hectarea.getIdHectarea();
                if (findHectarea(id) == null) {
                    throw new NonexistentEntityException("The hectarea with id " + id + " no longer exists.");
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
            Hectarea hectarea;
            try {
                hectarea = em.getReference(Hectarea.class, id);
                hectarea.getIdHectarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hectarea with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleFertilizado> detalleFertilizadoListOrphanCheck = hectarea.getDetalleFertilizadoList();
            for (DetalleFertilizado detalleFertilizadoListOrphanCheckDetalleFertilizado : detalleFertilizadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hectarea (" + hectarea + ") cannot be destroyed since the DetalleFertilizado " + detalleFertilizadoListOrphanCheckDetalleFertilizado + " in its detalleFertilizadoList field has a non-nullable idHectarea field.");
            }
            List<DetalleDeshierbe> detalleDeshierbeListOrphanCheck = hectarea.getDetalleDeshierbeList();
            for (DetalleDeshierbe detalleDeshierbeListOrphanCheckDetalleDeshierbe : detalleDeshierbeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hectarea (" + hectarea + ") cannot be destroyed since the DetalleDeshierbe " + detalleDeshierbeListOrphanCheckDetalleDeshierbe + " in its detalleDeshierbeList field has a non-nullable idHectarea field.");
            }
            List<DetalleRiego> detalleRiegoListOrphanCheck = hectarea.getDetalleRiegoList();
            for (DetalleRiego detalleRiegoListOrphanCheckDetalleRiego : detalleRiegoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hectarea (" + hectarea + ") cannot be destroyed since the DetalleRiego " + detalleRiegoListOrphanCheckDetalleRiego + " in its detalleRiegoList field has a non-nullable idHectarea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(hectarea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hectarea> findHectareaEntities() {
        return findHectareaEntities(true, -1, -1);
    }

    public List<Hectarea> findHectareaEntities(int maxResults, int firstResult) {
        return findHectareaEntities(false, maxResults, firstResult);
    }

    private List<Hectarea> findHectareaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hectarea.class));
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

    public Hectarea findHectarea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hectarea.class, id);
        } finally {
            em.close();
        }
    }

    public int getHectareaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hectarea> rt = cq.from(Hectarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

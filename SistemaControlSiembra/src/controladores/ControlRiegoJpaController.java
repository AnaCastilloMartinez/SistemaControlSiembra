/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.ControlRiego;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.DetalleRiego;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class ControlRiegoJpaController implements Serializable {

    public ControlRiegoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlRiego controlRiego) {
        if (controlRiego.getDetalleRiegoList() == null) {
            controlRiego.setDetalleRiegoList(new ArrayList<DetalleRiego>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleRiego idDetalleRiego = controlRiego.getIdDetalleRiego();
            if (idDetalleRiego != null) {
                idDetalleRiego = em.getReference(idDetalleRiego.getClass(), idDetalleRiego.getIdDetalleRiego());
                controlRiego.setIdDetalleRiego(idDetalleRiego);
            }
            Usuario idUsuario = controlRiego.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                controlRiego.setIdUsuario(idUsuario);
            }
            List<DetalleRiego> attachedDetalleRiegoList = new ArrayList<DetalleRiego>();
            for (DetalleRiego detalleRiegoListDetalleRiegoToAttach : controlRiego.getDetalleRiegoList()) {
                detalleRiegoListDetalleRiegoToAttach = em.getReference(detalleRiegoListDetalleRiegoToAttach.getClass(), detalleRiegoListDetalleRiegoToAttach.getIdDetalleRiego());
                attachedDetalleRiegoList.add(detalleRiegoListDetalleRiegoToAttach);
            }
            controlRiego.setDetalleRiegoList(attachedDetalleRiegoList);
            em.persist(controlRiego);
            if (idDetalleRiego != null) {
                idDetalleRiego.getControlRiegoList().add(controlRiego);
                idDetalleRiego = em.merge(idDetalleRiego);
            }
            if (idUsuario != null) {
                idUsuario.getControlRiegoList().add(controlRiego);
                idUsuario = em.merge(idUsuario);
            }
            for (DetalleRiego detalleRiegoListDetalleRiego : controlRiego.getDetalleRiegoList()) {
                ControlRiego oldIdRiegoOfDetalleRiegoListDetalleRiego = detalleRiegoListDetalleRiego.getIdRiego();
                detalleRiegoListDetalleRiego.setIdRiego(controlRiego);
                detalleRiegoListDetalleRiego = em.merge(detalleRiegoListDetalleRiego);
                if (oldIdRiegoOfDetalleRiegoListDetalleRiego != null) {
                    oldIdRiegoOfDetalleRiegoListDetalleRiego.getDetalleRiegoList().remove(detalleRiegoListDetalleRiego);
                    oldIdRiegoOfDetalleRiegoListDetalleRiego = em.merge(oldIdRiegoOfDetalleRiegoListDetalleRiego);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlRiego controlRiego) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlRiego persistentControlRiego = em.find(ControlRiego.class, controlRiego.getIdRiego());
            DetalleRiego idDetalleRiegoOld = persistentControlRiego.getIdDetalleRiego();
            DetalleRiego idDetalleRiegoNew = controlRiego.getIdDetalleRiego();
            Usuario idUsuarioOld = persistentControlRiego.getIdUsuario();
            Usuario idUsuarioNew = controlRiego.getIdUsuario();
            List<DetalleRiego> detalleRiegoListOld = persistentControlRiego.getDetalleRiegoList();
            List<DetalleRiego> detalleRiegoListNew = controlRiego.getDetalleRiegoList();
            List<String> illegalOrphanMessages = null;
            for (DetalleRiego detalleRiegoListOldDetalleRiego : detalleRiegoListOld) {
                if (!detalleRiegoListNew.contains(detalleRiegoListOldDetalleRiego)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleRiego " + detalleRiegoListOldDetalleRiego + " since its idRiego field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDetalleRiegoNew != null) {
                idDetalleRiegoNew = em.getReference(idDetalleRiegoNew.getClass(), idDetalleRiegoNew.getIdDetalleRiego());
                controlRiego.setIdDetalleRiego(idDetalleRiegoNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                controlRiego.setIdUsuario(idUsuarioNew);
            }
            List<DetalleRiego> attachedDetalleRiegoListNew = new ArrayList<DetalleRiego>();
            for (DetalleRiego detalleRiegoListNewDetalleRiegoToAttach : detalleRiegoListNew) {
                detalleRiegoListNewDetalleRiegoToAttach = em.getReference(detalleRiegoListNewDetalleRiegoToAttach.getClass(), detalleRiegoListNewDetalleRiegoToAttach.getIdDetalleRiego());
                attachedDetalleRiegoListNew.add(detalleRiegoListNewDetalleRiegoToAttach);
            }
            detalleRiegoListNew = attachedDetalleRiegoListNew;
            controlRiego.setDetalleRiegoList(detalleRiegoListNew);
            controlRiego = em.merge(controlRiego);
            if (idDetalleRiegoOld != null && !idDetalleRiegoOld.equals(idDetalleRiegoNew)) {
                idDetalleRiegoOld.getControlRiegoList().remove(controlRiego);
                idDetalleRiegoOld = em.merge(idDetalleRiegoOld);
            }
            if (idDetalleRiegoNew != null && !idDetalleRiegoNew.equals(idDetalleRiegoOld)) {
                idDetalleRiegoNew.getControlRiegoList().add(controlRiego);
                idDetalleRiegoNew = em.merge(idDetalleRiegoNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getControlRiegoList().remove(controlRiego);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getControlRiegoList().add(controlRiego);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (DetalleRiego detalleRiegoListNewDetalleRiego : detalleRiegoListNew) {
                if (!detalleRiegoListOld.contains(detalleRiegoListNewDetalleRiego)) {
                    ControlRiego oldIdRiegoOfDetalleRiegoListNewDetalleRiego = detalleRiegoListNewDetalleRiego.getIdRiego();
                    detalleRiegoListNewDetalleRiego.setIdRiego(controlRiego);
                    detalleRiegoListNewDetalleRiego = em.merge(detalleRiegoListNewDetalleRiego);
                    if (oldIdRiegoOfDetalleRiegoListNewDetalleRiego != null && !oldIdRiegoOfDetalleRiegoListNewDetalleRiego.equals(controlRiego)) {
                        oldIdRiegoOfDetalleRiegoListNewDetalleRiego.getDetalleRiegoList().remove(detalleRiegoListNewDetalleRiego);
                        oldIdRiegoOfDetalleRiegoListNewDetalleRiego = em.merge(oldIdRiegoOfDetalleRiegoListNewDetalleRiego);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlRiego.getIdRiego();
                if (findControlRiego(id) == null) {
                    throw new NonexistentEntityException("The controlRiego with id " + id + " no longer exists.");
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
            ControlRiego controlRiego;
            try {
                controlRiego = em.getReference(ControlRiego.class, id);
                controlRiego.getIdRiego();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlRiego with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleRiego> detalleRiegoListOrphanCheck = controlRiego.getDetalleRiegoList();
            for (DetalleRiego detalleRiegoListOrphanCheckDetalleRiego : detalleRiegoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ControlRiego (" + controlRiego + ") cannot be destroyed since the DetalleRiego " + detalleRiegoListOrphanCheckDetalleRiego + " in its detalleRiegoList field has a non-nullable idRiego field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            DetalleRiego idDetalleRiego = controlRiego.getIdDetalleRiego();
            if (idDetalleRiego != null) {
                idDetalleRiego.getControlRiegoList().remove(controlRiego);
                idDetalleRiego = em.merge(idDetalleRiego);
            }
            Usuario idUsuario = controlRiego.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getControlRiegoList().remove(controlRiego);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(controlRiego);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlRiego> findControlRiegoEntities() {
        return findControlRiegoEntities(true, -1, -1);
    }

    public List<ControlRiego> findControlRiegoEntities(int maxResults, int firstResult) {
        return findControlRiegoEntities(false, maxResults, firstResult);
    }

    private List<ControlRiego> findControlRiegoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlRiego.class));
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

    public ControlRiego findControlRiego(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlRiego.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlRiegoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlRiego> rt = cq.from(ControlRiego.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

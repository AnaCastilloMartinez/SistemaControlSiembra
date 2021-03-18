/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.ControlFertilizado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.DetalleFertilizado;
import entidades.Insumo;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class ControlFertilizadoJpaController implements Serializable {

    public ControlFertilizadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlFertilizado controlFertilizado) throws IllegalOrphanException {
        if (controlFertilizado.getDetalleFertilizadoList() == null) {
            controlFertilizado.setDetalleFertilizadoList(new ArrayList<DetalleFertilizado>());
        }
        List<String> illegalOrphanMessages = null;
        DetalleFertilizado idDetalleFertilizadoOrphanCheck = controlFertilizado.getIdDetalleFertilizado();
        if (idDetalleFertilizadoOrphanCheck != null) {
            ControlFertilizado oldIdFertilizadoOfIdDetalleFertilizado = idDetalleFertilizadoOrphanCheck.getIdFertilizado();
            if (oldIdFertilizadoOfIdDetalleFertilizado != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The DetalleFertilizado " + idDetalleFertilizadoOrphanCheck + " already has an item of type ControlFertilizado whose idDetalleFertilizado column cannot be null. Please make another selection for the idDetalleFertilizado field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFertilizado idDetalleFertilizado = controlFertilizado.getIdDetalleFertilizado();
            if (idDetalleFertilizado != null) {
                idDetalleFertilizado = em.getReference(idDetalleFertilizado.getClass(), idDetalleFertilizado.getIdDetalleFertilizado());
                controlFertilizado.setIdDetalleFertilizado(idDetalleFertilizado);
            }
            Insumo idInsumo = controlFertilizado.getIdInsumo();
            if (idInsumo != null) {
                idInsumo = em.getReference(idInsumo.getClass(), idInsumo.getIdInsumo());
                controlFertilizado.setIdInsumo(idInsumo);
            }
            Usuario idUsuario = controlFertilizado.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                controlFertilizado.setIdUsuario(idUsuario);
            }
            List<DetalleFertilizado> attachedDetalleFertilizadoList = new ArrayList<DetalleFertilizado>();
            for (DetalleFertilizado detalleFertilizadoListDetalleFertilizadoToAttach : controlFertilizado.getDetalleFertilizadoList()) {
                detalleFertilizadoListDetalleFertilizadoToAttach = em.getReference(detalleFertilizadoListDetalleFertilizadoToAttach.getClass(), detalleFertilizadoListDetalleFertilizadoToAttach.getIdDetalleFertilizado());
                attachedDetalleFertilizadoList.add(detalleFertilizadoListDetalleFertilizadoToAttach);
            }
            controlFertilizado.setDetalleFertilizadoList(attachedDetalleFertilizadoList);
            em.persist(controlFertilizado);
            if (idDetalleFertilizado != null) {
                idDetalleFertilizado.setIdFertilizado(controlFertilizado);
                idDetalleFertilizado = em.merge(idDetalleFertilizado);
            }
            if (idInsumo != null) {
                idInsumo.getControlFertilizadoList().add(controlFertilizado);
                idInsumo = em.merge(idInsumo);
            }
            if (idUsuario != null) {
                idUsuario.getControlFertilizadoList().add(controlFertilizado);
                idUsuario = em.merge(idUsuario);
            }
            for (DetalleFertilizado detalleFertilizadoListDetalleFertilizado : controlFertilizado.getDetalleFertilizadoList()) {
                ControlFertilizado oldIdFertilizadoOfDetalleFertilizadoListDetalleFertilizado = detalleFertilizadoListDetalleFertilizado.getIdFertilizado();
                detalleFertilizadoListDetalleFertilizado.setIdFertilizado(controlFertilizado);
                detalleFertilizadoListDetalleFertilizado = em.merge(detalleFertilizadoListDetalleFertilizado);
                if (oldIdFertilizadoOfDetalleFertilizadoListDetalleFertilizado != null) {
                    oldIdFertilizadoOfDetalleFertilizadoListDetalleFertilizado.getDetalleFertilizadoList().remove(detalleFertilizadoListDetalleFertilizado);
                    oldIdFertilizadoOfDetalleFertilizadoListDetalleFertilizado = em.merge(oldIdFertilizadoOfDetalleFertilizadoListDetalleFertilizado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlFertilizado controlFertilizado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlFertilizado persistentControlFertilizado = em.find(ControlFertilizado.class, controlFertilizado.getIdFertilizado());
            DetalleFertilizado idDetalleFertilizadoOld = persistentControlFertilizado.getIdDetalleFertilizado();
            DetalleFertilizado idDetalleFertilizadoNew = controlFertilizado.getIdDetalleFertilizado();
            Insumo idInsumoOld = persistentControlFertilizado.getIdInsumo();
            Insumo idInsumoNew = controlFertilizado.getIdInsumo();
            Usuario idUsuarioOld = persistentControlFertilizado.getIdUsuario();
            Usuario idUsuarioNew = controlFertilizado.getIdUsuario();
            List<DetalleFertilizado> detalleFertilizadoListOld = persistentControlFertilizado.getDetalleFertilizadoList();
            List<DetalleFertilizado> detalleFertilizadoListNew = controlFertilizado.getDetalleFertilizadoList();
            List<String> illegalOrphanMessages = null;
            if (idDetalleFertilizadoOld != null && !idDetalleFertilizadoOld.equals(idDetalleFertilizadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DetalleFertilizado " + idDetalleFertilizadoOld + " since its idFertilizado field is not nullable.");
            }
            if (idDetalleFertilizadoNew != null && !idDetalleFertilizadoNew.equals(idDetalleFertilizadoOld)) {
                ControlFertilizado oldIdFertilizadoOfIdDetalleFertilizado = idDetalleFertilizadoNew.getIdFertilizado();
                if (oldIdFertilizadoOfIdDetalleFertilizado != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The DetalleFertilizado " + idDetalleFertilizadoNew + " already has an item of type ControlFertilizado whose idDetalleFertilizado column cannot be null. Please make another selection for the idDetalleFertilizado field.");
                }
            }
            for (DetalleFertilizado detalleFertilizadoListOldDetalleFertilizado : detalleFertilizadoListOld) {
                if (!detalleFertilizadoListNew.contains(detalleFertilizadoListOldDetalleFertilizado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFertilizado " + detalleFertilizadoListOldDetalleFertilizado + " since its idFertilizado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDetalleFertilizadoNew != null) {
                idDetalleFertilizadoNew = em.getReference(idDetalleFertilizadoNew.getClass(), idDetalleFertilizadoNew.getIdDetalleFertilizado());
                controlFertilizado.setIdDetalleFertilizado(idDetalleFertilizadoNew);
            }
            if (idInsumoNew != null) {
                idInsumoNew = em.getReference(idInsumoNew.getClass(), idInsumoNew.getIdInsumo());
                controlFertilizado.setIdInsumo(idInsumoNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                controlFertilizado.setIdUsuario(idUsuarioNew);
            }
            List<DetalleFertilizado> attachedDetalleFertilizadoListNew = new ArrayList<DetalleFertilizado>();
            for (DetalleFertilizado detalleFertilizadoListNewDetalleFertilizadoToAttach : detalleFertilizadoListNew) {
                detalleFertilizadoListNewDetalleFertilizadoToAttach = em.getReference(detalleFertilizadoListNewDetalleFertilizadoToAttach.getClass(), detalleFertilizadoListNewDetalleFertilizadoToAttach.getIdDetalleFertilizado());
                attachedDetalleFertilizadoListNew.add(detalleFertilizadoListNewDetalleFertilizadoToAttach);
            }
            detalleFertilizadoListNew = attachedDetalleFertilizadoListNew;
            controlFertilizado.setDetalleFertilizadoList(detalleFertilizadoListNew);
            controlFertilizado = em.merge(controlFertilizado);
            if (idDetalleFertilizadoNew != null && !idDetalleFertilizadoNew.equals(idDetalleFertilizadoOld)) {
                idDetalleFertilizadoNew.setIdFertilizado(controlFertilizado);
                idDetalleFertilizadoNew = em.merge(idDetalleFertilizadoNew);
            }
            if (idInsumoOld != null && !idInsumoOld.equals(idInsumoNew)) {
                idInsumoOld.getControlFertilizadoList().remove(controlFertilizado);
                idInsumoOld = em.merge(idInsumoOld);
            }
            if (idInsumoNew != null && !idInsumoNew.equals(idInsumoOld)) {
                idInsumoNew.getControlFertilizadoList().add(controlFertilizado);
                idInsumoNew = em.merge(idInsumoNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getControlFertilizadoList().remove(controlFertilizado);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getControlFertilizadoList().add(controlFertilizado);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (DetalleFertilizado detalleFertilizadoListNewDetalleFertilizado : detalleFertilizadoListNew) {
                if (!detalleFertilizadoListOld.contains(detalleFertilizadoListNewDetalleFertilizado)) {
                    ControlFertilizado oldIdFertilizadoOfDetalleFertilizadoListNewDetalleFertilizado = detalleFertilizadoListNewDetalleFertilizado.getIdFertilizado();
                    detalleFertilizadoListNewDetalleFertilizado.setIdFertilizado(controlFertilizado);
                    detalleFertilizadoListNewDetalleFertilizado = em.merge(detalleFertilizadoListNewDetalleFertilizado);
                    if (oldIdFertilizadoOfDetalleFertilizadoListNewDetalleFertilizado != null && !oldIdFertilizadoOfDetalleFertilizadoListNewDetalleFertilizado.equals(controlFertilizado)) {
                        oldIdFertilizadoOfDetalleFertilizadoListNewDetalleFertilizado.getDetalleFertilizadoList().remove(detalleFertilizadoListNewDetalleFertilizado);
                        oldIdFertilizadoOfDetalleFertilizadoListNewDetalleFertilizado = em.merge(oldIdFertilizadoOfDetalleFertilizadoListNewDetalleFertilizado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlFertilizado.getIdFertilizado();
                if (findControlFertilizado(id) == null) {
                    throw new NonexistentEntityException("The controlFertilizado with id " + id + " no longer exists.");
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
            ControlFertilizado controlFertilizado;
            try {
                controlFertilizado = em.getReference(ControlFertilizado.class, id);
                controlFertilizado.getIdFertilizado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlFertilizado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            DetalleFertilizado idDetalleFertilizadoOrphanCheck = controlFertilizado.getIdDetalleFertilizado();
            if (idDetalleFertilizadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ControlFertilizado (" + controlFertilizado + ") cannot be destroyed since the DetalleFertilizado " + idDetalleFertilizadoOrphanCheck + " in its idDetalleFertilizado field has a non-nullable idFertilizado field.");
            }
            List<DetalleFertilizado> detalleFertilizadoListOrphanCheck = controlFertilizado.getDetalleFertilizadoList();
            for (DetalleFertilizado detalleFertilizadoListOrphanCheckDetalleFertilizado : detalleFertilizadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ControlFertilizado (" + controlFertilizado + ") cannot be destroyed since the DetalleFertilizado " + detalleFertilizadoListOrphanCheckDetalleFertilizado + " in its detalleFertilizadoList field has a non-nullable idFertilizado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Insumo idInsumo = controlFertilizado.getIdInsumo();
            if (idInsumo != null) {
                idInsumo.getControlFertilizadoList().remove(controlFertilizado);
                idInsumo = em.merge(idInsumo);
            }
            Usuario idUsuario = controlFertilizado.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getControlFertilizadoList().remove(controlFertilizado);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(controlFertilizado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlFertilizado> findControlFertilizadoEntities() {
        return findControlFertilizadoEntities(true, -1, -1);
    }

    public List<ControlFertilizado> findControlFertilizadoEntities(int maxResults, int firstResult) {
        return findControlFertilizadoEntities(false, maxResults, firstResult);
    }

    private List<ControlFertilizado> findControlFertilizadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlFertilizado.class));
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

    public ControlFertilizado findControlFertilizado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlFertilizado.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlFertilizadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlFertilizado> rt = cq.from(ControlFertilizado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

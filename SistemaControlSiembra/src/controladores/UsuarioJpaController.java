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
import java.util.ArrayList;
import java.util.List;
import entidades.ControlRiego;
import entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author anacastillo
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getControlFertilizadoList() == null) {
            usuario.setControlFertilizadoList(new ArrayList<ControlFertilizado>());
        }
        if (usuario.getControlRiegoList() == null) {
            usuario.setControlRiegoList(new ArrayList<ControlRiego>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ControlFertilizado> attachedControlFertilizadoList = new ArrayList<ControlFertilizado>();
            for (ControlFertilizado controlFertilizadoListControlFertilizadoToAttach : usuario.getControlFertilizadoList()) {
                controlFertilizadoListControlFertilizadoToAttach = em.getReference(controlFertilizadoListControlFertilizadoToAttach.getClass(), controlFertilizadoListControlFertilizadoToAttach.getIdFertilizado());
                attachedControlFertilizadoList.add(controlFertilizadoListControlFertilizadoToAttach);
            }
            usuario.setControlFertilizadoList(attachedControlFertilizadoList);
            List<ControlRiego> attachedControlRiegoList = new ArrayList<ControlRiego>();
            for (ControlRiego controlRiegoListControlRiegoToAttach : usuario.getControlRiegoList()) {
                controlRiegoListControlRiegoToAttach = em.getReference(controlRiegoListControlRiegoToAttach.getClass(), controlRiegoListControlRiegoToAttach.getIdRiego());
                attachedControlRiegoList.add(controlRiegoListControlRiegoToAttach);
            }
            usuario.setControlRiegoList(attachedControlRiegoList);
            em.persist(usuario);
            for (ControlFertilizado controlFertilizadoListControlFertilizado : usuario.getControlFertilizadoList()) {
                Usuario oldIdUsuarioOfControlFertilizadoListControlFertilizado = controlFertilizadoListControlFertilizado.getIdUsuario();
                controlFertilizadoListControlFertilizado.setIdUsuario(usuario);
                controlFertilizadoListControlFertilizado = em.merge(controlFertilizadoListControlFertilizado);
                if (oldIdUsuarioOfControlFertilizadoListControlFertilizado != null) {
                    oldIdUsuarioOfControlFertilizadoListControlFertilizado.getControlFertilizadoList().remove(controlFertilizadoListControlFertilizado);
                    oldIdUsuarioOfControlFertilizadoListControlFertilizado = em.merge(oldIdUsuarioOfControlFertilizadoListControlFertilizado);
                }
            }
            for (ControlRiego controlRiegoListControlRiego : usuario.getControlRiegoList()) {
                Usuario oldIdUsuarioOfControlRiegoListControlRiego = controlRiegoListControlRiego.getIdUsuario();
                controlRiegoListControlRiego.setIdUsuario(usuario);
                controlRiegoListControlRiego = em.merge(controlRiegoListControlRiego);
                if (oldIdUsuarioOfControlRiegoListControlRiego != null) {
                    oldIdUsuarioOfControlRiegoListControlRiego.getControlRiegoList().remove(controlRiegoListControlRiego);
                    oldIdUsuarioOfControlRiegoListControlRiego = em.merge(oldIdUsuarioOfControlRiegoListControlRiego);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            List<ControlFertilizado> controlFertilizadoListOld = persistentUsuario.getControlFertilizadoList();
            List<ControlFertilizado> controlFertilizadoListNew = usuario.getControlFertilizadoList();
            List<ControlRiego> controlRiegoListOld = persistentUsuario.getControlRiegoList();
            List<ControlRiego> controlRiegoListNew = usuario.getControlRiegoList();
            List<String> illegalOrphanMessages = null;
            for (ControlFertilizado controlFertilizadoListOldControlFertilizado : controlFertilizadoListOld) {
                if (!controlFertilizadoListNew.contains(controlFertilizadoListOldControlFertilizado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlFertilizado " + controlFertilizadoListOldControlFertilizado + " since its idUsuario field is not nullable.");
                }
            }
            for (ControlRiego controlRiegoListOldControlRiego : controlRiegoListOld) {
                if (!controlRiegoListNew.contains(controlRiegoListOldControlRiego)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ControlRiego " + controlRiegoListOldControlRiego + " since its idUsuario field is not nullable.");
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
            usuario.setControlFertilizadoList(controlFertilizadoListNew);
            List<ControlRiego> attachedControlRiegoListNew = new ArrayList<ControlRiego>();
            for (ControlRiego controlRiegoListNewControlRiegoToAttach : controlRiegoListNew) {
                controlRiegoListNewControlRiegoToAttach = em.getReference(controlRiegoListNewControlRiegoToAttach.getClass(), controlRiegoListNewControlRiegoToAttach.getIdRiego());
                attachedControlRiegoListNew.add(controlRiegoListNewControlRiegoToAttach);
            }
            controlRiegoListNew = attachedControlRiegoListNew;
            usuario.setControlRiegoList(controlRiegoListNew);
            usuario = em.merge(usuario);
            for (ControlFertilizado controlFertilizadoListNewControlFertilizado : controlFertilizadoListNew) {
                if (!controlFertilizadoListOld.contains(controlFertilizadoListNewControlFertilizado)) {
                    Usuario oldIdUsuarioOfControlFertilizadoListNewControlFertilizado = controlFertilizadoListNewControlFertilizado.getIdUsuario();
                    controlFertilizadoListNewControlFertilizado.setIdUsuario(usuario);
                    controlFertilizadoListNewControlFertilizado = em.merge(controlFertilizadoListNewControlFertilizado);
                    if (oldIdUsuarioOfControlFertilizadoListNewControlFertilizado != null && !oldIdUsuarioOfControlFertilizadoListNewControlFertilizado.equals(usuario)) {
                        oldIdUsuarioOfControlFertilizadoListNewControlFertilizado.getControlFertilizadoList().remove(controlFertilizadoListNewControlFertilizado);
                        oldIdUsuarioOfControlFertilizadoListNewControlFertilizado = em.merge(oldIdUsuarioOfControlFertilizadoListNewControlFertilizado);
                    }
                }
            }
            for (ControlRiego controlRiegoListNewControlRiego : controlRiegoListNew) {
                if (!controlRiegoListOld.contains(controlRiegoListNewControlRiego)) {
                    Usuario oldIdUsuarioOfControlRiegoListNewControlRiego = controlRiegoListNewControlRiego.getIdUsuario();
                    controlRiegoListNewControlRiego.setIdUsuario(usuario);
                    controlRiegoListNewControlRiego = em.merge(controlRiegoListNewControlRiego);
                    if (oldIdUsuarioOfControlRiegoListNewControlRiego != null && !oldIdUsuarioOfControlRiegoListNewControlRiego.equals(usuario)) {
                        oldIdUsuarioOfControlRiegoListNewControlRiego.getControlRiegoList().remove(controlRiegoListNewControlRiego);
                        oldIdUsuarioOfControlRiegoListNewControlRiego = em.merge(oldIdUsuarioOfControlRiegoListNewControlRiego);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ControlFertilizado> controlFertilizadoListOrphanCheck = usuario.getControlFertilizadoList();
            for (ControlFertilizado controlFertilizadoListOrphanCheckControlFertilizado : controlFertilizadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ControlFertilizado " + controlFertilizadoListOrphanCheckControlFertilizado + " in its controlFertilizadoList field has a non-nullable idUsuario field.");
            }
            List<ControlRiego> controlRiegoListOrphanCheck = usuario.getControlRiegoList();
            for (ControlRiego controlRiegoListOrphanCheckControlRiego : controlRiegoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ControlRiego " + controlRiegoListOrphanCheckControlRiego + " in its controlRiegoList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

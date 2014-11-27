/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Contacorrentes;
import model.Transacoes;

/**
 *
 * @author koonjshah
 */
public class TransacoesJpaController implements Serializable {

    public TransacoesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transacoes transacoes) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contacorrentes idOrigem = transacoes.getIdOrigem();
            if (idOrigem != null) {
                idOrigem = em.getReference(idOrigem.getClass(), idOrigem.getId());
                transacoes.setIdOrigem(idOrigem);
            }
            Contacorrentes idDestino = transacoes.getIdDestino();
            if (idDestino != null) {
                idDestino = em.getReference(idDestino.getClass(), idDestino.getId());
                transacoes.setIdDestino(idDestino);
            }
            em.persist(transacoes);
            if (idOrigem != null) {
                idOrigem.getTransacoesCollection().add(transacoes);
                idOrigem = em.merge(idOrigem);
            }
            if (idDestino != null) {
                idDestino.getTransacoesCollection().add(transacoes);
                idDestino = em.merge(idDestino);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transacoes transacoes) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transacoes persistentTransacoes = em.find(Transacoes.class, transacoes.getId());
            Contacorrentes idOrigemOld = persistentTransacoes.getIdOrigem();
            Contacorrentes idOrigemNew = transacoes.getIdOrigem();
            Contacorrentes idDestinoOld = persistentTransacoes.getIdDestino();
            Contacorrentes idDestinoNew = transacoes.getIdDestino();
            if (idOrigemNew != null) {
                idOrigemNew = em.getReference(idOrigemNew.getClass(), idOrigemNew.getId());
                transacoes.setIdOrigem(idOrigemNew);
            }
            if (idDestinoNew != null) {
                idDestinoNew = em.getReference(idDestinoNew.getClass(), idDestinoNew.getId());
                transacoes.setIdDestino(idDestinoNew);
            }
            transacoes = em.merge(transacoes);
            if (idOrigemOld != null && !idOrigemOld.equals(idOrigemNew)) {
                idOrigemOld.getTransacoesCollection().remove(transacoes);
                idOrigemOld = em.merge(idOrigemOld);
            }
            if (idOrigemNew != null && !idOrigemNew.equals(idOrigemOld)) {
                idOrigemNew.getTransacoesCollection().add(transacoes);
                idOrigemNew = em.merge(idOrigemNew);
            }
            if (idDestinoOld != null && !idDestinoOld.equals(idDestinoNew)) {
                idDestinoOld.getTransacoesCollection().remove(transacoes);
                idDestinoOld = em.merge(idDestinoOld);
            }
            if (idDestinoNew != null && !idDestinoNew.equals(idDestinoOld)) {
                idDestinoNew.getTransacoesCollection().add(transacoes);
                idDestinoNew = em.merge(idDestinoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transacoes.getId();
                if (findTransacoes(id) == null) {
                    throw new NonexistentEntityException("The transacoes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transacoes transacoes;
            try {
                transacoes = em.getReference(Transacoes.class, id);
                transacoes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transacoes with id " + id + " no longer exists.", enfe);
            }
            Contacorrentes idOrigem = transacoes.getIdOrigem();
            if (idOrigem != null) {
                idOrigem.getTransacoesCollection().remove(transacoes);
                idOrigem = em.merge(idOrigem);
            }
            Contacorrentes idDestino = transacoes.getIdDestino();
            if (idDestino != null) {
                idDestino.getTransacoesCollection().remove(transacoes);
                idDestino = em.merge(idDestino);
            }
            em.remove(transacoes);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transacoes> findTransacoesEntities() {
        return findTransacoesEntities(true, -1, -1);
    }

    public List<Transacoes> findTransacoesEntities(int maxResults, int firstResult) {
        return findTransacoesEntities(false, maxResults, firstResult);
    }

    private List<Transacoes> findTransacoesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transacoes.class));
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

    public Transacoes findTransacoes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transacoes.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransacoesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transacoes> rt = cq.from(Transacoes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

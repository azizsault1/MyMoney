/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Clientes;
import model.Agencias;
import model.Transacoes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Contacorrentes;

/**
 *
 * @author koonjshah
 */
public class ContacorrentesJpaController implements Serializable {

    public ContacorrentesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contacorrentes contacorrentes) throws RollbackFailureException, Exception {
        if (contacorrentes.getTransacoesCollection() == null) {
            contacorrentes.setTransacoesCollection(new ArrayList<Transacoes>());
        }
        if (contacorrentes.getTransacoesCollection1() == null) {
            contacorrentes.setTransacoesCollection1(new ArrayList<Transacoes>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Clientes cliente = contacorrentes.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                contacorrentes.setCliente(cliente);
            }
            Agencias agencia = contacorrentes.getAgencia();
            if (agencia != null) {
                agencia = em.getReference(agencia.getClass(), agencia.getId());
                contacorrentes.setAgencia(agencia);
            }
            Collection<Transacoes> attachedTransacoesCollection = new ArrayList<Transacoes>();
            for (Transacoes transacoesCollectionTransacoesToAttach : contacorrentes.getTransacoesCollection()) {
                transacoesCollectionTransacoesToAttach = em.getReference(transacoesCollectionTransacoesToAttach.getClass(), transacoesCollectionTransacoesToAttach.getId());
                attachedTransacoesCollection.add(transacoesCollectionTransacoesToAttach);
            }
            contacorrentes.setTransacoesCollection(attachedTransacoesCollection);
            Collection<Transacoes> attachedTransacoesCollection1 = new ArrayList<Transacoes>();
            for (Transacoes transacoesCollection1TransacoesToAttach : contacorrentes.getTransacoesCollection1()) {
                transacoesCollection1TransacoesToAttach = em.getReference(transacoesCollection1TransacoesToAttach.getClass(), transacoesCollection1TransacoesToAttach.getId());
                attachedTransacoesCollection1.add(transacoesCollection1TransacoesToAttach);
            }
            contacorrentes.setTransacoesCollection1(attachedTransacoesCollection1);
            em.persist(contacorrentes);
            if (cliente != null) {
                cliente = em.merge(cliente);
            }
            for (Transacoes transacoesCollectionTransacoes : contacorrentes.getTransacoesCollection()) {
                Contacorrentes oldIdOrigemOfTransacoesCollectionTransacoes = transacoesCollectionTransacoes.getIdOrigem();
                transacoesCollectionTransacoes.setIdOrigem(contacorrentes);
                transacoesCollectionTransacoes = em.merge(transacoesCollectionTransacoes);
                if (oldIdOrigemOfTransacoesCollectionTransacoes != null) {
                    oldIdOrigemOfTransacoesCollectionTransacoes.getTransacoesCollection().remove(transacoesCollectionTransacoes);
                    oldIdOrigemOfTransacoesCollectionTransacoes = em.merge(oldIdOrigemOfTransacoesCollectionTransacoes);
                }
            }
            for (Transacoes transacoesCollection1Transacoes : contacorrentes.getTransacoesCollection1()) {
                Contacorrentes oldIdDestinoOfTransacoesCollection1Transacoes = transacoesCollection1Transacoes.getIdDestino();
                transacoesCollection1Transacoes.setIdDestino(contacorrentes);
                transacoesCollection1Transacoes = em.merge(transacoesCollection1Transacoes);
                if (oldIdDestinoOfTransacoesCollection1Transacoes != null) {
                    oldIdDestinoOfTransacoesCollection1Transacoes.getTransacoesCollection1().remove(transacoesCollection1Transacoes);
                    oldIdDestinoOfTransacoesCollection1Transacoes = em.merge(oldIdDestinoOfTransacoesCollection1Transacoes);
                }
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

    public void edit(Contacorrentes contacorrentes) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contacorrentes persistentContacorrentes = em.find(Contacorrentes.class, contacorrentes.getId());
            Clientes clienteOld = persistentContacorrentes.getCliente();
            Clientes clienteNew = contacorrentes.getCliente();
            Agencias agenciaOld = persistentContacorrentes.getAgencia();
            Agencias agenciaNew = contacorrentes.getAgencia();
            Collection<Transacoes> transacoesCollectionOld = persistentContacorrentes.getTransacoesCollection();
            Collection<Transacoes> transacoesCollectionNew = contacorrentes.getTransacoesCollection();
            Collection<Transacoes> transacoesCollection1Old = persistentContacorrentes.getTransacoesCollection1();
            Collection<Transacoes> transacoesCollection1New = contacorrentes.getTransacoesCollection1();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                contacorrentes.setCliente(clienteNew);
            }
            if (agenciaNew != null) {
                agenciaNew = em.getReference(agenciaNew.getClass(), agenciaNew.getId());
                contacorrentes.setAgencia(agenciaNew);
            }
            Collection<Transacoes> attachedTransacoesCollectionNew = new ArrayList<Transacoes>();
            for (Transacoes transacoesCollectionNewTransacoesToAttach : transacoesCollectionNew) {
                transacoesCollectionNewTransacoesToAttach = em.getReference(transacoesCollectionNewTransacoesToAttach.getClass(), transacoesCollectionNewTransacoesToAttach.getId());
                attachedTransacoesCollectionNew.add(transacoesCollectionNewTransacoesToAttach);
            }
            transacoesCollectionNew = attachedTransacoesCollectionNew;
            contacorrentes.setTransacoesCollection(transacoesCollectionNew);
            Collection<Transacoes> attachedTransacoesCollection1New = new ArrayList<Transacoes>();
            for (Transacoes transacoesCollection1NewTransacoesToAttach : transacoesCollection1New) {
                transacoesCollection1NewTransacoesToAttach = em.getReference(transacoesCollection1NewTransacoesToAttach.getClass(), transacoesCollection1NewTransacoesToAttach.getId());
                attachedTransacoesCollection1New.add(transacoesCollection1NewTransacoesToAttach);
            }
            transacoesCollection1New = attachedTransacoesCollection1New;
            contacorrentes.setTransacoesCollection1(transacoesCollection1New);
            contacorrentes = em.merge(contacorrentes);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew = em.merge(clienteNew);
            }
            for (Transacoes transacoesCollectionOldTransacoes : transacoesCollectionOld) {
                if (!transacoesCollectionNew.contains(transacoesCollectionOldTransacoes)) {
                    transacoesCollectionOldTransacoes.setIdOrigem(null);
                    transacoesCollectionOldTransacoes = em.merge(transacoesCollectionOldTransacoes);
                }
            }
            for (Transacoes transacoesCollectionNewTransacoes : transacoesCollectionNew) {
                if (!transacoesCollectionOld.contains(transacoesCollectionNewTransacoes)) {
                    Contacorrentes oldIdOrigemOfTransacoesCollectionNewTransacoes = transacoesCollectionNewTransacoes.getIdOrigem();
                    transacoesCollectionNewTransacoes.setIdOrigem(contacorrentes);
                    transacoesCollectionNewTransacoes = em.merge(transacoesCollectionNewTransacoes);
                    if (oldIdOrigemOfTransacoesCollectionNewTransacoes != null && !oldIdOrigemOfTransacoesCollectionNewTransacoes.equals(contacorrentes)) {
                        oldIdOrigemOfTransacoesCollectionNewTransacoes.getTransacoesCollection().remove(transacoesCollectionNewTransacoes);
                        oldIdOrigemOfTransacoesCollectionNewTransacoes = em.merge(oldIdOrigemOfTransacoesCollectionNewTransacoes);
                    }
                }
            }
            for (Transacoes transacoesCollection1OldTransacoes : transacoesCollection1Old) {
                if (!transacoesCollection1New.contains(transacoesCollection1OldTransacoes)) {
                    transacoesCollection1OldTransacoes.setIdDestino(null);
                    transacoesCollection1OldTransacoes = em.merge(transacoesCollection1OldTransacoes);
                }
            }
            for (Transacoes transacoesCollection1NewTransacoes : transacoesCollection1New) {
                if (!transacoesCollection1Old.contains(transacoesCollection1NewTransacoes)) {
                    Contacorrentes oldIdDestinoOfTransacoesCollection1NewTransacoes = transacoesCollection1NewTransacoes.getIdDestino();
                    transacoesCollection1NewTransacoes.setIdDestino(contacorrentes);
                    transacoesCollection1NewTransacoes = em.merge(transacoesCollection1NewTransacoes);
                    if (oldIdDestinoOfTransacoesCollection1NewTransacoes != null && !oldIdDestinoOfTransacoesCollection1NewTransacoes.equals(contacorrentes)) {
                        oldIdDestinoOfTransacoesCollection1NewTransacoes.getTransacoesCollection1().remove(transacoesCollection1NewTransacoes);
                        oldIdDestinoOfTransacoesCollection1NewTransacoes = em.merge(oldIdDestinoOfTransacoesCollection1NewTransacoes);
                    }
                }
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
                Integer id = contacorrentes.getId();
                if (findContacorrentes(id) == null) {
                    throw new NonexistentEntityException("The contacorrentes with id " + id + " no longer exists.");
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
            Contacorrentes contacorrentes;
            try {
                contacorrentes = em.getReference(Contacorrentes.class, id);
                contacorrentes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contacorrentes with id " + id + " no longer exists.", enfe);
            }
            Clientes cliente = contacorrentes.getCliente();
            if (cliente != null) {
                cliente = em.merge(cliente);
            }
            Collection<Transacoes> transacoesCollection = contacorrentes.getTransacoesCollection();
            for (Transacoes transacoesCollectionTransacoes : transacoesCollection) {
                transacoesCollectionTransacoes.setIdOrigem(null);
                transacoesCollectionTransacoes = em.merge(transacoesCollectionTransacoes);
            }
            Collection<Transacoes> transacoesCollection1 = contacorrentes.getTransacoesCollection1();
            for (Transacoes transacoesCollection1Transacoes : transacoesCollection1) {
                transacoesCollection1Transacoes.setIdDestino(null);
                transacoesCollection1Transacoes = em.merge(transacoesCollection1Transacoes);
            }
            em.remove(contacorrentes);
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

    public List<Contacorrentes> findContacorrentesEntities() {
        return findContacorrentesEntities(true, -1, -1);
    }

    public List<Contacorrentes> findContacorrentesEntities(int maxResults, int firstResult) {
        return findContacorrentesEntities(false, maxResults, firstResult);
    }

    private List<Contacorrentes> findContacorrentesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contacorrentes.class));
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

    public Contacorrentes findContacorrentes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contacorrentes.class, id);
        } finally {
            em.close();
        }
    }

    public int getContacorrentesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contacorrentes> rt = cq.from(Contacorrentes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

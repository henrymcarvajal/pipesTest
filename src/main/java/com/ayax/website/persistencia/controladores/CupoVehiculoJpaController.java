/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.persistencia.controladores;

import com.ayax.website.persistencia.controladores.exceptions.NonexistentEntityException;
import com.ayax.website.persistencia.controladores.exceptions.PreexistingEntityException;
import com.ayax.website.persistencia.entidades.CupoVehiculo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author hmcarvajal@ayax.co
 */
public class CupoVehiculoJpaController implements Serializable {

    public CupoVehiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CupoVehiculo cupoVehiculo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cupoVehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCupoVehiculo(cupoVehiculo.getId()) != null) {
                throw new PreexistingEntityException("CupoVehiculo " + cupoVehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CupoVehiculo cupoVehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cupoVehiculo = em.merge(cupoVehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cupoVehiculo.getId();
                if (findCupoVehiculo(id) == null) {
                    throw new NonexistentEntityException("The cupoVehiculo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CupoVehiculo cupoVehiculo;
            try {
                cupoVehiculo = em.getReference(CupoVehiculo.class, id);
                cupoVehiculo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cupoVehiculo with id " + id + " no longer exists.", enfe);
            }
            em.remove(cupoVehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CupoVehiculo> findCupoVehiculoEntities() {
        return findCupoVehiculoEntities(true, -1, -1);
    }

    public List<CupoVehiculo> findCupoVehiculoEntities(int maxResults, int firstResult) {
        return findCupoVehiculoEntities(false, maxResults, firstResult);
    }

    private List<CupoVehiculo> findCupoVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CupoVehiculo.class));
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

    public CupoVehiculo findCupoVehiculo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CupoVehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCupoVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CupoVehiculo> rt = cq.from(CupoVehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

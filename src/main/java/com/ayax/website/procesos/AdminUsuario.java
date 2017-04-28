/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayax.website.procesos;

import com.ayax.website.persistencia.EntityManagerFactoryBuilder;
import com.ayax.website.persistencia.entidades.Servicio;
import com.ayax.website.persistencia.entidades.Usuario;
import com.ayax.website.persistencia.fachadas.UsuarioJpaController;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mauris
 */
public class AdminUsuario {

    public AdminUsuario() {
    }

    public Usuario obtenerUsuario(String id) {
        UsuarioJpaController sc = new UsuarioJpaController(EntityManagerFactoryBuilder.INSTANCE.build());
        EntityManager em = sc.getEntityManager();
        TypedQuery q = em.createNamedQuery("Usuario.findById", Servicio.class);
        q.setParameter("id", id);

        Usuario usuario = null;
        try {
            usuario = (Usuario) q.getSingleResult();
        } catch (NoResultException ex) {
        }
        return usuario;
    }
}

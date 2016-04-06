package br.com.ladobsoftware.arquillian.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.ladobsoftware.arquillian.model.User;

@Stateless 
public class UserDao {

	@PersistenceContext(unitName = "test")
	EntityManager em;
 
	public void save(User user) {
		em.persist(user);
	}
	
	public void update(User user) {
		em.merge(user);
	}

	public User find(int id) {
		return em.find(User.class, id);
	}

	public List<User> findAll() {
		return em.createQuery("SELECT p FROM User p ORDER BY p.id", User.class).getResultList();
	}
}

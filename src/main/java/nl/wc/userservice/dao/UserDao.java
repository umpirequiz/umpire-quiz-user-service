package nl.wc.userservice.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import nl.wc.userservice.model.User;

import java.util.List;

@ApplicationScoped
public class UserDao {
    @PersistenceContext
    private EntityManager em;

    UserDao() {
    }

    @Transactional
    public User saveUser(User u) {
        return em.merge(u);
    }

    @Transactional
    public void deleteUser(int id) {
        em.remove(findById(id));
    }

    public User findById(int id) {
        return em.createQuery("select u from User u where u.id= :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public boolean userExists(String username) {
        return !em.createQuery("select u from User u where u.username =: u", User.class)
                .setParameter("u", username)
                .getResultList()
                .isEmpty();
    }

    public List<User> findByName(String input) {
        return em.createQuery("select u from User u where u.username like  :s", User.class)
                .setParameter("s", "%" + input + "%")
                .getResultList();
    }

    public User findByUsernameAndPassword(String username, String password) {
        return em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }

    public List<User> getAllUsers() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}

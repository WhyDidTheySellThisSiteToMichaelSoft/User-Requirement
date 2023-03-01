package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Investidor;

@Stateful
public class InvestidorDAO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private EntityManager em;

  public Boolean isUnique(String email) {
    // Cria objeto de consulta
    CriteriaBuilder cb = em.getCriteriaBuilder();
    // Retorno é da classe Investidor
    CriteriaQuery<Investidor> criteria = cb.createQuery(Investidor.class);
    // From Investidor
    Root<Investidor> investidor = criteria.from(Investidor.class);
    // Select *
    criteria.select(investidor);
    // where investidor.email = email
    criteria.where(cb.like(investidor.get("email"), email));
    if (em.createQuery(criteria).getResultList().isEmpty())
      return true;
    return false;
  }
  
  public Boolean isUniqueHQL(String email) {

    String hql = "SELECT a FROM investidor WHERE email = :email";
    Query query = em.createQuery(hql);
    query.setParameter("email", email);
    return query.getResultList().isEmpty();

  }

  public void save(Investidor investidor) {
    em.persist(investidor);
  }

  public Investidor find(Integer id) {
    return em.find(Investidor.class, id);
  }

  // returns null if it is not found
  public Investidor find(String email) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Investidor> criteria = cb.createQuery(Investidor.class);
    Root<Investidor> investidor = criteria.from(Investidor.class);
    criteria.select(investidor);
    criteria.where(cb.like(investidor.get("email"), email));
    return em.createQuery(criteria).getSingleResult();
    
    /*
     * List<Investidor> listInvestidores = em.createQuery("from Investidor",
     * Investidor.class).getResultList();
     * for (Investidor i : listInvestidores)
     * if (i.getEmail() == email)
     * return i;
     * return null;
     */
	  
    /*
     * return em.createQuery("from Investidor where email like :email",
     * Investidor.class).setParameter("email", email).getSingleResult();
     */
  }

 
  // returns a list with all rows
  public List<Investidor> list() {
    return em.createQuery("from Investidor", Investidor.class).getResultList();
  }

  public void update(Investidor investidor) {
    em.merge(investidor);
  }

  public void delete(Investidor investidor) {
    em.remove(em.getReference(Investidor.class, investidor.getId()));
  }

}

package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Investimento;

@Stateful
public class InvestimentoDAO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private EntityManager em;

  // Use CNPJ String to know whether it has already been registered or not
  public Boolean isUnique(String cnpj) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Investimento> criteria = cb.createQuery(Investimento.class);
    Root<Investimento> investimento = criteria.from(Investimento.class);
    criteria.select(investimento);
    criteria.where(cb.like(investimento.get("cnpj"), cnpj));
    if (em.createQuery(criteria).getResultList().isEmpty())
      return true;
    return false;
  }

  // Use CNPJ String to know whether it has already been registered or not
  public Boolean isUniqueHQL(String cnpj) {
    // String hql = "SELECT i.id FROM Investimento i WHERE i.email = ";
    // Query query = em.createQuery(hql);
    // query.setParameter("id", email);
    // return query.getResultList().isEmpty();

    return em.createQuery("from Investimento where cnpj like :cnpj",
        Investimento.class).setParameter("cnpj", cnpj).getResultList().isEmpty();
  }

  // CRUD

  public void save(Investimento investimento) {
    em.persist(investimento);
  }

  public Investimento find(Integer id) {
    return em.find(Investimento.class, id);
  }

  public Investimento find(String cnpj) {
    return em.createQuery("from Investimento where cnpj like :cnpj",
        Investimento.class).setParameter("cnpj", cnpj).getSingleResult();
  }

  // returns a list with all rows
  public List<Investimento> list() {
    return em.createQuery("From Investimento", Investimento.class).getResultList();
  }

  public void update(Investimento investimento) {
    em.merge(investimento);
  }

  public void delete(Investimento investimento) {
    em.remove(em.getReference(Investimento.class, investimento.getId()));
  }

}

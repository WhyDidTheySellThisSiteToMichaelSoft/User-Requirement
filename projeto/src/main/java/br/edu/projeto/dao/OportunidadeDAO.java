package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Oportunidade;

@Stateful
public class OportunidadeDAO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private EntityManager em;

  public Boolean isUnique(String id) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Oportunidade> criteria = cb.createQuery(Oportunidade.class);
    Root<Oportunidade> oportunidade = criteria.from(Oportunidade.class);
    criteria.select(oportunidade).where(cb.like(oportunidade.get("id"), id));
    if (em.createQuery(criteria).getResultList().isEmpty())
      return true;
    return false;
  }

  public Boolean isUniqueHQL(int id) {
    // String hql = "SELECT i.id FROM Oportunidade i WHERE i.id = ";
    // Query query = em.createQuery(hql);
    // query.setParameter("id", id);
    // return query.getResultList().isEmpty();
    return em.createQuery("from Oportunidade where id = :id",
        Oportunidade.class).setParameter("id", id).getResultList().isEmpty();
  }

  // CRUD

  public void save(Oportunidade oportunidade) {
    em.persist(oportunidade);
  }

  public Oportunidade find(Integer id) {
    return em.find(Oportunidade.class, id);
  }

  public List<Oportunidade> find(String nome) {
    return em.createQuery("from Oportunidade where nome like :nome",
        Oportunidade.class).setParameter("nome", nome).getResultList();
  }

  // returns a list with all rows
  public List<Oportunidade> list() {
    return em.createQuery("from Oportunidade", Oportunidade.class).getResultList();
  }

  public void update(Oportunidade oportunidade) {
    em.merge(oportunidade);
  }

  public void delete(Oportunidade oportunidade) {
    em.remove(em.getReference(Oportunidade.class, oportunidade.getId()));
  }

}

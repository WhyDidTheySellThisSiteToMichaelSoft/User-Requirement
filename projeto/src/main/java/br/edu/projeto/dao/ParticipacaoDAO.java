package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.edu.projeto.model.Participacao;
import br.edu.projeto.model.ParticipacaoId;

@Stateful
public class ParticipacaoDAO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private EntityManager em;

  public Boolean isUnique(Integer id_investidor, Integer id_investimento) {
    return em
        .createQuery("from Participacao where id_investidor = :id_investidor and id_investimento = :id_investimento",
            Participacao.class)
        .setParameter("id_investidor", id_investidor).setParameter("id_investimento", id_investimento)
        .getResultList().isEmpty();
  }

  public void save(Participacao participacao) {
    em.persist(participacao);
  }

  public Participacao find(ParticipacaoId id) {
    return em.find(Participacao.class, id);
  }

  // returns a list with all rows
  public List<Participacao> list() {
    return em.createQuery("from Participacao", Participacao.class).getResultList();
  }

  public void update(Participacao participacao) {
    em.merge(participacao);
  }

  public void delete(Participacao participacao) {
    em.remove(em.getReference(Participacao.class, participacao.getId()));
  }

}

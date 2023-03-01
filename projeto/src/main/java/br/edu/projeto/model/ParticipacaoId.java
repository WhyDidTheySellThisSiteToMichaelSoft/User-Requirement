package br.edu.projeto.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ParticipacaoId implements Serializable {

  private static final long serialVersionUID = 1L;
  @Column(name = "id_investidor")
  private Integer id_investidor;
  @Column(name = "id")
  private Integer id_investimento;

  /*
   * 
   * Transient???
   * 
   * public Integer getIdInvestidor() {
   * return id_investidor;
   * }
   * 
   * public Integer getIdInvestimento() {
   * return id_investimento;
   * }
   * 
   * public void setidinvestidor(int id_investidor) {
   * this.id_investidor = id_investidor;
   * }
   * 
   * public void setidinvestimento(int id_investimento) {
   * this.id_investimento = id_investimento;
   * }
   */

  @Override
  public int hashCode() {
    return Objects.hash(id_investidor, id_investimento);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ParticipacaoId other = (ParticipacaoId) obj;
    return id_investidor == other.id_investidor && id_investimento == other.id_investimento;
  }

}

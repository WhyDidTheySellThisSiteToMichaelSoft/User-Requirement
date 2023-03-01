package br.edu.projeto.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

// IN CASE THE ACCESS DOES NOT WORK, FAZER UM MAPEAMENTO DIRETO NA DB E ADICIONAR/RETIRAR MANUALMENTE NOS DAOS/CONTROLLERS //

/*
@AssociationOverrides({
    @AssociationOverride(name = "id_investidor.investidor", joinColumns = @JoinColumn(name = "id_investidor")),
    @AssociationOverride(name = "primaryKey.investimento", joinColumns = @JoinColumn(name = "id")) })
*/
@Entity
@Table(name = "participacao")
public class Participacao {

  @EmbeddedId
  private ParticipacaoId participacaoID;

  @ManyToOne
  @MapsId("id_investidor")
  @JoinColumn(name = "id_investidor")
  private Investidor investidor;
  @ManyToOne
  @MapsId("id_investimento")
  @JoinColumn(name = "id")
  private Investimento investimento;

  @Column(name = "vinit")
  //@NotEmpty(message = "valor não pode estar vazio")
  private long vpinit;

  @Column(name = "vpago")
  //@NotEmpty(message = "valor não pode estar vazio")
  private long vpago;

  @Column(name = "qnt_participacao")
  //@NotEmpty(message = "valor não pode estar vazio")
  private short qnt_participacao;

  public Participacao() {
  }

  public Participacao(Investidor investidor, Investimento investimento, long vinit, long vpago,
      short qnt_participacao) {
    this.investidor = investidor;
    this.investimento = investimento;
    this.vpinit = vpago;
    this.qnt_participacao = qnt_participacao;
  }

  public ParticipacaoId getId() {
    return participacaoID;
  }

  public void setId(ParticipacaoId id) {
    this.participacaoID = id;
  }

  // @Transient
  public Investidor getInvestidor() {
    return this.investidor;
  }

  // @Transient
  public Investimento getInvestimento() {
    return this.investimento;
  }

  public long getVpinit() {
    return vpinit;
  }

  public void setVpinit(long vpinit) {
    this.vpinit = vpinit;
  }

  public long getVpago() {
    return vpago;
  }

  public void setVpago(long vpago) {
    this.vpago = vpago;
  }

  public short getQntParticipacao() {
    return qnt_participacao;
  }

  public void setQntParticipacao(short qnt_participacao) {
    this.qnt_participacao = qnt_participacao;
  }

}

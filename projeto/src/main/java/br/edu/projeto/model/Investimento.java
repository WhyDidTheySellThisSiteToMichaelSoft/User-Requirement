package br.edu.projeto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "investimento")
public class Investimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotEmpty(message = "Nomes não podem estar vazios")
  @Size(min = 1, max = 30, message = "Nomes devem ter no máximo 30 caracteres")
  private String nome;

  @NotEmpty(message = "A descrição não pode ficar vazia.")
  @Size(min = 1, max = 200, message = "A descrição dete ter no máximo 200 caracteres")
  private String descr;

  @CNPJ
  @Column(unique = true)
  private String cnpj;
  private int vinit;
  private int vatual;

  @NotEmpty(message = "Nomes não podem estar vazios")
  private String cidade;

  @NotEmpty(message = "O logradouro não pode estar vazio")
  private String logradouro;

  @NotEmpty(message = "Nomes não podem estar vazios")
  private String nrua;

  @OneToMany(mappedBy = "investimento", cascade = CascadeType.ALL)
  private List<Participacao> participacoes = new ArrayList<Participacao>();

  public Investimento() {
  }

  public Investimento(String nome, String descr, String cnpj, int vinit, int vatual,
      String cidade, String logradouro, String nrua) {

    this.nome = nome;
    this.descr = descr;
    this.cnpj = cnpj;
    this.vinit = vinit;
    this.vatual = vatual;
    this.cidade = cidade;
    this.logradouro = logradouro;
    this.nrua = nrua;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescr() {
    return descr;
  }

  public void setDescr(String descr) {
    this.descr = descr;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public int getVinit() {
    return vinit;
  }

  public void setVinit(int vinit) {
    this.vinit = vinit;
  }

  public int getVatual() {
    return vatual;
  }

  public void setVatual(int vatual) {
    this.vatual = vatual;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public List<Participacao> getParticipacoes() {
    return participacoes;
  }

  public void setParticipacoes(List<Participacao> participacoes) {
    this.participacoes = participacoes;
  }

  public void addParticipacao(Participacao participacao) {
    this.participacoes.add(participacao);
  }

  // @ManyToMany(mappedBy = "investidores", fetch = FetchType.EAGER)
  // private List<Investidor> investidors = new ArrayList<Investidor>();

  // public List<Investidor> getInvestidores() {
  // return investidors;
  // }

  // public void setInvestidor(List<Investidor> investidors) {
  // this.investidors = investidors;
  // }

  // public void addInvestidor(Investidor investidor) {
  // this.investidors.add(investidor);
  // investidor.getInvestidores().add(this);
  // }

  // public void removeInvestidor(Investidor investidor) {
  // this.getInvestidores().remove(investidor);
  // }

  @Override
  public int hashCode() {
    return Objects.hash(cidade, cnpj, descr, id, logradouro, nome, nrua, participacoes, vatual, vinit);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Investimento other = (Investimento) obj;
    return Objects.equals(cidade, other.cidade) && Objects.equals(cnpj, other.cnpj)
        && Objects.equals(descr, other.descr) && Objects.equals(id, other.id)
        && Objects.equals(logradouro, other.logradouro) && Objects.equals(nome, other.nome)
        && Objects.equals(nrua, other.nrua) && Objects.equals(participacoes, other.participacoes)
        && Objects.equals(vatual, other.vatual) && Objects.equals(vinit, other.vinit);
  }

}

package br.edu.projeto.model;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "investidor")
public class Investidor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_investidor")
  private Integer id;

  @NotEmpty
  @Size(min = 1, max = 30, message = "Nomes devem ter no máximo 30 caracteres.")
  private String nome;

  @NotEmpty
  private String senha;

  @NotEmpty
  @Column(unique = true)
  @Email(message = "Não é um endereço de E-mail válido")
  private String email;

  @NotNull
  @Column(name = "nivacesso")
  private short nivacesso;

  @NotNull
  //@Pattern(regexp = "[0-9()-]*$/", message = "Telefones devem conter apenas números, traços e parenteses")
  private String telefone;

  @OneToMany(mappedBy = "investidor", cascade = CascadeType.ALL)
  private List<Participacao> participacoes = new ArrayList<Participacao>();

  // DOMINANTE
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "interesse", joinColumns = @JoinColumn(name = "id_investidor"), inverseJoinColumns = @JoinColumn(name = "id_oportunidade"))

  // SUBMISSO
  // @ManyToMany(mappedBy = "investidores", fetch = FetchType.EAGER)
  private List<Oportunidade> oportunidades = new ArrayList<Oportunidade>();


  public Investidor() {
  }

  public Investidor(String nome, String email, String senha, short nivacesso, String telefone) {

    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.nivacesso = nivacesso;
    this.telefone = telefone;
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

  public String getEmail() {
	    return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }
  
  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public short getNivAcesso() {
    return nivacesso;
  }

  public void setNivAcesso(short nivacesso) {
    this.nivacesso = nivacesso;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public List<Participacao> getParticipacoes() {
    return participacoes;
  }

  public void setParticicoes(List<Participacao> participacoes) {
    this.participacoes = participacoes;
  }

  public void addParticipacao(Participacao participacao) {
    this.participacoes.add(participacao);
  }

  public List<Oportunidade> getOportunidades() {
    return oportunidades;
  }

  public void setOportunidade(List<Oportunidade> oportunidades) {
    this.oportunidades = oportunidades;
  }

  public void addOportunidade(Oportunidade oportunidade) {
    this.oportunidades.add(oportunidade);
    // PARA O SUBMISSO
    // oportunidade.getInvestidores().add(this);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, id, nivacesso, nome, oportunidades, participacoes, senha, telefone);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Investidor other = (Investidor) obj;
    return Objects.equals(email, other.email) && Objects.equals(id, other.id)
        && Objects.equals(nivacesso, other.nivacesso) && Objects.equals(nome, other.nome)
        && Objects.equals(oportunidades, other.oportunidades) && Objects.equals(participacoes, other.participacoes)
        && Objects.equals(senha, other.senha) && Objects.equals(telefone, other.telefone);
  }

}


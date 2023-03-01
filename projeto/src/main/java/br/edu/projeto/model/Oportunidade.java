package br.edu.projeto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "oportunidade")
public class Oportunidade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotEmpty(message = "Nomes não podem estar vazios")
  @Size(min = 1, max = 30, message = "Nomes devem ter no máximo 30 caracteres")
  @Pattern(regexp = "[a-zA-Z0-9\s]*$/;", message = "Nomes devem conter apenas letras e espaços")
  private String nome;

  @NotEmpty(message = "A descrição não pode ficar vazia.")
  @Size(min = 1, max = 200, message = "A descrição deve ter no máximo 200 caracteres")
  private String descr;

  // DOMINANTE
  // @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  // @JoinTable(name = "interesse", joinColumns = @JoinColumn(name = "id"),
  // inverseJoinColumns = @JoinColumn(name = "id_investidor"))

  // SUBMISSO
  @ManyToMany(mappedBy = "oportunidades", fetch = FetchType.EAGER)
  private List<Investidor> investidores = new ArrayList<Investidor>();

  public Oportunidade() {
  }

  public Oportunidade(String nome, String descr) {

    this.nome = nome;
    this.descr = descr;
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

  public List<Investidor> getInvestidores() {
    return investidores;
  }

  public void addInvestidor(Investidor investidor) {
    this.investidores.add(investidor);
    // PARA O SUBMISSO
    investidor.getOportunidades().add(this);
  }

  @Override
	public int hashCode() {
		return Objects.hash(descr, id, investidores, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oportunidade other = (Oportunidade) obj;
		return Objects.equals(descr, other.descr) && Objects.equals(id, other.id)
				&& Objects.equals(investidores, other.investidores) && Objects.equals(nome, other.nome);
	}
}

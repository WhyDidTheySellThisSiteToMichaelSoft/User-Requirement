
package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import br.edu.projeto.dao.InvestidorDAO;
import br.edu.projeto.model.Investidor;

// Memory is Dropped on URL change/refresh
@ViewScoped
@Named
public class InvestidorController implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private FacesContext facesContext;

  @Inject
  transient private Pbkdf2PasswordHash passwordHash;

  @Inject
  private InvestidorDAO investidorDAO;

  private Investidor investidor;

  private List<Investidor> listaInvestidores;

  // Mandatory Always First Called Method (aka initiator)
  @PostConstruct
  public void init() {

    VerifyAccess();
    this.investidor = new Investidor();
    this.listaInvestidores = investidorDAO.list();
  }

  // OK
  // Create e Update
  public void save() {
    if (investidorValido()) {
      try {
        String m;
        this.investidor.setSenha(this.passwordHash.generate(this.investidor.getSenha().toCharArray()));
        if (this.investidor.getId() == null) {
          this.investidorDAO.save(this.investidor);
          if (this.investidor.getNivAcesso() == 1)
            m = "Novo admin cadastrado";
          else
            m = "Novo investidor cadastrado";
        } else {
          this.investidorDAO.update(this.investidor);
          if (this.investidor.getNivAcesso() == 1)
            m = "Admin atualizado";
          else
            m = "Investidor atualizado";
        }
        // typical DML backtrace messages
        this.facesContext.addMessage(null, new FacesMessage(m));
        Refresh();
      } catch (Exception e) {
        // typical Exception backtrace messages
        String errorMessage = getMensagemErro(e);
        this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
      }
    }
  }

  // Data Verification
  private boolean investidorValido() {
	//if (this.investidor.getId() == null) && !this.investidorDAO.isUnique(this.investidor.getEmail())) {
    if (this.investidor.getId() == null) {
      this.facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_WARN, "Este investidor já está cadastrado.", null));
      return false;
    }
    return true;
  }

  // Delete
  public void remove() {
    try {
      String m;
      this.investidorDAO.delete(this.investidor);
      if (this.investidor.getNivAcesso() == 1)
        m = "Admin removido";
      else
        m = "Investidor removido";
      Refresh();
      this.facesContext.addMessage(null, new FacesMessage(m));
    } catch (Exception e) {
      String errorMessage = getMensagemErro(e);
      this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
    }
  }

  // quando salvamos ou mudamos um elemento da tabela, queremos começar "fresco"
  // O metodo mais simples de conseguir isso é chamar o .list() novamente e
  // anulando/recriando o investidor
  // poderiamos buscar na propria lista e altera-la se eficiencia fosse o caso.
  private void Refresh() {
    // this.investidor = null;
    this.investidor = new Investidor();
    this.listaInvestidores = this.investidorDAO.list();
  }

  public void VerifyAccess() {
    try {
      Investidor pageRequester = this.investidorDAO.find(this.facesContext.getExternalContext().getRemoteUser());
      if (pageRequester == null) {
        this.facesContext.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha em acessar usuário remoto (agente desconhecido).",
                null));
        facesContext.getExternalContext().redirect("login.xhtml");
      } else if (pageRequester.getNivAcesso() != 1) {
        this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Tentativa de acesso por usuário sem privilégios rejeitada", null));
        facesContext.getExternalContext().redirect("login.xhtml");
      }
    } catch (Exception e) {
      String errorMessage = getMensagemErro(e);
      this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
      try {
        facesContext.getExternalContext().redirect("login.xhtml");
        // A este ponto eu não sei
      } catch (Exception ex) {
      }
    }
  }

  // Captura mensagem de erro das validações do Hibernate
  private String getMensagemErro(Exception e) {
    String erro = "Falha no sistema!. Contacte o administrador do sistema.";
    if (e == null)
      return erro;
    Throwable t = e;
    while (t != null) {
      erro = t.getLocalizedMessage();
      t = t.getCause();
    }
    return erro;
  }

  // Read (List)
  public Investidor getInvestidor() {
    return investidor;
  }

  public void setInvestidor(Investidor investidor) {
    this.investidor = investidor;
  }

  // Read (List)
  public List<Investidor> getListaInvestidores() {
    return listaInvestidores;
  }

  public void setListaInvestidores(List<Investidor> listaInvestidores) {
    this.listaInvestidores = listaInvestidores;
  }
}

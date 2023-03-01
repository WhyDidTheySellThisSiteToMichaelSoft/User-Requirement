package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.projeto.dao.InvestimentoDAO;
import br.edu.projeto.dao.InvestidorDAO;
import br.edu.projeto.model.Investimento;
import br.edu.projeto.model.Investidor;

@ViewScoped
@Named
public class InvestimentoController implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private FacesContext facesContext;

  @Inject
  private InvestimentoDAO investimentoDAO;

  @Inject
  private InvestidorDAO investidorDAO;

  private Investimento investimento;

  private List<Investimento> listaInvestimentos;

  @PostConstruct
  public void init() {
    this.investimento = new Investimento();
    this.listaInvestimentos = investimentoDAO.list();
  }

  public void save() {
    if (investimentoValido()) {
      try {
        if (this.investimento.getId() == null) {
          this.investimentoDAO.save(this.investimento);
          this.facesContext.addMessage(null, new FacesMessage("Investimento cadastrado"));
        } else {
          this.investimentoDAO.update(this.investimento);
          this.facesContext.addMessage(null, new FacesMessage("Investimento atualizado"));
        }
        Refresh();
      } catch (Exception e) {
        String errorMessage = getMensagemErro(e);
        this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
      }
    }
  }

  private boolean investimentoValido() {
    if (this.investimento.getId() == null && !this.investimentoDAO.isUnique(this.investimento.getCnpj())) {
      this.facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_WARN, "Este investimento já está cadastrado.", null));
      return false;
    }
    return true;
  }

  public void remove() {
    try {
      this.investimentoDAO.delete(this.investimento);
      Refresh();
      this.facesContext.addMessage(null, new FacesMessage("Investimento removido"));
    } catch (Exception e) {
      String errorMessage = getMensagemErro(e);
      this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
    }
  }

  public void Refresh() {
    // this.investimento = null;
    this.investimento = new Investimento();
    this.listaInvestimentos = investimentoDAO.list();
  }

  public void VerifyAccess() {
    try {
      Investidor pageRequester = this.investidorDAO.find(this.facesContext.getExternalContext().getRemoteUser());
      if (pageRequester == null) {
        this.facesContext.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha em acessar usuário remoto (agente desconhecido).",
                null));
        facesContext.getExternalContext().redirect("login.html");
      } else if (pageRequester.getNivAcesso() != 1) {
        this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Tentativa de acesso por usuário sem privilégios rejeitada", null));
        facesContext.getExternalContext().redirect("login.html");
      }
    } catch (Exception e) {
      String errorMessage = getMensagemErro(e);
      this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
      try {
        facesContext.getExternalContext().redirect("login.html");
        // A este ponto eu não sei
      } catch (Exception ex) {
      }
    }
  }

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

  public Investimento getInvestimento() {
    return investimento;
  }

  public void setInvestimento(Investimento investimento) {
    this.investimento = investimento;
  }

  public List<Investimento> getListaInvestimentos() {
    return listaInvestimentos;
  }

  public void setListaInvestimentos(List<Investimento> listaInvestimentos) {
    this.listaInvestimentos = listaInvestimentos;
  }

}

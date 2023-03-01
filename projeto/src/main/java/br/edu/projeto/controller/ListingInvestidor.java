package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.projeto.dao.InvestidorDAO;
import br.edu.projeto.model.Investidor;

// Memory is Dropped on URL change/refresh
@ViewScoped
@Named
public class ListingInvestidor implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private FacesContext facesContext;

  @Inject
  private InvestidorDAO investidorDAO;

  private List<Investidor> listaInvestidores;

  // Mandatory Always First Called Method (aka initiator)
  @PostConstruct
  public void init() {
    VerifyAccess();
    this.listaInvestidores = investidorDAO.list();
  }

  /*
   * tenta acessar o email (nome de login) do usuário remoto e checar contra a
   * database.
   * Em caso de falha, redireciona para a tela de login
   */
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

  // Read (List)
  public List<Investidor> getListaInvestidores() {
    return listaInvestidores;
  }

  public void setListaInvestidores(List<Investidor> listaInvestidores) {
    this.listaInvestidores = listaInvestidores;
  }
}

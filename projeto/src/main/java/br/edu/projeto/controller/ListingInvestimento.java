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
import br.edu.projeto.model.Participacao;

@ViewScoped
@Named
public class ListingInvestimento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private FacesContext facesContext;

  @Inject
  private InvestimentoDAO investimentoDAO;

  @Inject
  private InvestidorDAO investidorDAO;

  private Investidor pageRequester;
  private List<Investimento> listaInvestimentos;

  @PostConstruct
  public void init() {
    VerifyAccess();
    if (this.pageRequester.getNivAcesso() == 1)
      this.listaInvestimentos = investimentoDAO.list();
    else {
      List<Participacao> participacoesDoInvestidor = this.pageRequester.getParticipacoes();
      for (Participacao p : participacoesDoInvestidor)
        this.listaInvestimentos.add(p.getInvestimento());
    }
  }

  public void VerifyAccess() {
    try {
      this.pageRequester = this.investidorDAO.find(this.facesContext.getExternalContext().getRemoteUser());
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
      this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getMensagemErro(e), null));
      try {
        facesContext.getExternalContext().redirect("login.html");
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

  public List<Investimento> getListaInvestimentos() {
    return this.listaInvestimentos;
  }

  public void setListaInvestimentos(List<Investimento> listaInvestimentos) {
    this.listaInvestimentos = listaInvestimentos;
  }
}

package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.projeto.dao.OportunidadeDAO;
import br.edu.projeto.model.Oportunidade;
import br.edu.projeto.dao.InvestidorDAO;
import br.edu.projeto.model.Investidor;

@ViewScoped
@Named
public class OportunidadeController implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private FacesContext facesContext;

  @Inject
  private OportunidadeDAO OportunidadeDAO;

  @Inject
  private InvestidorDAO investidorDAO;

  private Oportunidade oportunidade;
  private List<Oportunidade> listaOportunidades;

  @PostConstruct
  public void init() {
    // Inicializa elementos importantes
    this.oportunidade = new Oportunidade();
    this.listaOportunidades = OportunidadeDAO.list();
  }

  public void save() {
    if (OportunidadeValida()) {
      try {
        if (this.oportunidade.getId() == null) {
          this.OportunidadeDAO.save(this.oportunidade);
          this.facesContext.addMessage(null, new FacesMessage("Oportunidade Criado"));
        } else {
          this.OportunidadeDAO.update(this.oportunidade);
          this.facesContext.addMessage(null, new FacesMessage("Oportunidade Atualizado"));
        }
      } catch (Exception e) {
        String errorMessage = getMensagemErro(e);
        this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
      }
    }
  }

  private boolean OportunidadeValida() {
    if (this.oportunidade.getId() == null
        && !this.OportunidadeDAO.isUnique(this.oportunidade.getId().toString())) {
      this.facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_WARN, "Este oportunidade já existe.", null));
      return false;
    }
    return true;
  }

  public void delete() {
    try {
      this.OportunidadeDAO.delete(this.oportunidade);
      Refresh();
      this.facesContext.addMessage(null, new FacesMessage("Oportunidade Removido"));
    } catch (Exception e) {
      String errorMessage = getMensagemErro(e);
      this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
    }
  }

  public void Refresh() {
    // this.oportunidade = null;
    this.oportunidade = new Oportunidade();
    this.listaOportunidades = OportunidadeDAO.list();
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

  public Oportunidade getOportunidade() {
    return oportunidade;
  }

  public void setOportunidade(Oportunidade Oportunidade) {
    this.oportunidade = Oportunidade;
  }

  public List<Oportunidade> getListaOportunidades() {
    return listaOportunidades;
  }

  public void setListaOportunidades(List<Oportunidade> listaOportunidades) {
    this.listaOportunidades = listaOportunidades;
  }

}

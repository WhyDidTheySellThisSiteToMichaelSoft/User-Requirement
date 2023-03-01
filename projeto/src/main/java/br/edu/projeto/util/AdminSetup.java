package br.edu.projeto.util;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.edu.projeto.dao.InvestidorDAO;
import br.edu.projeto.model.Investidor;

@WebListener
public class AdminSetup implements ServletContextListener {

  @Inject
  private Pbkdf2PasswordHash passwordHash;

  @Inject
  private InvestidorDAO investidorDAO;

  private Investidor admin;

  public void contextInitialized(ServletContextEvent event) {
      if (investidorDAO.isUnique("admin@gmail.com")) {
      admin = new Investidor();
      //admin.setId(0);
      admin.setNome("Pedro Spegiorin");
      admin.setEmail("admin@gmail.com");
      admin.setNivAcesso((short) 1);
      admin.setTelefone("99999-9999");
      String senhaPadrao = "admin";
      admin.setSenha(passwordHash.generate(senhaPadrao.toCharArray()));
      investidorDAO.save(admin);
    }
  }
}

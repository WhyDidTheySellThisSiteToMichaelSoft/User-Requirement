package br.edu.projeto.security;

// OK?
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "login.xhtml", errorPage = "login-error.xhtml"))

@DatabaseIdentityStoreDefinition(dataSourceLookup = "java:/PostgresDS", callerQuery = "SELECT senha FROM investidor i WHERE i.email = ?", groupsQuery = "SELECT senha FROM investidor i WHERE i.email = ?")

@ApplicationScoped
public class AppConfig {

}

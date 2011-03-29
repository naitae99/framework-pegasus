package ar.pegasus.framework.boss;

import java.util.HashMap;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import org.jboss.security.auth.callback.UsernamePasswordHandler;

import ar.pegasus.framework.componentes.error.PRuntimeException;
import ar.pegasus.framework.entidades.core.Usuario;

public class BossUsuarioVerificado {

	private static Usuario usuarioVerificado;
	private static LoginContext loginContext;

	public static Usuario getUsuarioVerificado() {
		return usuarioVerificado;
	}

	public static void setUsuarioVerificado(Usuario usuarioVerificado) {
		BossUsuarioVerificado.usuarioVerificado = usuarioVerificado;
	}
	
	public static void loginJAASClienteJBoss () {
		//System.setProperty("java.security.auth.login.config",
        //"d:/auth.conf");

		final HashMap<String, String> options = new HashMap<String,String>();//("multi-threaded",false)
		options.put("multi-threaded","false");
		options.put("password-stacking",null);
		
		Configuration configuration = new javax.security.auth.login.Configuration()
	      {
	         private AppConfigurationEntry[] aces = { new AppConfigurationEntry( 
	        		 "org.jboss.security.ClientLoginModule", 
	                  LoginModuleControlFlag.REQUIRED,options 
	               ) };
	         @Override
	         public AppConfigurationEntry[] getAppConfigurationEntry(String name)
	         {
	            return "sgpmateriales".equals(name) ? aces : null;
	         }
	         @Override
	         public void refresh() {}
	      };
		javax.security.auth.login.Configuration.setConfiguration(configuration);
		
		UsernamePasswordHandler handler = null;
	    handler = new UsernamePasswordHandler(usuarioVerificado.getNombre(), usuarioVerificado.getPassword());
		try {
			loginContext = new LoginContext("sgpmateriales",handler);
			loginContext.login();
		} catch (LoginException e) {
			throw new PRuntimeException("JAAS login Exception",e);
		}
	}

}
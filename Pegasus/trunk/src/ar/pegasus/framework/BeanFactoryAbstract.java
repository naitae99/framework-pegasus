package ar.pegasus.framework;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.error.PRuntimeException;

public abstract class BeanFactoryAbstract {

	private static InitialContext initialContext;
	protected Map<Class<?>, String> jndiNames = new HashMap<Class<?>, String>();
	protected Map<Class<?>, Object> beanCache = new HashMap<Class<?>, Object>();
	private String applicationName;
	
	protected String getApplicationName(){
		return applicationName;
	}

	protected void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clazz) throws PException  {
		if (beanCache.get(clazz) == null) {
			beanCache.put(clazz, getNewBean(clazz)) ;
		}
		return (T) beanCache.get(clazz) ;
	}
	
	/**
	 * Llama a getBean, pero envuelve la excepcion PException en CLRuntimeException. 
	 * (Generalmente un error de este tipo es irreparable y se debe manejar mostrando un error que impide seguir usando la aplicacion )
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws CLRuntimeException
	 */
	public <T> T getBean2(Class<T> clazz) throws PRuntimeException  {
		try {
			return getBean(clazz);
		} catch(PException e) {
			throw new PRuntimeException(e);
		}
	}	

	@SuppressWarnings("unchecked")
	public <T> T getNewBean(Class<T> clazz) throws PException  {
		String jndiName = jndiNames.get(clazz) ;
		if (jndiName == null) {
			throw new PException("No está definido el nombre JNDI para la clase " + clazz) ;
		}
		try {
			return (T) getInitialContext().lookup(jndiName);
		} catch(Exception e) {
			throw new PException("Error accediendo vía JNDI al bean " + jndiName, e);
		}
	}

	public synchronized InitialContext getInitialContext() throws PException {
		if (initialContext == null) {
			try {
				initialContext = new InitialContext();
			} catch (NamingException e) {
				throw new PException("No se pudo crear un contexto inicial para acceder al Servidor de aplicaciones", e) ;
			}
		}
		return initialContext ;
	}

	protected Map<Class<?>, String> getJndiNames() {
		return jndiNames ;
	}

	protected abstract String getBeanName(Class<?> clazz) throws PException;

	protected void addJndiNameWithOutAppName(Class<?> clazz) throws PException {
		String beanName = getBeanName(clazz);
		getJndiNames().put(clazz, beanName);
	}
}

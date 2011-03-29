package ar.pegasus.framework.util;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import ar.pegasus.framework.BeanFactoryAbstract;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.error.PRuntimeException;

public class BeanFactoryRemoteAbstract extends BeanFactoryAbstract {
	protected BeanFactoryRemoteAbstract() throws PException {
		this(null);
	}
	
	protected BeanFactoryRemoteAbstract(String defaultAppName) throws PException {
		setApplicationName( System.getProperty("applicationName") );
		if (getApplicationName() == null) {
			if (StringUtil.isNullOrEmpty(defaultAppName))
				throw new PException("No se pudo determinar a que aplicación acceder") ;
			else
				setApplicationName(defaultAppName);
		}
		if (!getApplicationName().endsWith("/")) {
			setApplicationName( getApplicationName() + "/" );
		}
	}

	protected void addJndiName(Class<?> interfaceRemota) throws PException {
		String beanName = getBeanName(interfaceRemota);
		getJndiNames().put(interfaceRemota, getApplicationName() + beanName);
	}

	protected String getBeanName(Class<?> interfaceRemota) throws PException {
		if (!interfaceRemota.getName().endsWith("Remote")) {
			throw new PException ("Una interfaz remota debe terminar en Remote") ;
		}
		String beanName = interfaceRemota.getName().replaceFirst("Remote$", "/remote") ;
		if (beanName.lastIndexOf('.') != -1) {
			beanName = beanName.substring(beanName.lastIndexOf('.') + 1) ;
		}
		return beanName;
	}

	/**
	 * Similar a {@link #getUnregisteredBean(Class)}, pero puede recibir el  contexto jndi.
	 * @param <T>
	 * @param clazz
	 * @param jndiCtx
	 * @return
	 * @throws CLRuntimeException
	 */
	@SuppressWarnings("unchecked")
	public <T> T getUnregisteredBean(Class<T> clazz, InitialContext jndiCtx) throws PRuntimeException  {
		try {
			String bn = getApplicationName() + getBeanName(clazz);
			if (jndiCtx==null)
				jndiCtx = getInitialContext();
			return (T) jndiCtx.lookup(bn);
		} catch(Exception e) {
			try {
				logger.warn("JNDI erroneo?:" + getApplicationName() + getBeanName(clazz));
			} catch(PException e1) {}
			throw new PRuntimeException("Error accediendo vía JNDI al bean " , e);
		}
	}
	
	/**
	 * 
	 * Busca y creo un nuevo Bean, pero no registrado. 
	 * Util para evitar empaquetar en la aplicación Beans que son solo de test.
	 * Ej: TestMainDineroMail es un bean de test, no debería estar presente en el deploy final.
	 * 
	 * <B>EVITAR SU USO EN OTROS CASOS</B>
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws CLRuntimeException
	 */
	public <T> T getUnregisteredBean(Class<T> clazz) throws PRuntimeException  {
		return getUnregisteredBean(clazz, null);
	}
	
	private final static Logger logger = Logger.getLogger(BeanFactoryRemoteAbstract.class);
	
}

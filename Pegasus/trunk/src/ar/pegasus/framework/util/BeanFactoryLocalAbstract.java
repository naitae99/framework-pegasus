package ar.pegasus.framework.util;

import javax.ejb.Local;

import ar.pegasus.framework.BeanFactoryAbstract;
import ar.pegasus.framework.boss.EnumAplicacionGeneral;
import ar.pegasus.framework.componentes.PException;

public class BeanFactoryLocalAbstract extends BeanFactoryAbstract {

	protected BeanFactoryLocalAbstract() throws PException {
		setApplicationName(EnumAplicacionGeneral.getBaseJndiName()) ;
		System.out.println("Asumiendo " + getApplicationName() + " para los lookups locales.");
	}

	public BeanFactoryLocalAbstract(String appName) {
		setApplicationName(appName);
		System.out.println("Asumiendo " + getApplicationName() + " para los lookups locales.");
	}

	protected void addJndiName(Class<?> interfaceLocal) throws PException {
		getJndiNames().put(interfaceLocal, getApplicationName() + getBeanName(interfaceLocal));
	}

	@Override
	protected String getBeanName(Class<?> clazz) throws PException {
		if(!clazz.getName().endsWith("Local")) {
			throw new PException("Una interfaz local debe terminar en Local");
		}
		if(clazz.getAnnotation(Local.class) == null) {
			throw new PException("Una interfaz local debe tener la anotacion @javax.ejb.Local (" + clazz.getName()+ ")");
		}
		String beanName = clazz.getName().replaceFirst("Local$", "/local");
		if(beanName.lastIndexOf('.') != -1) {
			beanName = beanName.substring(beanName.lastIndexOf('.') + 1);
		}
		return beanName;
	}
}

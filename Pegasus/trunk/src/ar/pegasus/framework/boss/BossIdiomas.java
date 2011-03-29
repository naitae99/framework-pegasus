package ar.pegasus.framework.boss;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import ar.pegasus.framework.util.DateUtil;

public class BossIdiomas {

	public static final String FW = "fw";

	private static Map<String, BossIdiomas> instances = new HashMap<String, BossIdiomas>();
	private Locale locale;
	private ResourceBundle messageBundle;
	private String resourceBundle;

	private BossIdiomas(String resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public static BossIdiomas getInstance(String resourceBundle) {
		if(instances.get(resourceBundle) == null) {
			instances.put(resourceBundle, new BossIdiomas(resourceBundle));
		}
		return instances.get(resourceBundle);
	}

	private SimpleDateFormat dateFormatter = null;

	/**
	 * @param fecha
	 * @return fecha Corta, en el locale que corresponda
	 */
	public String formatDate(java.util.Date fecha) {
		if(dateFormatter == null) {
			if(isSpanish())
				//dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				dateFormatter = DateUtil.getSimpleDateFormat("dd/MM/yyyy");
			else
				//dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
				dateFormatter = DateUtil.getSimpleDateFormat("MM/dd/yyyy");
		}
		return dateFormatter.format(fecha);
	}

	private boolean isSpanish() {
		return getLocale().getLanguage().equals("") || getLocale().getLanguage().equals("es");
	}

	private Locale getLocale() {
		if(locale == null) {
//			locale = new Locale("es", "","");//esto no funciona
			locale = new Locale("", "", "");//Ojo!! Si quiero el lenguaje default (español,backdt.properties) tengo que usar esto. 
//			locale = new Locale("en", "US");
		}
		return locale;
	}

	private ResourceBundle getMessageBundle() {
		if(messageBundle == null) {
			messageBundle = ResourceBundle.getBundle(resourceBundle, getLocale());
		}
		return messageBundle;
	}

	/**
	 * @deprecated
	 * Recarga TODOS los archivos *.properties. 
	 * Solo debe usarse durante desarrollo, para ver que los cambios tomaron efecto
	 * NO debe usarse en ninguna otra circunstancia. 
	 */
	public void reload() {
		try {
			Class klass = ResourceBundle.getBundle(this.resourceBundle).getClass().getSuperclass();
			java.lang.reflect.Field field = klass.getDeclaredField("cacheList");
			field.setAccessible(true);
			sun.misc.SoftCache cache = (sun.misc.SoftCache)field.get(null);
			cache.clear();
		} catch(SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messageBundle = ResourceBundle.getBundle(resourceBundle, getLocale());
	}

	public String getString(String key) {
		return getMessageBundle().getString(key);
	}

	public String getString(String key, Object[] messageArguments) {
		MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(getLocale());
		formatter.applyPattern(getMessageBundle().getString(key));
		return formatter.format(messageArguments);
	}

}

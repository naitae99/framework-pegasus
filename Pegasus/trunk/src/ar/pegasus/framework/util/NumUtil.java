package ar.pegasus.framework.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;

/**
 * Clase que contiene funciones utiles de numeros.
 * 
 * @author oarias
 */
public class NumUtil {

	public static final double FC_PUNTOS_A_CENTIMETROS = 0.0352778;

	/**
	 * Setea 2 decimales al n�mero con punto flotante pasado por par�metro.
	 * 
	 * @param f
	 *            El n�mero con punto flotante
	 * @return El n�mero con 2 decimales seteados
	 */
	public static float setDecimales(float f) {
		return setDecimales(f, 2);
	}

	/**
	 * Setea la <b>cantidad de decimales</b> al n�mero con punto flotante pasado
	 * por par�metro.
	 * 
	 * @param f
	 *            El n�mero con punto flotante
	 * @param cantDecimales
	 *            La cantidad de decimales a asignarle al nro
	 * @return El n�mero con la cantidad de decimales asignada
	 */
	public static float setDecimales(float f, int cantDecimales) {
		String pattern = "##.";
		int posSeparador = 0;
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
		char separadorDecimal = formatSymbols.getDecimalSeparator();
		for(int i = 0; i < cantDecimales; i++) {
			pattern = pattern + "#";
		}
		DecimalFormat formato = new DecimalFormat(pattern);
		String resultado = formato.format(f);
		posSeparador = resultado.indexOf(separadorDecimal);
		if(posSeparador != -1)
			resultado = resultado.replace(separadorDecimal, '.');
		return (Float.parseFloat(resultado));
	}

	/**
	 * Convierte un float con el formato decimal del sistema para ser ingresado
	 * en los componentes.
	 * 
	 * @param valor
	 *            El valor de punto flotante
	 * @return El valor de punto flotante con el formato decimal del sistema
	 */
	public static String convertirFloat(float valor) {
		DecimalFormat formato = new DecimalFormat("##.##");
		return formato.format(setDecimales(valor));
	}

	/**
	 * Realiza un redondeo decimal al par�metro valor.
	 * 
	 * @param valor
	 * @param digitos
	 * @param patron
	 * @return
	 */
	public static float redondearDecimales(float valor, int digitos, int patron) {
		BigDecimal bd = new BigDecimal(valor);
		return bd.setScale(digitos, patron).floatValue();
	}

	public static double redondearDecimalesd(double valor, int digitos, int patron) {
		BigDecimal bd = new BigDecimal(valor);
		return bd.setScale(digitos, patron).doubleValue();
	}

	/**
	 * Convierte un valor de puntos a cent�metros.
	 * 
	 * @param f
	 *            El valor en puntos
	 * @return El nuevo valor en cent�metros
	 */
	public static double puntosToCentimetros(float f) {
		return (Math.round((float)((f * FC_PUNTOS_A_CENTIMETROS) * 100)) / 100.00);
	}

	/**
	 * Convierte un valor de cent�metros a puntos.
	 * 
	 * @param f
	 *            El valor en centimetros
	 * @return El nuevo valor en puntos
	 */
	public static double centimetrosToPuntos(float f) {
		return f / FC_PUNTOS_A_CENTIMETROS;
	}

	/**
	 * Devuelve <b>true</b> si el valor pasado por par�metro es un valor
	 * num�rico.
	 * 
	 * @param str
	 *            La cadena de caracteres a evaluar
	 * @return
	 */
	public static boolean esNumerico(String str) {
		if(str == null) {
			return false;
		}
		if(str.length() == 0)
			return false;
		for(int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Obtiene <b>MBytes</b> a partir de bytes.
	 * 
	 * @param bytes
	 *            El valor en bytes
	 * @return El nuevo valor en MBytes
	 */
	public static float toMBytes(int bytes) {
		int factor = 1024 * 1024;
		float res = (float)bytes / factor;
		int resInt = (int)(res * 100);
		res = (float)((float)resInt / 100f);
		return res;
	}

	/**
	 * Obtiene <b>MBytes</b> a partir de bytes.
	 * 
	 * @param bytes
	 *            El valor en bytes
	 * @return El nuevo valor en MBytes
	 */
	public static float toMBytes(long bytes) {
		return NumUtil.toMBytes(new Long(bytes).intValue());
	}

	/**
	 * Convierte a un int el valor de un String.
	 * 
	 * @param valor
	 * @return
	 */
	public static int toInt(String valor) {
		return Float.valueOf(toFloat(valor)).intValue();
	}

	/**
	 * Convierte a un float el valor de un String.
	 * 
	 * @param valor
	 * @return
	 */
	public static float toFloat(String valor) {
		return Float.parseFloat(valor.replace(',', '.'));
	}

	/**
	 * Devuelve el porcentaje de <b>valor</b> con respecto a <b>total</b>.
	 * 
	 * @param valor
	 * @param total
	 * @return El porcentaje
	 */
	public static float getPorcentaje(float valor, float total) {
		return (valor / total) * 100;
	}

	/**
	 * Devuelve el n�mero m�s cercano a <b>valor</b> y que es divisible por
	 * <b>divisor</b>.
	 * 
	 * @param valor
	 * @param divisor
	 * @return El menor valor divisible por <b>divisor</b> y mayor o igual a
	 *         <b>valor</b>
	 */
	public static int getMultiploMasCercano(int valor, int divisor) {
		if(valor % divisor == 0) {
			return valor;
		} else {
			int valorNuevo = valor + 1;
			while(valorNuevo % divisor != 0) {
				valorNuevo++;
			}
			return valorNuevo;
		}
	}

	/**
	 * Convierte <code>valor</code> en la unidad de medida cuyo factor de
	 * conversi�n es <code>factorConversionActual</code> en otra unidad de
	 * medida cuyo factor de conversi�n es <code>factorConversionNuevo</code>.
	 * 
	 * @param valor
	 * @param factorConversionActual
	 * @param factorConversionNuevo
	 */
	public static float getValorConvertido(float valor, float factorConversionActual, float factorConversionNuevo) {
		return (valor * factorConversionActual) / factorConversionNuevo;
	}

	/**
	 * <p>
	 * Recibe el tama�o de un archivo en bytes y lo formatea para expresarlo con
	 * la minima cantidad de numeros.
	 * </p>
	 * Ej:
	 * <ul>
	 * <li>150 -> 150 bytes</li>
	 * <li>2050 -> 2 KB</li>
	 * <li>2048576 -> 1,95 MB</li>
	 * <li>2073741824 -> 1,93 GB</li>
	 * </ul>
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		Formatter f = new Formatter();
		float tempFloat;
		if(size < MEGABYTE) {
			if(size < KILOBYTE) {
				return size + " bytes";
			}
			tempFloat = (float)size / KILOBYTE;
			return f.format("%,.0f", tempFloat).toString() + " KB";
		} else {
			if(size > GIGABYTE) {
				tempFloat = (float)size / GIGABYTE;
				return f.format("%,.2f", tempFloat).toString() + " GB";
			}
			tempFloat = (float)size / MEGABYTE;
			return f.format("%,.2f", tempFloat).toString() + " MB";
		}
	}

	private static final int MEGABYTE = 1048576;
	private static final int KILOBYTE = 1024;
	private static final int GIGABYTE = 1073741824;

	public static long combinatoria(int n, int x) {
		double comb = 1;
		int dif = Math.max(x, n - x);
		int term = Math.min(x, n - x);
		for(int i = 1; i <= term; i++) {
			comb *= (double)(i + dif) / i;
		}
		return (long)comb;
	}

	/**
	 * Asume que obj es de tipo <b>Number</b>. Devuelve NULL o un Integer. Util
	 * para leer enteros devueltos por alg�n driver JDBC.
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer toInteger(Object obj) {
		if(obj == null)
			return null;
		return ((Number)obj).intValue();
	}

	/**
	 * Asume que obj es de tipo <b>Number</b>. Devuelve NULL o un BigInteger.
	 * Util para leer enteros grandes devueltos por alg�n driver JDBC.
	 * 
	 * @param obj
	 * @return
	 */
	public static BigInteger toBigInteger(Object obj) {
		if(obj == null)
			return null;
		return new BigInteger(obj.toString(), 10);
	}

	/**
	 * Asume que obj es de tipo <b>Number</b>. Devuelve NULL o un BigDecimal.
	 * Util para leer fracciones devueltas por alg�n driver JDBC.
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		if(obj == null)
			return null;
		if(obj instanceof BigDecimal) {
			return (BigDecimal)obj;
		}
		BigDecimal bd = new BigDecimal(((Number)obj).doubleValue());
		return bd;
	}

	/**
	 * Asume que obj es de tipo <b>Number</b>. Devuelve NULL o un Byte. Util
	 * para leer enteros devueltos por alg�n driver JDBC.
	 * 
	 * @param obj
	 * @return
	 */
	public static Byte toByte(Object obj) {
		if(obj == null)
			return null;
		return ((Number)obj).byteValue();
	}

	public static boolean esPar(int valor){
		return (valor%2 == 0);
	}
	
	public static boolean esPar(long valor){
		return (valor%2 == 0);
	}
	
	public static Integer toInteger(BigDecimal nro) {
		int i;
		Integer ptos;
		i = nro.intValue();
		ptos = new Integer(i);
		return ptos;
	}
	
	public static String padToFixedLength(Integer id, int fixedLength) {
		String idString = id.toString();
		if (idString.length() < fixedLength) {
			StringBuilder stringBuilder = new StringBuilder();
			int falta = fixedLength - idString.length();
			for (int i = 0; i < falta; i++) {
				stringBuilder.append('0');
			}
			stringBuilder.append(idString);
			return stringBuilder.toString();
		} else if (idString.length() < fixedLength) {
			return idString.substring(idString.length() - fixedLength);
		} else {
			return idString;
		}
	}

	public static void main(String[] args) {
		System.out.println(formatFileSize(150));
		System.out.println(formatFileSize(2050));
		System.out.println(formatFileSize(2048576));
		System.out.println(formatFileSize(2073741824));
	}
}
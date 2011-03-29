package ar.pegasus.framework.componentes;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.taglibs.string.util.StringW;

import ar.pegasus.framework.util.FileUtil;
import ar.pegasus.framework.util.StringUtil;

/**
 * Componente para la selecci�n de archivos y directorios.
 * 
 * << Particularidades del JFileChooser >>
 * 
 * Permitiendo seleccionar múltiples archivos y/o directorios no resalta el archivo indicado con setSelectedFile
 * Lo muestra en el TextField pero no lo resalta en el panel.
 * 
 * Permitiendo seleccionar m�ltiples directorios cuando retorna no permite discriminar si seleccion� un directorio 
 * parado en el padre o si lo seleccion� estando adentro ...
 * 
 * Si se seleccionan m�ltiples archivos y/o directorios y se cambia de directorio manteniendo la selecci�n, retorna
 * la lista de archivos y/o directorios sin el path ...
 * 
 * Algunas veces el directorio / archivo seleccionado est� seleccionado pero no aparece en la parte visible del panel.
 * 
 * Si el panel abarca todas las entradas no se selecciona el archivo / directorio corriente (excepto si esta en modo DIRECTORIES_ONLY ...).
 * 
 * << Comportamiento CLFileChooser (para lidiar con las Particularidades del JFileChooser) >>
 * 
 * Si se seleccionan m�ltiples archivos y/o directorios el �ltimo archivo seleccionado queda en el padre de los mismos.
 * 
 * Si se selecciona un directorio en un di�logo que permite selecci�n m�ltiple el �ltimo archivo seleccionado queda en ese directorio.
 * 
 * Si se selecciona un archivo en un di�logo que permite selecci�n m�ltiple el �ltimo archivo seleccionado queda en el directorio padre del archivo seleccionado.
 *  
 * Cuando el �ltimo archivo seleccionado es un directorio y fue seleccionado con un di�logo que permit�a selecci�n 
 * m�ltiple el di�logo se abre en ese directorio.
 *
 * Si el �ltimo archivo seleccionado es un archivo y se abre el di�logo en modo directorios solamente o con un filtro
 * que haga que el archivo no aparezca, y el usuario cancela, no pierde la selecci�n anterior.
 * 
 * Si saveOrOpen es OPEN no permite seleccionar archivos que no existen.
 * 
 * @author oarias
 */
public class PFileSelector {

	public static final int SAVE = 0;
	public static final int OPEN = 1;
	public static final int FILES_AND_DIRECTORIES = JFileChooser.FILES_AND_DIRECTORIES;
	public static final int FILES_ONLY = JFileChooser.FILES_ONLY;
	public static final int DIRECTORIES_ONLY = JFileChooser.DIRECTORIES_ONLY;
	private static boolean fromMultiple = false;
	private static File lastSelectedFile = null;
	private static File lastSelectedFileBackup = null;

	/**
	 * Retorna una instacia de JFileChooser.
	 * @param mode El modo de selecci�n. Posibles modos:
	 *             FILES_ONLY Solo selecci�n de archivos.
	 *             DIRECTORIES_ONLY Solo selecci�n de directorios.
	 *             FILES_AND_DIRECTORIES Selecci�n de archivos y directorios.
	 * @param fileFilter El filtro de archivos a utilizar (Ej) para seleccionar solo archivos .zip)
	 * @param multiSelectionEnabled Flag para indicar si se puede seleccionar mas de un archivo o/y directorio.
	 * @return fileChooser La instancia del componente.
	 */
	private static JFileChooser getFileChooserInstance(int saveOrOpen, int fileSelectionMode, FileFilter fileFilter, boolean multiSelectionEnabled) {
		JFileChooser fileChooser = new JFileChooser();
		//fileChooser.setPreferredSize(new Dimension(600, 600)) ;
		fileChooser.setFileHidingEnabled(false);
		fileChooser.setFileSelectionMode(fileSelectionMode);
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setMultiSelectionEnabled(multiSelectionEnabled);
		if(getLastSelectedFile() == null) {
			return fileChooser;
		}
		//System.out.println("getLastSelectedFile: " + getLastSelectedFile()) ;
		File archivoSeleccionadoExistente = null;
		if(saveOrOpen == OPEN) {
			archivoSeleccionadoExistente = getParentExistFile(getLastSelectedFile());
		} else {
			archivoSeleccionadoExistente = getLastSelectedFile();
		}
		setLastSelectedFileBackup(archivoSeleccionadoExistente);
		if(fileFilter != null) {
			if(!fileFilter.accept(archivoSeleccionadoExistente)) {
				if(archivoSeleccionadoExistente.exists() && archivoSeleccionadoExistente.isFile()) {
					setLastSelectedFile(archivoSeleccionadoExistente.getParentFile());
					archivoSeleccionadoExistente = getLastSelectedFile();
				}
			}
		}
		//System.out.println("archivoSeleccionadoExistente: " + archivoSeleccionadoExistente) ;
		//En archivoSeleccionadoExistente tengo el 1er archivo o directorio existente que respeta el filtro
		if(fileSelectionMode == JFileChooser.FILES_ONLY) {
			if(archivoSeleccionadoExistente.isFile() || !archivoSeleccionadoExistente.exists()) {
				fileChooser.setCurrentDirectory(archivoSeleccionadoExistente.getParentFile());
				fileChooser.setSelectedFile(archivoSeleccionadoExistente);
			} else {
				fileChooser.setCurrentDirectory(archivoSeleccionadoExistente);
				fileChooser.setSelectedFile(null);
			}
		} else if(fileSelectionMode == JFileChooser.DIRECTORIES_ONLY) {
			if(archivoSeleccionadoExistente.isFile()) {
				fileChooser.setCurrentDirectory(archivoSeleccionadoExistente.getParentFile());
				fileChooser.setSelectedFile(null);
			} else {
				if(getFromMultiple()) {
					fileChooser.setCurrentDirectory(archivoSeleccionadoExistente);
					fileChooser.setSelectedFile(null);
				} else {
					fileChooser.setCurrentDirectory(archivoSeleccionadoExistente.getParentFile());
					fileChooser.setSelectedFile(archivoSeleccionadoExistente);
				}
			}
		} else if(fileSelectionMode == JFileChooser.FILES_AND_DIRECTORIES) {
			if(archivoSeleccionadoExistente.isFile() || !archivoSeleccionadoExistente.exists()) {
				fileChooser.setCurrentDirectory(archivoSeleccionadoExistente.getParentFile());
				fileChooser.setSelectedFile(archivoSeleccionadoExistente);
			} else {
				if(getFromMultiple()) {
					fileChooser.setCurrentDirectory(archivoSeleccionadoExistente);
					fileChooser.setSelectedFile(null);
				} else {
					fileChooser.setCurrentDirectory(archivoSeleccionadoExistente.getParentFile());
					fileChooser.setSelectedFile(archivoSeleccionadoExistente);
				}
			}
		} else {
			return null;
		}
		return fileChooser;
	}

	/**
	 * Abre un di�logo de selecci�n de archivos acorde a la parametrizaci�n.
	 * @param saveOrOpen Indica si el di�logo debe decir Guardar o Abrir.
	 * @param fileSelectionMode El modo de selecci�n. Posibles modos:
	 *             FILES_ONLY Solo selecci�n de archivos.
	 *             DIRECTORIES_ONLY Solo selecci�n de directorios.
	 *             FILES_AND_DIRECTORIES Selecci�n de archivos y directorios.
	 * @param parent Permite indicar el componente padre del di�logo.
	 * @return File El archivo seleccionado o null si se cancel� la selecci�n.
	 */
	public static File obtenerArchivo(int saveOrOpen, int fileSelectionMode, Component parent) {
		return obtenerArchivo(saveOrOpen, fileSelectionMode, null, parent);
	}

	/**
	 * Abre un di�logo de selecci�n de archivos acorde a la parametrizaci�n.
	 * @param saveOrOpen Indica si el di�logo de decir Guardar o Abrir.
	 * @param fileSelectionMode El modo de selecci�n. Posibles modos:
	 *             FILES_ONLY Solo selecci�n de archivos.
	 *             DIRECTORIES_ONLY Solo selecci�n de directorios.
	 *             FILES_AND_DIRECTORIES Selecci�n de archivos y directorios.
	 * @param fileFilter El filtro de archivos a utilizar (Ej) para seleccionar solo archivos .zip)
	 * @param parent Permite indicar el componente padre del di�logo.
	 * @return File El archivo seleccionado o null si se cancel� la selecci�n.
	 */
	public static File obtenerArchivo(int saveOrOpen, int fileSelectionMode, FileFilter filter, Component parent) {
		File selectedFile = null;
		JFileChooser fileChooser = getFileChooserInstance(saveOrOpen, fileSelectionMode, filter, false);
		int returnValue = mostrarDialogo(saveOrOpen, parent, fileChooser);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			setLastSelectedFile(selectedFile);
		} else {
			//Para no perder el archivo seleccionado en modo s�lo directorios si el usuario cancela
			setLastSelectedFile(getLastSelectedFileBackup());
		}
		if(selectedFile != null && saveOrOpen == OPEN && !selectedFile.exists()) {
			String detalleTipo;
			String detalleAdv;
			if(fileSelectionMode == DIRECTORIES_ONLY){
				detalleTipo ="La ruta ";
				detalleAdv="Ruta ";
			}else{
				if(fileSelectionMode == FILES_AND_DIRECTORIES){
					detalleTipo="El archivo o la ruta ";
					detalleAdv ="Archivo/Ruta ";
				}else{
					detalleTipo="El archivo ";
					detalleAdv ="Archivo ";
				}
			}				
			PJOptionPane.showInformationMessage(parent, detalleTipo + selectedFile.getAbsolutePath() + " no existe.", detalleAdv + "inexistente");
			selectedFile = obtenerArchivo(saveOrOpen, fileSelectionMode, filter, parent);
		}
		if(selectedFile != null && saveOrOpen == SAVE && selectedFile.exists()) {
			if(PJOptionPane.showQuestionMessage(parent, "El archivo " + selectedFile.getAbsolutePath() + " ya existe. �Desea reemplazarlo?", "Archivo ya existe") == PJOptionPane.NO_OPTION) {
				selectedFile = obtenerArchivo(saveOrOpen, fileSelectionMode, filter, parent);
			} else {
				if(FileUtil.isFileOpened(selectedFile)) {
					PJOptionPane.showErrorMessage(parent, "El archivo " + selectedFile.getAbsolutePath() + " se encuentra abierto." + StringUtil.RETORNO_CARRO + "Debe cerrarlo antes de guardar.", "Archvo abierto");
					selectedFile = obtenerArchivo(saveOrOpen, fileSelectionMode, filter, parent);
				}
			}
		}
		return selectedFile;
	}

	/**
	 * Abre un di�logo de selecci�n de archivos acorde a la parametrizaci�n (permite la selecci�n de m�ltiples archivos y/o directorios).
	 * @param saveOrOpen Indica si el di�logo de decir Guardar o Abrir.
	 * @param fileSelectionMode El modo de selecci�n. Posibles modos:
	 *             FILES_ONLY Solo selecci�n de archivos.
	 *             DIRECTORIES_ONLY Solo selecci�n de directorios.
	 *             FILES_AND_DIRECTORIES Selecci�n de archivos y directorios.
	 * @param parent Permite indicar el componente padre del di�logo.
	 * @return File[] Los archivos seleccionados o null si se cancel� la selecci�n.
	 */
	public static File[] obtenerArchivos(int saveOrOpen, int fileSelectionMode, Component parent) {
		return obtenerArchivos(saveOrOpen, fileSelectionMode, null, parent);
	}

	/**
	 * Abre un di�logo de selecci�n de archivos acorde a la parametrizaci�n (permite la selecci�n de m�ltiples archivos y/o directorios).
	 * @param saveOrOpen Indica si el di�logo de decir Guardar o Abrir.
	 * @param fileSelectionMode El modo de selecci�n. Posibles modos:
	 *             FILES_ONLY Solo selecci�n de archivos.
	 *             DIRECTORIES_ONLY Solo selecci�n de directorios.
	 *             FILES_AND_DIRECTORIES Selecci�n de archivos y directorios.
	 * @param fileFilter El filtro de archivos a utilizar (Ej) para seleccionar solo archivos .zip)
	 * @param parent Permite indicar el componente padre del di�logo.
	 * @return File[] Los archivos seleccionados o null si se cancel� la selecci�n.
	 */
	public static File[] obtenerArchivos(int saveOrOpen, int fileSelectionMode, FileFilter filter, Component parent) {
		File[] selectedFiles = null;
		JFileChooser fileChooser = getFileChooserInstance(saveOrOpen, fileSelectionMode, filter, true);
		int returnValue = mostrarDialogo(saveOrOpen, parent, fileChooser);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFiles = fileChooser.getSelectedFiles();
			if(selectedFiles.length > 1) {
				for(int i = 0; i < selectedFiles.length; i++) {
					File file = selectedFiles[i];
					if(file.getParentFile() == null) {
						try {
							selectedFiles[i] = new File(fileChooser.getCurrentDirectory().getCanonicalPath() + File.separator + selectedFiles[i].getName());
						} catch(IOException e) {
							e.printStackTrace();
						}
					}
				}
				setLastSelectedFile(selectedFiles[0].getParentFile(), true);
			} else {
				if(fileChooser.getSelectedFile() != null && fileChooser.getSelectedFile().isDirectory()) {
					setLastSelectedFile(fileChooser.getSelectedFile(), true);
				} else {
					setLastSelectedFile(fileChooser.getCurrentDirectory(), true);
				}
			}
		} else {
			//Para no perder el archivo seleccionado en modo s�lo directorios si el usuario cancela.
			setLastSelectedFile(getLastSelectedFileBackup());
		}
		if(selectedFiles != null && saveOrOpen == OPEN) {
			List<String> inexistentes = new ArrayList<String>();
			for(int i = 0; i < selectedFiles.length; i++) {
				if(!selectedFiles[i].exists()) {
					inexistentes.add(selectedFiles[i].getAbsolutePath());
				}
			}
			if(!inexistentes.isEmpty()) {
				StringBuffer archivos = new StringBuffer();
				for(String archivo : inexistentes) {
					if(archivos.length() > 0) {
						archivos.append(", ");
					}
					archivos.append(archivo);
				}
				String message = null;
				String title = null;
				if(inexistentes.size() > 1) {
					message = StringW.wordWrap("Los archivos "
							+ (archivos.length() > 2048 ? (archivos.toString().substring(1, 2048) + " ...") : archivos.toString()) + " no existen.");
					title = "Archivos inexistentes";
				} else {
					message = "El archivo " + archivos.toString() + " no existe.";
					title = "Archivo inexistente";
				}
				PJOptionPane.showInformationMessage(null, message, title);
				selectedFiles = obtenerArchivos(saveOrOpen, fileSelectionMode, filter, parent);
			}
		}
		return selectedFiles;
	}

	private static int mostrarDialogo(int saveOrOpen, Component parent, JFileChooser fileChooser) {
		int returnValue;
		if(saveOrOpen == OPEN)
			returnValue = fileChooser.showOpenDialog(parent);
		else
			returnValue = fileChooser.showSaveDialog(parent);
		return returnValue;
	}

	private static File getParentExistFile(File file) {
		while(file != null && !file.exists()) {
			file = file.getParentFile();
		}
		return file;
	}

	/**
	 * Permite indicar el �ltimo archivo seleccionado (modificar la "memoria" del componente).
	 * @param lastSelectedFile El nuevo �ltimo archivo seleccionado.
	 */
	public static void setLastSelectedFile(File lastSelectedFile) {
		setLastSelectedFile(lastSelectedFile, false);
	}

	/**
	 * Permite indicar el �ltimo archivo seleccionado (modificar la "memoria" del componente).
	 * @param lastSelectedFile El nuevo �ltimo archivo seleccionado.
	 * @param fromMultiple Permite indicar expl�citamente si la selecci�n fu� m�ltiple.
	 */
	public static void setLastSelectedFile(File lastSelectedFile, boolean fromMultiple) {
		PFileSelector.lastSelectedFile = lastSelectedFile;
		PFileSelector.fromMultiple = fromMultiple;
	}

	/**
	 * 
	 * @return El �ltimo archivo seleccionado.
	 */
	public static File getLastSelectedFile() {
		if(PFileSelector.lastSelectedFile == null)
			PFileSelector.setLastSelectedFile((new JFileChooser()).getFileSystemView().getDefaultDirectory());
		return PFileSelector.lastSelectedFile;
	}

	private static boolean getFromMultiple() {
		return PFileSelector.fromMultiple;
	}

	/**
	 * 
	 * @return El prefijo existente del �ltimo archivo seleccionado.
	 * Ej) Si el �ltimo archivo seleccionado es C:\TEMP\NO_EXISTE, y NO_EXISTE no existe, retorna C:\TEMP.
	 */
	public static File getLastSelectedFileExists() {
		return getParentExistFile(getLastSelectedFile());
	}

	private static File getLastSelectedFileBackup() {
		return lastSelectedFileBackup;
	}

	private static void setLastSelectedFileBackup(File lastSelectedFileBackup) {
		PFileSelector.lastSelectedFileBackup = lastSelectedFileBackup;
	}

}
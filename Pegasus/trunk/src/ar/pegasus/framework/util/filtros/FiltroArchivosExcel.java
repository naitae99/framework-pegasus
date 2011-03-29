package ar.pegasus.framework.util.filtros;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import ar.pegasus.framework.util.EtiquetasBotones;

public class FiltroArchivosExcel extends FileFilter {
	public boolean accept(File archivo) {
		return archivo.getName().endsWith(EtiquetasBotones.EXTENSION_EXCEL)
				|| archivo.isDirectory();
	}

	public String getDescription() {
		return EtiquetasBotones.EXTENSION_EXCEL;
	}
}
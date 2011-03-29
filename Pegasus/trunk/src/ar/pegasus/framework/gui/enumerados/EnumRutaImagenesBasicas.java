package ar.pegasus.framework.gui.enumerados;

public enum EnumRutaImagenesBasicas {
	IMAGEN_BUSCAR_HAB(1,"ar/pegasus/framework/imagenes/b_buscar.png"), 
	IMAGEN_BUSCAR_DESHAB(2,"ar/pegasus/framework/imagenes/b_buscar_des.png");
	
	private int id;
	private String ruta;
	
	private EnumRutaImagenesBasicas(int id, String ruta){
		this.setId(id);
		this.setRuta(ruta);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getRuta() {
		return ruta;
	}
	
	

}

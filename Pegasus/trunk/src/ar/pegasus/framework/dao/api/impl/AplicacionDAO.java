package ar.pegasus.framework.dao.api.impl;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.jboss.seam.annotations.Name;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.configuracion.EnumParametros;
import ar.pegasus.framework.dao.api.local.AplicacionDAOLocal;
import ar.pegasus.framework.entidades.core.Aplicacion;
import ar.pegasus.framework.entidades.core.Usuario;
import ar.pegasus.framework.facade.api.local.ConfiguracionFacadeLocal;

@Stateless
@Name("aplicacionDAO")
public class AplicacionDAO extends GenericDAO<Aplicacion, Integer> implements AplicacionDAOLocal {

	@EJB
	private ConfiguracionFacadeLocal<EnumParametros> configuracionFacade ;
	
	/**
	 * Accede al WS LoginService del cual obtiene un XML con los datos de la aplicación (incluye los módulos).
	 * Se parsea el XML y se devuelve una lista de objetos <b>Modulo</b>.
	 * @param idUsuario El nro. de id del usuario.
	 * @param idAplicacion El nro. de id de la aplicación.
	 * @return modulos La lista de módulos de la aplicación a los que el usuario tiene acceso.
	 * @throws PException
	 */
	public Aplicacion getDatosAplicacion(int idAplicacion) throws PException {
		Aplicacion aplicacion = getById(idAplicacion);
		aplicacion.getModulos().size();
		return aplicacion;
	}

	private String getURL() {
		return configuracionFacade.get(EnumParametros.LOGIN_ENDPOINT) + configuracionFacade.get(EnumParametros.NOMBRE_SERVICIO_LOGIN) ; 
	}
	/**
	 * @param idAplicacion
	 * @return Lista de Usuarios por Aplicacion
	 * @throws PException
	 */
	public List<Usuario> getUsuariosPorAplicacion(int idAplicacion) throws PException {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			Service service = new Service();
			Call call = (Call)service.createCall();
			call.setTargetEndpointAddress(new URL(getURL()));
			call.setOperationName(new QName("getUsuariosPorAplicacion"));
			call.addParameter("idAplicacion", XMLType.XSD_INTEGER, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String xml = (String)call.invoke(new Object[] { new Integer(idAplicacion) });
			//Lee el XML que devuelve el WS
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(xml));
			Element root = document.getRootElement();
			for(int i = 0; i < root.getChildren().size(); i++) {
				Element element = (Element)root.getChildren().get(i);
				//Crea y carga el objeto usuario
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(Integer.valueOf(element.getAttributeValue("id")));
				usuario.setNombre(element.getAttributeValue("nombre"));
				usuario.setUserName(element.getAttributeValue("commonName"));
				//Agrega el usuario a la lista
				usuarios.add(usuario);
			}
			return usuarios;
		} catch(Exception e) {
			String mensaje = "No se pudieron recuperar los usuarios para la aplicación con id " + idAplicacion ;
			throw new PException(BossError.ERR_INDETERMINADO, mensaje, mensaje, e, null);
		}
	}

	
}
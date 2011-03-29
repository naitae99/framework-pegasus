package ar.pegasus.framework.engine.business;

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

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.configuracion.EnumParametros;
import ar.pegasus.framework.entidades.core.Rol;
import ar.pegasus.framework.facade.api.local.ConfiguracionFacadeLocal;

@Stateless
@Name("rolDAO")
public class RolDAO implements RolDAOLocal {

	@EJB
	private ConfiguracionFacadeLocal<EnumParametros> configuracionFacade ;

	/**
	 * Devuelve todos los roles disponibles para el usuario y la aplicación pasados por parámetro.
	 * @param idUsuario El nro. de id del usuario.
	 * @param idAplicacion El nro. de id de la aplicación.
	 * @return roles La lista de roles disponibles.
	 * @throws PException
	 */
	private List<Rol> getRolesPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException {
		try {
			List<Rol> roles = new ArrayList<Rol>();
			Service service = new Service();
			Call call = (Call)service.createCall();
			call.setTargetEndpointAddress(new URL(getURL()));
			call.setOperationName(new QName("getRolesPorIdUsuario"));
			call.addParameter("idUsuario", XMLType.XSD_INTEGER, ParameterMode.IN);
			call.addParameter("idAplicacion", XMLType.XSD_INTEGER, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
//			String xml = (String)call.invoke(new Object[] { new Integer(idUsuario), new Integer(idAplicacion) });
			String xml = (String)call.invoke(new Object[] { Integer.valueOf(idUsuario), Integer.valueOf(idAplicacion) });
			//Lee el XML que devuelve el WS
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(xml));
			Element root = document.getRootElement();
			for(int i = 0; i < root.getChildren().size(); i++) {
				Element element = (Element)root.getChildren().get(i);
				//Agrega el rol a la lista
				Rol rol = new Rol();
				rol.setIdRol(Integer.valueOf(element.getAttributeValue("Id")));
				rol.setNombre(element.getAttributeValue("Nombre"));
				rol.setDescripcion(element.getAttributeValue("Descripcion"));
				roles.add(rol);
			}
			return roles;
		} catch(Exception e) {
			throw new PException("No se pudieron recuperar los roles para el usuario con id " + idUsuario + " y la aplicación con id " + idAplicacion, e);
		}
	}

	/**
	 * Devuelve el <b>rol</b> asociado al usuario y la aplicación pasadas por parámetro.
	 * @param idUsuario El id del usuario.
	 * @param idAplicacion El id de la aplicación.
	 * @return El rol asociado al usuario.
	 * @throws PException
	 */
	public Rol getRolPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException {
		List<Rol> roles = getRolesPorUsuarioAplicacion(idUsuario, idAplicacion);
        return ((roles == null) || (roles.isEmpty())) ? null : roles.get(0);
	}

	private String getURL() {
		return configuracionFacade.get(EnumParametros.LOGIN_ENDPOINT) + configuracionFacade.get(EnumParametros.NOMBRE_SERVICIO_LOGIN) ;
	}

}
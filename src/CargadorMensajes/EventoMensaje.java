package CargadorMensajes;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import modelo.MensajeWhatsApp;

@SuppressWarnings("serial")
public class EventoMensaje extends EventObject {
	private List<MensajeWhatsApp> mensajes = new LinkedList<MensajeWhatsApp>();

	public EventoMensaje(CargadorMensajes cargadorMensajes, List<MensajeWhatsApp> chat) {
		super(cargadorMensajes);
		this.setMensajes(chat);
		
	}

	public List<MensajeWhatsApp> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<MensajeWhatsApp> mensajes) {
		this.mensajes = mensajes;
	}

}

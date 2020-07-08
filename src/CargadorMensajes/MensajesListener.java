package CargadorMensajes;
import java.util.EventListener;

//Observer del EventoMensaje
public interface MensajesListener extends EventListener {

	void nuevosMensajes(EventoMensaje e);

}

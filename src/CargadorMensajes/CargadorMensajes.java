package CargadorMensajes;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import parser.SimpleTextParser;

public class CargadorMensajes {
	
    private Vector<MensajesListener> observers;

    public CargadorMensajes() {
    	observers = new Vector<MensajesListener>();
    }

    public synchronized void addObserver(MensajesListener oyente) {
    	observers.add(oyente);
    }

    public synchronized void removeObserver(MensajesListener oyente) {
    	observers.remove(oyente);
    }

    public void setFichero (String ruta, Plataforma plataforma) {
        
        List<MensajeWhatsApp> mensajesWhatsapp = null;
        try {
        	String FormatoFechaWhatsapp =  "d/M/yy H:mm:ss";
        	mensajesWhatsapp = SimpleTextParser.parse(ruta, FormatoFechaWhatsapp, plataforma);
        } catch (IOException e) {
            e.printStackTrace();
        }
        notificarNuevosMensajes(new EventoMensaje(this, mensajesWhatsapp));
    }

    @SuppressWarnings("unchecked")
    private void notificarNuevosMensajes(EventoMensaje e) {
        Vector<MensajesListener> lista;
        synchronized (this) {
            lista = (Vector<MensajesListener>) observers.clone();
        }
        for (MensajesListener oyente : lista) {
            oyente.update(e);
        }
    }
}
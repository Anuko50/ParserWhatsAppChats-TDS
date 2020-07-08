package CargadorMensajes;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import parser.SimpleTextParser;

public class CargadorMensajes {
	
    private Vector<MensajesListener> oyentes;

    public CargadorMensajes() {
        oyentes = new Vector<MensajesListener>();
    }

    public synchronized void addObserver(MensajesListener oyente) {
        oyentes.add(oyente);
    }

    public synchronized void removeObserver(MensajesListener oyente) {
        oyentes.remove(oyente);
    }

    public void setFichero (String ruta, Plataforma plataforma) {
        
        List<MensajeWhatsApp> chat = null;
        try {
        	String FormatoFechaWhatsapp =  "d/M/yy H:mm:ss";
            chat = SimpleTextParser.parse(ruta, FormatoFechaWhatsapp, plataforma);
        } catch (IOException e) {
            e.printStackTrace();
        }
        notificarNuevosMensajes(new EventoMensaje(this, chat));
    }

    @SuppressWarnings("unchecked")
    private void notificarNuevosMensajes(EventoMensaje e) {
        Vector<MensajesListener> lista;
        synchronized (this) {
            lista = (Vector<MensajesListener>) oyentes.clone();
        }
        for (MensajesListener oyente : lista) {
            oyente.update(e);
        }
    }
}
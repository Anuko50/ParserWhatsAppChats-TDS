package Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Test;

import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import parser.SimpleTextParser;

public class TestParser {

	@Test
	public void testIOS() {
		String formatDateWhatsApp = "d/M/yy H:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDateWhatsApp);
		List<MensajeWhatsApp> chat = null;
		try {
			chat = SimpleTextParser.parse("test-chat/chat.txt", formatDateWhatsApp, Plataforma.IOS);
			for (MensajeWhatsApp mensaje : chat) {
				System.out.println(">" + mensaje.getFecha().format(formatter) +
									" " + mensaje.getAutor() + " : " + mensaje.getTexto());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull("El chat no se ha cargado", chat);
		assertEquals("Faltan mensajes en el chat", 11, chat.size());
		System.out.println("** Size chat: " + chat.size());
	}

	@Test
	public void testANDROID() {
		String formatDateWhatsApp = "d/M/yy H:mm";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDateWhatsApp);
		List<MensajeWhatsApp> chat = null;
		try {
			chat = SimpleTextParser.parse("test-chat/chat-tds.txt", formatDateWhatsApp, Plataforma.ANDROID);
			for (MensajeWhatsApp mensaje : chat) {
				System.out.println(">" + mensaje.getFecha().format(formatter) +
									" " + mensaje.getAutor() + " : " + mensaje.getTexto());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull("El chat no se ha cargado", chat);
		assertTrue(true);
		System.out.println("** Size chat: " + chat.size());
	}
	
	@Test
	public void testANDROID2() {
		String formatDateWhatsApp = "d/M/yyyy, H:mm";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDateWhatsApp);
		List<MensajeWhatsApp> chat = null;
		try {
			chat = SimpleTextParser.parse("/Users/fjavier/Downloads/chat-jav.txt", formatDateWhatsApp, Plataforma.ANDROID);
			for (MensajeWhatsApp mensaje : chat) {
				System.out.println(">" + mensaje.getFecha().format(formatter) +
									" " + mensaje.getAutor() + " : " + mensaje.getTexto());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull("El chat no se ha cargado", chat);
		assertTrue(true);
		System.out.println("** Size chat: " + chat.size());		
	}

}

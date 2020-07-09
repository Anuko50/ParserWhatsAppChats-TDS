package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import modelo.MensajeWhatsApp;
import modelo.Plataforma;

public class SimpleTextParser {

	// filePath - Ruta absoluta o relativa
	// (cualquier path v√°lido para FielReader
	public static List<MensajeWhatsApp> parse(String filePath, String formatDate, Plataforma plataforma) throws IOException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);

		FileReader input = new FileReader(filePath);
		BufferedReader bufRead = new BufferedReader(input);
		String line = null;

		LinkedList<MensajeWhatsApp> mensajes = new LinkedList<>();

		String fecha = null;
		String autor = null;
		String texto = null;
		List<String> lines = new LinkedList<>();

		while ((line = bufRead.readLine()) != null) {
			lines.add(line);
		}
		bufRead.close();
		int textNumber = 0;
		System.out.println("Reading texts");
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);

			switch (plataforma) {
			case ANDROID:
				if (line.indexOf("-") == -1)
					continue;
				fecha = line.substring(0, line.indexOf("-"));
				line = line.substring(line.indexOf("-") + 2);
				break;
			case IOS:
				fecha = line.substring(1, line.indexOf("]"));
				line = line.substring(line.indexOf("]") + 2);
				break;
			}
			LocalDateTime localDate = LocalDateTime.parse(fecha.trim(), formatter);

			if (line.indexOf(":") == -1)
				continue;
			autor = line.substring(0, line.indexOf(":"));
			texto = line.substring(line.indexOf(":") + 1);

			while (i + 1 < lines.size() && !nextLineIsDate(plataforma, formatter, lines.get(i + 1))) {
				i++;
				texto += " " + lines.get(i);
			}
			textNumber++;
			System.out.print(".");
			if (textNumber % 10 == 0)
				System.out.println("[" + textNumber + "]");
			mensajes.add(new MensajeWhatsApp(texto, autor, localDate));
		}
		System.out.println("Done!");

		return mensajes;
	}

	private static boolean nextLineIsDate(Plataforma plataforma, DateTimeFormatter formatter, String nextLine) {
		switch (plataforma) {
		case IOS: return nextLine.startsWith("[");
		case ANDROID:
			try {
				if (nextLine.indexOf("-") == -1) return false;
				LocalDateTime.parse(nextLine.substring(0, nextLine.indexOf("-")).trim(), formatter); 
			}
			catch (DateTimeParseException e) {
				return false;
			}
			return true; 
		}
		
		return false;
	}

	public static String getFormatoDate(String ruta) throws IOException {
		//me meto aquÌ para comprobar si es ANDROID 1 o 2.
		//"d/M/yy H:mm" o "d/M/yyyy H:mm" 
		FileReader input;
		input = new FileReader(ruta);
		
		@SuppressWarnings("resource")
		BufferedReader bufRead = new BufferedReader(input);
		//leo la primera linea
		//si es android 1 tendr· esta pinta: "21/10/19 19:44 - Pedro: Buenos dÌas, el componente ya est· listo. "
		//si es android 2 ser·:  "21/10/2019 19:44 - Pedro: Buenos dÌas, el componente ya est· listo. "
		String line = bufRead.readLine();
		//"d/M/yy H:mm" o "d/M/yyyy H:mm" 
		StringTokenizer tok = new StringTokenizer(line, " ");
		String fecha = tok.nextToken(); //tengo guardado aqui lo que me interesa, ahora la analizo para ver a que formato pertenece.
		
		if (fecha.matches("[0-9]{2}/[0-9]{2}/[0-9]{2}")) {
			return "d/M/yy H:mm";
		}else return "d/M/yyyy H:mm";
	}

}

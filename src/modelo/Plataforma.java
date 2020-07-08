package modelo;

public enum Plataforma {
	IOS, ANDROID;

	public String getFormatoDate() {
		String formato = "";
		switch (this.toString()) {
		case "IOS":
			formato = "d/M/yy H:mm:ss";
			return formato;
		case "ANDROID": 
			formato = "d/M/yyyy H:mm";
			return formato;
		}
		return formato;
	}
}

package juego.modelo;

/**
 * Clase que gestiona la configuracion de los jugadores
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public class Configuracion {

	private Celda conjunto[];

	/**
	 * Constructor de configuracion que le asocia el tamaño
	 * 
	 * @param tamaño
	 */
	public Configuracion(int tamaño) {
		conjunto = new Celda[tamaño];
	}

	/**
	 * Añade una celda a la configuracion
	 * 
	 * @param celda
	 */
	public void añadir(Celda celda) {
		for (int i = 0; i < conjunto.length; i++) {
			if (conjunto[i] == null) {
				conjunto[i] = celda;
				break;
			}
		}
	}

	public Celda[] consultar() {
		return conjunto;
	}

	/**
	 * Comprueba si la configuracion contiene una celda en concreto
	 * 
	 * @param celda
	 * @return contenida es true si esta, false si no
	 */
	public boolean contiene(Celda celda) {
		boolean contenida = false;
		for (int i = 0; i < conjunto.length; i++) {
			if (conjunto[i] == celda) {
				contenida = true;
			}
		}
		return contenida;
	}

	/**
	 * Dibuja la configuracion
	 */
	public String toString() {
		String configuracion = "";
		Celda celda;
		for (int i = 0; i < conjunto.length; i++) {
			if (conjunto[i] != null) {
				celda = conjunto[i];
				configuracion += "(" + celda.obtenerFila() + ","
						+ celda.obtenerColumna() + ")";
			}
		}
		return configuracion;
	}
}

package juego.modelo;

/**
 * Clase que gestiona una jugada en concreto
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public class Jugada {
	private Celda origen;
	private Celda destino;

	/**
	 * Constructor de jugada
	 * 
	 * @param origen
	 * @param destino
	 */
	public Jugada(Celda origen, Celda destino) {
		this.origen = origen;
		this.destino = destino;
	}

	public Celda consultarOrigen() {
		return this.origen;
	}

	public Celda consultarDestino() {
		return this.destino;
	}

	/**
	 * Dibuja una jugada
	 */
	public String toString() {
		return "[" + "(" + origen.obtenerFila() + "," + origen.obtenerColumna()
				+ ")" + "-" + "(" + destino.obtenerFila() + ","
				+ destino.obtenerColumna() + ")" + "]";
	}
}

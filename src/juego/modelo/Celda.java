package juego.modelo;

/**
 * Clase que crea las celdas del tablero
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public class Celda {
	/**
	 * Fila
	 */
	private int fila;
	/**
	 * Columna
	 */
	private int columna;
	/**
	 * Pieza
	 */
	private Pieza pieza;

	/**
	 * Construye una celda a partir de sus coordenadas.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Celda(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}

	public Pieza obtenerPieza() {
		return pieza;
	}

	public void establecerPieza(Pieza pieza) {
		this.pieza = pieza;
	}

	/**
	 * Consulta si la celda está vacía o no.
	 * 
	 * @return true o false
	 */
	public boolean estaVacia() {
		// boolean vacia = true;
		if (pieza == null) {
			return true;
		} else {
			return false;
		}
	}

	public int obtenerFila() {
		return fila;
	}

	public int obtenerColumna() {
		return columna;
	}

	/**
	 * Vacia una celda
	 */
	public void vaciar() {
		pieza = null;
	}

	/**
	 * Dibuja una celda en el tablero
	 */
	public String toString() {
		// String texto;
		if (estaVacia())
			return "-";
		else
			return obtenerPieza().toString();
		// return "(" + this.fila + "/" + this.columna + ")";
	}
}

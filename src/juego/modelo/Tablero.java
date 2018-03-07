package juego.modelo;

/**
 * Clase con todos los métodos correspondientes al tablero
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public class Tablero {
	private Celda[][] matriz;

	/**
	 * Constructor del tablero con una matriz de celdas
	 * 
	 * @param fila
	 * @param columna
	 */
	public Tablero(int fila, int columna) {
		matriz = new Celda[fila][columna];

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				matriz[i][j] = new Celda(i, j);
			}
		}
	}

	/**
	 * Coloca una pieza en una determinada celda mediante los métodos de colocar
	 * y establecerPieza
	 * 
	 * @param pieza
	 * @param celda
	 */
	public void colocar(Pieza pieza, Celda celda) {
		celda.establecerPieza(pieza);
		pieza.colocar(celda);
	}

	/**
	 * Mueve una pieza del origen al destino
	 * 
	 * @param jugada
	 */
	public void mover(Jugada jugada) {
		if (!jugada.consultarOrigen().estaVacia())
			jugada.consultarDestino().establecerPieza(
					jugada.consultarOrigen().obtenerPieza());
	}

	/**
	 * Devuelve la celda si esta en el tablero y null si no lo esta
	 * 
	 * @param fila
	 * @param columna
	 * @return la celda si esta, null si no
	 */
	public Celda obtenerCelda(int fila, int columna) {
		if (estaEnTablero(fila, columna))
			return matriz[fila][columna];
		else
			return null;
	}

	/**
	 * Comprueba que unas determiadas coordenadas (fila y columna) están en los
	 * límites del tablero
	 * 
	 * @param fila
	 * @param columna
	 * @return false si esas coordenadas no están en el tablero, true si sí que
	 *         están
	 */
	public boolean estaEnTablero(int fila, int columna) {
		if (fila >= 0 && fila < obtenerNumeroFilas() && columna >= 0
				&& columna < obtenerNumeroColumnas()) {
			return true;
		}
		return false;
	}

	public int obtenerNumeroFilas() {

		return matriz.length;
	}

	public int obtenerNumeroColumnas() {

		return matriz[0].length;
	}

	/**
	 * Dibuja el tablero con guiones en función del número de filas y columnas
	 */
	public String toString() {
		String texto = " ";
		int fila = obtenerNumeroFilas();
		int columna = obtenerNumeroColumnas();
		char caracter = 'a';
		int k = 0;

		for (int i = 0; i < fila; i++) {
			texto = texto + "\n" + i + "\t";
			for (int j = 0; j < columna; j++) {
				if (!obtenerCelda(i, j).estaVacia()) {
					texto += obtenerCelda(i, j).obtenerPieza().obtenerColor()
							.toChar();
				} else {
					texto += ("-");
				}
			}
		}
		texto += "\n";
		texto += "\n";
		texto += "\t";
		while (k < columna) {
			texto = texto + caracter;
			caracter++;
			k++;
		}
		return texto;
	}

	/**
	 * Devuelve un array con las celdas donde se encuentran los electrones
	 * 
	 * @param color
	 * @return array de celdas con los electrones
	 */
	public Celda[] obtenerCeldasElectron(Color color) {
		Celda electrones[] = new Celda[matriz.length];
		int k = 0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if(!matriz[i][j].estaVacia()){
					if (matriz[i][j].obtenerPieza().obtenerColor() == color) {
						electrones[k] = matriz[i][j];
						if (k < matriz.length - 1)
							k++;
					}
				}
			}
		}
		return electrones;
	}

	/**
	 * Devuelve la celda donde se encuentra el neutron
	 * 
	 * @return la celda con el neutron
	 */
	public Celda obtenerCeldaNeutron() {
		Celda celda = null;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if(!matriz[i][j].estaVacia()){
					if (matriz[i][j].obtenerPieza().esNeutron())
						celda = matriz[i][j];
				}
			}
		}
		return celda;
	}
}

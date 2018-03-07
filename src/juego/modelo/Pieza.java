package juego.modelo;

/**
 * Clase que crea las piezas y las asocia a celdas
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public class Pieza {
	private Color color;

	private Celda celda;

	/**
	 * Constructo de pieza que le asigna un color
	 * 
	 * @param color
	 */
	public Pieza(Color color) {
		this.color = color;
	}

	public Color obtenerColor() {
		return color;
	}

	/**
	 * Devuelve el caracter asociado al color
	 */
	public String toString() {
		return "" + color.toChar();
	}

	/**
	 * Coloca una celda
	 * 
	 * @param celda
	 */
	public void colocar(Celda celda) {
		this.celda = celda;
	}

	public Celda obtenerCelda() {
		return celda;
	}

	/**
	 * Comprueba si la pieza es el neutron mediante su color
	 * 
	 * @return true si lo es, false si no
	 */
	public boolean esNeutron() {
		if (obtenerColor() == Color.AMARILLO)
			return true;
		else
			return false;
	}
}

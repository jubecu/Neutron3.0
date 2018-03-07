package juego.modelo;

/**
 * Tipo enumerado que contiene los colores de las piezas
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public enum Color {
	AZUL('X'), ROJO('O'), AMARILLO('N');
	private char caracter;

	/**
	 * Constructor del color que asocia el caracter correspondiente al color
	 * 
	 * @param c
	 */
	private Color(char c) {
		this.caracter = c;
	}

	/**
	 * Devuelve el caracter del color
	 * 
	 * @return caracter del color
	 */
	public char toChar() {
		return caracter;
	}
}

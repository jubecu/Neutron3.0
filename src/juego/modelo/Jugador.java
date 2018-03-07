package juego.modelo;

/**
 * Jugador de una partida de Neutrón. Tendremos dos jugadores.
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public class Jugador {
	/**
	 * Nombre
	 */
	private String nombre;
	/**
	 * Color
	 */
	private Color color;
	private Configuracion configuracion;

	/**
	 * Inicializa el jugador de la partida.
	 * 
	 * @param nombre
	 *            nombre
	 * @param color
	 *            color
	 */
	public Jugador(String nombre, Color color) {
		this.nombre = nombre;
		this.color = color;
		// configuracion=new Configuracion();
	}

	/**
	 * Obtiene el color asignado al jugador.
	 * 
	 * @return color actual
	 */
	public Color obtenerColor() {
		return this.color;
	}

	/**
	 * Obtiene el nombre
	 * 
	 * @return nombre actual
	 */
	public String consultarNombre() {
		return this.nombre;
	}

	/**
	 * Genera una nueva pieza del color actual asignado.
	 * 
	 * @return nueva pieza con el color actual
	 */
	public Pieza generarPieza() {
		Pieza pieza = new Pieza(color);
		return pieza;
	}
	
	public Configuracion consultarConfiguracion() {
		return this.configuracion;
	}

	public void establecerConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * Devuelve las caracteristicas de un jugador
	 */
	public String toString() {
		return consultarNombre() + ","
				+ " su color es el " + obtenerColor()
				+ " y su configuración es " + consultarConfiguracion();
	}
}

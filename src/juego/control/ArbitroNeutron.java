package juego.control;

import juego.modelo.*;

import juego.util.Sentido;

/**
 * Clase que regula las normas y restricciones del juego.
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 */
public class ArbitroNeutron {
	/**
	 * tablero de la partida.
	 */
	private Tablero tablero;
	/**
	 * los dos jugadores de la partida.
	 */
	private Jugador jugador1, jugador2;
	/**
	 * turno de la partida.
	 */
	private Turno turno;
	/**
	 * la configuracion de cada uno de los jugadores.
	 */
	private Configuracion configuracion1, configuracion2;
	/**
	 * contador del numero de turno.
	 */
	private int numTurno=1;

	/**
	 * Contructor del árbitro que realiza una serie de pasos para iniciar el
	 * juego.
	 * 
	 * @param tablero
	 *            tablero del juego
	 * @param nombreAzules
	 *            nombre del jugador que tiene las piezas de color azul
	 * @param nombreRojas
	 *            nombre del jugador que tiene las piezas de color rojo
	 */
	public ArbitroNeutron(Tablero tablero, String nombreAzules,
			String nombreRojas) {
		this.tablero = tablero;
		int numeroColumnas = tablero.obtenerNumeroColumnas();
		int numeroFilas = tablero.obtenerNumeroFilas() - 1;
		configuracion1 = new Configuracion(numeroColumnas);
		configuracion2 = new Configuracion(numeroColumnas);
		turno = new Turno(nombreAzules, nombreRojas);
		jugador1 = turno.obtenerJugadorSinTurno();
		jugador2 = turno.obtenerJugadorConTurno();
		rellenarConfiguracion(tablero, configuracion1, 0, jugador1);
		rellenarConfiguracion(tablero, configuracion2, numeroFilas, jugador2);
		colocarNeutronCentro(tablero, numeroFilas, numeroColumnas);
	}

	/**
	 * Obtiene el turno actual de la partida.
	 * 
	 * @return turno actual
	 */
	public Turno consultarTurno() {
		return turno;
	}

	/**
	 * Mueve una pieza de un sitio a otro en función de una jugada y cambia el
	 * movimiento.
	 * 
	 * @param jugada
	 *            la jugada que vamos a realizar
	 */
	public void jugar(Jugada jugada) {
		consultarTablero().mover(jugada);
		jugada.consultarOrigen().vaciar();
		if (consultarTurno().estaMoviendoNeutron())
			consultarTurno().cambiarMovimientoDeNeutronAElectron();
		else{
			consultarTurno().cambiarTurno();
			numTurno++;
		}
	}

	/**
	 * Devuelve el tablero en un momento dado de la partida.
	 * 
	 * @return tablero actual
	 */
	public Tablero consultarTablero() {
		return tablero;
	}

	/**
	 * Te dice si la partida esta acabada.
	 * 
	 * @return true si lo esta, false si no lo esta
	 */
	public boolean estaAcabado() {
		if (consultarGanador() != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Devuelve el ganador de la partida.
	 * 
	 * @return el jugador ganador
	 */
	public Jugador consultarGanador() {
		if (configuracion1.contiene(tablero.obtenerCeldaNeutron())) {
			return jugador1;
		} else if (configuracion2.contiene(tablero.obtenerCeldaNeutron())) {
			return jugador2;
		} else {
			return null;
		}
	}

	/**
	 * Te dice si una jugada se puede hacer o no.
	 * 
	 * @param jugada
	 *            la jugada que vamos a comprobar
	 * @return true si es legal, false si no
	 */
	public boolean esMovimientoLegal(Jugada jugada) {
		if (turnoCorrecto(jugada) && jugada.consultarDestino().estaVacia()
				&& movimientoCorrecto(jugada, obtenerSentido(jugada))
				&& noMoverseMasAlla(obtenerSentido(jugada), jugada)
				&& noHayPiezaEnMedio(obtenerSentido(jugada), jugada))
			return true;
		else
			return false;
	}
	
	/**
	 * Devuelve el numero del turno actual.
	 * 
	 * @return turno actual
	 */
	public int obtenerContador(){
		return this.numTurno;
	}

	/**
	 * Determina si un movimiento es correcto.
	 * 
	 * @param jugada
	 *            jugada que analizamos para comprobar si es correcta
	 * @param sentido
	 *            sentido de la jugada
	 * @return movimientoCorrecto true si sí que lo es, false si no
	 */
	private boolean movimientoCorrecto(Jugada jugada, Sentido sentido) {
		boolean movimientoCorrecto = false;
		int fd = jugada.consultarDestino().obtenerFila();
		int cd = jugada.consultarDestino().obtenerColumna();
		int fo = jugada.consultarOrigen().obtenerFila();
		int co = jugada.consultarOrigen().obtenerColumna();
		if (sentido == Sentido.ABAJO || sentido == Sentido.ARRIBA) {
			if (cd - co == 0)
				movimientoCorrecto = true;
		}
		if (sentido == Sentido.DERECHA || sentido == Sentido.IZQUIERDA) {
			if (fd - fo == 0)
				movimientoCorrecto = true;
		}
		if (sentido == Sentido.DIAGONAL_NO_SE_ABAJO
				|| sentido == Sentido.DIAGONAL_NO_SE_ARRIBA
				|| sentido == Sentido.DIAGONAL_SO_NE_ABAJO
				|| sentido == Sentido.DIAGONAL_SO_NE_ARRIBA) {
			if (Math.abs(cd - co) == Math.abs(fd - fo))
				movimientoCorrecto = true;
		}
		return movimientoCorrecto;
	}

	/**
	 * Rellena la configuracion inicial de un jugador.
	 * 
	 * @param tablero
	 *            tablero de la partida
	 * @param configuracion
	 *            configuracion a rellenar
	 * @param fila
	 *            fila del tablero donde se encuentra la configuracion
	 * @param jugador
	 *            jugador al que asignamos la configuracion rellenada
	 */
	private void rellenarConfiguracion(Tablero tablero,
			Configuracion configuracion, int fila, Jugador jugador) {

		for (int i = 0; i < configuracion.consultar().length; i++) {
			Pieza pieza = jugador.generarPieza();
			Celda celda = tablero.obtenerCelda(fila, i);
			tablero.colocar(pieza, celda);
			configuracion.añadir(celda);
			jugador.establecerConfiguracion(configuracion);
		}
	}

	/**
	 * Coloca el neutron en el centro del tablero.
	 * 
	 * @param tablero
	 *            tablero de la partida
	 * @param numeroFilas
	 *            numero de filas del tablero
	 * @param numeroColumnas
	 *            numero de columnas del tablero
	 */
	private void colocarNeutronCentro(Tablero tablero, int numeroFilas,
			int numeroColumnas) {
		Celda celda = tablero.obtenerCelda(numeroFilas / 2, numeroColumnas / 2);
		Pieza pieza = new Pieza(Color.AMARILLO);
		tablero.colocar(pieza, celda);
	}

	/**
	 * Te dice si un turno es correcto en funcion del jugador que le toque y del
	 * movimiento que este haciendo.
	 * 
	 * @param jugada
	 *            jugada que analizamos para comprobar si el turno es correcto
	 * @return true si lo es, false si no
	 */
	private boolean turnoCorrecto(Jugada jugada) {
		if (consultarTurno().obtenerJugadorConTurno() == jugador1
				&& consultarTurno().estaMoviendoElectron()
				&& jugada.consultarOrigen().obtenerPieza().obtenerColor() == Color.AZUL) {
			return true;
		} else if (consultarTurno().obtenerJugadorConTurno() == jugador1
				&& consultarTurno().estaMoviendoNeutron()
				&& jugada.consultarOrigen().obtenerPieza().obtenerColor() == Color.AMARILLO) {
			return true;
		} else if (consultarTurno().obtenerJugadorConTurno() == jugador2
				&& consultarTurno().estaMoviendoElectron()
				&& jugada.consultarOrigen().obtenerPieza().obtenerColor() == Color.ROJO) {
			return true;
		} else if (consultarTurno().obtenerJugadorConTurno() == jugador2
				&& consultarTurno().estaMoviendoNeutron()
				&& jugada.consultarOrigen().obtenerPieza().obtenerColor() == Color.AMARILLO) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Te dice si no hay ninguna pieza en el camino de una jugada.
	 * 
	 * @param sentido
	 *            sentido de la jugada
	 * @param jugada
	 *            jugada a analizar
	 * @return true si no hay ninguna pieza, false si la hay
	 */
	private boolean noHayPiezaEnMedio(Sentido sentido, Jugada jugada) {
		boolean noHayPieza = true;
		if (sentido == Sentido.ABAJO) {
			for (int i = jugada.consultarDestino().obtenerFila(); i > jugada
					.consultarOrigen().obtenerFila(); i--) {
				if (!consultarTablero().obtenerCelda(i,
						jugada.consultarOrigen().obtenerColumna()).estaVacia()) {
					noHayPieza = false;
				}
			}
		} else if (sentido == Sentido.ARRIBA) {
			for (int i = jugada.consultarDestino().obtenerFila(); i < jugada
					.consultarOrigen().obtenerFila(); i++) {
				if (!consultarTablero().obtenerCelda(i,
						jugada.consultarOrigen().obtenerColumna()).estaVacia()) {
					noHayPieza = false;
				}
			}
		} else if (sentido == Sentido.DERECHA) {
			for (int i = jugada.consultarDestino().obtenerColumna(); i > jugada
					.consultarOrigen().obtenerColumna(); i--) {
				if (!consultarTablero().obtenerCelda(
						jugada.consultarOrigen().obtenerFila(), i).estaVacia()) {
					noHayPieza = false;
				}
			}
		} else if (sentido == Sentido.IZQUIERDA) {
			for (int i = jugada.consultarDestino().obtenerColumna(); i < jugada
					.consultarOrigen().obtenerColumna(); i++) {
				if (!consultarTablero().obtenerCelda(
						jugada.consultarOrigen().obtenerFila(), i).estaVacia()) {
					noHayPieza = false;
				}
			}

		} else if (sentido == Sentido.DIAGONAL_SO_NE_ARRIBA) {
			int i = jugada.consultarOrigen().obtenerFila();
			int j = jugada.consultarOrigen().obtenerColumna();
			while (consultarTablero().obtenerCelda(i, j) != jugada
					.consultarDestino()
					&& noHayPieza == true
					&& consultarTablero().estaEnTablero(i - 1, j + 1)) {
				i--;
				j++;
				if (!consultarTablero().obtenerCelda(i, j).estaVacia())
					noHayPieza = false;
			}

		} else if (sentido == Sentido.DIAGONAL_NO_SE_ARRIBA) {
			int i = jugada.consultarOrigen().obtenerFila();
			int j = jugada.consultarOrigen().obtenerColumna();
			while (consultarTablero().obtenerCelda(i, j) != jugada
					.consultarDestino()
					&& noHayPieza == true
					&& consultarTablero().estaEnTablero(i - 1, j - 1)) {
				i--;
				j--;
				if (!consultarTablero().obtenerCelda(i, j).estaVacia())
					noHayPieza = false;
			}
		} else if (sentido == Sentido.DIAGONAL_SO_NE_ABAJO) {
			int i = jugada.consultarOrigen().obtenerFila();
			int j = jugada.consultarOrigen().obtenerColumna();
			while (consultarTablero().obtenerCelda(i, j) != jugada
					.consultarDestino()
					&& noHayPieza == true
					&& consultarTablero().estaEnTablero(i + 1, j - 1)) {
				i++;
				j--;
				if (!consultarTablero().obtenerCelda(i, j).estaVacia())
					noHayPieza = false;
			}
		} else if (sentido == Sentido.DIAGONAL_NO_SE_ABAJO) {
			int i = jugada.consultarOrigen().obtenerFila();
			int j = jugada.consultarOrigen().obtenerColumna();
			while (consultarTablero().obtenerCelda(i, j) != jugada
					.consultarDestino()
					&& noHayPieza == true
					&& consultarTablero().estaEnTablero(i + 1, j + 1)) {
				i++;
				j++;
				if (!consultarTablero().obtenerCelda(i, j).estaVacia())
					noHayPieza = false;
			}
		}
		return noHayPieza;
	}

	/**
	 * Comprueba que la siguiente celda del destino o no este en tablero o este
	 * ocupada.
	 * 
	 * @param sentido
	 *            sentido de la jugada
	 * @param jugada
	 *            jugada a analizar
	 * @return true si no puedes moverte mas, false si si que puedes
	 */
	private boolean noMoverseMasAlla(Sentido sentido, Jugada jugada) {
		boolean noMoverse = false;
		int i = jugada.consultarDestino().obtenerFila();
		int j = jugada.consultarDestino().obtenerColumna();
		if (sentido == Sentido.ABAJO) {
			if (!consultarTablero().estaEnTablero(i + 1, j)
					|| !consultarTablero().obtenerCelda(i + 1, j).estaVacia())
				noMoverse = true;
		} else if (sentido == Sentido.ARRIBA) {
			if (!consultarTablero().estaEnTablero(i - 1, j)
					|| !consultarTablero().obtenerCelda(i - 1, j).estaVacia())
				noMoverse = true;
		} else if (sentido == Sentido.DERECHA) {
			if (!consultarTablero().estaEnTablero(i, j + 1)
					|| !consultarTablero().obtenerCelda(i, j + 1).estaVacia())
				noMoverse = true;
		} else if (sentido == Sentido.IZQUIERDA) {
			if (!consultarTablero().estaEnTablero(i, j - 1)
					|| !consultarTablero().obtenerCelda(i, j - 1).estaVacia())
				noMoverse = true;
		} else if (sentido == Sentido.DIAGONAL_SO_NE_ARRIBA) {
			if (!consultarTablero().estaEnTablero(i - 1, j + 1)
					|| !consultarTablero().obtenerCelda(i - 1, j + 1)
							.estaVacia())
				noMoverse = true;
		} else if (sentido == Sentido.DIAGONAL_NO_SE_ARRIBA) {
			if (!consultarTablero().estaEnTablero(i - 1, j - 1)
					|| !consultarTablero().obtenerCelda(i - 1, j - 1)
							.estaVacia())
				noMoverse = true;
		} else if (sentido == Sentido.DIAGONAL_SO_NE_ABAJO) {
			if (!consultarTablero().estaEnTablero(i + 1, j - 1)
					|| !consultarTablero().obtenerCelda(i + 1, j - 1)
							.estaVacia())
				noMoverse = true;
		} else if (sentido == Sentido.DIAGONAL_NO_SE_ABAJO) {
			if (!consultarTablero().estaEnTablero(i + 1, j + 1)
					|| !consultarTablero().obtenerCelda(i + 1, j + 1)
							.estaVacia())
				noMoverse = true;
		}
		return noMoverse;
	}

	/**
	 * Obtiene el sentido de una jugada comparando las coordenadas del destino
	 * con las del origen.
	 * 
	 * @param jugada
	 *            jugada de la que obtenemos el sentido segun su origen y
	 *            destino
	 * @return el sentido de la jugada
	 */
	private Sentido obtenerSentido(Jugada jugada) {
		if (jugada.consultarOrigen().obtenerFila() == jugada.consultarDestino()
				.obtenerFila()) {
			if (jugada.consultarOrigen().obtenerColumna() > jugada
					.consultarDestino().obtenerColumna()) {
				return Sentido.IZQUIERDA;
			} else {
				return Sentido.DERECHA;
			}
		} else if (jugada.consultarOrigen().obtenerColumna() == jugada
				.consultarDestino().obtenerColumna()) {
			if (jugada.consultarOrigen().obtenerFila() > jugada
					.consultarDestino().obtenerFila()) {
				return Sentido.ARRIBA;
			} else {
				return Sentido.ABAJO;
			}
		} else if (jugada.consultarDestino().obtenerFila() > jugada
				.consultarOrigen().obtenerFila()) {
			if (jugada.consultarDestino().obtenerColumna() < jugada
					.consultarOrigen().obtenerColumna()) {
				return Sentido.DIAGONAL_SO_NE_ABAJO;
			} else {
				return Sentido.DIAGONAL_NO_SE_ABAJO;
			}
		} else if (jugada.consultarDestino().obtenerFila() < jugada
				.consultarOrigen().obtenerFila()) {
			if (jugada.consultarDestino().obtenerColumna() < jugada
					.consultarOrigen().obtenerColumna()) {
				return Sentido.DIAGONAL_NO_SE_ARRIBA;
			} else {
				return Sentido.DIAGONAL_SO_NE_ARRIBA;
			}
		} else {
			return null;
		}
	}
}

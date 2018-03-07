package juego.control;

import juego.modelo.Color;

import juego.modelo.Jugador;

/**
 * Clase que gestiona cambios de turno y movimiento
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/11/13
 * 
 */
public class Turno {
	// private String nombre1;
	// private String nombre2;

	private Jugador jugador1;
	private Jugador jugador2;

	private boolean turnoJug1 = false;

	private static final int ME = 1;
	private static final int MN = 0;

	private int movimiento = ME;

	/**
	 * Contructor de turno que crea los jugadores de la partida con su nombre
	 * 
	 * @param nombreAzules
	 * @param nombreRojas
	 */
	public Turno(String nombreAzules, String nombreRojas) {
		jugador1 = new Jugador(nombreAzules, Color.AZUL);
		jugador2 = new Jugador(nombreRojas, Color.ROJO);
		// this.nombre1 = nombreAzules;
		// this.nombre2 = nombreRojas;
	}

	/**
	 * Cambia el turno de un jugador a otro
	 */
	public void cambiarTurno() {
		if (obtenerJugadorConTurno() == jugador1) {
			turnoJug1 = false;
			movimiento = MN;
		} else {
			turnoJug1 = true;
			movimiento = MN;
		}
	}

	/**
	 * Devuelve el jugador con turno
	 * 
	 * @return jugador con turno
	 */
	public Jugador obtenerJugadorConTurno() {
		if (turnoJug1)
			return jugador1;
		else
			return jugador2;
	}

	/**
	 * Devuelve el jugador sin turno
	 * 
	 * @return jugador sin turno
	 */
	public Jugador obtenerJugadorSinTurno() {
		if (turnoJug1)
			return jugador2;
		else
			return jugador1;
	}

	/**
	 * Comprueba si se esta moviendo el neutron
	 * 
	 * @return true si se esta moviendo, false si no
	 */
	public boolean estaMoviendoNeutron() {
		if (movimiento == MN) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Comprueba si se esta moviendo el electron
	 * 
	 * @return true si se esta moviendo, false si no
	 */
	public boolean estaMoviendoElectron() {
		if (movimiento == ME) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Cambia el movimiento del turno de neutron a electron
	 */
	public void cambiarMovimientoDeNeutronAElectron() {
		if (movimiento == MN) {
			movimiento = ME;
		}
	}
}

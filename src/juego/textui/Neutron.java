package juego.textui;

import juego.modelo.*;
import juego.util.ConversorJugada;
import juego.control.*;

import java.util.Scanner;

/**
 * 
 * Clase con el main donde crea los objetos necesarios para poder jugar.
 * 
 * @author Alberto Uriarte Martínez y Juan Francisco Benito Cuesta
 * @since 1.0
 * @version 2015/12/21
 * 
 */
public class Neutron {
	private static Tablero tablero;
	private static ArbitroNeutron arbitro;

	/**
	 * Main donde primero comprueba los argumentos que le pasan y despues
	 * arranca la partida.
	 * 
	 * @param args
	 *            Array de tipo string donde le pasamos los argumentos con los
	 *            que queremos jugar
	 */
	public static void main(String[] args) {
		boolean correcto=true;
		if (args.length == 0) {
			tablero = new Tablero(5, 5);
			arbitro = new ArbitroNeutron(tablero, "Azules", "Rojas");
		} else if (args.length == 3 && Integer.parseInt(args[2]) >= 5
				&& Integer.parseInt(args[2]) <= 9
				&& (Integer.parseInt(args[2]) % 2) != 0) {
			tablero = new Tablero(Integer.parseInt(args[2]),
					Integer.parseInt(args[2]));
			arbitro = new ArbitroNeutron(tablero, args[0], args[1]);
		} else {
			System.out.println("Error en los argumentos pasados");
			System.out
					.println("Introducir en primer lugar el nombre del primer jugador");
			System.out.println("Después el nombre del segundo jugador");
			System.out
					.println("Por último un número de filas/columnas que sea impar,"
							+ " mayor o igual a 5 y menor o igual a 9");
			System.out.println("O bien no introducir ningún parámetro.");
		}

		if (args.length == 0
				|| (args.length == 3 && Integer.parseInt(args[2]) >= 5
						&& Integer.parseInt(args[2]) <= 9 && (Integer
						.parseInt(args[2]) % 2) != 0)) {
			Scanner sc = new Scanner(System.in);
			do {
				System.out.println(arbitro.consultarTablero().toString());
				System.out.println();
				System.out.println("El turno es de: "
						+ arbitro.consultarTurno().obtenerJugadorConTurno().consultarNombre()
						+ " con fichas "
						+ arbitro.consultarTurno().obtenerJugadorConTurno().obtenerColor()
								.toChar() + " de color "
						+ arbitro.consultarTurno().obtenerJugadorConTurno().obtenerColor());
				System.out.println("Turno número: " + arbitro.obtenerContador());
				if (arbitro.consultarTurno().estaMoviendoElectron())
					System.out.println("Debe mover un electrón");
				else
					System.out.println("Debe mover un neutrón");
				
				do{
					System.out.println("Introduce jugada:");
					
					String textoJugada = sc.nextLine();
					if(arbitro.esMovimientoLegal(ConversorJugada.convertir(textoJugada, tablero))){
						arbitro.jugar(ConversorJugada.convertir(textoJugada, tablero));
						correcto=true;
					}else{
						System.out.println("La jugada no es correcta.");
						correcto=false;
					}
				} while (correcto==false);
			} while (!arbitro.estaAcabado());
			sc.close();
			System.out.println();
			System.out.println("Enhorabuena ha ganado el jugador "
					+ arbitro.consultarGanador());
		}
	}
}

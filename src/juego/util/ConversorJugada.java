package juego.util;

import java.util.Scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juego.modelo.Celda;
import juego.modelo.Jugada;
import juego.modelo.Tablero;

import org.slf4j.Logger;

/**
 * Jugada introducida por el usuario. Agradecimientos a César I. García Osorio
 * por la revisión del uso de expresiones regulares y a Carlos Pardo por la
 * simplificación del código en el tratamiento de números y letras.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:rredondo@ubu.es">Raquel Redondo</a>
 * @version 1.0 20151003
 */
public class ConversorJugada {

	/** Logger. */
	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(ConversorJugada.class);

	/**
	 * Valida la corrección de la jugada.
	 * 
	 * @param textoJugada
	 *            texto a validar
	 * @param tablero
	 *            tablero con las figuras colocadas
	 * @return jugada con la lista de celdas ordenadas o null si la entrada no
	 *         puede ser convertida
	 */
	public static Jugada convertir(String textoJugada, Tablero tablero) {
		Scanner scanner = null;
		Jugada jugada = null;
		try {
			Celda origen = null, destino = null;
			// Suponemos que el tablero es siempre cuadrado...
			if (validar(textoJugada, tablero.obtenerNumeroFilas())) {  
				// sintáctica y semánticamente correcta
				scanner = new Scanner(textoJugada).useDelimiter("-");
				while (scanner.hasNext()) {
					String coordenadas = scanner.next();
					Celda celda = traducirACelda(coordenadas, tablero);
					logger.debug("Añadiendo:" + celda.toString());
					if (origen == null) {
						origen = celda;
					}
					else {
						destino = celda;
					}
				}
				jugada = new Jugada(origen, destino);
			}
			return jugada;
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	} // validar

	
	/**
	 * Valida la sintaxis y semántica de la jugada en función
	 * del tamaño del tablero.
	 * 
	 * @param textoJugada texto
	 * @param tamaño tamaño
	 * @return true si es válida y false en caso contrario
	 */
	public static boolean validar(String textoJugada, int tamaño) {

		final char LIM_NUM_SUP = calcularSuperior('0', tamaño);
		final char LIM_CAR_SUP = calcularSuperior('a', tamaño);

		/** Patrón de texto a utilizar. */
		final Pattern PATRON = Pattern.compile("^[0-" + LIM_NUM_SUP + "][a-"
				+ LIM_CAR_SUP + "](\\-[0-" + LIM_NUM_SUP + "][a-" + LIM_CAR_SUP
				+ "])$");

		Matcher matcher = PATRON.matcher(textoJugada);

		return matcher.matches();
	}

	/**
	 * Calcula el valor límite a introducir.
	 * 
	 * @param letra
	 *            inicio
	 * @param tamaño
	 *            tamaño
	 * @return límite superior
	 */
	private static char calcularSuperior(char letra, int tamaño) {
		final char LIMSUP = (char) (letra + tamaño - 1);
		return LIMSUP;
	}

	/**
	 * Convierte una coordenadas en texto a su correspondiente celda.
	 * 
	 * @param coordenadas
	 *            coordenadas en texto Ej: 0a
	 * @param tablero
	 *            tablero
	 * @return celda correspondiente a las coordenadas
	 */
	private static Celda traducirACelda(String coordenadas, Tablero tablero) {
		int x = coordenadas.charAt(0) - '0';
		int y = coordenadas.charAt(1) - 'a';
		logger.debug("Coordenadas obtenidas: " + x + "/" + y);
		return tablero.obtenerCelda(x, y);
	}
} // ConversorJugada

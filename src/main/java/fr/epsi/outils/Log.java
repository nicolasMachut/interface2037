package fr.epsi.outils;

import java.util.Date;

public class Log {

	/**
	 * <p>Ecris le log sur la sortie standard avec la date</p>
	 * @param message
	 */
	public static void ecris (String message) {
		System.out.println(new Date() + " - " + message);
	}
	
}

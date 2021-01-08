package ch.avalgan.job.utils;

/**
 * Classe des méthodes répétées souvent, et des petits utilitaires
 * 
 * @author __Wither
 * @since 0.0.1-SNAPSHOT
 */
public class Commons {
	/**
	 * Enumération des codes couleurs et formattages AINSI, simplifiant l'écriture
	 * en couleur dans la console.
	 * 
	 * @author __Wither
	 * @since 0.0.1-SNAPSHOT
	 */
	public static enum AinsiColors {
		NONE("\u001b[0m"), RED("\u001b[31m"), BLACK("\u001b[30m"), GREEN("\u001b[32m"), GOLD("\u001b[33m"),
		BLUE("\u001b[34m"), PURPLE("\u001b[35m"), CYAN("\u001b[36m"), LIGHT_GRAY("\u001b[37m"), GRAY("\u001b[30;1m"),
		LIGHT_RED("\u001b[31;1m"), LIME("\u001b[32;1m"), YELLOW("\u001b[33;1m"), LIGHT_BLUE("\u001b[34;1m"),
		LIGHT_PURPLE("\u001b[35;1m"), AQUA("\u001b[36;1m"), WHITE("\u001b[37;1m"), BOLD("\u001b[1m"),
		UNDERLINED("\u001b[4m");

		private String code;

		private AinsiColors(String code) {
			this.code = code;
		}

		public String toString() {
			return this.code;
		}
	}
}

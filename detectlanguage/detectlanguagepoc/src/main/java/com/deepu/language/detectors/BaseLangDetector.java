
package com.deepu.language.detectors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import me.champeau.ld.AbstractGramTree;

/**
 * Wraps n-gram trees to detect language. Queries the registered gram trees, and
 * returns the language with the one which returns the best score
 *
 */
public class BaseLangDetector {

	private static final Log theLogger = LogFactory.getLog(BaseLangDetector.class);

	private Map<String, AbstractGramTree> statsMap = new HashMap<String, AbstractGramTree>();

	public BaseLangDetector() {
	}

	/**
	 * Creates a language detector using the same language profiles as the
	 * provided detector.
	 * 
	 * @param other
	 *            the detector from which copy resources from.
	 */
	protected BaseLangDetector(BaseLangDetector other) {
		for (Map.Entry<String, AbstractGramTree> entry : other.statsMap.entrySet()) {
			statsMap.put(entry.getKey(), entry.getValue());
		}
	}

	public void register(String lang, ObjectInputStream in) {
		try {
			statsMap.put(lang, (AbstractGramTree) in.readObject());
			in.close();
		} catch (IOException e) {
			theLogger.error("Exception occured " + e.getMessage());
		} catch (ClassNotFoundException e) {
			theLogger.error("Exception occured " + e.getMessage());
		}
	}

	public void register(String lang, AbstractGramTree tree) {
		statsMap.put(lang, tree);
	}

	/**
	 * Performs a language detection the whole set of possible languages.
	 * 
	 * @param aText
	 * @return
	 */
	public String detectLanguage(CharSequence aText) {
		return detectLanguage(aText, statsMap.keySet());
	}

	/**
	 * 
	 * Performs a language detection to the set of provided languages
	 * 
	 * @param aText
	 * @param languageRestrictions
	 * @return
	 */
	public String detectLanguage(CharSequence aText, Set<String> languageRestrictions) {
		String bestLang = null;
		if (aText != null && languageRestrictions != null && !languageRestrictions.isEmpty()) {
			double best = 0;
			for (Map.Entry<String, AbstractGramTree> entry : statsMap.entrySet()) {
				final String currentLanguage = entry.getKey();
				if (languageRestrictions.contains(currentLanguage)) {
					double score = entry.getValue().scoreText(aText);
					if (score > best) {
						best = score;
						bestLang = currentLanguage;
					}
				}
			}
		}
		return bestLang;
	}
}

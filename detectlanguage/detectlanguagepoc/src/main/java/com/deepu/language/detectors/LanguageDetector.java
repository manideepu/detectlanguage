
package com.deepu.language.detectors;

import java.io.BufferedInputStream;
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
 * 
 * A language detector which includes
 * <ul>
 * <li>Bulgarian</li>
 * <li>Czech</li>
 * <li>Danish</li>
 * <li>German</li>
 * <li>Greek</li>
 * <li>English</li>
 * <li>Spanish</li>
 * <li>Estonian</li>
 * <li>Finnish</li>
 * <li>French</li>
 * <li>Hungarian</li>
 * <li>Italian</li>
 * <li>Lithuanian</li>
 * <li>Latvian</li>
 * <li>Dutch</li>
 * <li>Polish</li>
 * <li>Portuguese</li>
 * <li>Romanian</li>
 * <li>Slovak</li>
 * <li>Slovene</li>
 * <li>Swedish</li>
 * <li>Russian</li>
 * <li>Chinese</li>
 * <li>Japanese</li>
 * <li>Korean</li>
 * </ul>
 * 
 *
 */
public class LanguageDetector {
	private static final Log LOG = LogFactory.getLog(LanguageDetector.class);
	private Map<String, AbstractGramTree> statsMap = new HashMap<String, AbstractGramTree>();
	private final static LanguageDetector INSTANCE = new LanguageDetector();

	public static LanguageDetector getInstance() {
		return INSTANCE;
	}

	/**
	 * Load all the available binary files to map with language name as key and
	 * binary file as value
	 */
	private LanguageDetector() {
		ClassLoader loader = LanguageDetector.class.getClassLoader();
		ObjectInputStream in = null;
		for (LanguageEnum lang : LanguageEnum.values()) {
			try {
				in = new ObjectInputStream(
						new BufferedInputStream(loader.getResourceAsStream("detect/" + lang.name() + "_tree.bin")));
				statsMap.put(lang.name(), (AbstractGramTree) in.readObject());
				in.close();
			} catch (IOException e) {
				LOG.error("Unable to read resources for language " + lang);
			} catch (ClassNotFoundException e) {
				LOG.error("ClassNotFoundException occured " + e.getMessage());
			}
		}
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


package com.deepu.language.detectors;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
	 * @param fileContent
	 * @return
	 */
	public String detectLanguage(CharSequence fileContent) {
		return detectLanguage(fileContent, statsMap.keySet());
	}

	/**
	 * 
	 * Performs a language detection to the set of provided languages
	 * 
	 * @param fileContent
	 * @param languageSet
	 * @return
	 */
	public String detectLanguage(CharSequence fileContent, Set<String> languageSet) {
		String bestMatchedLanguage = null;
		if (StringUtils.isNotBlank(fileContent) && languageSet != null && !languageSet.isEmpty()) {
			double best = 0;
			for (Map.Entry<String, AbstractGramTree> entry : statsMap.entrySet()) {
				final String currentLanguage = entry.getKey();
				if (languageSet.contains(currentLanguage)) {
					double score = entry.getValue().scoreText(fileContent);
					if (score > best) {
						best = score;
						bestMatchedLanguage = currentLanguage;
					}
				}
			}
		}
		return bestMatchedLanguage;
	}
}

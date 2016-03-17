
package com.deepu.language.detectors;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
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
public class MyLanguageDetector extends BaseLangDetector {

	private static final Log LOG = LogFactory.getLog(MyLanguageDetector.class);

	private final static MyLanguageDetector INSTANCE = new MyLanguageDetector();

	public MyLanguageDetector() {
		super();
		ClassLoader loader = MyLanguageDetector.class.getClassLoader();
		for (LanguageEnum lang : LanguageEnum.values()) {
			try {
				register(lang.name(), new ObjectInputStream(
						new BufferedInputStream(loader.getResourceAsStream("detect/" + lang.name() + "_tree.bin"))));
			} catch (IOException e) {
				LOG.warn("Unable to read resources for language " + lang);
			}
		}
	}	

	public static MyLanguageDetector getInstance() {
		return INSTANCE;
	}

	@Override
	public void register(final String lang, final ObjectInputStream in) {
		if (INSTANCE != null) {
			throw new IllegalStateException("Cannot add languages to Europarl detector once loaded");
		}
		super.register(lang, in);
	}

}

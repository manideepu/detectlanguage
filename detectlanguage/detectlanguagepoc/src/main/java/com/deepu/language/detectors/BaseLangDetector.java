
package com.deepu.language.detectors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import me.champeau.ld.AbstractGramTree;

/**
 * This class wraps several n-gram trees in order to detect languages. The
 * detection algorithm is really simple : it queries the registered gram trees,
 * and returns the language associated with the one which returns the best
 * score.
 * 
 * 
 *
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
	 * Performs a language detection, using the whole set of possible languages.
	 *
	 * @param aText
	 *            the text for which to detect the language
	 * @return the detected language
	 */
	public String detectLang(CharSequence aText) {
		return detectLang(aText, statsMap.keySet());
	}

	/**
	 * Performs a language detection, but limits the detection to the set of
	 * provided languages. This is useful when the detector has been trained
	 * with many languages, but you wish to discriminate between a smaller set
	 * of possible languages (or, you know that the document is either in
	 * english or french).
	 *
	 *
	 * @param aText
	 *            the text for which to detect the language
	 * @param languageRestrictions
	 *            the set of languages the detector should be limited to
	 * @return the detected language
	 */
	public String detectLang(CharSequence aText, Set<String> languageRestrictions) {
		double best = 0;
		String bestLang = null;
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
		return bestLang;
	}

	/**
	 * Returns the scores of each language profile for the given input text. The
	 * language detection is limited to the languages specified by the
	 * languageRestrictions parameter, and the resulting list is sorted by
	 * descending score.
	 * 
	 * @param aText
	 *            the text for which to detect score
	 * @param languageRestrictions
	 *            the list of languages to be tested
	 * @return the scores for each language, sorted by descending score
	 */
	public Collection<Score> scoreLanguages(CharSequence aText, Set<String> languageRestrictions) {
		List<Score> scores = new LinkedList<Score>();
		for (Map.Entry<String, AbstractGramTree> entry : statsMap.entrySet()) {
			final String currentLanguage = entry.getKey();
			if (languageRestrictions.contains(currentLanguage)) {
				scores.add(new Score(currentLanguage, entry.getValue().scoreText(aText)));
			}
		}
		Collections.sort(scores);
		return scores;
	}

	/**
	 * Returns the scores of each language profile for the given input text. The
	 * resulting list is sorted by descending score.
	 * 
	 * @param aText
	 *            the text for which to detect score
	 * @return the scores for each language, sorted by descending score
	 */
	public Collection<Score> scoreLanguages(CharSequence aText) {
		return scoreLanguages(aText, statsMap.keySet());
	}

	public static class Score implements Comparable<Score> {

		private final String language;
		private final double score;

		public Score(final String language, final double score) {
			this.language = language;
			this.score = score;
		}

		public int compareTo(final Score o) {
			return Double.compare(o.score, score);
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("Score");
			sb.append("{language='").append(language).append('\'');
			sb.append(", score=").append(score);
			sb.append('}');
			return sb.toString();
		}
	}
}

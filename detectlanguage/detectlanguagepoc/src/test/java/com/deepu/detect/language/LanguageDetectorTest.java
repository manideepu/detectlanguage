package com.deepu.detect.language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.deepu.language.detectors.LanguageDetector;
import com.deepu.language.detectors.LanguageEnum;;

/**
 * Unit test cases
 */
public class LanguageDetectorTest {

	private LanguageDetector languageDetector = LanguageDetector.getInstance();

	public Set<String> getLanguageRestrictions() {
		Set<String> languageRestrictions = new HashSet<String>();
		for (LanguageEnum lang : LanguageEnum.values()) {
			languageRestrictions.add(lang.name());
		}
		return languageRestrictions;
	}

	@Test
	public void testDetectLanguageStringNull() {

		String aText = null;
		String language = languageDetector.detectLanguage(aText);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageEmptyString() {

		String aText = "";
		String language = languageDetector.detectLanguage(aText);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageDanish() {
		String content = "Hvordan har du det";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("da", language);
	}

	@Test
	public void testDetectLanguageGerman() {
		String content = "Wie geht es Ihnen";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("de", language);
	}

	@Test
	public void testDetectLanguageEnglish() {
		String content = "How are you";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageSpanish() {
		String content = "adios";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("es", language);
	}

	@Test
	public void testDetectLanguageEstonian() {
		String content = "head aega";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("et", language);
	}

	@Test
	public void testDetectLanguageFinnish() {
		String content = "Kuinka voit";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fi", language);
	}

	@Test
	public void testDetectLanguageFrench() {
		String content = "Comment allez-vous";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageHungarian() {
		String content = "hogy vagy";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("hu", language);
	}

	@Test
	public void testDetectLanguageItalian() {
		String content = "arrivederci";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("it", language);
	}

	@Test
	public void testDetectLanguageLithuanian() {
		String content = "kaip laikaisi";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("lt", language);
	}

	@Test
	public void testDetectLanguageLatvian() {
		String content = "kur tu esi";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("lv", language);
	}

	@Test
	public void testDetectLanguageDutch() {
		String content = "waar ben jij";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("nl", language);
	}

	@Test
	public void testDetectLanguagePolish() {
		String content = "Dziękuję Ci";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("pl", language);
	}

	@Test
	public void testDetectLanguagePortuguese() {
		String content = "obrigado";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("pt", language);
	}

	@Test
	public void testDetectLanguageSlovak() {
		String content = "koľko máš rokov";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("sk", language);
	}

	@Test
	public void testDetectLanguageSwedish() {
		String content = "avtio";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("sv", language);
	}

	@Test
	public void testDetectLanguageRussian() {
		String content = "Как делаss";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("ru", language);
	}

	@Test
	public void testDetectLanguageEnglishSpecialCharColon() {
		String content = "The world is a stage: play your role well.";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageFrenchSpecialCharColon() {
		String content = "Le monde est une scene : jouer votre role bien.";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageEnglishSpecialCharSemiColon() {
		String content = "The match was really nice; but we lost that";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageFrenchSpecialCharSemiColon() {
		String content = "Le match etait vraiment agreable ; mais nous avons perdu cette";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageEnglishSpecialCharColonCommaDot() {
		String content = "The match was really nice: two stumpings, one runout and five wickets for one bowler.";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageFrenchSpecialCharColonCommaDot() {
		String content = "Le match etait vraiment agreable : deux stumpings , un voile et cinq guichets pour un chapeau melon.";
		String language = languageDetector.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsEmptyContent() {
		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String aText = " ";
		String language = languageDetector.detectLanguage(aText, languageRestrictions);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsEmptyContentEmptySet() {
		Set<String> languageRestrictions = new HashSet<String>();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() == 0);

		String aText = "";
		String language = languageDetector.detectLanguage(aText, languageRestrictions);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsNull() {
		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String aText = null;
		String language = languageDetector.detectLanguage(aText, languageRestrictions);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsNullSet() {
		Set<String> languageRestrictions = null;
		assertNull(languageRestrictions);

		String aText = "How are you";
		String language = languageDetector.detectLanguage(aText, languageRestrictions);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsEmptySet() {
		Set<String> languageRestrictions = new HashSet<String>();
		assertNotNull(languageRestrictions);

		String aText = "How are you";
		String language = languageDetector.detectLanguage(aText, languageRestrictions);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsNullTextNullSet() {
		Set<String> languageRestrictions = null;
		assertNull(languageRestrictions);

		String aText = null;
		String language = languageDetector.detectLanguage(aText, languageRestrictions);
		assertNull(language);

	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsDanish() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "Hvordan har du det";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("da", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsGerman() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "Wie geht es Ihnen";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("de", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsEnglish() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "How are you";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsSpanish() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "adios";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("es", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsEstonian() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "head aega";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("et", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsFinnish() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "Kuinka voit";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("fi", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsFrench() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "Comment allez-vous";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsHungarian() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "hogy vagy";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("hu", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsItalian() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "arrivederci";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("it", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsLithuanian() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "kaip laikaisi";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("lt", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsLatvian() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "kur tu esi";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("lv", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsDutch() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "waar ben jij";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("nl", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsPolish() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "Dziękuję Ci";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("pl", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsPortuguese() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "obrigado";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("pt", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsSlovak() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "koľko máš rokov";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("sk", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsSwedish() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "avtio";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("sv", language);
	}

	@Test
	public void testDetectLanguageStringLanguageRestrictionsRussian() {

		Set<String> languageRestrictions = getLanguageRestrictions();
		assertNotNull(languageRestrictions);
		assertTrue(languageRestrictions.size() > 0);

		String content = "Как делаss";
		String language = languageDetector.detectLanguage(content, languageRestrictions);
		assertNotNull(language);
		assertEquals("ru", language);
	}

}

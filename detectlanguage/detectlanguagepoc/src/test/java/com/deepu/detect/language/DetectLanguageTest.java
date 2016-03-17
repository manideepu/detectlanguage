package com.deepu.detect.language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;;

/**
 * Unit test cases
 */
public class DetectLanguageTest {

	DetectLanguage detectLanguage = new DetectLanguage();
	private final static String INPUT_FILE_PATH = "/src/main/resources/input/";
	private final static String EMPTY_INPUT_FILE_PATH = "/src/main/resources/emptyinput/";
	private final static String NOTPRESENT_INPUT_FILE_PATH = "/src/main/resources/emptyinput/";
	private final static String EMPTY_UNKNOWN_INPUT_FILE_NAME = "unknown.txt";
	private final static String NONEMPTY_UNKNOWN_INPUT_FILE_NAME = "text.txt";
	private final static String NOTPRESENT_UNKNOWN_INPUT_FILE_NAME = "notpresent.txt";

	@Test
	public void testGetDisplayLanguagesLowerCase() {
		assertNotNull(detectLanguage.getDisplayLanguagesLowerCase());
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().size() > 0);
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("english"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("french"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("german"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("russian"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("spanish"));
	}

	@Test
	public void testGetLocaleLanguagesAndDisplayLanguage() {
		assertNotNull(detectLanguage.getLocaleLanguagesAndDisplayLanguage());
		assertTrue(detectLanguage.getLocaleLanguagesAndDisplayLanguage().size() > 0);
		Set<String> keySet = detectLanguage.getLocaleLanguagesAndDisplayLanguage().keySet();
		assertNotNull(keySet);
		assertTrue(keySet.size() > 0);

		assertTrue(keySet.contains("en"));
		assertTrue(keySet.contains("fr"));
		assertTrue(keySet.contains("da"));
		assertTrue(keySet.contains("es"));
		assertTrue(keySet.contains("ru"));
		assertTrue(keySet.contains("de"));

		Collection<String> valueSet = detectLanguage.getLocaleLanguagesAndDisplayLanguage().values();
		assertNotNull(valueSet);
		assertTrue(valueSet.size() > 0);

		assertTrue(valueSet.contains("english"));
		assertTrue(valueSet.contains("french"));
		assertTrue(valueSet.contains("german"));
		assertTrue(valueSet.contains("russian"));
		assertTrue(valueSet.contains("spanish"));
	}

	@Test
	public void testReadUnknownFileNonEmpty() {
		String folderName = detectLanguage.getFileInputFolderName(INPUT_FILE_PATH);
		assertNotNull(folderName);
		assertTrue(folderName.length() > 0);
		File file = new File(folderName + NONEMPTY_UNKNOWN_INPUT_FILE_NAME);
		assertNotNull(file);
		String content = detectLanguage.readUnknownFile(file);
		assertNotNull(content);
		assertTrue(content.length() > 0);
	}

	@Test
	public void testReadUnknownFileEmpty() {
		String folderName = detectLanguage.getFileInputFolderName(INPUT_FILE_PATH);
		assertNotNull(folderName);
		assertTrue(folderName.length() > 0);
		File file = new File(folderName + EMPTY_UNKNOWN_INPUT_FILE_NAME);
		assertNotNull(file);
		String content = detectLanguage.readUnknownFile(file);
		assertNotNull(content);
		assertTrue(content.length() == 0);
	}

	@Test
	public void testReadUnknownFileNull() {
		File file = null;
		assertNull(file);
		String content = detectLanguage.readUnknownFile(file);
		assertNotNull(content);
		assertTrue(content.length() == 0);
	}

	@Test
	public void testReadUnknownFileFileNotPresent() {
		String folderName = detectLanguage.getFileInputFolderName(INPUT_FILE_PATH);
		assertNotNull(folderName);
		assertTrue(folderName.length() > 0);
		File file = new File(folderName + NOTPRESENT_UNKNOWN_INPUT_FILE_NAME);
		assertNotNull(file);
		String content = detectLanguage.readUnknownFile(file);
		assertNotNull(content);
		assertTrue(content.length() == 0);
	}

	@Test
	public void testReadUnknownFileFolderNotPresent() {
		String folderName = detectLanguage.getFileInputFolderName(NOTPRESENT_INPUT_FILE_PATH);
		assertNotNull(folderName);
		assertTrue(folderName.length() > 0);
		File file = new File(folderName + NOTPRESENT_UNKNOWN_INPUT_FILE_NAME);
		assertNotNull(file);
		String content = detectLanguage.readUnknownFile(file);
		assertNotNull(content);
		assertTrue(content.length() == 0);
	}

	@Test
	public void testGetFileInputFolder() {
		String folderName = detectLanguage.getFileInputFolderName(INPUT_FILE_PATH);
		assertNotNull(folderName);
		assertTrue(folderName.length() > 0);
	}

	@Test
	public void testDetectLanguageValidInput() {
		detectLanguage.detectLanguageFromFile(INPUT_FILE_PATH);
		assertTrue(true);
	}

	@Test
	public void testDetectLanguageNullInput() {
		detectLanguage.detectLanguageFromFile(null);
		assertTrue(true);
	}

	@Test
	public void testDetectLanguageEmptyInput() {
		detectLanguage.detectLanguageFromFile("");
		assertTrue(true);
	}

	@Test
	public void testDetectLanguageNull() {
		String language = detectLanguage.detectLanguage(null);
		assertNotNull(language);
		assertTrue(language.length() == 0);
	}

	@Test
	public void testDetectLanguageEmpty() {
		String language = detectLanguage.detectLanguage("");
		assertNotNull(language);
		assertTrue(language.length() == 0);
	}

	@Test
	public void testDetectLanguageSpaces() {
		String language = detectLanguage.detectLanguage("  ");
		assertNotNull(language);
		assertTrue(language.length() == 0);
	}

	@Test
	public void testGetFileNameWithoutExtension() {
		String fileName = "english";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("english", language);
	}

	@Test
	public void testGetFileNameWithTxtExtension() {
		String fileName = "english.txt";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("english", language);
	}

	@Test
	public void testGetFileNameWithNull() {
		String fileName = null;
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("", language);
	}

	@Test
	public void testGetFileNameWithEmptyString() {
		String fileName = "";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("", language);
	}

	@Test
	public void testGetFileNameWithSpecialCharString() {
		String fileName = "english,.;";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("english", language);
	}

	@Test
	public void testGetFileNameWithDigitExtn() {
		String fileName = "english.1";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("english", language);
	}

	@Test
	public void testGetFileNameWithCapDigitExtn() {
		String fileName = "ENGLISH.2";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("ENGLISH", language);
	}

	@Test
	public void testGetFileNameWithCapCharTxtExtn() {
		String fileName = "French.txt";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("French", language);
	}

	@Test
	public void testGetFileNameWithCharUnderScoreLangTxtExtn() {
		String fileName = "text_French.txt";
		String language = detectLanguage.getFileNameWithoutExtension(fileName);
		assertEquals("text", language);
	}

	@Test
	public void testDetectLanguageDanish() {
		String content = "Hvordan har du det";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("da", language);
	}

	@Test
	public void testDetectLanguageGerman() {
		String content = "Wie geht es Ihnen";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("de", language);
	}

	@Test
	public void testDetectLanguageEnglish() {
		String content = "How are you";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageSpanish() {
		String content = "adios";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("es", language);
	}

	@Test
	public void testDetectLanguageEstonian() {
		String content = "head aega";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("et", language);
	}

	@Test
	public void testDetectLanguageFinnish() {
		String content = "Kuinka voit";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fi", language);
	}

	@Test
	public void testDetectLanguageFrench() {
		String content = "Comment allez-vous";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageHungarian() {
		String content = "hogy vagy";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("hu", language);
	}

	@Test
	public void testDetectLanguageItalian() {
		String content = "arrivederci";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("it", language);
	}

	@Test
	public void testDetectLanguageLithuanian() {
		String content = "kaip laikaisi";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("lt", language);
	}

	@Test
	public void testDetectLanguageLatvian() {
		String content = "kur tu esi";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("lv", language);
	}

	@Test
	public void testDetectLanguageDutch() {
		String content = "waar ben jij";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("nl", language);
	}

	@Test
	public void testDetectLanguagePolish() {
		String content = "Dziękuję Ci";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("pl", language);
	}

	@Test
	public void testDetectLanguagePortuguese() {
		String content = "obrigado";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("pt", language);
	}

	@Test
	public void testDetectLanguageSlovak() {
		String content = "koľko máš rokov";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("sk", language);
	}

	@Test
	public void testDetectLanguageSwedish() {
		String content = "avtio";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("sv", language);
	}

	@Test
	public void testDetectLanguageRussian() {
		String content = "Как делаss";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("ru", language);
	}

	@Test
	public void testDetectLanguageEnglishSpecialCharColon() {
		String content = "The world is a stage: play your role well.";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}
	
		@Test
	public void testDetectLanguageFrenchSpecialCharColon() {
		String content = "Le monde est une scene : jouer votre role bien.";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageEnglishSpecialCharSemiColon() {
		String content = "The match was really nice; but we lost that";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageFrenchSpecialCharSemiColon() {
		String content = "Le match etait vraiment agreable ; mais nous avons perdu cette";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageEnglishSpecialCharColonCommaDot() {
		String content = "The match was really nice: two stumpings, one runout and five wickets for one bowler.";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("en", language);
	}

	@Test
	public void testDetectLanguageFrenchSpecialCharColonCommaDot() {
		String content = "Le match etait vraiment agreable : deux stumpings , un voile et cinq guichets pour un chapeau melon.";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testProcessAvailableFilesNullInput() {
		String inputFolderPath = null;
		Set<String> languageSet = null;
		detectLanguage.processAvailableFiles(inputFolderPath, languageSet);
		assertTrue(true);
	}

	@Test
	public void testProcessAvailableFilesEmptySpaceInput() {
		String inputFolderPath = "";
		Set<String> languageSet = null;
		detectLanguage.processAvailableFiles(inputFolderPath, languageSet);
		assertTrue(true);
	}

	@Test
	public void testProcessAvailableFilesNullLanguage() {
		Set<String> languageSet = null;
		detectLanguage.processAvailableFiles(INPUT_FILE_PATH, languageSet);
		assertTrue(true);
	}

	@Test
	public void testProcessAvailableFilesEmptyLanguage() {
		Set<String> languageSet = new HashSet<String>();
		detectLanguage.processAvailableFiles(INPUT_FILE_PATH, languageSet);
		assertTrue(true);
	}

	@Test
	public void testProcessAvailableFiles() {

		Set<String> languageSet = detectLanguage.getDisplayLanguagesLowerCase();
		assertNotNull(languageSet);
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().size() > 0);
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("english"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("french"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("german"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("russian"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("spanish"));

		detectLanguage.processAvailableFiles(INPUT_FILE_PATH, languageSet);
		assertTrue(true);
	}

	@Test
	public void testProcessAvailableFilesNoFiles() {

		Set<String> languageSet = detectLanguage.getDisplayLanguagesLowerCase();
		assertNotNull(languageSet);
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().size() > 0);
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("english"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("french"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("german"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("russian"));
		assertTrue(detectLanguage.getDisplayLanguagesLowerCase().contains("spanish"));

		detectLanguage.processAvailableFiles(EMPTY_INPUT_FILE_PATH, languageSet);
		assertTrue(true);
	}

}

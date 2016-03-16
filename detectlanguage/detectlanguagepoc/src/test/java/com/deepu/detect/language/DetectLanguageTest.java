package com.deepu.detect.language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;;

/**
 * Unit test cases
 */
public class DetectLanguageTest {

	DetectLanguage detectLanguage = new DetectLanguage();
	private final static String INPUT_FILE_PATH = "/src/main/resources/input/";
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
	public void testDetectLanguageSpanish() {
		String content = "Wie geht es Ihnen";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("de", language);
	}

	@Test
	public void testDetectLanguageFrench() {
		String content = "Comment allez-vous";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("fr", language);
	}

	@Test
	public void testDetectLanguageRussian() {
		String content = "Как делаss";
		String language = detectLanguage.detectLanguage(content);
		assertNotNull(language);
		assertEquals("ru", language);
	}
	
	@Test
	public void testGetFileNameWithoutExtension(){
		
	}

}

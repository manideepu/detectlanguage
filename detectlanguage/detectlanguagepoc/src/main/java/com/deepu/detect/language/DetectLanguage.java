package com.deepu.detect.language;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deepu.language.detectors.LanguageDetector;

/**
 * Read files from file path and detect language
 */
public class DetectLanguage {

	private final static String ENCODING = "UTF-8";

	private static final Log LOG = LogFactory.getLog(DetectLanguage.class);

	/**
	 * @param inputFilePath
	 *            Detect the Language of files in inputFilePath
	 */
	public void detectLanguageFromFile(String inputFilePath) {
		Set<String> allLanguages = getDisplayLanguagesLowerCase();
		processAvailableFiles(inputFilePath, allLanguages);
	}

	/**
	 * Get the DisplayLanguges in Lowercase
	 *
	 * @return Set<String>
	 */
	public Set<String> getDisplayLanguagesLowerCase() {
		Set<String> languageSet = new HashSet<String>();
		Locale locales[] = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			languageSet.add(locale.getDisplayLanguage().toLowerCase());
		}
		LOG.debug("Available Languages names :: " + languageSet);
		return languageSet;
	}

	/**
	 * @return Map<String, String> ; key = Locale Languages; value = Display
	 *         Language in Lower case
	 */
	public Map<String, String> getLocaleLanguagesAndDisplayLanguage() {
		Map<String, String> languageMap = new HashMap<String, String>();
		Locale locales[] = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			languageMap.put(locale.getLanguage(), locale.getDisplayLanguage().toLowerCase());
		}
		LOG.debug("Available Languages code :: names :: " + languageMap);
		return languageMap;
	}

	@SuppressWarnings("unchecked")
	public void processAvailableFiles(String inputFolderPath, Set<String> languageSet) {
		if (StringUtils.isNotBlank(inputFolderPath) && languageSet != null && languageSet.size() > 0) {
			List<String> unknownFiles = new ArrayList<String>();
			File inputFolder = new File(getFileInputFolderName(inputFolderPath));
			Iterator<File> iterator = FileUtils.iterateFiles(inputFolder, null, false);
			while (iterator.hasNext()) {
				File file = (File) iterator.next();
				String fileName = getFileNameWithoutExtension(file.getName());
				if (StringUtils.isNotEmpty(fileName) && languageSet.contains(fileName.toLowerCase())) {
					// Detect Language from the file name
					LOG.info(WordUtils.capitalizeFully(fileName) + " is the language of the file " + file.getName());
				} else {
					// Detect Language from content
					String fileContent = readUnknownFile(file);
					String detectedLangugage = detectLanguage(fileContent);
					if (StringUtils.isNotBlank(detectedLangugage)) {
						String displayLanguage = new Locale(detectedLangugage).getDisplayLanguage().toLowerCase();
						LOG.info(WordUtils.capitalizeFully(displayLanguage) + " is the language of the file "
								+ file.getName());
					} else {
						LOG.info("Unable to detect language for the file " + file.getName());
						unknownFiles.add(file.getName());
					}
				}
			}
			LOG.info("\n\nPrinting unknown files :: ");
			LOG.info(unknownFiles);
		}
	}

	/**
	 * @param inputFolderPath
	 *            relative folder
	 * @return String Complete folder name in the file system
	 */
	public String getFileInputFolderName(String inputFolderPath) {
		StringBuilder userDirectory = new StringBuilder(System.getProperty("user.dir"));
		userDirectory.append(inputFolderPath);
		return userDirectory.toString();
	}

	/**
	 * Get the file name with extension
	 * 
	 * @param fileName
	 * @return fileName without extension
	 */
	public String getFileNameWithoutExtension(String fileName) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(fileName)) {
			for (int i = 0; i < fileName.length(); i++) {
				if (Character.isLetter(fileName.charAt(i))) {
					sb.append(fileName.charAt(i));
				} else {
					// Break the loop if a special character is found.
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Read a file and return the file content as String
	 *
	 * @param file
	 * @return String
	 */
	public String readUnknownFile(File file) {
		String content = "";
		Scanner scanner = null;
		try {
			if (file != null) {
				scanner = new Scanner(file, ENCODING);
				if (scanner != null && scanner.hasNext()) {
					content = scanner.useDelimiter("\\Z").next();
				}
			}
		} catch (FileNotFoundException fe) {
			LOG.error("FileNotFoundException occured :: " + fe.getMessage());
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return content;
	}

	/**
	 * Accept a String and detect the language of that
	 * 
	 * @param fileContent
	 * @return String value of detected Language
	 */
	public String detectLanguage(String fileContent) {
		String detectedLangugage = "";
		if (StringUtils.isNotBlank(fileContent)) {
			LanguageDetector detector = LanguageDetector.getInstance();
			detectedLangugage = detector.detectLanguage(fileContent);
		}
		return detectedLangugage;
	}
}
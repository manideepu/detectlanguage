
package me.champeau.ld;

import java.util.Iterator;

/**
 * Tokenizes char sequences. Wrapper for an @link NGramIterator.
 */
public class NGramTokenizer implements Iterable<CharSequence> {
	final private CharSequence buffer;
	final private int min, max;

	public NGramTokenizer(CharSequence buffer, int min, int max) {
		this.buffer = buffer;
		this.min = min;
		this.max = max;
	}

	public Iterator<CharSequence> iterator() {
		return new NGramIterator(buffer, min, max);
	}
}

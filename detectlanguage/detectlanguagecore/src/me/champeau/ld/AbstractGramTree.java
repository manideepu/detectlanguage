
package me.champeau.ld;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A gram tree is used to learn n-grams from texts, and is able to score a text.
 * The n-gram data is represented as a lexical tree. The representation is
 * rather compact, but one could do better with annotated-DFAs.
 *
 * Not thread-safe.
 *
 */
public abstract class AbstractGramTree implements Serializable {
	private static final Log theLogger = LogFactory.getLog(AbstractGramTree.class);

	private static final long serialVersionUID = 3284917449023378874L;
	protected AbstractNode root;
	protected long gramcount;
	protected int min;
	protected int max;

	protected AbstractGramTree(int min, int max) {
		this.max = max;
		this.min = min;
		gramcount = 0;
	}

	protected AbstractGramTree(int min, int max, long gramcount) {
		this.max = max;
		this.min = min;
		this.gramcount = gramcount;
	}

	/**
	 * Returns a score for the input sequence against this n-gram tree.
	 *
	 * @param text
	 *            the text to be checked
	 * @return a score
	 */
	public double scoreText(CharSequence text) {
		NGramTokenizer tokenizer = new NGramTokenizer(text, min, max);
		double tot = 0;
		for (CharSequence charSequence : tokenizer) {
			double s = scoreGram(charSequence);
			if (theLogger.isDebugEnabled()) {
				theLogger.debug(charSequence + " scores " + s);
			}
			tot += s;
		}
		double score = tot / Math.log(gramcount);
		if (theLogger.isDebugEnabled()) {
			theLogger.debug(text + ", total " + tot + "/" + Math.log(gramcount) + "=" + score);
		}
		return score;
	}

	private double scoreGram(CharSequence gram) {
		AbstractNode cur = root;
		for (int i = 0; i < gram.length(); i++) {
			char c = gram.charAt(i);
			AbstractNode next = cur.getChild(c);
			if (next == null)
				return 0;
			cur = next;
		}
		return Math.log(cur.freq);
	}

	/**
	 * Base class for an n-gram tree node.
	 */
	protected static abstract class AbstractNode implements Serializable {
		protected int freq;
		protected AbstractNode[] children;
		char c;

		public AbstractNode getChild(char c) {
			if (children == null)
				return null;
			final int childcount = children.length;
			for (int i = 0; i < childcount; i++) {
				if (children[i].c == c)
					return children[i];
				if (children[i].c > c)
					return null;
			}
			return null;
		}

	}
}

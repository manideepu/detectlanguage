
package me.champeau.ld;

/**
 * An immutable n-gram tree implementation, which aims at being as compact as
 * possible as a memory object.
 */
public class GramTreeImpl extends AbstractGramTree {
	protected GramTreeImpl(AbstractNode root, int min, int max, long gramCount) {
		super(min, max, gramCount);
		this.root = root;
	}
}

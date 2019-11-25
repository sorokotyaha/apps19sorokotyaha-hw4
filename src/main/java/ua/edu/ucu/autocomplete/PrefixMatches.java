package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int added = 0;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                String[] words = strings[i].split(" ");
                for (String word : words) {
                    if (word.length() > 2) {
                        trie.add(new Tuple(word, word.length()));
                        added += 1;
                    }
                }
            }
        }
        return added;

    }


    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            return null;
        }
        return this.trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            return null;
        }
        final int num_k = k;
        final String curprefix = pref;
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    private int k = num_k;
                    private RWayTrie tr = (RWayTrie) trie;
                    private String pref = curprefix;
                    private ArrayList<Integer> diffLength;
                    private RWayTrie.FuckingIterator iter = new RWayTrie.FuckingIterator(tr);
                    private String wrd = tryNext();

                    @Override
                    public boolean hasNext() {
                        return (k > 0) && (!(wrd == null));
                    }

                    public String tryNext() {
                        if (this.iter.hasNext()) {
                            return this.iter.next();
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public String next() {
                        String temp = this.wrd;
                        this.diffLength.add(temp.length());
                        this.wrd = this.tryNext();
                        return temp;
                    }
                };
            }
        };

    }

    public int size() {
        return this.trie.size();
    }
}

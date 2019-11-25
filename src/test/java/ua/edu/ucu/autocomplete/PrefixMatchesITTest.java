
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
    }


    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }


    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

//
//        Iterable<String> result = pm.wordsWithPrefix(pref, k);
//
//        String[] expResult = {"abc", "abce", "abcd", "abcde"};
//
//        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testAdd() {
        RWayTrie rw = new RWayTrie();
        Tuple tp = new Tuple("abc", 3);
        rw.add(tp);

        rw.add(new Tuple("abb", 3));
        rw.add(new Tuple("ab", 2));

        for (String word : rw.wordsWithPrefix("abb")) {
            System.out.println(word);
        }
    }

}

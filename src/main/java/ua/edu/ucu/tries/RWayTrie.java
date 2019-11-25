package ua.edu.ucu.tries;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import ua.edu.ucu.immutable.Queue;


public class RWayTrie implements Trie {
    private int size = 0;
    private Hashtable<Character, RWayTrie> mydict;
    // private final Character end = ' ';
    private boolean end = false;
    private RWayTrie parent = null;
    private int level = 0;
    private Character prevLetter = ' ';


    public RWayTrie() {
        this.mydict = new Hashtable<>();
    }

    @Override
    public void add(Tuple t) {
        String str = t.term;
        int len = t.weight;
        RWayTrie currTrie = this;
        int level = this.level;
        if (!this.contains(str)) {


            //System.out.printf("Starting level %d\n", level);
            for (int i = 0; i < len; i++) {
                // System.out.printf("%c %d",str.charAt(i), i);
                RWayTrie parent = currTrie;
                //System.out.println("Parent trie: " + parent.toString());

                currTrie = currTrie.addOne(str.charAt(i));

                //System.out.println("trie at this moment : " + this.toString());
                level += 1;
                currTrie.level = level;
                //System.out.printf("Level of current trie : %d\n", currTrie.level);
                currTrie.parent = parent;
                currTrie.prevLetter = str.charAt(i);

                //System.out.println("Prevletter of current trie : " + currTrie.prevLetter);
            }
            currTrie.end = true;
            currTrie.size += 1;
            this.size += 1;
        }
    }

    private RWayTrie addOne(Character cr) {
        if (!this.isKey(cr)) {
            this.size += 1;
            this.mydict.put(cr, new RWayTrie());
        }
        return this.getValue(cr);
    }


    private RWayTrie getValue(Character cr) {
        return this.mydict.get(cr);
    }


    private boolean isKey(Character letter) {

        return this.mydict.containsKey(letter);
    }

    @Override
    public boolean contains(String word) {
        RWayTrie currTrie = this;
        for (int i = 0; i < word.length(); i++) {
            Character cr = word.charAt(i);
            if (currTrie.isKey(cr)) {
                currTrie = currTrie.getValue(cr);
            } else {
                return false;
            }
        }
        //return currTrie.isKey(this.end);
        return currTrie.end;
    }

    @Override
    public boolean delete(String word) {
        if (this.deleted(word, this)) {
            this.size -= 1;
            return true;
        }
        return false;
    }


    private boolean deleted(String word, RWayTrie trie) {
        // if we reached the end of the word and now check if there is the 'end' char in the trie
        System.out.println(word);
        if (word.isEmpty()) {
            //if (trie.isKey(trie.end)){
            System.out.print(trie.toString());
            System.out.println(trie.end);
            if (trie.end) {
                //trie.mydict.remove(trie.end);
                trie.end = false;
                return true;
            }
            return false;
        }
        Character cr = word.charAt(0);
        if (trie.isKey(cr)) {
            boolean deletedd = trie.deleted(word.substring(1), trie.getValue(cr));
            System.out.println(cr);
            System.out.println(trie.toString());
            if (deletedd) {
                if (trie.getValue(cr).isEmpty()) {
                    trie.mydict.remove(cr);
                }
                return true;
            }
            return false;
        }
        return false;

    }


    public static class FuckingIterator implements Iterator<String> {

        private RWayTrie head;
        private Queue allChildrenQueue = new Queue();

        public FuckingIterator(RWayTrie rw) {
            System.out.println("I was created");
            this.head = rw;
            this.allChildrenQueue.enqueue(this.head);
        }

        private Queue nextNode() {
            RWayTrie parent = (RWayTrie) this.allChildrenQueue.dequeue();
            Set<Character> keys = parent.mydict.keySet();
            for (Character cr : keys) {
                this.allChildrenQueue.enqueue(parent.mydict.get(cr));
            }
            return this.allChildrenQueue;
        }

        @Override
        public boolean hasNext() {
            return !this.allChildrenQueue.isEmpty();
        }

        @Override
        public String next() {
            RWayTrie temp = (RWayTrie) this.allChildrenQueue.peek();
            while (!temp.end) {
                //System.out.println(temp.toString());
                temp = (RWayTrie) this.nextNode().peek();
            }

            StringBuilder word = new StringBuilder();
            RWayTrie trie = (RWayTrie) this.allChildrenQueue.peek();
            while (trie.level != 0) {
                //System.out.println(trie.prevLetter);
                word.insert(0, trie.prevLetter.toString());
                trie = trie.parent;
            }
            this.nextNode();
            //System.out.println(String.valueOf(word));
            return String.valueOf(word.toString());

        }
    }

    @Override
    public Iterable<String> words() {
        RWayTrie trie = this;
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new FuckingIterator(trie);
            }
        };
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        RWayTrie cur = this;
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (cur.isKey(ch)) {
                cur = cur.mydict.get(ch);
            } else {
                return new RWayTrie().words();
            }
        }
        return cur.words();
    }

    @Override
    public int size() {
        // number of words in a trie
        return this.size;
    }

    public boolean isEmpty() {

        return this.mydict.isEmpty() && !this.end;
    }

    @Override
    public String toString() {
        return this.mydict.toString();
    }


}

package com.csy.booklibrary.core;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Trie {

    private TrieNode rootNode;

    public Trie() {
        rootNode = new TrieNode((char)0);
    }


    public void insert(String word)  {

        int length = word.length();
        TrieNode crawl = rootNode;

        for( int level = 0; level < length; level++)
        {
            HashMap<Character,TrieNode> child = crawl.getChild();
            char ch = word.charAt(level);

            if( child.containsKey(ch))
                crawl = child.get(ch);
            else   // Else create a child
            {
                TrieNode temp = new TrieNode(ch);
                child.put( ch, temp );
                crawl = temp;
            }
        }

        // Set bIsEnd true for last character
        crawl.setIsEnd(true);
    }

    public void load(String phrase) {
        loadRecursive(rootNode, phrase + "$");
    }

    private void loadRecursive(TrieNode node, String phrase) {
        if (StringUtils.isBlank(phrase)) {
            return;
        }
        char firstChar = phrase.charAt(0);
        node.add(firstChar);
        TrieNode childNode = node.getChildNode(firstChar);
        if (childNode != null) {
            loadRecursive(childNode, phrase.substring(1));
        }
    }

    public boolean matchPrefix(String prefix) {
        TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
        return (matchedNode != null);
    }

    private TrieNode matchPrefixRecursive(TrieNode node, String prefix) {
        if (StringUtils.isBlank(prefix)) {
            return node;
        }
        char firstChar = prefix.charAt(0);
        TrieNode childNode = node.getChildNode(firstChar);
        if (childNode == null) {
            // no match at this char, exit
            return null;
        } else {
            // go deeper
            return matchPrefixRecursive(childNode, prefix.substring(1));
        }
    }

    public List<String> findCompletions(String prefix) {
        TrieNode matchedNode = matchPrefixRecursive(rootNode, prefix);
        List<String> completions = new ArrayList<>();
        findCompletionsRecursive(matchedNode, prefix, completions);
        return completions;
    }

    private void findCompletionsRecursive(TrieNode node, String prefix, List<String> completions) {
        if (node == null) {
            return;
        }
        if (node.isEnd() == true) {
            completions.add(prefix.substring(0, prefix.length()));
        }
        Collection<TrieNode> childNodes = node.getChildren();
        for (TrieNode childNode : childNodes) {
            char childChar = childNode.getNodeValue();
            findCompletionsRecursive(childNode, prefix + childChar, completions);
        }
    }

    public String toString() {
        return "Trie:" + rootNode.toString();
    }

    public static void main(String[] args) {
        Trie dict = new Trie();
        dict.insert("are");
        dict.insert("area");
        dict.insert("base");
        dict.insert("cat");
        dict.insert("cater");
        dict.insert("basement");

        String input = "caterer";
//        System.out.print(input + ":   ");
//        System.out.println(dict.findCompletions(input));

        input = "basement";
        System.out.print(input + ":   ");
        System.out.println(dict.findCompletions(input));

        input = "are";
        System.out.print(input + ":   ");
        System.out.println(dict.findCompletions(input));

        input = "a";
        System.out.print(input + ":   ");
        System.out.println(dict.findCompletions(input));

//        input = "basemexz";
//        System.out.print(input + ":   ");
//        System.out.println(dict.findCompletions(input));
//
//        input = "xyz";
//        System.out.print(input + ":   ");
//        System.out.println(dict.findCompletions(input));
    }
}

// TrieNode.java
 class TrieNode {

    private Character character;
    private HashMap<Character,TrieNode> children;
    private boolean bIsEnd;
    public TrieNode(char c) {
        this.character = new Character(c);
        children = new HashMap<Character,TrieNode>();
        bIsEnd = false;
    }

    public char getNodeValue() {
        return character.charValue();
    }

    public Collection<TrieNode> getChildren() {
        return children.values();
    }
    public HashMap<Character, TrieNode> getChild(){
        return children;
    }
    public Set<Character> getChildrenNodeValues() {
        return children.keySet();
    }

    public void add(char c) {
        if (children.get(new Character(c)) == null) {
            // children does not contain c, add a TrieNode
            children.put(new Character(c), new TrieNode(c));
        }
    }

    public TrieNode getChildNode(char c) {
        return children.get(new Character(c));
    }

    public boolean contains(char c) {
        return (children.get(new Character(c)) != null);
    }

    public boolean isEnd(){ return bIsEnd;}

    public void setIsEnd(boolean val) { bIsEnd = val; }

    public int hashCode() {
        return character.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TrieNode)) {
            return false;
        }
        TrieNode that = (TrieNode) obj;
        return (this.getNodeValue() == that.getNodeValue());
    }

}

class StringUtils {
    public static boolean isBlank(String word){
        return word == null || word.isEmpty();
    }
}
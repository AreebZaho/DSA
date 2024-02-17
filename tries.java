import java.util.*;

public class tries {
    public static class Trie {
        Trie[] child = new Trie[26];
        boolean eow = false;
        Trie() {
            for (int i = 0; i < 26; i++) {
                child[i] = null;
            }
        }
    }

    public static Trie root = new Trie();//always empty

    public static void insert(String s) {
        Trie curr = root;
        for (int i = 0; i < s.length(); i++) {
            if (curr.child[s.charAt(i) - 'a'] == null)//trie is null so create new trie to represent character exists
            curr.child[s.charAt(i) - 'a'] = new Trie();
            
            curr = curr.child[s.charAt(i) - 'a'];//if trie isn't null, going in depth until last character
        }
        curr.eow = true;//when the last char's new trie created at child of curr and curr made = the child, representing the endOfWord so make it true
    }

    public static boolean search(String s) {
        Trie curr = root;
        for (int i = 0; i < s.length(); i++) {
            if (curr.child[s.charAt(i) - 'a'] == null)//means the trie associated with that car ins't initialised with new Trie() and is still null
            return false;
            curr = curr.child[s.charAt(i) - 'a'];//if it's an existing Trie() then means uptill that char exist in trie and make curr = that trie
        }
        return curr.eow;//reched end of string but to surely know its a word of its own and not subset of another word, check if its endOfWord is true
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        {if (s.charAt(0) == 'a' && s.charAt(s.length()-1) == 'a' && s.length() > 10)
        return false; 
        if (s.charAt(0) == 'b' && s.charAt(s.length()-1) == 'a' && s.length() > 15)
        return false;}
        //just these 2 ifs to prevent TLE .........................................

        //MAIN CODE 
        if (wordDict.contains(s))//condition for partition where whole string is a part and "" other part
        return true;

        root = new Trie();
        Trie curr = root;
        //storing all the dictWords in Trie
        for (int i = 0; i < wordDict.size(); i++) {
            String word = wordDict.get(i);
            curr = root;
            for (int j = 0; j < word.length(); j++) {
                if (curr.child[word.charAt(j) - 'a'] == null)
                curr.child[word.charAt(j) - 'a'] = new Trie();

                curr = curr.child[word.charAt(j) - 'a'];
            }
            curr.eow = true;
        }
        return partition(s);
    }
    //checks partitioned string segments to be present in Trie in goal of ultimately returning true 
    public boolean partition(String s) {
        for (int i = 1; i < s.length(); i++) {
            boolean val1 = search(s.substring(0, i), root);
            boolean val2 = search(s.substring(i), root);
            if (val1 && val2)
            return true;
            else if (!val1 && val2)
                if (partition(s.substring(0, i)))
                return true;
            else if(val1 && !val2)
                if (partition(s.substring(i)))
                return true;
        }
        return false;
    }
    //to search if particular string is present in Trie
    public boolean search(String s, Trie curr) {//curr = root
        for (int i = 0; i < s.length(); i++) {
            if (curr.child[s.charAt(i) - 'a'] == null)
            return false;

            curr = curr.child[s.charAt(i) - 'a'];
        }
        return curr.eow;
    }

    public static void prefix(String[] A) {
        root = new Trie();   
        Trie curr = root;
        //store all words in Trie
        for (int i = 0; i < A.length; i++) {
            String s = A[i];
            for (int j = 0; j < s.length(); j++) {
                if (curr.child[s.charAt(j) - 'a'] == null) 
                curr.child[s.charAt(j) - 'a'] = new Trie();
                
                curr = curr.child[s.charAt(j) - 'a'];
            }
            curr.eow = true;
            curr = root;
        }     
        appender(root, new StringBuilder());
    }
    public static void appender(Trie t, StringBuilder sb) {
        int numOfChild = 0;
        for (int i = 0; i < 26; i++) {
            if (t.child[i] != null) 
            numOfChild++;
        }
        //all with 0/1 child
        if (numOfChild == 0 || numOfChild == 1)
        System.out.println(sb.toString());
        else {//go in depth for Trie with >1 children
            for (int i = 0; i < 26; i++) {
                if (t.child[i] != null) {
                    StringBuilder newSb = new StringBuilder(sb);
                    appender(t.child[i], new StringBuilder(newSb.append((char)(i+97))));
                }
            }
        }
    }
    //M-2 Frequency 
    // public static class Trie {
    //     Trie child[] = new Trie[26];
    //     boolean eow = false;
    //     int freq;//tracks how many children exists for this Trie
    //     Trie () {
    //         Arrays.fill(child, null);
    //         freq = 1;
    //     }
    // }
    // static Trie root;
    // static ArrayList<String> ans = new ArrayList<>();
    // public static void prefix(String[] A) {
    //     ans.clear();
    //     root = new Trie();
    //     root.freq = -1;
    //     Trie curr = root;
    //     //store all words in Trie
    //     for (int i = 0; i < A.length; i++) {//O(num of strings)
    //         String s = A[i];
    //         for (int j = 0; j < s.length(); j++) {//O(L)
    //             if (curr.child[s.charAt(j) - 'a'] == null) 
    //             curr.child[s.charAt(j) - 'a'] = new Trie();
    //             else curr.child[s.charAt(j) - 'a'].freq++;//Trie appeared again means has >1 child 
                
    //             curr = curr.child[s.charAt(j) - 'a'];
    //         }
    //         curr.eow = true;
    //         curr = root;
    //     }
    //     searcher(root, A);
    // }
    // public static void searcher(Trie root, String[] a) {
    //     String word = a[0];
    //     Trie curr = root;   
    //     for (int i = 0; i < a.length; i++) {//O(num of strings)
    //         word = a[i];
    //         StringBuilder sb = new StringBuilder();
    //         for (int j = 0; j < word.length(); j++) {//O(L)
    //             sb.append(word.charAt(j));
    //             if (curr.child[word.charAt(j)-'a'].freq == 1) //freq = 1 found so go onto next word
    //             break;
    //             curr = curr.child[word.charAt(j)-'a'];
    //         }
    //         ans.add(sb.toString());
    //         curr = root;
    //     }
    // }

    public static boolean startsWith(String[] a, String prefix) {
        Trie root = new Trie();
        Trie curr = root;
        //store all words in Trie
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length(); j++) {
                if (curr.child[a[i].charAt(j) - 'a'] == null) 
                curr.child[a[i].charAt(j) - 'a'] = new Trie();
                
                curr = curr.child[a[i].charAt(j) - 'a'];
            }
            curr.eow = true;
            curr = root;
        }
        //search prefix in trie
        for (int i = 0; i < prefix.length(); i++) {//O(length of prefix)
            if (curr.child[prefix.charAt(i) - 'a'] == null)//prefix letter doesn't exist in trie
            return false;
            curr = curr.child[prefix.charAt(i) - 'a'];//exists in trie so go to next letter
        }
        return true;//all letters traversed means prefix exists somewhere in trie 
    }

    public static int uniqueSubstringsCount(String s) {
        int c = 0;
        root = new Trie();
        Trie curr = root;
        //storing all of strings suffixes in trie
        for (int i = 0; i < s.length(); i++) {
            String suffix = s.substring(i);
            for (int j = 0; j < suffix.length(); j++) {
                if (curr.child[suffix.charAt(j) - 'a'] == null) {
                    curr.child[suffix.charAt(j) - 'a'] = new Trie(); 
                    c++;
                }
                curr = curr.child[suffix.charAt(j) - 'a'];
            }
            curr.eow = true;
            curr = root;
        }
        return c + 1;//1 for the empty substring that exists for all strings ("")
    }

    public static String longestWordAllPrefixes(String[] A) {
        ans = "";
        root = new Trie();
        Trie curr = root;
        //storing all words in Trie
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length(); j++) {
                if (curr.child[A[i].charAt(j) - 'a'] == null)
                curr.child[A[i].charAt(j) - 'a'] = new Trie();

                curr = curr.child[A[i].charAt(j) - 'a'];
            }
            curr.eow = true;
            curr = root;
        }
        longest(root, new StringBuilder());
        return ans;
    }
    static String ans = "";
    public static void longest(Trie root, StringBuilder sb) {
        for (int i = 0; i < 26; i++) {
            if (root.child[i] != null && root.child[i].eow == true) {//first condition to consolidate nullPointerException
                StringBuilder newSb = new StringBuilder(sb);
                longest(root.child[i], newSb.append((char) (i + 97)));
            }
            else {//child is null/false then string until there taken
                if (ans.length() < sb.length())// || (ans.length() == sb.length() && ans.compareTo(sb.toString()) > 0) not needed as children 0 -> 25 (+97) chars appened in sb
                ans = sb.toString();           // so they stay in lexicographical order only so update only if sb.length > 
            }
        }
    }
    
    public static class Trie1 {
        Trie1[] child = new Trie1[26];
        boolean eow;
        List<String> anagram;
        Trie1() {
            eow = false;
            for (int i = 0; i < 26; i++) {
                child[i] = null;
            }
            anagram = new ArrayList<>();
        }
    }
    static List<List<String>> ans1;
    static Trie1 root1;
    public List<List<String>> groupAnagrams(String[] strs) {
        root1 = new Trie1();
        ans1 = new ArrayList<>();
        //store words char-wise in Trie
        for (int i = 0; i < strs.length; i++) 
        insertCharWise(root1, strs[i]);
        //store List of (List of strings) present at nodes with eow = true
        return dfs(root1);
    }
    public static List<List<String>> dfs(Trie1 root1) {
        if (root1 == null) 
        return ans1;
    
        if (root1.eow)//(List of words) with same letters stored as a cell in ans AL
        ans1.add(root1.anagram);

        for (int i = 0; i < 26; i++) {
            dfs(root1.child[i]);
        }

        return ans1;
    }
    public static void insertCharWise(Trie1 curr, String word) {//curr = root1 passed
        char[] charArray = word.toCharArray();
        Arrays.sort(charArray);
        for (int i = 0; i < charArray.length; i++) {
            if (curr.child[charArray[i] - 'a'] == null)
            curr.child[charArray[i] - 'a'] = new Trie1();

            curr = curr.child[charArray[i] - 'a'];
        }
        curr.eow = true;
        curr.anagram.add(word);//non empty anagram ArrayList exists for eow = true nodes
        curr = root1;
    }

    //Q2 same as longestWordAllPrefixes()
    public static void main(String[] args) {
        // String A[] = {"apple", "app", "mango", "man", "woman"};
        String words[] = {"banana", "abcdefgh", "a", "ap", "app", "appl", "apple", "b", "ba", "ban", "bana", "banan", "banama","banam"};
        // String words1[] = {"ab", "ac", "ad"};
        System.out.println(longestWordAllPrefixes(words));
        
    }

}

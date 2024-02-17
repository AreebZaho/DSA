import java.util.*;

public class hashing {
    // https://leetcode.com/problems/design-hashmap
    static class HashMap<K, V> {
        private class Node {
            K key;
            V val;
            Node(K key, V val) {
                this.key = key;
                this.val = val;
            }
        }
        private int n;//num of nodes
        private int N;//bucket size
        private LinkedList<Node> buckets[];
        @SuppressWarnings("unchecked")
        public HashMap() {
            this.N = 4;
            this.buckets = new LinkedList[4];
            for (int i = 0; i < 4; i++) {
                this.buckets[i] = new LinkedList<>();
            }
        }
        private int hashFunction(K key) {
            return Math.abs(key.hashCode()) % N;//use Integer.hashCode(key)
        }
        private int searchInLL(K key, int bucketIdx) {
            LinkedList<Node> ll = buckets[bucketIdx];
            for (int i = 0; i < ll.size(); i++) {
                if (ll.get(i).key == key) 
                return i;
            }
            return -1;//reached null but didn't find idx
        }
        @SuppressWarnings("unchecked")
        private void rehash() {
            LinkedList<Node> oldBucket[] = buckets;
            N *= 2;
            buckets = new LinkedList[N];//double size now
            for (int i = 0; i < N; i++) {
                buckets[i] = new LinkedList<>();
            }
            //add the nodes to main buckets array
            for (LinkedList<Node> list : oldBucket) {
                for (Node node : list) {
                    put(node.key, node.val);//can also do .get(i).key, val 
                }//auto rehash is run everytime if n/N > threshold to arrange properly
            }
        }
        public void put(K key, V val) {
            int bucketIdx = hashFunction(key);
            int dataIdx = searchInLL(key, bucketIdx);
            if (dataIdx != -1) {
                buckets[bucketIdx].get(dataIdx).val = val;
            }
            else {//key doesn't exist and create new to add at end of bucketIdx (inc size)
                buckets[bucketIdx].add(new Node(key, val));
                n++;
            }//check if rehashing req
            if ((double) n/N > 2.0) {//threshold 
                rehash();
            }
        }
        public V remove(K key) {
            int bucketIdx = hashFunction(key);
            int dataIdx = searchInLL(key, bucketIdx);
            if (dataIdx != -1) {
                Node node = buckets[bucketIdx].remove(dataIdx);
                n--;
                return node.val;
            }
            else return null;//key doesn't exist to remove 
        }
        public V get(K key) {
            int bucketIdx = hashFunction(key);
            int dataIdx = searchInLL(key, bucketIdx);
            return (dataIdx != -1) ? buckets[bucketIdx].get(dataIdx).val : null;
        }
        public boolean containsKey(K key) {
            int bucketIdx = hashFunction(key);
            int dataIndex = searchInLL(key, bucketIdx);
            return dataIndex != -1;//returns true when node with key exists in HashMap
        }
        public ArrayList<K> keySet() {
            ArrayList<K> keys = new ArrayList<>();
            for (LinkedList<Node> ll : buckets) {
                for (Node node: ll) {
                    if (node != null) 
                    keys.add(node.key);  
                } 
            }
            return keys;
        }
        public V getOrDefault(K key, V val) {
            V existingVal = get(key);
            return (existingVal != null) ? existingVal : val;
        }
        public boolean isEmpty() { return n == 0; }
        public void clear() {
            for (int i = 0; i < N; ++i) {
                buckets[i] = new LinkedList<>();
            }
        }
    }

    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // hm.put(nums[i], hm.getOrDefault(nums[i], 0) + 1);
        }
        for (Integer key : hm.keySet()) 
        if (hm.get(key) > nums.length/2)
        return key;
        return -1;
    }

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length())
        return false;

        HashMap<Character, Integer> hm = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {//means length same if reached here
            // hm.put(s.charAt(i), hm.getOrDefault(s.charAt(i), 0) + 1);
            // hm.put(t.charAt(i), hm.getOrDefault(t.charAt(i), 0) - 1);
        }
        for (Character keys : hm.keySet()) {
            if (hm.get(keys) != 0) 
            return false;
        }
        return true;

        //OR REMOVING KEY METHOD 
        // if (s.length() != t.length())
        // return false;
        // HashMap<Character, Integer> hm = new HashMap<>();
        // for (int i = 0; i < s.length(); i++) {
        //     hm.put(s.charAt(i), hm.getOrDefault(s.charAt(i), 0) + 1);
        // }
        // for (int i = 0; i < t.length(); i++) {
        //     if (hm.containsKey(t.charAt(i))) {
        //         if (hm.get(t.charAt(i)) > 1)
        //         hm.put(t.charAt(i), hm.get(t.charAt(i)) - 1);
        //         else hm.remove(t.charAt(i));
        //     }
        //     else return false;
        // }
        // return hm.isEmpty();
    }

    public static int distinctElements(int[] a) {
        HashSet<Integer> hs = new HashSet<>();
        for (int i : a) {
            hs.add(i);
        }
        return hs.size();
    }

    public static boolean uniqueOccurrences(int[] arr) {
        HashMap<Integer, Integer> hm = new HashMap<>();//has freq of each element 
        for (Integer i : arr) {
            hm.put(i, hm.getOrDefault(i, 0) + 1);
        }
        HashSet<Integer> hs = new HashSet<>();//has the different types of freq numbers
        for (Integer i : hm.keySet()) {//iterating the keys 
            if (hs.contains(hm.get(i)))//if the val of key is alr in hashmap means a element (a key in hm) alr has that freq
            return false;
            hs.add(hm.get(i));//if not means val of key (freq of element) unique and add to hs
        }
        return true;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> hs = new HashSet<>();
        HashSet<Integer> intersect = new HashSet<>();//stores common elements of both
        for (int i : nums1) {//storing 1st array elements in HashSet1
            hs.add(i);
        }
        for (int i : nums2) {//then if 2nd array has those elements then only add to intersect set
            if (hs.contains(i))
            intersect.add(i);
        }
        int ans[] = new int[intersect.size()];
        int x = 0;
        for (Iterator<Integer> i = intersect.iterator(); i.hasNext();) {//transfer all elemnts in intersect set to ans array
            ans[x++] = i.next();
        }
        return ans;
    }

    public static void reconstructItinerary(String[][] tickets) {
        HashSet<String> ends = new HashSet<>();
        for (int i = 0; i < tickets.length; i++) {//storing the end points in a hs 
            ends.add(tickets[i][1]);
        }
        HashMap<String, String> path = new HashMap<>();
        for (int i = 0; i < tickets.length; i++) {//storing all start->end path in hm where key=start, val=end
            path.put(tickets[i][0], tickets[i][1]);
        }
        String[] ans = new String[tickets.length+1];//arrays to store the sequence 
        for (int i  = 0; i < tickets.length; i++) {//looping over start points and checking if any of them is NOT in HashSet (containing ends) because its the first start point
            if (!ends.contains(tickets[i][0])) {
                ans[0] = tickets[i][0];//making first index of array the start point
                break;
            }
        }
        int idx = 0;
        while (path.containsKey(ans[idx])) {
            ans[idx+1] = path.get(ans[idx]);//storing the start's (key) end (val) in next index of array 
            idx++;  //IN THE END WHEN LAST PATH EXTRACTED FROM HM, now ans[idx] represents the last val whose not present as key in HashMap
        }
        for (String i : ans) 
        System.out.print(i + "->");
    }

    public static int maxLen(int arr[], int n) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        int maxLen = 0;
        int sum = 0;
        hm.put(0, -1);
        
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            if (hm.containsKey(sum)) {
                maxLen = Math.max(maxLen, i - hm.get(sum));
                continue;//don't put if a subarray exists with 0 sum, because newer idx will reduce the length for possible later subarray with 0 sum
            }
            hm.put(sum, i);
        }
        return maxLen;
    }

    public static int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        hm.put(0, 1);
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (hm.containsKey(sum - k)) {
                ans += hm.get(sum - k);//if anywhere sum-k matches sum at other place in hm, take it's count
            }
            hm.put(sum, hm.getOrDefault(sum, 0) + 1);//sum uptill each i elements being stored with starting count 1
        }
        return ans;
    }

    static class Node {
        int val; Node left; Node right;
        Node(int _val) {
            val = _val; left = right = null;
        }
    }
    static HashMap<Integer, Node> hm = new HashMap<>();
    static int min, max;
    public static class Info {
        Node node;
        int hd;
        Info(Node node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }
    public ArrayList<Integer> bottomView(Node root) {
        hm.clear();
        min = 0;
        max = 0;//to match root's hd
        ArrayList<Integer> ans = new ArrayList<>();
        bfs(root);
        for (int i = min; i <= max; i++) {
            ans.add(hm.get(i).val);
        }
        return ans;
    }
    public static void bfs(Node root) {
        Queue<Info> q = new LinkedList<>();
        q.add(new Info(root, 0));
        q.add(null);
        Stack<Info> s = new Stack<>();
        while (!q.isEmpty()) {
            Info curr = q.remove();
            if (curr == null) {
                if (q.isEmpty())
                break;
                else q.add(null);
            }
            else {//adding left first in q so later in stack right on top and it's hd added first in HashMap
                s.push(curr);//pushing each removed in stack
                if (curr.node.left != null) {
                    q.add(new Info(curr.node.left, curr.hd-1));
                    min = Math.min(min, curr.hd-1);
                }
                if (curr.node.right != null) {
                    q.add(new Info(curr.node.right, curr.hd+1));
                    max = Math.max(max, curr.hd+1);
                }
            }
        }
        while (!s.isEmpty()) {
            Info curr = s.pop();
            if (!hm.containsKey(curr.hd)) 
            hm.put(curr.hd, curr.node);
        }
    }

    public static String frequencySort(String s) {
        HashMap<Character, Integer> freq = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {//O(n)
            freq.put(s.charAt(i), freq.getOrDefault(s.charAt(i), 0) + 1);
        }
        PriorityQueue<Character> pq = new PriorityQueue<>((a,b) -> freq.get(b) - freq.get(a));//descending order freq sorting
        for (Character keys : freq.keySet()) {//worst- O(n)
            pq.add(keys);//adding the characters based on their freq in dec order 
        }
        StringBuilder ans = new StringBuilder();
        while (!pq.isEmpty()) {//both loops together- O(n)
            char curr = pq.remove();
            for (int i = 0; i < freq.get(curr); i++) {//append char at peek() the number of times its supposed to come
                ans.append(curr);
            }
        }
        return ans.toString();
    }

    //LRU cache Q
    public static void main(String[] args) {
        // Set<String> keys = hm.keySet();
        // for (String k : keys) {
        //     System.out.println("key: " + k + ", val: " + hm.get(k));
        // }
        // System.out.println(hm.entrySet());
        
        // HashSet<Integer> hs = new HashSet<>();
        // hs.add(1);
        // hs.add(2);
        // hs.add(3);
        // Iterator i = hs.iterator();
        // while (i.hasNext()) {
        //     System.out.println(i.next());
        // }
        // for (Integer i1 : hs) {
        //     System.out.println(i1);
        // }

        int[] a = {1};
        System.out.println(subarraySum(a, 0));

        


    }
}
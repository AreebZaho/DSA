import java.util.*;
import java.util.Arrays;

public class recursion {
    public static void dec(int n) {
        if (n == 1) {
            System.out.print(n + " ");
            return;
        }
        System.out.print(n + " ");
        dec(n-1);
    }

    public static void inc(int n) {
        if (n == 1) {
            System.out.print(n + " ");
            return;
        }
        inc(n-1);
        System.out.print(n + " ");
    }

    public static void inc2(int n, int c) {
        if (c ==  n) {
            System.out.print(n + " ");
            return;
        }
        System.out.print(c + " ");
        inc2(n, c+1);
    }

    public static int fact(int n) {
        if (n == 1) {
            return 1;
        }
        return n * fact(n-1);
    }

    public static int sum(int n) {
        if (n == 1) {
            return 1;
        }
        return n + sum(n-1);
    }

    public static int fibonacci(int n) {
        if (n == 0 || n == 1) {
            return n;   
        }
        return fibonacci(n-1) + fibonacci(n-2); 
    }

    public static boolean sorted_arr(int[] a, int index) {
        if (index == a.length - 1) {//base case : index is second_last and index + 1 is last
            return true;
        }
        if (a[index] > a[index + 1]) {//if in the last, second_last element < last_element (means index = second_last) means full array is sorted and next time recursive call
            return false;             //given, index becomes last index which is (a.length-1) means return true
        }  
        return sorted_arr(a, index + 1);//recursive call 
    }

    public static int first_occurence(int[] a, int index, int key) {
        if (key == a[index]) {//base case
            return index;
        }
        if (index == a.length - 1) {//whole array transversed by recursive func and above statement checked if key's first occurence at last index, if not then key not found
            return -1;              //or can put up also with "if index = a.length"
        }
        return first_occurence(a, index + 1, key); 
    }

    public static int last_occurence(int[] a, int index, int key, int ans) {//same as first occurence ut transverse array from behind
        //M-1: reverse approach of first_occurence()
        //M-2: use ans to store the last index key found at 
        if (index == a.length) {
            return ans;//whole array is transversing whole array and whatever ans index we obtained, if key never found even once, ans remains -1
        }
        if (a[index] == key) {//if key found, make ans = that index and pass on 
            return last_occurence(a, index + 1, key, index);
        }
        return last_occurence(a, index + 1, key, ans);//if key not at index, go to next index and keep ans same           
    }

    public static int power(int x, int n) {//M-1: O(n)
        if (n == 0) {
            return 1;
        }
        return x * power(x, n-1);
    }
    public static int power1(int x, int n, int ans) {//M-2: O(logn)
        if (n == 0) {
            return ans;//return ans in the end (also ans initialised by 1 so if 0 power entered, directly 1 returned)
        }
        if ((n & 1) == 1) {//update ans if bit at 0th index is 1
            ans = ans * x; 
        }
        return power1(x*x, (n>>1), ans);//sq x each time with shifting of bits of n and passing the updated/non-updated ans

    }
    public static int power2(int x, int n) {//M-3: O(n) but can be made O(logn)
        if (n == 0) {
            return 1;
        }
        int k = power2(x, n/2);//intermediate step to make O(logn)
        if ((n & 1) == 1) {//odd powers
            return x * k * k;
            // return x * power2(x, n/2) * power2(x, n/2);//makes it O(n)
        }
        else {//even powers
            return k * k;
            // return power2(x, n/2) * power2(x, n/2);//makes it O(n)
        }
    }

    public static int tiling_2x1(int n) {
        if (n == 0 || n == 1) {//only 1 way to place tile in 2x1 space in end and if 0 then 0 
            return 1;
        }
        return tiling_2x1(n-1) + tiling_2x1(n-2);
    }

    public static int tiling_4x1(int n) {
        if (n < 4) {//only 1 way to place tile in 4x1 space in end and if 3/2/1/0 x (4x1) bricks available  
            return 1;
        }
        // if (n == 4) {
        //     return 2;
        // }
        return tiling_4x1(n-1) + tiling_4x1(n-4);
    }

    public static String duplicatesRemoved(String s, int index, StringBuilder sb, boolean map[]) {
        if (index == s.length()) {
            return sb.toString();
        }
        
        if (map[s.charAt(index) - 'a'] == false) {//not present before 
            sb.append(s.charAt(index));
        }
        
        map[(s.charAt(index) - 'a')] = true;//NOW that char is made true in map[] FOR FUTURE stays true for every next recursive call
        
        return duplicatesRemoved(s, index + 1, sb, map);
    }

    public static int friendsPairing(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        return friendsPairing(n-1) + (n-1)*friendsPairing(n-2);
    }

    public static void binaryStrings(int n, int prevPlace, StringBuilder sb) {//n = size of binary string 
        if (n == 0) {
            System.out.println(sb);
            return;
        }
        StringBuilder sb1 = new StringBuilder(sb);
        StringBuilder sb2 = new StringBuilder(sb);
        binaryStrings(n-1, 0, sb1.append("0"));
        if (prevPlace == 0) {
            binaryStrings(n-1, 1, sb2.append("1"));  
        }
    }

    public static void Q1(int a[], int index, int key) {
        if (index == a.length) {
            System.out.println();
            return;
        }
        if (a[index] == key) {
            System.out.print(index + ", ");
        }
        Q1(a, index + 1, key);
    }

    public static void Q3(String s, int l) {
        if (l == s.length()) {
            System.out.println(l);
            return;
        }
        Q3(s, l+1);
    }

    // public static void Q4(String s, int index, StringBuilder sb) {
    //     if (index == s.length()) {
    //         return;
    //     }
    //     if (s.charAt(index) == sb.charAt(0)) {
    //         System.out.println(sb.toString());
    //     }
    //     StringBuilder sb1 = new StringBuilder();
    //     Q4(s, index+1, sb1);
    // }

    public static void Q5towerOfHanoi(int n, char a, char b, char c) {
        if (n == 0) {
            return;
        }
        System.out.println("from " + a + " to " + c);
        System.out.println("from " + a + " to " + b);
        System.out.println("from " + c + " to " + b);
        System.out.println("from " + a + " to " + c);
        Q5towerOfHanoi(n-1, b, a, c);

        
    }
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        inc(5); System.out.println();
        inc2(5, 1); System.out.println();
        dec(5); System.out.println();
        System.out.println(fact(5));
        System.out.println(sum(5));
        System.out.println(fibonacci(7));
        int[] a = {0,1,2,3,2};
        System.out.println(sorted_arr(a, 0));
        int[] a1 = {8,3,6,5,3,2};
        System.out.println(first_occurence(a1, 0, 3));
        int[] a2 = {8,3,6,5,3,2,3,1};
        System.out.println(last_occurence(a2, 0, 3, -1));
        System.out.println(power1(3,5,1));
        System.out.println(power2(3,4));
        System.out.println(tiling_2x1(4));//do for 4x1 tiling also 
        System.out.println(tiling_4x1(6));
        StringBuilder sb = new StringBuilder();
        boolean map[] = new boolean[26];
        Arrays.fill(map, false);//initialise array with false
        System.out.println(duplicatesRemoved("aabccabcddeefgdefghii", 0, sb, map));
        System.out.println(friendsPairing(4));
        binaryStrings(3, 0, new StringBuilder());

        Q1(a2, 0, 3);
        Q3("abcdef", 0);
        // Q4("abcab", 0, new StringBuilder());
        Q5towerOfHanoi(3, 'a', 'b', 'c');
        sc.close();
    }
}
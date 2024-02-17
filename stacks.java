import java.util.*;

public class stacks {
    // static class Stack {//using arraylists
    // static ArrayList<Integer> s = new ArrayList<>();
    // Stack(ArrayList<Integer> a) {//not needed
    // s = a;
    // }
    // public static boolean isEmpty() {
    // return s.size() == 0;
    // }
    // public static void push(int data) {
    // s.add(data);
    // }
    // public static int pop() {
    // if (s.isEmpty()) {
    // return -1;//represents stack is empty
    // }
    // int top = s.get(s.size() - 1);
    // s.remove(s.size() - 1);
    // return top;
    // }
    // public static int peek() {
    // if (s.isEmpty()) {
    // return -1;//can't see top element of stack if stack is empty
    // }
    // return s.get(s.size() - 1);
    // }
    // }

    // static class Node {//using linkedlist
    // int data;
    // Node next;
    // Node(int data) {
    // this.data = data;
    // this.next = null;
    // }
    // }
    // static class Stack {
    // public static Node head = null;
    // public static boolean isEmpty() {
    // return head == null;
    // }
    // public static void push(int data) {//head is always top of stack as acccessed
    // in O(1)
    // Node newNode = new Node(data);
    // if (isEmpty()) {
    // head = newNode;
    // return;
    // }
    // newNode.next = head;
    // head = newNode;
    // }
    // public static int pop() {
    // if (isEmpty()) {
    // return -1;
    // }
    // int top = head.data;
    // head = head.next;
    // return top;
    // }
    // public static int peek() {
    // if (head == null) {
    // return -1;
    // }
    // return head.data;
    // }
    // }

    public static void addBottom(Stack<Integer> s, int num) {
        // base case
        if (s.isEmpty()) {
            s.push(num);
            return;
        }
        // work
        int curr = s.pop();// pops the value and stores it in curr also
        // recursive call
        addBottom(s, num);
        s.push(curr);// like backtrack step
    }

    public static String reverseString(String str) {
        Stack<Character> s = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            s.push(str.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        while (!s.isEmpty()) {
            sb.append(s.pop());// appends and removes from top as well
        }
        return sb.toString();
    }

    public static void reverseStack(Stack<Integer> s) {
        // base case
        if (s.isEmpty()) {
            return;
        }
        // work
        int curr = s.pop();
        // recursive call
        reverseStack(s);
        addBottom(s, curr);// backtrack step
    }

    public static void stockSpan(int[] stocks, int[] span) {
        Stack<Integer> s = new Stack<>();
        // s.push(0);
        // span[0] = 1;

        for (int i = 0; i < span.length; i++) {
            while ((!s.isEmpty()) && stocks[i] >= stocks[s.peek()]) {
                s.pop();
            }
            if (s.isEmpty()) {
                span[i] = i + 1;
            } else
                span[i] = i - s.peek();
            s.push(i);
        }
    }

    public static void nextGreater(int[] a, int[] auxi) {
        Stack<Integer> s = new Stack<>();
        // s.push(a[a.length-1]);
        // g[g.length-1] = -1;

        for (int i = a.length - 1; i >= 0; i--) {
            while ((!s.isEmpty()) && s.peek() <= a[i]) {// want next greater so if = pop that out, it's not needed
                s.pop();
            }
            if (s.isEmpty()) {
                auxi[i] = -1;
            } else
                auxi[i] = s.peek();
            s.push(a[i]);
        }
    }

    public static boolean validParentheses(String str) {
        Stack<Character> s = new Stack<>();
        s.push(str.charAt(0));

        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if ((!s.isEmpty()) && ((ch == 41 && s.peek() == 40) || (ch == 93 && s.peek() == 91)
                    || (ch == 125 && s.peek() == 123))) {
                s.pop();
            } else
                s.push(str.charAt(i));
        }

        return s.isEmpty();
    }

    public static boolean duplicateParentheses(String str) {
        Stack<Character> s = new Stack<>();
        // s.push(str.charAt(0));

        for (int i = 0; i < str.length(); i++) {
            if (!s.isEmpty() && str.charAt(i) == ')') {
                if (s.peek() == '(') {
                    return true;
                } else {
                    while (s.peek() != '(') {
                        s.pop();// popping out all other chars
                    }
                    s.pop();// pop out the (
                }
            } else
                s.push(str.charAt(i));
        }
        return false;
    }

    public static void prevSmaller(int[] a, int[] auxi) {
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < a.length; i++) {
            while ((!s.isEmpty()) && a[s.peek()] >= a[i]) {
                s.pop();
            }
            if (s.isEmpty()) {
                auxi[i] = -1;
            } else
                auxi[i] = s.peek();
            s.push(i);
        }
    }

    public static void nextSmaller(int[] a, int[] auxi) {
        Stack<Integer> s = new Stack<>();
        for (int i = a.length - 1; i >= 0; i--) {
            while ((!s.isEmpty()) && a[s.peek()] >= a[i]) {
                s.pop();
            }
            if (s.isEmpty()) {
                auxi[i] = a.length;// stack empty means all elements greater only so width for rectangle is
                                   // arraylength - the index we are at + 1
            } else
                auxi[i] = s.peek();
            s.push(i);
        }
    }

    public static void AreaHistogram(int a[]) {// O(3n), S(n)
        int[] auxiLeft = new int[a.length];
        int[] auxiRight = new int[a.length];
        prevSmaller(a, auxiLeft);
        nextSmaller(a, auxiRight);
        System.out.println("area of histograms: ");
        for (int i = 0; i < a.length; i++) {
            System.out.print((auxiRight[i] - auxiLeft[i] - 1) * (a[i]) + " ");
        }
    }

    public static void main(String args[]) {
    }
}

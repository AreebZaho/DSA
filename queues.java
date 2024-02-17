import java.util.*;

public class queues {
    static class QueueA {
        static int[] a;
        static int size;
        static int rear;
        static int front;

        QueueA(int n) {
            a = new int[n];
            size = n;
            rear = -1;
            front = -1;
        }

        public static boolean isEmpty() {
            return rear == -1 && front == -1;
        }

        public static boolean isFull() {
            return ((rear + 1) % size == front);
        }

        public static void add(int data) {
            if (rear == size - 1) {
                System.out.println("queue is full");
                return;
            }
            if (front == -1) {// first element being added
                front = 0;
            }
            rear = (rear + 1) % size;
            a[rear] = data;
        }

        public static int remove() {
            if (isEmpty()) {
                System.out.println("empty queue");
                return -1;
            }
            int frontElement = a[front];
            if (rear == front) {// if only one element in queue
                rear = front = -1;
            } else
                front = (front + 1) % size;
            return frontElement;
        }

        public static int peek() {
            if (isEmpty()) {
                System.out.println("empty queue");
                return -1;
            }
            return a[front];
        }
    }

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static class QueueB {
        static Node head;
        static Node tail;

        public static boolean isEmpty() {
            return head == null && tail == null;
        }

        public static void add(int data) {
            Node newNode = new Node(data);
            if (isEmpty()) {
                head = tail = newNode;
                return;
            }
            tail.next = newNode;
            tail = newNode;
        }

        public static int remove() {
            if (isEmpty()) {
                System.out.println("empty queue");
                return -1;
            }
            int val = head.data;
            if (head == tail) {
                head = tail = null;
            } else
                head = head.next;
            return val;
        }

        public static int peek() {
            if (isEmpty()) {
                System.out.println("empty queue");
                return -1;
            }
            return head.data;
        }
    }

    static class QueueC {
        static Stack<Integer> main = new Stack<>();
        static Stack<Integer> auxi = new Stack<>();

        public static boolean isEmpty() {
            return main.isEmpty();
        }

        public static void add(int data) {// O(1)
            main.push(data);
        }

        public static int remove() {// O(n)
            if (isEmpty()) {
                return -1;
            }
            while (!main.isEmpty()) {
                auxi.push(main.pop());
            }
            int val = auxi.pop();
            while (!auxi.isEmpty()) {
                main.push(auxi.pop());
            }
            return val;
        }

        public static int peek() {// O(n)
            if (isEmpty()) {
                return -1;
            }
            while (!main.isEmpty()) {
                auxi.push(main.pop());
            }
            int val = auxi.peek();
            while (!auxi.isEmpty()) {
                main.push(auxi.pop());
            }
            return val;
        }
    }

    static class QueueD {
        static Stack<Integer> main = new Stack<>();
        static Stack<Integer> auxi = new Stack<>();

        public static boolean isEmpty() {
            return main.isEmpty();
        }

        public static void add(int data) {// O(n)
            while (!main.isEmpty()) {
                auxi.push(main.pop());
            }
            main.push(data);
            while (!auxi.isEmpty()) {
                main.push(auxi.pop());
            }
        }

        public static int remove() {// O(1)
            if (isEmpty()) {
                return -1;
            }
            return main.pop();
        }

        public static int peek() {// O(1)
            if (isEmpty()) {
                return -1;
            }
            return main.peek();
        }
    }

    static class StackA {
        static Queue<Integer> q1 = new LinkedList<>();
        static Queue<Integer> q2 = new LinkedList<>();

        public static boolean isEmpty() {
            return q1.isEmpty() && q2.isEmpty();
        }

        public static void push(int data) {
            if (q1.isEmpty()) {// insert in q which isn't empty
                q2.add(data);
            } else
                q1.add(data);
        }

        public static int pop() {
            if (isEmpty()) {
                System.out.println("empty stack");
                return -1;
            }
            int val = -1;
            if (q1.isEmpty()) {
                while (!q2.isEmpty()) {
                    val = q2.remove();// keeps storing removed value until the queue is empty and in the end
                    if (q2.isEmpty()) {
                        break;// as we just want value of last queue element (top of stack) and don't want to
                              // store it in other queue as to be popped out
                    }
                    q1.add(val);
                }
            } else {// means q2 is empty and q1 has the elements
                while (!q1.isEmpty()) {
                    val = q1.remove();
                    if (q1.isEmpty()) {
                        break;
                    }
                    q2.add(val);
                }
            }
            return val;
        }

        public static int peek() {// same as pop but adding the last element of queue (peek) to other queue
                                  // instead of just removing it like pop when primary queue got empty
            if (isEmpty()) {
                System.out.println("stack empty");
                return -1;
            }
            int val = -1;
            if (q1.isEmpty()) {
                while (!q2.isEmpty()) {
                    val = q2.remove();
                    q1.add(val);
                }
            } else {// means q2 is empty
                while (!q1.isEmpty()) {
                    val = q1.remove();
                    q2.add(val);
                }
            }
            return val;
        }

    }

    public static void firstNonRepeating(String s) {
        Queue<Character> q = new LinkedList<>();
        int[] freq = new int[26];
        Arrays.fill(freq, 0);
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 97]++;
            q.add(s.charAt(i));
            while (!q.isEmpty() && freq[q.peek() - 97] > 1) {
                q.remove();
            }
            if (q.isEmpty()) {
                System.out.println(-1);
            } else
                System.out.println(q.peek());
        }
    }

    public static void interleaveQueues(Queue<Integer> q) {
        Queue<Integer> firstHalf = new LinkedList<>();
        int size = q.size();
        for (int i = 0; i < size / 2; i++) {
            firstHalf.add(q.remove());// has starting half nums
        }
        while (!firstHalf.isEmpty()) {
            q.add(firstHalf.remove());
            q.add(q.remove());
        }
        while (!q.isEmpty()) {// printing final queue
            System.out.println(q.remove());
        }
    }

    public static void reverseQueue(Queue<Integer> q) {
        Stack<Integer> s = new Stack<>();
        while (!q.isEmpty()) {
            s.push(q.remove());
        }
        while (!s.isEmpty()) {
            q.add(s.pop());
        }
        while (!q.isEmpty()) {
            System.out.println(q.remove());
        }
    }

    static class StackByDeque {
        static Deque<Integer> deq = new LinkedList<>();

        public static boolean isEmpty() {
            return deq.isEmpty();
        }

        public static void push(int data) {
            deq.addLast(data);
        }

        public static int pop() {
            return deq.removeLast();
        }

        public static int peek() {
            return deq.peekLast();
        }
    }

    static class QueueByDeque {
        static Deque<Integer> deq = new LinkedList<>();

        public static boolean isEmpty() {
            return deq.isEmpty();
        }

        public static void add(int data) {
            deq.addLast(data);
        }

        public static int remove() {
            return deq.removeFirst();
        }

        public static int peek() {
            return deq.peekFirst();
        }
    }

    // Asg

    // https://practice.geeksforgeeks.org/problems/generate-binary-numbers-1587115620/1?utm_source=geeksforgeeks&utm_medium=article_practice_tab&utm_campaign=article_practice_tab

    // https://practice.geeksforgeeks.org/problems/minimum-cost-of-ropes-1587115620/1?utm_source=geeksforgeeks&utm_medium=ml_article_practice_tab&utm_campaign=article_practice_tab

    // https://practice.geeksforgeeks.org/problems/job-sequencing-problem-1587115620/1?utm_source=geeksforgeeks&utm_medium=ml_article_practice_tab&utm_campaign=article_practice_tab

    // https://practice.geeksforgeeks.org/problems/reverse-first-k-elements-of-queue/1?utm_source=geeksforgeeks&utm_medium=ml_article_practice_tab&utm_campaign=article_practice_tab

    // https://leetcode.com/problems/sliding-window-maximum/

    public static void main(String args[]) {
    }
}
import java.util.*;

public class heaps {
    static class Student implements Comparable<Student>{
        String name;
        int rank;
        public Student(String name, int rank) {
            this.name = name;
            this.rank = rank;
        }
        @Override 
        public int compareTo(Student s2) {
            return s2.rank - this.rank;
        }
    }
    static class Heap {
        ArrayList<Integer> a = new ArrayList<>();
        public boolean isEmpty() {
            return a.size() == 0;
        }

        public void add(int data) {
            a.add(data);
            int childIdx = a.size()-1;
            int parentIdx = (childIdx - 1)/2;
            while ((parentIdx >= 0) && a.get(childIdx) < a.get(parentIdx)) {
                int temp = a.get(childIdx);
                a.set(childIdx, a.get(parentIdx));
                a.set(parentIdx, temp);
                childIdx = parentIdx;
                parentIdx = (childIdx-1)/2;
            }
        }

        public int peek() {
            return a.get(0);
        }

        public int remove() {
            //swap first and last
            int temp = a.get(0);
            a.set(0, a.get(a.size()-1));
            a.set(a.size()-1, temp);
            //delete last
            a.remove(a.size()-1);
            heapifyMinHeap(0);//heapify by sending first element (root)
            return temp;//return deleted value
        }
        public void heapifyMinHeap(int idx) {
            int left = idx*2 + 1;
            int right = idx*2 + 2;
            int min = idx; 
            if ((left < a.size() && right < a.size())) {//both left, right not null
                if (a.get(idx) > a.get(left) && a.get(idx) > a.get(right)) {//greater than both left, right so pick smaller of the 2
                    if (a.get(left) < a.get(right))
                    min = left;
                    else min = right;
                }
                else if (a.get(idx) > a.get(right))//greater than only right so pick it
                min = right;
                else if (a.get(idx) > a.get(left))//greater than only left so pick it
                min = left;
                //else less than both so let it stay = idx only
            }
            // else if (right < a.size() && a.get(idx) > a.get(right)) {//left null is never true for a CBT (complete binary tree)
            //     min = right;
            // }
            else if (left < a.size() && a.get(idx) > a.get(left)) {//right null + if idx < right element stays idx
                min = left;
            }
            //else both left, right = null and min = idx
            if (min != idx) {
                int temp = a.get(idx);
                a.set(idx, a.get(min));
                a.set(min, temp);
                heapifyMinHeap(min);
            }           
        }
        
        //+ requires separate add func and separate delete func exclusive with reverse >/<
        public void heapifyMaxHeap(int idx) {
            int left = idx*2 + 1;
            int right = idx*2 + 2;
            int max = idx; 
            if ((left < a.size() && right < a.size())) {//both left, right not null
                if (a.get(idx) < a.get(left) && a.get(idx) < a.get(right)) {//greater than both left, right so pick smaller of the 2
                    if (a.get(left) > a.get(right))
                    max = left;
                    else max = right;
                }
                else if (a.get(idx) < a.get(right))//greater than only right so pick it
                max = right;
                else if (a.get(idx) < a.get(left))//greater than only left so pick it
                max = left;
                //else less than both so let it stay = idx only
            }
            // else if (right < a.size() && a.get(idx) < a.get(right)) {//left null is never true for a CBT (complete binary tree)
            //     max = right;
            // }
            else if (left < a.size() && a.get(idx) < a.get(left)) {//right null + if idx < right element stays idx 
                max = left;
            }
            //else both left, right = null and min = idx
            if (max != idx) {
                int temp = a.get(idx);
                a.set(idx, a.get(max));
                a.set(max, temp);
                heapifyMaxHeap(max);
            }           
        }
    }

    public static void heapSortAscending(int[] a) {
        //step 1 to create a max heap first
        for (int i = (a.length/2)-1; i >= 0; i--) {
            heapifyMaxAsc(a, i, a.length);
        }
        //step 2 to push largest end and heapify again for -1 elements each time
        for (int i = a.length-1; i > 0; i--) {
            int temp = a[0];
            a[0] = a[i];
            a[i]= temp;
            heapifyMaxAsc(a, 0, i);
        }
    }
    public static void heapifyMaxAsc(int[] a, int idx, int size) {
        int left = idx*2 + 1;
        int right = idx*2 + 2;
        int max = idx; 
        if ((left < size && right < size)) {
            if (a[idx] < a[left] && a[idx] < a[right]) {
                if (a[left] > a[right])
                max = left;
                else max = right;
            }
            else if (a[idx] < a[right])
            max = right;
            else if (a[idx] < a[left])
            max = left;
        }
        else if (left < size && a[idx] < a[left]) {
            max = left;
        }
        if (max != idx) {
            int temp = a[idx];
            a[idx] = a[max];
            a[max] = temp;
            heapifyMaxAsc(a, max, size);
        }
    }

    public static void heapSortDescending(int[] a) {
        //step 1 to create a max heap first
        for (int i = (a.length/2)-1; i >= 0; i--) {
            heapifyMinDes(a, i, a.length);
        }
        //step 2 to push largest end and heapify again for -1 elements each time
        for (int i = a.length-1; i > 0; i--) {
            int temp = a[0];
            a[0] = a[i];
            a[i]= temp;
            heapifyMinDes(a, 0, i);
        }
    }
    public static void heapifyMinDes(int[] a, int idx, int size) {
        int left = idx*2 + 1;
            int right = idx*2 + 2;
            int min = idx; 
            if ((left < size && right < size)) {
                if (a[idx] > a[left] && a[idx] > a[right]) {
                    if (a[left] < a[right])
                    min = left;
                    else min = right;
                }
                else if (a[idx] > a[right])
                min = right;
                else if (a[idx] > a[left])
                min = left;
            }
            else if (left < size && a[idx] > a[left]) {
                min = left;
            }
            if (min != idx) {
                int temp = a[idx];
                a[idx] = a[min];
                a[min] = temp;
                heapifyMinDes(a, min, size);
            }
    }

    static class Info implements Comparable<Info>{
        int x;
        int y;
        int dist;
        int idx;
        Info(int x, int y, int dist, int idx) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.idx = idx;
        }
        @Override 
        public int compareTo(Info point) {//ascending order in terms of distance
            return this.dist - point.dist;
        }
    }
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<Info> ans = new PriorityQueue<>();
        for (int i = 0; i < points.length; i++) {
            ans.add(new Info(points[i][0], points[i][1], points[i][0]*points[i][0] + points[i][1]*points[i][1], i));
        }

        int[][] a = new int[k][2];//to store the first k points added in priorityQueue

        for (int i = 0; i < k; i++) {//k points mean 0 -> k-1
            a[i][0] = ans.peek().x;
            a[i][1] = ans.peek().y;  
            ans.remove();
        }
        return a;
    }

    long minCost(long arr[], int n) {
        if (n == 1)
        return 0;
        
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            pq.add(arr[i]);
        }
        long sum = 0;
        while (!pq.isEmpty()) {
            long num1 = pq.remove();
            sum += num1;
            long num2 = pq.remove();
            sum += num2;
            if (pq.isEmpty()) {//if after removal of 2 last joining ropes cost, pq becomes empty then exit otherwise if a price exists then add sum of these 2 prices in it
                break;
            }
            pq.add(num1 + num2);//then pq would have this and another price and after another loop execution, those 2 also out 
        }
        return sum;
    }

    public static class Row implements Comparable<Row> {
        int soldiers;
        int idx;
        Row(int soldiers, int idx) {
            this.soldiers = soldiers;
            this.idx = idx;
        }
        @Override 
        public int compareTo(Row r2) {
            if (this.soldiers == r2.soldiers) {
                return this.idx - r2.idx;//if equal soldiers in both rows then ascending order of row indexes
            }
            return this.soldiers - r2.soldiers;//sorted in ascending order of soldiers
        }
    }
    public int[] kWeakestRows(int[][] mat, int k) {
        PriorityQueue<Row> rows = new PriorityQueue<>();

        for (int i = 0; i < mat.length; i++) {
            int soldiers = 0;
            for (int j = 0; j < mat[i].length; j++) {//finding num of soldiers/row
                if (mat[i][j] == 1) 
                soldiers++;
            }
            rows.add(new Row(soldiers, i));
        }

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {//storing first k sorted in ans[]
            ans[i] = rows.remove().idx;
        }
        return ans;
    }

    public static class Info1 implements Comparable<Info1> {
        int val;
        int idx;
        public Info1(int val, int idx) {//stores index, value of element from array
            this.val = val;
            this.idx = idx;
        }
        @Override
        public int compareTo(Info1 i2) {
            return i2.val - this.val;//descending order needed
        }
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<Info1> pq = new PriorityQueue<>();
        int[] ans = new int[nums.length-k+1];//to store the max of k elements from (a.len-k+1) segments of arr

        for (int i = 0; i < k; i++) {//first store the k elements in pq
            pq.add(new Info1(nums[i], i));
        }
        ans[0] = pq.peek().val;//and max of the first segment from array of k elements put in ans

        for (int i = k; i < nums.length; i++) {//for the OTHER segments' LAST INDEXES 
            while (!pq.isEmpty() && pq.peek().idx < i-k+1) {//empty condition for corner case of k = 1 
                pq.remove();//removing elements in pq.peek() that don't lie in the selected segment's range
            }
            pq.add(new Info1(nums[i], i));//then add the current segment's last element in pq
            ans[i-k+1] = pq.peek().val;//then add pq.peek() which is max for current segment and add in ans (idx from 1 -> end)
        }

        return ans;
    }
    
    //q1 is a concept 

    public static int minTimeNSlots(int[] a) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < a.length-1; i++) {
            pq.add(Math.abs(a[i] - a[i+1]));
        }
        return pq.remove()/2;
    }

    public int halveArray(int[] nums) {
        PriorityQueue<Double> pq = new PriorityQueue<>(Comparator.reverseOrder());
        double origSum = 0;
        for (int i = 0; i < nums.length; i++) {
            pq.add((double) nums[i]);
            origSum += nums[i];
        }
        int steps = 0;
        double newSum = origSum;
        double curr = 0;
        while (newSum > origSum/2) {
            curr = pq.remove()/2;
            newSum -= curr;
            pq.add(curr);
            steps++;
        }
        return steps;
    }




    public static void main(String args[]) {
        // PriorityQueue<Student> pq = new PriorityQueue<>();//Comparator.reverseOrder()
        // Student s3 = new Student("areeb", 94);
        // Student s1 = new Student("areeb", 100);
        // Student s2 = new Student("areeb", 98);
        // pq.add(s1);
        // pq.add(s2);
        // pq.add(s3);
        // System.out.println(pq.remove().rank);
        // System.out.println(pq.remove().rank);
        // System.out.println(pq.peek().rank);

        // Heap h = new Heap();
        // h.add(4);
        // h.add(3);
        // h.add(1);
        // h.add(2);
        // while (!h.isEmpty()) {
        //     System.out.println(h.remove());
        // }
        int[] a = {7,5,6,3,4,2,1,0,9,7,8,4,6,0,1,3,3,6,2};
        heapSortAscending(a);
        for (int el : a) System.out.print(el+" ");

        


    }
}

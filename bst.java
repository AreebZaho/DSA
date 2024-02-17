import java.util.*;
public class bst {
    static class Node {
        int data;
        Node left;
        Node right;
        Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    public static Node insert(Node root, int val) {
        if (root == null) {
            return new Node(val);
        }
        if (root.data > val) {
            root.left = insert(root.left, val);
        }
        else root.right = insert(root.right, val);
        return root;
    }

    public static void inorder(Node root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public static boolean search(Node root, int key) {
        if (root == null) {
            return false;
        }
        if (root.data == key) {
            return true;
        }
        if (key > root.data) {
            return search(root.right, key);
        }
        else return search(root.left, key);
    }

    public static Node delete(Node root, int key) {
        if (root == null) {
            System.out.println("Node doesn't exist to delete");
            return null;
        }
        if (root.data == key) {
            if (root.left == null && root.right == null)
            return null;
            else if (root.left == null) 
            return root.right;
            else if (root.right == null) 
            return root.left;
            else return delHelper(root);//when both child nodes not null
        }
        if (key > root.data) {
            root.right = delete(root.right, key);
        }
        else root.left = delete(root.left, key);
        return root;
    }
    public static Node delHelper(Node root) {//key node to be deleted
        Node succLeft = root.right;
        while (succLeft.left != null) {
            succLeft = succLeft.left;
        }
        succLeft.left = root.left;
        root.left = null;
        Node key = root;
        root = root.right;
        key.right = null;
        return root;
    }

    public static void printRange(Node root, int k1, int k2) {
        if (root == null) {
            return;
        }
        if (root.data >= k1 && root.data <= k2) {
            printRange(root.left, k1, k2);
            System.out.print(root.data + " ");
            printRange(root.right, k1, k2);
        }
        if (root.data < k1) {
            printRange(root.right, k1, k2);
        }
        if (root.data > k2) {
            printRange(root.left, k1, k2);
        }
    }

    static ArrayList<Integer> a1 = new ArrayList<>();
    public static void leafPaths(Node root) {
        if (root == null) {
            // System.out.println("empty tree");
            return;
        }
        a.add(root.data);
        if (root.left == null && root.right == null) {
            for (int i = 0; i < a.size(); i++) {
                System.out.print(a.get(i) + " ");
            }
            System.out.println();
            // a.remove(a.size()-1);
            // return;
        }
        // else if (root.left == null) {
        //     leafPaths(root.right);
        //     a.remove(a.size()-1);
        // }
        // else if (root.right == null) {
        //     leafPaths(root.left);
        //     a.remove(a.size()-1);
        // }
        // else {
            leafPaths(root.left);
            // a.remove(a.size()-1);
            leafPaths(root.right);
            a.remove(a.size()-1);
        // }
    }


    public boolean isValidBST(Node root) {
        if (root == null) {
            return true;
        }
        return valid(root.left, root.data, Integer.MIN_VALUE) && valid(root.right, Integer.MAX_VALUE, root.data);
    }   
    public boolean valid(Node root, int max, int min) {
        if (root == null) {
            return true;
        }
        if (root.data < max && root.data > min) {
            return valid(root.left, root.data, min) && valid(root.right, max, root.data);
        }
        else return false;
    }
    //ALT (top to bottom using TreeNode null instead of long MAX,MIN_VAL)
    // public boolean isValidBST(TreeNode root) {
    //     if (root == null) {
    //         return true;
    //     }
    //     return valid(root.left, root, null) && valid(root.right, null, root);
    // }   
    // public boolean valid(TreeNode root, TreeNode max, TreeNode min) {
    //     if (root == null) {
    //         return true;
    //     }
    //     if (min == null) {//in left
    //         if (root.val < max.val)
    //         return valid(root.left, root, min) && valid(root.right, max, root);
    //         else return false;
    //     }
    //     else if (max == null) {//in right
    //         if (min.val < root.val)
    //         return valid(root.left, root, min) && valid(root.right, max, root);
    //         else return false;
    //     }
    //     else {
    //         if (root.val < max.val && root.val > min.val) 
    //         return valid(root.left, root, min) && valid(root.right, max, root);
    //         else return false;
    //     }
    // }

    public static Node sortedArrayToBST(int[] nums) {
        return help(null, 0, nums.length-1, nums);
    }
    public static Node help(Node root, int s, int e, int[] nums) {
        if (s > e) {
            return null;
        }
        int mid = s + (e-s)/2;
        root = new Node(nums[mid]);
        root.left = help(root, s, mid-1, nums);
        root.right = help(root, mid+1, e, nums);
        return root;
    }

    public Node balanceBST(Node root) {
        if (root == null) {
            return null;
        }
        ArrayList<Node> info = new ArrayList<>();
        inorder(root, info);
        return newBST(root, 0, info.size()-1, info);
    }
    public static Node newBST(Node root, int s, int e, ArrayList<Node> info) {
        if (s > e) {
            return null;
        }
        int mid = s + (e-s)/2;
        root = info.get(mid);
        root.left = newBST(root.left, s, mid-1, info);
        root.right = newBST(root.right, mid+1, e, info);
        return root;
    }
    public static void inorder(Node root, ArrayList<Node> info) {
        if (root == null) {
            return;
        }
        inorder(root.left, info);
        info.add(root);
        inorder(root.right, info);
        return;
    }

    static class Info {
        boolean valid;
        int size;
        int max;
        int min;
        Info(boolean valid, int size, int max, int min) {
            this.valid = valid;
            this.size = size;
            this.max = max;
            this.min = min;
        }
    }
    public static int maxSize;
    public static Info largestBST(Node root) {//going bottom to top as size is needed
        if (root == null) {
            return new Info(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }
        Info left = largestBST(root.left);
        Info right = largestBST(root.right);
        
        if (left.max == Integer.MAX_VALUE && left.min == Integer.MIN_VALUE && right.max == Integer.MAX_VALUE && right.min == Integer.MIN_VALUE) {//both subtree null
            maxSize = Math.max(maxSize, 1);
            return new Info(true, 1, root.data, root.data);
        }
        else if (left.max == Integer.MAX_VALUE && left.min == Integer.MIN_VALUE) {//left subtree null
            if (right.valid && root.data < right.min)//entire valid condition
            maxSize = Math.max(maxSize, 1+right.size);
            return new Info(right.valid && (root.data < right.min), 1+right.size, Math.max(root.data, right.max), Math.min(root.data, right.min));
        }
        else if (right.max == Integer.MAX_VALUE && right.min == Integer.MIN_VALUE) {//right subtree null
            if (left.valid && left.max < root.data)//entire valid condition
            maxSize = Math.max(maxSize, 1+left.size);
            return new Info(left.valid && (left.max < root.data), 1+left.size, Math.max(root.data, left.max), Math.min(root.data, left.min));
        }
        else {
            int max = Math.max(Math.max(left.max, right.max), root.data);
            int min = Math.min(Math.min(left.min, right.min), root.data);
            boolean valid = ((left.valid && right.valid) && (left.max < root.data) && (root.data < right.min));
            if (valid)//entire valid condition
            maxSize = Math.max(maxSize, 1+left.size+right.size);    
            return new Info(valid, 1+left.size+right.size, max, min);
        }
	}

    public int rangeSumBST(Node root, int low, int high) {
        if (root == null) {
            return 0;
        }
        if (low <= root.data && root.data <= high) {
            return root.data + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
        }
        else if (root.data > high) {
            return rangeSumBST(root.left, low, high);
        }
        else {
            return rangeSumBST(root.right, low, high);
        }
    }

    static int min = Integer.MAX_VALUE;
    static int minDiff(Node  root, int K) { 
        if (root == null) {
            return K;
        }
        helper(root, K);
        return min;
    } 
    public static void helper(Node root, int k) {
        if (root == null) {
            return;
        }
        min = Math.min(min, Math.abs(root.data - k));
        if (k == root.data)
        return;
        else if (root.data > k) {
            helper(root.left, k);
        }
        else helper(root.right, k);
    }

    public static int i;
    public static int ans;
    public int kthSmallest(Node root, int k) {
        ans = -1;
        i = 0;
        help(root, k);
        return ans;
    }
    public static void help(Node root, int k) {
        if (ans != -1) {//means found and just return from everywhere
            return;
        }
        if (root == null) {
            return;
        }
        //inorder traversal
        help(root.left, k);
        i++;
        if (i == k) {
            ans = root.data;
            return;
        }
        help(root.right, k);
    }

    public static class Info1 {
        boolean valid;
        int sum;
        int max;
        int min;
        Info1(boolean valid, int sum, int max, int min) {
            this.valid = valid;
            this.sum = sum;
            this.max = max;
            this.min = min;
        }
    }
    static int maxSum;
    public int maxSumBST(Node root) {
        maxSum = 0;
        help(root);
        return maxSum;
    }
    public static Info1 help(Node root) {//first going in depth then backtrack to top
        if (root == null) {
            return new Info1(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }
        Info1 left = help(root.left);
        Info1 right = help(root.right);

        if (left.max == Integer.MAX_VALUE && right.max == Integer.MAX_VALUE) {//both left right null and need to check only max/min
            maxSum = Math.max(maxSum, root.data + left.sum + right.sum);//leaf node, always true, nothing to check

            return new Info1(true, root.data, root.data, root.data);
        }
        else if (left.max == Integer.MAX_VALUE) {//left only null
            if (right.valid && root.data < right.min)
            maxSum = Math.max(maxSum, root.data + left.sum + right.sum);

            return new Info1(right.valid && root.data < right.min, right.sum+root.data, Math.max(root.data, right.max), 
            Math.min(root.data, right.min));
        }
        else if (right.max == Integer.MAX_VALUE) {//right only null
            if (left.valid && root.data > left.max)
            maxSum = Math.max(maxSum, root.data + left.sum + right.sum);

            return new Info1(left.valid && root.data > left.max, left.sum+root.data, Math.max(root.data, left.max), 
            Math.min(root.data, left.min));
        }
        else {//both left, right subtree not null
            boolean valid = (left.valid && right.valid) && (root.data > left.max && root.data < right.min);

            if (valid)
            maxSum = Math.max(maxSum, root.data + left.sum + right.sum);
            
            return new Info1(valid, right.sum+left.sum+root.data, Math.max(root.data, Math.max(left.max, right.max)),
            Math.min(root.data, Math.min(left.min, right.min)));
        }
    }
	
    int total = 0;
    public Node bstToGst(Node root) {
        if (root == null) {
            return null;
        }
        bstToGst(root.right);
        total += root.data;
        root.data = total;
        bstToGst(root.left);
        return root;
    }

    static ArrayList<Integer> a = new ArrayList<>();
    public boolean findTarget(Node root, int k) {
        while (!a.isEmpty()) {
            a.remove(a.size()-1);
        }//emptying array each time
        help(root);
        int i = 0, j = a.size()-1;
        while (i < j) {
            if (a.get(i) + a.get(j) == k) return true;
            else if (a.get(i) + a.get(j) < k) i++;
            else j--;
        }
        return false;
    }
    public static void help1(Node root) {
        if (root == null) {
            return;
        }
        help1(root.left);
        a.add(root.data);
        help1(root.right);
    }
    public static void main(String args[]) {
        int values[] = {0,-1,-3,2,4};
        Node root = null;
        for (int i = 0; i < values.length; i++) {
            root = insert(root, values[i]);
        }

        Node root1 = new Node(20);
        root1.left = new Node(30);
        root1.left.left = new Node(5);
        root1.left.right = new Node(45);
        root1.right = new Node(60);
        root1.right.left = new Node(45);
        root1.right.right = new Node(70);
        root1.right.right.left = new Node(65);
        root1.right.right.right = new Node(80);


        
        
    }
}

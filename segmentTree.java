public class segmentTree {
    public static class Tree {
        int[] tree;

        Tree(int[] arr) {
            tree = new int[4 * arr.length - 1];
            buildTree(tree, arr, 0, 0, arr.length - 1);
        }

        public static void printTree(Tree t) {
            for (int el : t.tree)
                System.out.print(el + " ");
        }

        private static int buildTree(int[] tree, int arr[], int idx, int s, int e) {// O(N)
            if (s == e)
                return tree[idx] = arr[s];// or [e]
            return tree[idx] = buildTree(tree, arr, 2 * idx + 1, s, s + (e - s) / 2)
                    + buildTree(tree, arr, 2 * idx + 2, s + (e - s) / 2 + 1, e);
        }

        public static int rangeQuery(Tree t, int idx, int s, int e, int i, int j) {// O(logN)
            if (j < s || e < i)
                return 0;// no overlap -> no sum possible
            if (i <= s && e <= j)
                return t.tree[idx];// complete overlap
            // partial overlap
            return rangeQuery(t, idx * 2 + 1, s, s + (e - s) / 2, i, j)
                    + rangeQuery(t, idx * 2 + 2, s + (e - s) / 2 + 1, e, i, j);
        }

        public static void updateQuery(Tree t, int treeIdx, int[] arr, int idx, int val, int s, int e) {// O(logN)
            if (s == e) {//== idx
                t.tree[treeIdx] = arr[idx] = val;
                return;
            }
            int mid = (s + e) >> 1;
            if (idx <= mid)
                updateQuery(t, 2 * treeIdx + 1, arr, idx, val, s, m);
            
            else
                updateQuery(t, 2 * treeIdx + 2, arr, idx, val, m + 1, e);
            t.tree[treeIdx] = t.tree[treeIdx * 2 + 1] + t.tree[treeIdx * 2 + 2];
        }

        //* max/min segment trees
        public static int buildMaxtree(Tree t, int[] arr, int idx, int s, int e) {// returns whole array max
            if (s == e) return t.tree[idx] = arr[s];// or [e]
            return t.tree[idx] = Math.max(buildMaxtree(t, arr, 2 * idx + 1, s, s + (e - s) / 2), buildMaxtree(t, arr, 2 * idx + 2, s + (e - s) / 2 + 1, e));
            
        }

        public static int rangeMaxQuery(Tree t, int idx, int s, int e, int i, int j) {
            if (j < s || e < i) return Integer.MIN_VALUE;// no overlap -> no max possible
            if (i <= s && e <= j) return t.tree[idx];
            return Math.max(rangeMaxQuery(t, idx * 2 + 1, s, s + (e - s) / 2, i, j), rangeMaxQuery(t, idx * 2 + 2, s + (e - s) / 2 + 1, e, i, j));
        }

        public static void updateMaxQuery(Tree t, int[] arr, int idx, int val) {// idx of arr to update
            int s = 0;
            int e = arr.length - 1;// (t.tree.length + 1)/4 - 1
            int mid = s + (e - s) / 2;
            int treeIdx = 0;
            while (s != e) {
                t.tree[treeIdx] = Math.max(t.tree[treeIdx], val);
                mid = s + (e - s) / 2;
                if (idx > mid) {
                    s = mid + 1;
                    treeIdx = treeIdx * 2 + 2;// go in right
                } else {
                    e = mid;
                    treeIdx = treeIdx * 2 + 1;// go in left
                }
            }
            t.tree[treeIdx] = val;// s = e and last node where tree[treeIdx] = arr[idx] only
            arr[idx] = val;// update in arr too
        }
    }

    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8 };
        Tree t = new Tree(a);
        // Tree.printTree(t);
        // System.out.println(Tree.rangeQuery(t, 0, 0, a.length-1, 3, 5));

        // max/min
        Tree.buildMaxtree(t, a, 0, 0, 7);
        // Tree.printTree(t);
        // System.out.println(Tree.rangeMaxQuery(t, 0, 0, 7, 2, 7));
        Tree.updateMaxQuery(t, a, 5, 9);
        Tree.printTree(t);
    }
}
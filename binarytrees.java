import java.util.*;

public class binarytrees {
    static class Node {
        int data;
        Node left;
        Node right;
        Node (int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    static class BinaryTree {
        static int idx = -1;
        public static Node buildTree1(int[] nodes) {
            idx++;
            if (nodes[idx] == -1) {
                return null;
            }
            Node newNode = new Node(nodes[idx]);
            newNode.left = buildTree1(nodes);
            newNode.right = buildTree1(nodes);
            return newNode;
        }
        static int index = -1;
        public static Node buildTree(int[] nodes) {
            index++;
            if (nodes[index] == -1) {
                return null;
            }
            Node newNode = new Node(nodes[index]);
            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);
            return newNode;
        }

        public static void preorder(Node root) {
            if (root == null) {
                return;
            }
            System.out.println(root.data);
            preorder(root.left);
            preorder(root.right);
        }

        public static void inorder(Node root) {
            if (root == null) {
                return;
            }
            inorder(root.left);
            System.out.println(root.data);
            inorder(root.right);
        }

        public static void postorder(Node root) {
            if (root == null) {
                return;
            }
            postorder(root.left);
            postorder(root.right);
            System.out.println(root.data);
        }

        public static void levelorder(Node root) {
            Queue<Node> q = new LinkedList<>();
            if (root == null) {
                return;
            }
            q.add(root);
            q.add(null);
            while (!q.isEmpty()) {
                while (q.peek() != null) {
                    if (q.peek().left != null) {
                        q.add(q.peek().left);
                    }
                    if (q.peek().right != null) {
                        q.add(q.peek().right);
                    }
                    System.out.print(q.remove().data + " ");
                }
                System.out.println();
                q.remove();//if keep on adding null after removing then stuck in infinite loop so must check if q got empty
                if (q.isEmpty()) {
                    break;
                }
                else {
                    q.add(null);
                }
            }
            
        }

        public static int treeHeight(Node root) {
            //base case
            if (root == null) {
                return 0;
            }
            //work +
            //resursive call
            return (1 + Integer.max(treeHeight(root.left), treeHeight(root.right)));
        }

        public static int nodesCount(Node root) {
            if (root == null) {
                return 0;
            }
            return 1 + nodesCount(root.left) + nodesCount(root.right);
        }

        public static int nodesSum(Node root) {
            if (root == null) {
                return 0;
            }
            return root.data + nodesSum(root.left) + nodesSum(root.right);
        }

        // static int d;
        public static int diameter(Node root) {//O(n^2)
            if (root == null) {
                return 0;
            } 
            int lh = treeHeight(root.left);
            int rh = treeHeight(root.right);
            int d = 0;
            d = Integer.max(Integer.max(d, lh + rh + 1), Integer.max(diameter(root.left), diameter(root.right))); 
            // diameter(root.left);
            // diameter(root.right);
            return d;
        }
        
        public static class info {
            int d;
            int h;
            info(int d, int h) {
                this.d = d;
                this.h = h;
            }
        }
        public static info diameter1(Node root) {//O(n)
            //base case
            if (root == null) {
                return new info(0, 0);
            }
            //recursive call
            info leftInfo = diameter1(root.left);
            info rightInfo = diameter1(root.right);
            //work
            return new info(Math.max(Math.max(leftInfo.d, rightInfo.d), leftInfo.h+rightInfo.h+1), 1+Math.max(leftInfo.h, rightInfo.h));
        }
        
        public static boolean subTree(Node root, Node sub) {
            if (root == null) {
                return false;
            }
            if (root.data == sub.data) {
                if (helper(root, sub)) {//important to check and then return in case of nodes with same data to check fully
                    return true;
                }
            }
            boolean var1 = subTree(root.left, sub);
            boolean var2 = subTree(root.right, sub);
            return var1 || var2;
        }
        public static boolean helper(Node root, Node sub) {
            if (root == null && sub == null) return true;
            if ((root != null && sub == null) || (root == null && sub != null) || root.data != sub.data) return false;
            return helper(root.left, sub.left) && helper(root.right, sub.right);
        }

        // static HashMap<Integer, Node> map = new HashMap<>();
        // static int max, min;
        // public static HashMap<Integer, Node> topView(Node root, int d) {
        //     if (root == null) {
        //         return map;
        //     }
        //     min = Math.min(min, d);
        //     max = Math.max(max, d);
        //     if (!map.containsKey(d)) {
        //         map.put(d, root);
        //     }
        //     topView(root.left, d-1);
        //     topView(root.right, d+1); 
        //     return map;
        // }                                DFS IS WRONG APPROACH | USE BFS LEVEL ORDER APPROACH 
        public static class Info {
            Node node;
            int d;
            public Info(Node node, int d) {
                this.node = node;
                this.d = d;
            }
        }
        public static void topView(Node root) {
            Queue<Info> q = new LinkedList<>();
            HashMap<Integer, Node> map = new HashMap<>();
            int max = 0, min = 0;
            q.add(new Info(root, 0));
            q.add(null);
            while (!q.isEmpty()) {
                Info curr = q.remove();
                if (curr == null) {
                    if (q.isEmpty()) {
                        break;
                    }
                    else q.add(null);
                }
                else {//if curr is null then must no do these
                    if (!map.containsKey(curr.d)) {
                        map.put(curr.d, curr.node);
                    }
                    if (curr.node.left != null) {
                        q.add(new Info(curr.node.left, curr.d-1));
                        min = Math.min(min, curr.d-1);
                    }
                    if (curr.node.right != null) {
                        q.add(new Info(curr.node.right, curr.d+1));
                        max = Math.max(max, curr.d+1);
                    }
                }
            }
            for (int i = min; i <= max; i++) {
                System.out.println(map.get(i).data);
            }
        }

        public static void kthLevelIteration(Node root, int k) {
            if (k == 1) {
                System.out.println(root.data);
            }
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            q.add(null);
            int lvl = 1;
            while (!q.isEmpty()) {
                if (q.peek() == null) {
                    q.remove();
                    lvl++;
                    if (lvl == k) {
                        while (!q.isEmpty()) {//as null removed just now and only a certain level nodes present in node at a given time with null
                            System.out.println(q.remove().data);
                        }
                        break;
                    }
                    if (q.isEmpty()) {
                        System.out.println("tree doesn't have this level");
                        break;
                    }
                    q.add(null);
                }
                else {
                    if (q.peek().left != null) {
                        q.add(q.peek().left);
                    }
                    if (q.peek().right != null) {
                        q.add(q.peek().right);
                    }
                    q.remove();
                }
            }
        }
        public static void kthLevelRecursion(Node root, int k, int l) {
            if (root == null) {
                return;
            }
            if (k == l) {
                System.out.println(root.data);
                return;
            }
            kthLevelRecursion(root.left, k, l+1);
            kthLevelRecursion(root.right, k, l+1);
        }

        public static void lca(Node root, int n1, int n2) {//O(3n)
            ArrayList<Node> a1 = new ArrayList<>();
            ArrayList<Node> a2 = new ArrayList<>();
            if (helper(root, n1, a1) && helper(root, n2, a2)) {//O(2n)
                int i = 0;
                while (i < a1.size() && i < a2.size() && a1.get(i) == a2.get(i)) {//O(n)
                    i++;
                }
                System.out.println(a1.get(i-1).data);
            }
            else System.out.println("node(s) doesn't exist in tree");
        }
        public static boolean helper(Node root, int n, ArrayList<Node> a) {//O(n)
            if (root == null) {
                return false;
            }
            a.add(root);//storing when n node itself so if in-case it's the lca of itself!
            if (root.data == n) {
                return true;
            }
            boolean val1 = helper(root.left, n, a);
            boolean val2 = helper(root.right, n, a);
            if (val1 || val2) {
                return true;
            }
            a.remove(root);
            return false;
        }
        //O(n) cons space
        public static Node lca1(Node root, int n1, int n2) {
            if (root == null || root.data == n1 || root.data == n2) {
                return root;
            }
            Node left = lca1(root.left, n1, n2);
            Node right = lca1(root.right, n1, n2);

            //if both null situation is covered below
            if (left == null) {
                return right;
            }
            if (right == null) {
                return left;
            }
            return root;//correct subtree found where both n1, n2 direct children and return the parent (root)
        }

        // public static class Inf {
        //     Node node; 
        //     int h;
        //     public Inf(Node node, int h) {
        //         this.node = node;
        //         this.h = h;
        //     }
        // }
        // public static Inf minDist(Inf root, int n1, int n2) {
        //     if (root == null || root.node.data == n1 || root.node.data == n2) {
        //         return new Inf(root.node, 0);
        //     }
        //     Inf left = minDist(root, n1, n2);
        //     Inf right = minDist(root, n1, n2);
        //     if (left == null) {
        //         return new Inf(right.node, right.h+1);
        //     }
        //     if (right == null) {
        //         return new Inf(left.node, left.h+1);
        //     }
        //     return new Inf(root.node, left.h+right.h);
        // }
        public static int minDist(Node root, int n1, int n2) {
            // if (root == null) {
            //     return -1;
            // }
            // if (root.data == n1 || root.data == n2) {
            //     return 0;
            // }
            // int left = minDist(root.left, n1, n2);
            // int right = minDist(root.right, n1, n2);
            // //if both -1, must return -1 only and not the +1 anyhow
            // if (right == -1 && left == -1) {
            //     return -1;
            // }
            // if (right == -1) {
            //     return left+1;
            // }
            // if (left == -1) {
            //     return right+1;
            // }
            // return left+right;
            Node lca = lca1(root, n1, n2);
            int left = minDistHelp(lca, n1);
            int right = minDistHelp(lca, n2);
            return left + right;
        }
        public static int minDistHelp(Node lca, int n) {
            if (lca == null) {
                return -1;
            }
            if (lca.data == n) {
                return 0;
            }
            int l = minDistHelp(lca.left, n);
            int r = minDistHelp(lca.right, n);
            if (l == -1 && r == -1) 
            return -1;
            else if (l == -1) 
            return r+1;
            else //meaning r == -1
            return l+1;
        }

        public static int kthAncestor(Node root, int n, int k) {
            if (root == null) {
                return -1;
            }
            if (root.data == n) {
                return 0;
            }
            int left = kthAncestor(root.left, n, k);
            int right = kthAncestor(root.right, n, k);
            if (left == -1 && right == -1) 
            return -1;
            // else if (right == -1) {
            //     if (left+1 == k) 
            //     System.out.println(root.data);
            //     return left+1;
            // }
            // else {//left == -1 
            //     if (right+1 == k)
            //     System.out.println(root.data);
            //     return right+1;
            // }
            else {//easier as just picks out whose greater among the left or right dist returned (or not -1) 
                int max = Math.max(left, right);
                if (max+1 == k) {
                    System.out.println(root.data);
                }
                return max+1;
            }
        }

        public static int sumTree(Node root) {
            if (root == null) {
                return 0;
            }
            int left = sumTree(root.left);
            int right = sumTree(root.right);
            int curr = root.data;
            root.data = left + right;
            return curr + root.data;
        }
        //ASG
        public static boolean univalued(Node root) {
            // if (root == null) {
            //     return true;
            // }
            boolean val1 = false, val2 = false;
            if (root.left != null) {
                if (root.data == root.left.data)
                val1 = univalued(root.left);
                else val1 = false;
            }
            else val1 = true;

            if (root.right != null) {
                if (root.data == root.right.data)
                val2 = univalued(root.right);
                else val2 = false;
            }
            else val2 = true;

            return val1 && val2;
        }

        public static Node invert(Node root) {
            if (root == null) {
                return null;
            }
            Node left = invert(root.left);
            Node right = invert(root.right);
            Node temp = left;
            left = right;
            right = temp;
            return root;
        }

        public static Node leafDelete(Node root, int n) {
            if (root == null) {
                return null;
            }
            root.left = leafDelete(root.left, n);
            root.right = leafDelete(root.right, n);
            if (root.data == n && root.left == null && root.right == null) {
                return null;
            }
            return root;
        }

        static ArrayList<Node> a = new ArrayList<>();
        public static void duplicateSubtree(Node root) {
            
        }

        public static void maxPath(Node root) {
            
        }
    }

    public static void main(String args[]) {
        int nodes[] = {1,2,4,-1,-1,5,-1,-1,3,-1,6,-1,-1};// d = 5
        // int nodes[] = {1,2,3,4,-1,-1,-1,5,-1,6,-1,7,-1,-1,-1};// d = 6
        // int nodes[] = {1,2,3,-1,-1,4,-1,5,6,-1,-1,7,-1,-1,8,-1,9,-1,10,11,-1,-1,12,-1,-1};// d = 9
        // int nodes[] = {2,2,2,-1,-1,2,-1,-1,2,-1,3,-1,-1};
        // BinaryTree bt = new BinaryTree();
        Node root = BinaryTree.buildTree(nodes);

        // int subNodes[] = {6,-1,8,-1,-1}; 
        // BinaryTree subTree = new BinaryTree();
        // Node sub = BinaryTree.buildTree1(subNodes);
        
        root = BinaryTree.leafDelete(root, 5);
        BinaryTree.levelorder(root);
        
        
        
    }
}

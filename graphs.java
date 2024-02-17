import java.util.*;

@SuppressWarnings("unchecked")
public class graphs {
    static class E {
        int src;
        int dest;
        int wt;

        public E(int src, int dest, int wt) {
            this.src = src;
            this.dest = dest;
            this.wt = wt;
        }
    }

    public static void bfs(ArrayList<E>[] graph) {
        boolean[] visit = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (!visit[i])
                bfsUtil(graph, i, visit);
        }
    }

    public static void bfsUtil(ArrayList<E>[] graph, int idx, boolean[] visit) {// idx important to track disjoint
        Queue<Integer> q = new LinkedList<>();
        visit[idx] = true;
        q.add(idx);
        while (!q.isEmpty()) {
            int curr = q.remove();
            System.out.println(curr);
            for (int i = 0; i < graph[curr].size(); i++) {
                if (!visit[graph[curr].get(i).dest]) {
                    visit[graph[curr].get(i).dest] = true;
                    q.add(graph[curr].get(i).dest);
                }   
            }
        }
    }

    public static void dfs(ArrayList<E>[] graph) {
        boolean[] visit = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (!visit[i])
                dfsUtil(graph, i, visit);
        }
    }

    public static void dfsUtil(ArrayList<E>[] graph, int curr, boolean[] visit) {
        visit[curr] = true;
        System.out.println(curr);
        for (int i = 0; i < graph[curr].size(); i++)// neighbours of curr node
            if (!visit[graph[curr].get(i).dest]) {// neighbour not visited
                dfsUtil(graph, graph[curr].get(i).dest, visit); // visit them
            }
    }

    public static boolean hasPath(ArrayList<E>[] graph, int src, int dest, boolean[] visit) {
        if (src >= visit.length)
            return false;// case where incorrect args entered
        visit[graph[src].get(0).src] = true;// make curr node (graph[src] = AL so access.get(any idx) of that AL and
                                            // mark its src = curr node we are at) visited = true
        if (src == dest)// if curr node is dest
            return true;
        for (int i = 0; i < graph[src].size(); i++) {
            if (!visit[graph[src].get(i).dest])// go dfs for non visited (false) nodes
                if (hasPath(graph, graph[src].get(i).dest, dest, visit))// if returned true means dest path found then
                                                                        // only return true (arg2 acts as newSrc, arg3 -
                                                                        // final destination)
                    return true;
        }
        return false;
    }

    public static class Pair1 {
        int v;
        int parent;

        Pair1(int v, int parent) {
            this.v = v;
            this.parent = parent;
        }
    }

    public boolean cycleInUndirectedBFS(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visit = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!visit[i]) {
                if (cycleInUndirectedBFSUtil(i, adj, visit))
                    return true;
            }
        }
        return false;
    }

    public static boolean cycleInUndirectedBFSUtil(int curr, ArrayList<ArrayList<Integer>> adj, boolean[] visit) {
        Queue<Pair1> q = new LinkedList<>();
        q.add(new Pair1(curr, -1));
        visit[curr] = true;
        while (!q.isEmpty()) {
            Pair1 peek = q.remove();
            for (int neighbour : adj.get(peek.v)) {// neighbours
                if (!visit[neighbour]) {
                    visit[neighbour] = true;// make visited if not visited
                    q.add(new Pair1(neighbour, peek.v));
                } else if (neighbour != peek.parent)
                    return true;// visited but not parent
            }
        }
        return false;
    }

    public static boolean cycleInUndirectedDFS(ArrayList<E>[] graph) {
        boolean[] visit = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (!visit[graph[i].get(0).src])// for disjoint graph segments that aren't visited
                if (cycleInUndirectedDFSUtil(graph, i, graph[i].get(0).src, visit))// visit them and if cycle found
                                                                                   // (they
                                                                                   // return true) -> return true
                    return true;
        }
        return false;// if all disjoint graph segments visited and cycle not found
    }

    public static boolean cycleInUndirectedDFSUtil(ArrayList<E>[] graph, int curr, int parent, boolean[] visit) {
        visit[curr] = true;
        for (int i = 0; i < graph[curr].size(); i++) {
            int e = graph[curr].get(i).dest;
            if (!visit[e]) {
                if (cycleInUndirectedDFSUtil(graph, e, curr, visit))
                    return true;// not visited -> if cycle found in that part -> returns true
            } else if (visit[e] && e != parent)
                return true;// visited dest + its not parent
        }
        return false;// when all edges visited and none have their .dest alr visited except parent
                     // (where we reached them from) -> no cycle -> false
    }

    public static boolean cycleInDirectedDFS(ArrayList<E>[] graph) {
        boolean[] visit = new boolean[graph.length];
        boolean[] stackArray = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (!visit[i]) {
                if (cycleInDirectedDFSUtil(graph, i, visit, stackArray))
                    return true;// if cycle found in any disjoint graph segment
            }
        }
        return false;
    }

    public static boolean cycleInDirectedDFSUtil(ArrayList<E>[] graph, int curr, boolean[] visit, boolean[] stackArray) {
        visit[curr] = true;
        stackArray[curr] = true;
        for (int i = 0; i < graph[curr].size(); i++) {
            if (stackArray[graph[curr].get(i).dest])
                return true;// root which gave path to that node is its neighbour (present in stackArray) ->
                            // cycle condition
            else if (cycleInDirectedDFSUtil(graph, graph[curr].get(i).dest, visit, stackArray))
                return true;
        }
        stackArray[curr] = false;// remove the curr node as backtracking and path from 'this' node not required
        return false;// nowhere true retured above -> cycle unpresent
    }

    public static boolean cycleInDirectedBFS(int numCourses, int[][] prerequisites) {// detect cycle in directed graph
                                                                                     // using BFS
                                                                                     // get indegree of all nodes
        int[] indegree = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++)
            indegree[prerequisites[i][0]]++;
        // create q and 0 indegree nodes in it
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++)
            if (indegree[i] == 0)
                q.add(i);
        // counter to keep track of how many nodes visited until q gets empty
        int count = 0;
        // kahn's indegree bfs
        while (!q.isEmpty()) {
            int curr = q.remove();
            count++;
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1] == curr)
                    if (--indegree[prerequisites[i][0]] == 0)
                        q.add(prerequisites[i][0]);
            }
        }
        // in end if all courses had indegree = 0 made at some point so added in q and
        // count++ when removed means no cycle exists
        return count == numCourses;
    }

    //Graph Coloring - DFS
    static HashSet<Integer> hs1 = new HashSet<>();
    static HashSet<Integer> hs2 = new HashSet<>();
    public boolean isBipartite(int[][] graph) {
        boolean[] visit = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (!visit[i]) {
                hs1.clear();
                hs2.clear();
                hs1.add(i);
                if (!isBipartiteUtilDFS(graph, i, hs1, visit))
                    return false;
            }
        }
        return true;
    }
    static boolean isBipartiteUtilDFS(int[][] graph, int curr, HashSet<Integer> hs, boolean[] visit) {
        visit[curr] = true;
        HashSet<Integer> currentSet = (hs == hs1) ? hs1 : hs2;
        HashSet<Integer> otherSet = (hs == hs1) ? hs2 : hs1;
        for (int i = 0; i < graph[curr].length; i++) {
            int neighbor = graph[curr][i];
            if (visit[neighbor]) {
                if (currentSet.contains(neighbor))
                    return false;
            } else {
                otherSet.add(neighbor);
                if (!isBipartiteUtilDFS(graph, neighbor, otherSet, visit))
                    return false;
            }
        }
        return true;
    }

    // Graph Colouring - BFS
    public static boolean bipartiteBFS(ArrayList<E>[] graph) {
        int color[] = new int[graph.length];
        Arrays.fill(color, -1);// no color at start & 0, 1 represent complimentary colors
        for (int i = 0; i < graph.length; i++) {
            if (color[i] == -1) {
                if (!bipartiteUtilBFS(graph, i, color))
                    return false;
            }
        }
        return true;
    }
    public static boolean bipartiteUtilBFS(ArrayList<E>[] graph, int curr, int[] color) {
        Queue<Integer> q = new LinkedList<>();
        q.add(curr);
        color[curr] = 0;// disjoint graph segment root added in q with color 0
        while (!q.isEmpty()) {
            int peek = q.remove();
            for (int i = 0; i < graph[peek].size(); i++) {
                int e = graph[peek].get(i).dest;
                if (color[e] != -1) {// colored/visited (means also added in q)
                    if (color[e] == color[peek])
                        return false;
                } else {// uncolored/unvisited (== -1)
                    color[e] = ((color[peek] == 0) ? 1 : 0);// uncolored -> not added in q neighbours -> give opposite
                                                            // color -> add
                    q.add(e);
                }
            }
        }
        return true;
    }

    public static void topologicalSortDFS(ArrayList<E>[] graph) {// use stack
        boolean visit[] = new boolean[graph.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < graph.length; i++) {
            if (!visit[i]) {
                topologicalSortDFSUtil(graph, i, visit, stack);// gives disjoint graph segment topological order, all
                                                               // nodes of different segments don't have dependency on
                                                               // each other
            }
        }
        while (!stack.isEmpty())
            System.out.print(stack.pop() + " ");// a single stack formed for disjoint graph segment(s)
    }

    public static void topologicalSortDFSUtil(ArrayList<E>[] graph, int curr, boolean[] visit, Stack<Integer> stack) {
        visit[curr] = true;
        for (int i = 0; i < graph[curr].size(); i++) {
            if (!visit[graph[curr].get(i).dest])
                topologicalSortDFSUtil(graph, graph[curr].get(i).dest, visit, stack);
        }
        stack.push(curr);
    }

    public static void topologicalSortBFS(ArrayList<E>[] graph) {// kahn's algo - use stackArray, indegree array
        int[] indegree = new int[graph.length];// filled with 0 initially
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].size(); j++) {
                indegree[graph[i].get(j).dest]++;// incrementing all dest nodes to represent indegree of them
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++)
            if (indegree[i] == 0)
                q.add(i);// step 1 -> adding all indegree 0 nodes to q
        while (!q.isEmpty()) {
            int curr = q.remove();
            for (int i = 0; i < graph[curr].size(); i++) {
                if (--indegree[graph[curr].get(i).dest] == 0)// neighbour node's indegree-- & checked if it becomes 0
                                                             // then only
                    q.add(graph[curr].get(i).dest); // add in q
            }
            System.out.print(curr + " ");// print the curr node now as it's neighbours added to q with indegree = 0
        }
    }

    public static int[] findOrder(int numCourses, int[][] prerequisites) {// O(V+E)
        int[] indegree = new int[numCourses];// filled with 0 initially
        for (int i = 0; i < prerequisites.length; i++)
            indegree[prerequisites[i][0]]++;
        System.out.println(indegree[0] + " " + indegree[1]);
        int[] ans = new int[numCourses];
        int idx = 0;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++)
            if (indegree[i] == 0)
                q.add(i);
        while (!q.isEmpty()) {
            int curr = q.remove();
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1] == curr) {
                    int e = prerequisites[i][0];
                    if (--indegree[e] == 0)
                        q.add(e);
                }
            }
            ans[idx++] = curr;
        }
        return ans;
    }

    static List<List<Integer>> ans = new ArrayList<>();// O()

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {// src = 0, dest = n-1
        ans = new ArrayList<>();
        Stack<Integer> s = new Stack<>();// store the path in a stack
        s.push(0);// add 0 initially as it's the src and shouldn't be removed
        for (int i = 0; i < graph[0].length; i++) {// going to all neighbours of node 0
            path(graph, graph[0][i], s);
        }
        return ans;
    }

    public static void path(int[][] graph, int curr, Stack<Integer> s) {
        // if destination node reached
        if (curr == graph.length - 1) {
            s.push(graph.length - 1);// push the dest node in stack
            List<Integer> onePath = new ArrayList<>(s);// copy stack to arraylist (does it bottom to top which is src to dest)
            ans.add(onePath);// add the NEWLY CREATED LIST TO ans (dont use same static list and clear() as it clears from ans also)
            s.pop();// remove the dest node as now backtracks to go into new path
            return;
        }
        // go to all neighbours of curr node
        for (int i = 0; i < graph[curr].length; i++) {
            s.push(curr);// push it in stack as its part of the current path
            path(graph, graph[curr][i], s);// dfs
            s.pop();// backtracked and remove current node from path (stack) to go to next neighbour
                    // node or simply backtrack again
        }
    }

    public static void dijkstraAlgo(ArrayList<E>[] graph, int src) {// O(V + ElogV)
        int dist[] = new int[graph.length];// src -> i node distance (all 0 at initialization)
        boolean[] visit = new boolean[graph.length];
        for (int i = 0; i < dist.length; i++)
            if (i != src) dist[i] = Integer.MAX_VALUE;// initialise others' dist with max except src node
        PriorityQueue<Integer> pq = new PriorityQueue<>();// min heap
        pq.add(src);// add src with 0 dist at start
        while (!pq.isEmpty()) {
            int curr = pq.remove();
            if (!visit[curr]) {
            //if multiple times same noded added (its dist modified multiple times and hence added to pq) so the first one picked out is the minimum dist and hence mark visited as won't be needing it again: the bigger distances are useless LATER as NOW only this node's neighbor distances updated and LATER on won't be needing the bigger distance as it would make the neighbor distance more  
                visit[curr] = true;
                for (int i = 0; i < graph[curr].size(); i++) {// visiting all neighbours
                    E edgeDetail = graph[curr].get(i);
                    int u = edgeDetail.src, v = edgeDetail.dest, wt = edgeDetail.wt;
                    if (dist[v] > dist[u] + wt) {// add in pq as well so it weilds newer shorter paths from src to its neighbours
                        dist[v] = dist[u] + wt;// relaxation step
                        pq.add(v);
                    }
                }
            }
        }
        for (int i : dist)
            System.out.print(i + " ");// ans
    }

    // ASG part 1
    public boolean canFinish(int numCourses, int[][] prerequisites) {// detect cycle in directed graph using BFS -
                                                                     // O(v+e)
        // get indegree of all nodes
        int[] indegree = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++)
            indegree[prerequisites[i][0]]++;
        // create q and 0 indegree nodes in it
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++)
            if (indegree[i] == 0)
                q.add(i);
        // counter to keep track of how many nodes visited until q gets empty
        int count = 0;
        // kahn's indegree bfs
        while (!q.isEmpty()) {
            int curr = q.remove();
            count++;
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1] == curr)
                    if (--indegree[prerequisites[i][0]] == 0)
                        q.add(prerequisites[i][0]);
            }
        }
        // in end if all courses had indegree = 0 made at some point so added in q and
        // count++ when removed means no cycle exists
        return count == numCourses;
    }

    public int minDepth(TreeNode root) {//O(n)
        if (root == null) return 0;
        // create depthCounter, queue -> add null + root node
        int depth = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(null);
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode peek = q.remove();
            if (peek == null) {
                depth++;//adding nodes of next depth (peek's left, right) -> if they don't exist (peek = leaf) -> return current depth
                q.add(null);
            }
            else {//not null and valid node
                if (peek.left == null && peek.right == null) return depth;//first leaf node found = min depth
                if (peek.left != null) q.add(peek.left);
                if (peek.right != null) q.add(peek.right);
            }
        }
        return depth;//never executed as a leaf node will be reached
    }

    public static class Cell {// to store each orange's location in queue
        int row;
        int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public int orangesRotting(int[][] grid) {// O(row*col)
        Queue<Cell> q = new LinkedList<>();// to store a orange's location from grid
        int totalOranges = 0;// finding total oranges present
        int totalNonRotten = 0;
        int count = 0;// to track how many oranges become rotten, initially = number of 2s
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] != 0) {
                    if (grid[i][j] == 2) {
                        count++;
                        q.add(new Cell(i, j));// add the main rotten orange in queue first
                    }
                    if (grid[i][j] == 1)
                        totalNonRotten++;
                    totalOranges++;
                }
        int time = 0;// ans
        if (q.isEmpty() && totalOranges != 0)
            return -1;
        // no rotten orange present to begin rotting of other oranges and hence
        // impossible (-1)
        if (q.isEmpty() || totalNonRotten == 0)
            return time;// 0 returned as no rotten orange (2) present in queue and 0 time taken
        q.add(null);// to update time++ each time a orange's neighbours become rotten in a bfs
                    // fashion, then set of
                    // neighbours' neighbours becoming rotten ...
        while (!q.isEmpty()) {// bfs
            Cell peek = q.remove();
            if (peek == null) {
                if (q.isEmpty())
                    break;
                time++;
                q.add(null);
            } else {// valid rotten orange
                if (peek.row < grid.length - 1 && grid[peek.row + 1][peek.col] == 1) {
                    grid[peek.row + 1][peek.col] = 2;
                    q.add(new Cell(peek.row + 1, peek.col));
                    count++;
                }
                if (0 < peek.row && grid[peek.row - 1][peek.col] == 1) {
                    grid[peek.row - 1][peek.col] = 2;
                    q.add(new Cell(peek.row - 1, peek.col));
                    count++;
                }
                if (peek.col < grid[0].length - 1 && grid[peek.row][peek.col + 1] == 1) {
                    grid[peek.row][peek.col + 1] = 2;
                    q.add(new Cell(peek.row, peek.col + 1));
                    count++;
                }
                if (0 < peek.col && grid[peek.row][peek.col - 1] == 1) {
                    grid[peek.row][peek.col - 1] = 2;
                    q.add(new Cell(peek.row, peek.col - 1));
                    count++;
                }
            }
        }
        return (totalOranges == count) ? time : -1;
    }

    public static class Cell {
        int row; int col;
        Cell(int row, int col) {
            this.row = row; this.col = col;
        }
    }
    static int islandArea;// area of disjoint graph segment (island)
    static boolean[][] visit;// for disjoint island segments
    public int maxAreaOfIsland(int[][] grid) {// O(row*col)
        visit = new boolean[grid.length][grid[0].length];
        int maxArea = 0;// ans
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visit[i][j] && grid[i][j] == 1) {
                    islandArea = 0;// each time for a new directionally disjoint island segment of graph
                    dfs(new Cell(i, j), grid);
                    maxArea = Math.max(maxArea, islandArea);// updating maxArea
                }
            }
        }
        return maxArea;
    }
    public static void dfs(Cell curr, int[][] grid) {
        visit[curr.row][curr.col] = true;// mark the visited cell true
        islandArea++;// update area of island segment
        // go in dfs of directional neighbours that aren't visited and of course != 0
        if (0 < curr.row && !visit[curr.row - 1][curr.col] && grid[curr.row - 1][curr.col] != 0)
            dfs(new Cell(curr.row - 1, curr.col), grid);
        if (curr.row < grid.length - 1 && !visit[curr.row + 1][curr.col] && grid[curr.row + 1][curr.col] != 0)
            dfs(new Cell(curr.row + 1, curr.col), grid);
        if (0 < curr.col && !visit[curr.row][curr.col - 1] && grid[curr.row][curr.col - 1] != 0)
            dfs(new Cell(curr.row, curr.col - 1), grid);
        if (curr.col < grid[0].length - 1 && !visit[curr.row][curr.col + 1] && grid[curr.row][curr.col + 1] != 0)
            dfs(new Cell(curr.row, curr.col + 1), grid);
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {// O(num of words * word length * 26) time, O(num of words * word length * 26) space for new Strings created
        HashSet<String> hsOfWords = new HashSet<>();// all words of list
        HashSet<String> alrTaken = new HashSet<>();// words alr taken from list and added to queue | otherwise
        // creates loop same 2 words being added again & again by the for loop as they
        // only have one letter diff
        for (int i = 0; i < wordList.size(); i++) {// adding all words in hsOfWords for constant lookup time
            hsOfWords.add(wordList.get(i));
        }
        Queue<String> q = new LinkedList<>();
        // q to store all single letter differences from hsOfWords of word,
        // LinkedList<>() for string type don't store null
        q.add(beginWord);
        alrTaken.add(beginWord);
        q.add("");// like null
        int count = 1;
        // ans -> starting from begin word (strings in path = 1), when null in q -> 2nd
        // String in counted in path
        char[] diff;// for creation of various strings with single letter diff
        String diffStr;// to convert the charArray to string and check if present in hsOfWords
        char thatIdxOrigChar;
        // to store the letter to be changed + not make same string + change it back
        // again going to next letter
        String peek;
        while (!q.isEmpty()) {// bfs
            peek = q.remove();
            if (peek.equals("")) {
                if (q.isEmpty())
                    return 0;// endWord not found and queue got empty means no path exists
                count++;
                q.add("");// strings still in q so update count++ for next path
            } else if (peek.equals(endWord))
                return count;// found

            else {
                diff = peek.toCharArray();
                for (int i = 0; i < diff.length; i++) {
                    thatIdxOrigChar = diff[i];
                    for (int j = 0; (j < 26); j++) {
                        if (j == thatIdxOrigChar - 'a')
                            continue;// orig word only
                        diff[i] = (char) (j + 97);
                        diffStr = new String(diff);
                        if (hsOfWords.contains(diffStr) && !alrTaken.contains(diffStr))
                            q.add(diffStr);
                        alrTaken.add(diffStr);
                    }
                    diff[i] = thatIdxOrigChar;
                }
            }
        }
        return 0;// this statement never reached as return 0 from loop only instead of break when
                 // endWord not found
    }

    public static void bellmanFord(ArrayList<E>[] graph, int src) {// O(VE)
        int[] dist = new int[graph.length];
        for (int i = 0; i < dist.length; i++)
            if (i != src)
                dist[i] = Integer.MAX_VALUE;// initailizing all dist to inf except src = 0
        for (int i = 0; i < graph.length - 1; i++) {// v-1 iterations
            for (int j = 0; j < graph.length; j++) {// all edges
                for (int k = 0; k < graph[j].size(); k++) {
                    int u = graph[j].get(k).src, v = graph[j].get(k).dest, wt = graph[j].get(k).wt;
                    if (dist[u] != Integer.MAX_VALUE && dist[v] > dist[u] + wt)// relaxation step
                        dist[v] = dist[u] + wt;
                }
            }
        }
        for (int distances : dist)
            System.out.println(distances);
    }

    public static class Pair {
        int u;
        int v;
        int wt;
        Pair(int u, int v, int wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }
    }

    public static void primAlgo(ArrayList<E>[] graph) {// prim for undirected graph
        boolean[] visit = new boolean[graph.length];
        PriorityQueue<Pair> pq = new PriorityQueue<>((p1, p2) -> p1.wt - p2.wt);// min heap
        pq.add(new Pair(-1, 0, 0));// 0 no parent edge
        int totalWt = 0;
        ArrayList<E> finalEdges = new ArrayList<>();// answer(s)
        while (!pq.isEmpty()) {
            Pair curr = pq.remove();
            if (!visit[curr.v]) {
                visit[curr.v] = true;
                totalWt += curr.wt;// sum of all paths in final mst
                if (curr.u != -1)
                    finalEdges.add(new E(curr.u, curr.v, curr.wt));// dont add the -1 <-> 0 edge in finalEdges list
                for (int i = 0; i < graph[curr.v].size(); i++)// add all unvisited neighbours in pq
                    if (!visit[graph[curr.v].get(i).dest])
                        pq.add(new Pair(curr.v, graph[curr.v].get(i).dest, graph[curr.v].get(i).wt));
            }
        } // printing answers
        System.out.println(totalWt);
        for (E e : finalEdges) {
            System.out.println(e.src + "<->" + e.dest + ", wt: " + e.wt);
        }
    }

    public static class Info {
        int v;// the vertex
        int pathCost;// pathcost from src uptill curr
        int stops;// stops in between from src to this node

        Info(int v, int pathCost, int stops) {
            this.v = v;
            this.pathCost = pathCost;
            this.stops = stops;
        }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // converting 2d arr to adjacency list optimizes time
        ArrayList<E>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < flights.length; i++)
            graph[flights[i][0]].add(new E(flights[i][0], flights[i][1], flights[i][2]));

        int dist[] = new int[n];// total pathcost up till now for a node
        for (int i = 0; i < n; i++)
            if (i != src)
                dist[i] = Integer.MAX_VALUE;
        Queue<Info> q = new LinkedList<>();
        q.add(new Info(src, 0, 0));
        while (!q.isEmpty()) {
            Info curr = q.remove();
            if (curr.stops == k + 1)
                break;// all nodes till the k+1 stops added in q and dist[] updated so dist[dst] is
                      // needed only
            for (int i = 0; i < graph[curr.v].size(); i++) {
                E e = graph[curr.v].get(i);
                int d = e.dest;
                int cost = e.wt;
                if (dist[d] > curr.pathCost + cost) {
                    dist[d] = curr.pathCost + cost;// relaxation step to get min cost
                    q.add(new Info(d, dist[d], curr.stops + 1));
                }
            }
        }
        if (dist[dst] != Integer.MAX_VALUE)
            return dist[dst];
        else
            return -1;
    }

    public static class Pair2 {// class to hold node and the pathWt it came via
        int v;
        int wt;

        Pair2(int v, int wt) {
            this.v = v;
            this.wt = wt;
        }
    }

    int connectingCitiesMinCost(int n, int[][] connections) {// prim algo
        boolean[] visit = new boolean[n];// cities taken to form mst
        int ans = 0;// total cost
        PriorityQueue<Pair2> pq = new PriorityQueue<>((p1, p2) -> p1.wt - p2.wt);// min heap
        pq.add(new Pair2(0, 0));
        while (!pq.isEmpty()) {
            Pair2 peek = pq.remove();
            if (!visit[peek.v]) {
                visit[peek.v] = true;
                ans += peek.wt;
                for (int i = 0; i < connections[peek.v].length; i++) {
                    if (connections[peek.v][i] != 0 && !visit[i]) {
                        pq.add(new Pair2(i, connections[peek.v][i]));
                    }
                }
            }
        }
        for (boolean vis : visit)
            if (!vis)
                return -1;// if all nodes didn't form mst
        return ans;// totalCost wen all nodes formed mst
    }

    // static int n = 7;
    // static int[] parent = new int[n];
    // static int[] rank = new int[n];//0 initailly
    // public static void init() {
    // for (int i = 0; i < n; i++) parent[i] = i;
    // }
    public static int findPar(int x, int[] parent) {
        if (x == parent[x])
            return x;
        parent[x] = findPar(parent[x], parent);// path compression optimization
        return parent[x];
    }

    public static void union(int x, int y, int[] parent, int[] rank) {
        int parX = findPar(x, parent);
        int parY = findPar(y, parent);
        if (parX == parY)
            return;// alr in union
        if (rank[parX] == rank[parY]) {// any made parent
            parent[parX] = parY;
            rank[parY]++;
        } else if (rank[parX] < rank[parY]) {
            parent[parX] = parY;
        } else {
            parent[parY] = parX;
        }
    }

    static class Edge implements Comparable<Edge> {
        int s;
        int d;
        int wt;

        Edge(int s, int d, int wt) {
            this.s = s;
            this.d = d;
            this.wt = wt;
        }

        @Override
        public int compareTo(Edge e) {
            return this.wt - e.wt;
        }
    }

    static int kruskalAlgo(int V, int E, ArrayList<Edge> edges) {
        int[] parent = new int[V];
        int[] rank = new int[V];// 0 initailly
        for (int i = 0; i < V; i++)
            parent[i] = i;// init step
        int MSTedgeCount = 0;// of nodes taken to form MST -> (edges needed = total nodes V - 1)
        // kruskal's algo
        int ans = 0;
        Collections.sort(edges, (e1, e2) -> Integer.compare(e1.wt, e2.wt));
        for (int i = 0; MSTedgeCount < V - 1; i++) {
            Edge e = edges.get(i);
            int s = e.s;
            int d = e.d;
            int wt = e.wt;
            if (findPar(s, parent) == findPar(d, parent))
                continue;// don't do union if same parent
            union(s, d, parent, rank);
            ans += wt;
            MSTedgeCount++;
        }
        return ans;
    }

    static int nodeValToColor;
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        nodeValToColor = image[sr][sc];// nodes having this value are meant to be colored;
        dfs(image, sr, sc, color);
        return image;// commiting changes in the data itself
    }
    public static void dfs(int[][] image, int sr, int sc, int color) {
        image[sr][sc] = color;
        if (0 < sr && image[sr - 1][sc] != color && image[sr - 1][sc] == nodeValToColor) {
            dfs(image, sr - 1, sc, color);
        }
        if (sr < image.length - 1 && image[sr + 1][sc] != color && image[sr + 1][sc] == nodeValToColor) {
            dfs(image, sr + 1, sc, color);
        }
        if (0 < sc && image[sr][sc - 1] != color && image[sr][sc - 1] == nodeValToColor) {
            dfs(image, sr, sc - 1, color);
        }
        if (sc < image[0].length - 1 && image[sr][sc + 1] != color && image[sr][sc + 1] == nodeValToColor) {
            dfs(image, sr, sc + 1, color);
        }
    }

    public void floydWarshall(int[][] matrix) {
        int n = matrix.length;
        // this graph has no -ve edge wts
        // -1 represents no edge between i -> j, change it to infinity
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1)
                    matrix[i][j] = Integer.MAX_VALUE;
            }
        }
        // floyd-warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][k] != Integer.MAX_VALUE && matrix[k][j] != Integer.MAX_VALUE)// impo
                        if (matrix[i][k] + matrix[k][j] < matrix[i][j])// relaxation step
                            matrix[i][j] = matrix[i][k] + matrix[k][j];
                }
            }
        }
        // revert all infinity back to -1 as represents no valid min path from any other
        // node
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j && matrix[i][j] < 0)
                    System.out.println("negative cycle exists in graph");// dist of node back to node < 0 means -ve
                                                                         // cycle present
                if (matrix[i][j] == Integer.MAX_VALUE)
                    matrix[i][j] = -1;
            }
        }
    }

    public int kosarajuAlgo(int V, ArrayList<ArrayList<Integer>> adj) {// O(V+E) - Directed SCCs
        Stack<Integer> s = new Stack<>();
        boolean[] visit = new boolean[V];
        // getting the topological stack
        for (int i = 0; i < V; i++) {// for disjoint segments of graph
            if (!visit[i])
                dfs1(i, adj, visit, s);
        }
        // transpose graph
        ArrayList<ArrayList<Integer>> adjT = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adjT.add(new ArrayList<>());
        }
        for (int i = 0; i < adj.size(); i++) {
            visit[i] = false;// to reuse the visit array
            for (int j = 0; j < adj.get(i).size(); j++) {
                int src = i;
                int dest = adj.get(i).get(j);
                adjT.get(dest).add(src);
            }
        }
        // step 3 - finding the total number of strongly connected components
        int SCCs = 0;
        while (!s.isEmpty()) {
            int peek = s.pop();
            if (!visit[peek]) {
                dfs2(peek, adjT, visit);// can also print a single SSCs all nodes then after backtrack
                SCCs++; // new line for other component
            }
        }
        return SCCs;// number of SSCs
    }

    public static void dfs1(int curr, ArrayList<ArrayList<Integer>> adj, boolean[] visit, Stack<Integer> s) {
        visit[curr] = true;
        for (int neighbor : adj.get(curr)) {
            if (!visit[neighbor])
                dfs1(neighbor, adj, visit, s);
        }
        s.push(curr);
    }

    public static void dfs2(int curr, ArrayList<ArrayList<Integer>> adjT, boolean[] visit) {
        visit[curr] = true;
        for (int neighbor : adjT.get(curr)) {
            if (!visit[neighbor])
                dfs2(neighbor, adjT, visit);
        }
    }

    static int time;// used by both bridge & articulation point
    public List<List<Integer>> tarjanBridgeAlgo(int n, List<List<Integer>> connections) {// O(V+E) - Undirected SCCs
        // convert to adjacency list;
        ArrayList<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            // connections.get(i).get(0) <-> connections.get(i).get(1)
            adj[connections.get(i).get(0)].add(connections.get(i).get(1));
            adj[connections.get(i).get(1)].add(connections.get(i).get(0));
        }
        // ans list
        List<List<Integer>> ans = new ArrayList<>();
        // tarjan's arrays
        boolean[] vis = new boolean[n];
        int[] parent = new int[n];
        int[] dt = new int[n];// discovery time
        int[] ldt = new int[n];// lowest discovery time
        // dfs
        time = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {// for disjoint segments
                parent[i] = -1;
                dfsBridge(i, vis, parent, dt, ldt, adj, ans);
            }
        }
        return ans;
    }
    public static void dfsBridge(int curr, boolean[] vis, int[] parent, int[] dt, int[] ldt, ArrayList<Integer>[] adj, List<List<Integer>> ans) {
        vis[curr] = true;
        dt[curr] = ++time;
        ldt[curr] = dt[curr];
        for (int neighbor : adj[curr]) {
            if (neighbor == parent[curr]) continue;
            if (!vis[neighbor]) {
                parent[neighbor] = curr;
                dfsBridge(neighbor, vis, parent, dt, ldt, adj, ans);
                ldt[curr] = Math.min(ldt[curr], ldt[neighbor]);
                if (dt[curr] < ldt[neighbor]) {
                    List<Integer> bridgeEdge = new ArrayList<>();
                    bridgeEdge.add(curr);
                    bridgeEdge.add(neighbor);
                    ans.add(bridgeEdge);
                }
            } else {// neighbor visited
                ldt[curr] = Math.min(ldt[curr], dt[neighbor]);
            }
        }
    }
    public ArrayList<Integer> articulationPoints(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[V];
        int[] parent = new int[V];
        int[] dt = new int[V];
        int[] ldt = new int[V];
        ArrayList<Integer> ans = new ArrayList<>();
        time = 0;
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {// for disjoint segments
                parent[i] = -1;
                dfsAP(i, vis, parent, dt, ldt, adj, ans);
            }
        }
        Collections.sort(ans);
        if (ans.size() == 0)
            ans.add(-1);// no articulation point node
        return ans;
    }

    public static void dfsAP(int curr, boolean[] vis, int[] parent, int[] dt, int[] ldt,
            ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> ans) {
        vis[curr] = true;
        dt[curr] = ldt[curr] = ++time;
        int children = 0;
        for (int neighbor : adj.get(curr)) {
            if (neighbor == parent[curr])
                continue;
            if (!vis[neighbor]) {
                parent[neighbor] = curr;
                dfsAP(neighbor, vis, parent, dt, ldt, adj, ans);
                ldt[curr] = Math.min(ldt[curr], ldt[neighbor]);
                if (parent[curr] != -1 && dt[curr] <= ldt[neighbor]) {
                    if (!ans.contains(curr))
                        ans.add(curr);// no duplicates needed
                }
                children++;
            } else {// neighbor visited
                ldt[curr] = Math.min(ldt[curr], dt[neighbor]);
            }
        }
        if (parent[curr] == -1 && children > 1) {
            if (!ans.contains(curr))
                ans.add(curr);// no duplicates needed
        }
    }

    public int findMotherVertex(int V, ArrayList<ArrayList<Integer>> adj) {
        Stack<Integer> s = new Stack<>();
        boolean[] vis = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!vis[i])
                dfsToposort(i, adj, vis, s);
        }
        Arrays.fill(vis, false);
        int peek = s.pop();
        dfsMV(peek, adj, vis);
        for (boolean val : vis) {
            if (!val)
                return -1;
        }
        return peek;
    }

    public static void dfsToposort(int curr, ArrayList<ArrayList<Integer>> adj, boolean[] vis, Stack<Integer> s) {
        vis[curr] = true;
        for (int neighbor : adj.get(curr)) {
            if (!vis[neighbor])
                dfsToposort(neighbor, adj, vis, s);
        }
        s.push(curr);
    }

    public static void dfsMV(int curr, ArrayList<ArrayList<Integer>> adj, boolean[] vis) {
        vis[curr] = true;
        for (int neighbor : adj.get(curr)) {
            if (!vis[neighbor])
                dfsMV(neighbor, adj, vis);
        }
    }

    public int detectCycleDSU(int V, ArrayList<ArrayList<Integer>> adj) {
        HashSet<String> hs = new HashSet<>();// to not take same edge again as undirectional
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++)
            parent[i] = i;// initialising parent of node with itself
        int parS = -1;
        int parD = -1;
        for (int i = 0; i < adj.size(); i++) {
            for (int d : adj.get(i)) {
                if (!hs.contains(d + "-" + i)) {// undirectional edge not present then only add
                    hs.add(i + "-" + d);
                    parS = findParent(i, parent);
                    parD = findParent(d, parent);
                    if (parS == parD)
                        return 1;// cycle
                    else
                        union1(parS, parD, parent, rank);
                }
            }
        }
        return 0;// no cycle
    }

    public static int findParent(int x, int[] parent) {
        if (x == parent[x])
            return x;
        parent[x] = findParent(parent[x], parent);// path compression step
        return parent[x];
    }

    public static void union1(int X, int Y, int[] parent, int[] rank) {
        if (X == Y) {
            parent[X] = Y;
            rank[Y]++;
        } else if (X > Y)
            parent[Y] = X;
        else
            parent[X] = Y;
    }

    public int closedIsland(int[][] grid) {
        int ans = 0;
        boolean[][] vis = new boolean[grid.length][grid[0].length];
        for (int i = 1; i < vis.length - 1; i++) {
            for (int j = 1; j < vis[0].length - 1; j++) {
                if (!vis[i][j] && grid[i][j] == 0) {
                    if (dfs(i, j, vis, grid))
                        ans++;
                }
            }
        }
        return ans;
    }

    public static boolean dfs(int i, int j, boolean[][] vis, int[][] grid) {
        if (i == 0 || j == 0 || i == grid.length - 1 || j == grid[0].length - 1)
            return false;
        vis[i][j] = true;
        boolean down = true, up = true, right = true, left = true;
        if (0 <= i && i < grid.length - 1 && !vis[i + 1][j]) {
            if (grid[i + 1][j] == 0)
                down = dfs(i + 1, j, vis, grid);
            else
                down = true;
        }
        if (0 < i && i <= grid.length - 1 && !vis[i - 1][j]) {
            if (grid[i - 1][j] == 0)
                up = dfs(i - 1, j, vis, grid);
            else
                up = true;
        }
        if (0 <= j && j < grid[0].length - 1 && !vis[i][j + 1]) {
            if (grid[i][j + 1] == 0)
                right = dfs(i, j + 1, vis, grid);
            else
                right = true;
        }
        if (0 < j && j <= grid[0].length - 1 && !vis[i][j - 1]) {
            if (grid[i][j - 1] == 0)
                left = dfs(i, j - 1, vis, grid);
            else
                left = true;
        }
        return down && up && right && left;
    }

    public static void toposort(int curr, ArrayList<Integer>[] graph, Stack<Character> s, boolean[] vis) {
        vis[curr] = true;
        for (int neighbor : graph[curr]) {
            if (!vis[neighbor])
                toposort(neighbor, graph, s, vis);
        }
        s.push((char) (curr + 97));
    }

    public String findOrder(String[] dict, int N, int K) {
        ArrayList<Integer>[] graph = new ArrayList[26];
        for (int i = 0; i < 26; i++)
            graph[i] = new ArrayList<>();
        // build graph
        for (int i = 0; i < dict.length - 1; i++) {
            int c = 0;
            while (c < Math.min(dict[i].length(), dict[i + 1].length())) {// 2 consecutive words of dict
                if (dict[i].charAt(c) != dict[i + 1].charAt(c)) {// match until diff character
                    boolean exists = false;
                    // check if the dest alr exists in graph[src]
                    for (int j = 0; j < graph[dict[i].charAt(c) - 'a'].size();) {
                        if (graph[dict[i].charAt(c) - 'a'].get(j) == dict[i + 1].charAt(c) - 'a')
                            exists = true;
                        break;
                    }
                    // add if it doesn't
                    if (!exists)
                        graph[dict[i].charAt(c) - 'a'].add(dict[i + 1].charAt(c) - 'a');
                    break;// immediately break after first diff char found
                }
                c++;
            }
        }
        // toposort order
        Stack<Character> s = new Stack<>();
        boolean[] vis = new boolean[26];
        for (int i = 0; i < 26; i++) {
            if (!vis[i])
                toposort(i, graph, s, vis);
        }
        // stack contains alien order
        StringBuilder ans = new StringBuilder();
        while (!s.isEmpty()) {
            ans.append(s.pop());// lowest dependency char at top so correct order ina ns
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        int V = 11;
        ArrayList<E>[] graph = new ArrayList[V];// arraylists not instantiated so null technically making array elements
                                                // to be null as well
        for (int i = 0; i < V; i++) {
            graph[i] = new ArrayList<>();
        }
        // Vertex 0
        graph[0].add(new E(0, 1, 2));
        graph[0].add(new E(0, 2, 4));
        graph[0].add(new E(0, 3, 30));
        // Vertex 1
        graph[1].add(new E(1, 0, 3));
        graph[1].add(new E(1, 6, 1));
        graph[1].add(new E(1, 5, 1));
        // Vertex 2
        graph[2].add(new E(2, 0, 1));
        graph[2].add(new E(2, 4, 1));
        graph[2].add(new E(2, 5, 3));
        // Vertex 3
        graph[3].add(new E(3, 0, 30));
        // graph[3].add(new E(3, 1, 1));
        // graph[3].add(new E(3, 2, 10));
        // graph[3].add(new E(3, 5, 0));
        // Vertex 4
        graph[4].add(new E(4, 2, -1));
        // graph[4].add(new E(4, 5, 5));
        // graph[4].add(new E(4, 5, 0));
        // Vertex 5
        graph[5].add(new E(5, 1, 0));
        graph[5].add(new E(5, 2, 0));
        graph[5].add(new E(5, 6, 0));
        // Vertex 6
        graph[6].add(new E(6, 1, 0));
        graph[6].add(new E(6, 5, 0));
        //disjoint segment
        graph[7].add(new E(7, 8, 0));
        graph[7].add(new E(7, 10, 0));
        graph[8].add(new E(8, 7, 0));
        graph[8].add(new E(8, 9, 0));
        graph[9].add(new E(9, 8, 0));
        graph[9].add(new E(9, 10, 0));
        graph[10].add(new E(10, 7, 0));
        graph[10].add(new E(10, 9, 0));

        bfs(graph);

    }
}
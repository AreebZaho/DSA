import java.util.*;

public class dp {
    public static int fibonacci(int n, int[] f) {// memoization
        if (n == 0 || n == 1)
            return n;
        if (f[n] != 0)
            return f[n];
        f[n] = fibonacci(n - 1, f) + fibonacci(n - 2, f);
        return f[n];
    }

    public static int fibonacci(int n) {// tabulation
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static int climbStairs(int n, int[] stairs) {// memoization
        if (n == 0 || n == 1)
            return 1;
        if (stairs[n] != 0)
            return stairs[n];
        stairs[n] = climbStairs(n - 1, stairs) + climbStairs(n - 2, stairs);
        return stairs[n];
    }

    public static int climbStairs(int n) {// tabulation
        int[] s = new int[n + 1];
        s[0] = 1;
        s[1] = 1;
        for (int i = 2; i <= n; i++) {
            s[i] = s[i - 1] + s[i - 2];
        }
        return s[n];
    }

    static int knapSackMemoization(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n + 1][W + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < W + 1; j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
                else
                    dp[i][j] = -1;
            }
        }
        return dfs(W, wt, val, n, dp);
    }

    public static int dfs(int W, int[] wt, int[] val, int n, int[][] dp) {
        if (n == 0 || W == 0)
            return 0;
        if (dp[n][W] != -1)
            return dp[n][W];
        if (wt[n - 1] <= W) {
            dp[n][W] = Math.max(val[n - 1] + dfs(W - wt[n - 1], wt, val, n - 1, dp), dfs(W, wt, val, n - 1, dp));// pick max of when val included or not
        } else
            dp[n][W] = dfs(W, wt, val, n - 1, dp);// when doesn't have enough space for curr idx wt but can have enough for next idx wt
        return dp[n][W];
    }

    static int knapSackTabulation(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n + 1][W + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                if (wt[i - 1] <= j) {
                    dp[i][j] = Math.max(val[i - 1] + dp[i - 1][j - wt[i - 1]], dp[i - 1][j]);
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[n][W];
    }

    static boolean isSubsetSumMemoization(int N, int arr[], int sum) {
        boolean[][] dp = new boolean[N + 1][sum + 1];// false by default, necessary base cases become true in dfs()
        return dfs(N, arr, sum, dp);
    }

    public static boolean dfs(int N, int arr[], int sum, boolean[][] dp) {
        if (sum == 0)
            return true;
        if (N == 0)
            return false;
        if (dp[N][sum] != false)
            return dp[N][sum];
        if (arr[N - 1] <= sum) {
            dp[N][sum] = dfs(N - 1, arr, sum - arr[N - 1], dp) || dfs(N - 1, arr, sum, dp);// if any returns true
        } else
            dp[N][sum] = dfs(N - 1, arr, sum, dp);
        return dp[N][sum];
    }

    static boolean isSubsetSumTabulation(int N, int arr[], int sum) {
        boolean[][] dp = new boolean[N + 1][sum + 1];// false by default
        for (int i = 0; i < N + 1; i++)
            dp[i][0] = true;// initialization, first row except 0 idx all false
        // work
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= sum; j++) {
                if (arr[i - 1] <= j) {// include
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j];// if any true
                } else
                    dp[i][j] = dp[i - 1][j];// exclude
            }
        }
        return dp[N][sum];
    }

    static int unboundedKnapsack(int N, int W, int val[], int wt[]) {
        int[][] dp = new int[N + 1][W + 1];// all req BCs 0 at start
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= W; j++) {
                if (wt[i - 1] <= j)
                    dp[i][j] = Math.max(val[i - 1] + dp[i][j - wt[i - 1]], dp[i - 1][j]);
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[N][W];
        // * 1D dp
        // int[] dp = new int[W + 1];
        // for (int i = 0; i < N; i++) {
        // for (int j = 1; j <= W; j++) {
        // if (wt[i] <= j) {
        // dp[j] = Math.max(dp[j], dp[j-wt[i]] + val[i]);
        // }
        // else dp[j] = dp[j];//stays same
        // }
        // }
        // return dp[W];
    }

    public static int coinChangeWays(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        // BCs
        // 0 amount (col 0) -> 0 coins will be needed irrespective of array size = 1 way
        for (int i = 0; i <= coins.length; i++)
            dp[i][0] = 1;
        // 0 coins given (row 0) -> no possible ways to make any amount > 0 = 0 ways
        // (initialized)
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (coins[i - 1] <= j) {
                    dp[i][j] = dp[i][j - coins[i - 1]] + dp[i - 1][j];// total possible ways (inc + exc)
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[coins.length][amount];
    }

    public static int minCoinsForChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        // BCs
        for (int i = 0; i < dp.length; i++)
            for (int j = 1; j < dp[0].length; j++)
                dp[i][j] = -1;
        // 0 amount to make (r1 c1) -> coins req (col 1) = 0
        // coins given = 0 -> amount to make > 1 => no coins possible = -1
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (coins[i - 1] <= j) {
                    if (dp[i][j - coins[i - 1]] == -1)
                        dp[i][j] = dp[i - 1][j];
                    else {
                        if (dp[i - 1][j] == -1)
                            dp[i][j] = 1 + dp[i][j - coins[i - 1]];
                        else
                            dp[i][j] = Math.min(dp[i][j - coins[i - 1]] + 1, dp[i - 1][j]);
                    }
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[coins.length][amount];
    }

    public static int targetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int el : nums)
            sum += Math.abs(el);
        if (Math.abs(target) > sum)
            return 0;// +ve/-ve target not rechable even if all numbers +/-
        int[][] dp = new int[nums.length + 1][sum * 2 + 1];
        // BCs
        dp[0][sum] = 1;// rest by default 0
        // tabulation
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (dp[i - 1][j] != 0) {// adding in next expressions as they are reachable by +/- nums[i-1]
                    dp[i][j + nums[i - 1]] += dp[i - 1][j];
                    dp[i][j - nums[i - 1]] += dp[i - 1][j];
                }
            }
        }
        if (target < 0)
            return dp[nums.length][sum + target];
        else
            return dp[nums.length][sum - target];
    }

    public static int cutRodMaxPrice(int price[], int n) {
        int[][] dp = new int[price.length + 1][n + 1];// n == prices.length however
        // BCs
        // no prices gives (i = 0) and rod length exists -> 0 profit
        // rod length = 0 (j = 0) so profit = 0 for any number of rod pieces prices
        // given
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= price.length; j++) {
                if (i <= j) {
                    dp[i][j] = Math.max(price[i - 1] + dp[i][j - i], dp[i - 1][j]);
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[price.length][n];
    }

    public static int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        // BCs
        // if s1 length == 0 (i = 0) -> irrespective of s2 length, lcs = 0
        // if s2 length == 0 (j = 0) -> irrespective of s1 length, lcs = 0
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[text1.length()][text2.length()];
    }

    static int longestCommonSubstr(String S1, String S2, int n, int m) {
        int[][] dp = new int[S1.length() + 1][S2.length() + 1];
        // BCs
        // s1 length = 0 (i = 0) -> 0 longest substring length irrespective of length of
        // s2
        // s2 length = 0 (j = 0) -> 0 longest substring length irrespective of length of
        // s2
        int ans = 0;
        for (int i = 1; i <= S1.length(); i++) {
            for (int j = 1; j <= S2.length(); j++) {
                if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else
                    dp[i][j] = 0;
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    public static int lengthOfLIS(int[] nums) {
        HashSet<Integer> hs = new HashSet<>();
        for (int el : nums)
            hs.add(el);
        int[] copy = new int[hs.size()];
        int idx = 0;
        for (Iterator<Integer> i = hs.iterator(); i.hasNext();)
            copy[idx++] = i.next();
        Arrays.sort(copy);
        // copy acts like unique char (numbers here) + sorted substring
        int[][] dp = new int[copy.length + 1][nums.length + 1];
        // BCs
        // any arr len = 0 (i/j = 0) -> no subsequence possible ([0][cells], [cells][0]
        // = 0) irrespective of length of other arr
        for (int i = 1; i <= copy.length; i++) {
            for (int j = 1; j <= nums.length; j++) {
                if (copy[i - 1] == nums[j - 1])
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[copy.length][nums.length];
    }

    public static int minDistanceOperations3(String word1, String word2) {
        int dp[][] = new int[word1.length() + 1][word2.length() + 1];
        // BCs - (i = 0, j = 0) stays 0 as no steps req to make "" -> ""
        for (int j = 1; j <= word2.length(); j++)
            dp[0][j] = j;// str1 len = 0 (i = 0) -> include chars = length of str2
        for (int i = 1; i <= word1.length(); i++)
            dp[i][0] = i;// str2 len = 0 (j = 0) -> include chars = length of str1
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        }
        return dp[word1.length()][word2.length()];
    }

    public static int minimumDeleteSum(String s1, String s2) {// ASCII
        int dp[][] = new int[s1.length() + 1][s2.length() + 1];
        // BCs
        // (i = 0) s1.len = 0 -> ascii for s2 = all chars of s2
        for (int j = 1; j <= s2.length(); j++)
            dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
        // (j = 0) s2.len = 0 -> ascii for s1 = all chars of s1
        for (int i = 1; i <= s1.length(); i++)
            dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        // tabulation
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else
                    dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1), dp[i][j - 1] + s2.charAt(j - 1));
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public static int minDistanceDeleteOperationOnly(String word1, String word2) {
        int dp[][] = new int[word1.length() + 1][word2.length() + 1];
        // BCs (i = 0, j = 0) -> "", "" same only so 0 delete operations
        // (i = 0) s1.len = 0 -> lcs.len = 0 irrespective of s2.len -> delete operations
        // = s2.len
        for (int j = 1; j <= word2.length(); j++)
            dp[0][j] = word2.substring(0, j).length();
        // (j = 0) s2.len = 0 -> lcs.len = 0 irrespective of s1.len -> delete operations
        // = s1.len
        for (int i = 1; i <= word1.length(); i++)
            dp[i][0] = word1.substring(0, i).length();
        // tabulation
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], dp[i - 1][j]);
            }
        }
        return dp[word1.length()][word2.length()];
    }

    public static boolean wildcardMatch(String s, String p) {//p = wildcard (contains pattern)
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        // BCs
        dp[0][0] = true;// "" and "" match already
        for (int i = 1; i <= s.length(); i++) dp[i][0] = false;// s.len = 0 and p = "" -> never match
        // s = "" (i=0) -> j stays false for the ?/char present in p and,
        // p(j-1)=* && and prev j dp cell not false then only = true
        for (int j = 1; j <= p.length(); j++) if (p.charAt(j - 1) == '*' && dp[0][j - 1]) dp[0][j] = true;
        // tabulation
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') dp[i][j] = dp[i - 1][j - 1];
                else if (p.charAt(j - 1) == '*') dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                else
                    dp[i][j] = false;// non equal chars
            }
        }
        return dp[s.length()][p.length()];
    }

    public static int catalan(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

    public static int mcmMemoize(int[] a, int i, int j, int[][] dp) {// from main -> i = 1, j = n-1, dp[n][n]
        if (i == j) return 0;
        if (dp[i][j] != 0) return dp[i][j];
        int ans = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            if (dp[i][k] == 0) dp[i][k] = mcmMemoize(a, i, k, dp);
            if (dp[k + 1][j] == 0) dp[k + 1][j] = mcmMemoize(a, k + 1, j, dp);
            ans = Math.min(ans, dp[i][k] + dp[k + 1][j] + a[i - 1] * a[k] * a[j]);
        }
        return dp[i][j] = ans;
    }

    static int matrixMultiplication(int N, int arr[]) {
        int dp[][] = new int[N][N];
        for (int[] rows : dp)
            Arrays.fill(rows, Integer.MAX_VALUE);
        // BCs (i = j) => 0 No matrix multiplication possible
        // i = 0 row empty as not started from there
        for (int i = 0; i < dp.length; i++) {
            dp[i][i] = 0;
            dp[0][i] = 0;
        }
        // i > j section not accessed as matrix multiplication i x j where i < j
        // rest i < j left with +infinity as need to minimize cost
        // diagonal filling tabulation
        for (int x = 2; x <= N - 1; x++) {
            for (int i = 1; i <= N - x; i++) {
                int j = i - 1 + x;
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j]);
                }
            }
        }
        return dp[1][N - 1];
    }

    static class Info {
        boolean reach;
        int elements;

        Info(boolean reach, int elements) {
            this.reach = reach;// target reachable or not
            this.elements = elements;// num of elements from array req to get this target sum
        }
    }

    public static int minimumDifference(int[] nums) {
        int sum = 0;
        for (int el : nums)
            sum += el;
        Info dp[][] = new Info[nums.length + 1][sum / 2 + 1];
        // BCs
        // i = 0 -> no elements in array only -> only 0 sum (j = 0) reachable -> true
        // j = 0 -> 0 target to reach so no elemnts of array taken -> mark true
        for (int i = 0; i <= nums.length; i++)
            for (int j = 0; j <= sum / 2; j++)
                dp[i][j] = (j == 0) ? new Info(true, 0) : new Info(false, 0);
        // tabulation like knapsack
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= sum / 2; j++) {
                if (nums[i - 1] <= j)
                    dp[i][j] = new Info(dp[i - 1][j - nums[i - 1]].reach, dp[i - 1][j - nums[i - 1]].elements + 1);
                else
                    dp[i][j] = new Info(dp[i - 1][j].reach, dp[i - 1][j].elements);
            }
        }
        for (Info[] r : dp) {
            for (Info c : r)
                System.out.print(c.reach + "\t");
            System.out.println();
        }
        System.out.println();
        // ans determination
        for (int j = sum / 2; j >= 0; j--) {
            for (int i = 1; i <= nums.length; i++) {
                if (dp[i][j].reach && dp[i][j].elements == nums.length / 2)
                    return Math.abs(sum - 2 * j);// first knapsack j total, second has (sum - j) total
            }
        }
        return sum;
    }

    public static int minJumps(int[] nums) {
        if (nums.length == 1)
            return 0;
        int dp[] = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i + nums[i] >= nums.length - 1)
                return dp[i] + 1;
            for (int j = 1; j <= nums[i]; j++) {
                dp[i + j] = Math.min(dp[i + j], 1 + dp[i]);
                if (i + j == nums.length - 1)
                    return dp[i + j];
            }
        }
        return -1;// not executed as given that end always reachable
    }

    public static void main(String[] args) {

    }
}

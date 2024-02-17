import java.util.*;

public class greedy {
    public static void activities(int[] s, int[] e) {
        ArrayList<Integer> ans = new ArrayList<>();
        int maxActivities = 1;
        int prevEnd = e[0];
        ans.add(0);
        for (int i = 1; i < s.length; i++) {
            if (prevEnd <= s[i]) {
                maxActivities++;
                ans.add(i);//adding the start time indexes
                prevEnd = e[i];//update end if that activity is counted 
            }
        }
        System.out.println("maxActivities = " + maxActivities + " : " + ans);
    }

    public static void unsortedEndActivities(int[] s, int[] e) {
        ArrayList<Integer> ans = new ArrayList<>();
        int[][] activities = new int[s.length][3];//stores indexes, starting times, dec sorted end times
        for(int i = 0; i < s.length; i++) {
            activities[i][0] = i;
            activities[i][1] = s[i];
            activities[i][2] = e[i];
        }
        Arrays.sort(activities, Comparator.comparingDouble(o->o[2]));//sorting the ending array col and other cols sorted accordingly
        int maxActivities = 1;
        ans.add(activities[0][0]);
        int end = activities[0][2];
        for (int i = 1; i < s.length; i++) {
            if (end <= activities[i][1]) {
                maxActivities++;
                ans.add(activities[i][1]);
                end = activities[i][2];
            } 
        }
        System.out.println("maxActivities = " + maxActivities + " : " + ans);//ans contains indexes via the activities to be done can be traced in original start[]
    }

    public static void fractionalKnapsack(int[] val, int[] wt, int maxCap) {
        double[][] storage = new double[val.length][2];//stores inc sorted val/wt ratio, max capacity (wt) of each that can be put in knapsack
        for (int i = 0; i < val.length; i++) {
            double ratio = val[i]/wt[i];
            storage[i][0] = ratio;
            storage[i][1] = wt[i];
        }

        Arrays.sort(storage, Comparator.comparingDouble(o->o[0]));//lowest ratio above as sorted in inc order

        int maxVal = 0;
        int capacityUsed = 0;

        for (int i = val.length-1; i >= 0; i--) {
            if (capacityUsed + storage[i][1] > maxCap) {//next addition exceeding max capacity 
                maxVal += (maxCap-capacityUsed) * storage[i][0];
                capacityUsed += maxCap - capacityUsed;//will get full (= maxCap) in most cases on fractional part addition 
            }
            else {
                maxVal += storage[i][0] * storage[i][1];
                capacityUsed += storage[i][1];
            }
            System.out.println(maxVal + ".."+capacityUsed);//final statement
        }
    }

    public static void minDiffPairs(int[] A, int[] B) {
        Arrays.sort(A);
        Arrays.sort(B);
        int maxDiff = 0;
        for (int i = 0; i < A.length; i++) {
            maxDiff += Math.abs(A[i] - B[i]);
        }
        System.out.println(maxDiff);
    }

    public static void maxChainPairs(int[] p1, int[] p2) {
        ArrayList<Integer> ans = new ArrayList<>();
        int[][] storage = new int[p1.length][3];

        for (int i = 0; i < p1.length; i++) {
            storage[i][0] = i;//indexes
            storage[i][1] = p1[i];//1st part of pair
            storage[i][2] = p2[i];//2nd part of pair
        }

        Arrays.sort(storage, Comparator.comparingDouble(o->o[2]));//sortbased on ending pairs

        ans.add(storage[0][0]);
        int chainLength = storage[0][2] - storage[0][1] + 1;
        int end = storage[0][2];
        
        for (int i = 1; i < p1.length; i++) {
            if (end <= storage[i][1]) {
                chainLength += storage[i][2] - storage[i][1] + 1;
                ans.add(storage[i][0]);
                end = storage[i][2];
            }
        }

        System.out.println("chainLength : " + chainLength + " : " + ans);

    }

    public static void indianCoins(int v, Integer[] coinTypes) {
        Arrays.sort(coinTypes, Comparator.reverseOrder());
        ArrayList<Integer> coins = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < coinTypes.length; i++) {
            if (v == 0) {
                break;
            }
            while (v >= coinTypes[i]) {
                v -= coinTypes[i];
                coins.add(coinTypes[i]);
                count++;
            }
        }

        System.out.println("count : " + count + " : " + coins);
    }

    static class Job {
        int deadline;
        int profit;
        int id;
        public Job(int deadline, int profit, int id) {
            this.deadline = deadline;
            this.profit = profit;
            this.id = id;
        }
    }
    public static void jobSequencing(int[][] jobsInfo) {
        ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 0; i < jobsInfo.length; i++) {//storing the jobs info in a class job arraylist
            jobs.add(new Job(jobsInfo[i][0], jobsInfo[i][1], i));
        }

        Collections.sort(jobs, (a,b) -> b.profit-a.profit);//descending order profit wise

        ArrayList<Integer> ans = new ArrayList<>();
        int t = 0;
        int profit = 0;

        for (int i = 0; i < jobs.size(); i++) {
            if (t < jobs.get(i).deadline) {//every job takes 1 unit of time so it totalTime still < that job's deadline time then means that job can be done in 1 unit of time
                profit += jobs.get(i).profit;
                t++;
                ans.add(jobs.get(i).id);
            }
        }
        
        System.out.println("profit : " + profit + ", job ids : " + ans);
    }

    public static void minCostCutChocolate(Integer[] vPrices, Integer[] hPrices) {
        Arrays.sort(vPrices, Collections.reverseOrder());
        Arrays.sort(hPrices, Collections.reverseOrder());
        int v = 0;
        int h = 0;
        int vPieces = 1;
        int hPieces = 1;
        int cost = 0;

        while (v < vPrices.length && h < hPrices.length) {//remember that all cuts will definitely be made
            if (vPrices[v] > hPrices[h]) {
                cost += vPrices[v] * vPieces;
                hPieces++;
                v++;
            }
            else {//horizontal price greater that vertical price (or = : any can be taken in that case)
                cost += hPrices[h] * hPieces;
                vPieces++;
                h++;
            }
        }
        while (v < vPrices.length) {
            cost += vPrices[v] * vPieces;
            v++;
            // hPieces++; //no need to update as all horizontal cuts made (vPieces maxed out) and hPieces won't be required as cost from all horizontal cuts alr done
        }
        while (h < hPrices.length) {
            cost += hPrices[h] * hPieces;
            h++;
            // vPieces++; //no need to update as all vertical cuts made (hPieces maxed out) and vPieces won't be required as cost from all vertical cuts alr done
        }

        System.out.println("Cost : " + cost);
    }

    //Asg 

    //https://leetcode.com/problems/split-a-string-in-balanced-strings/

    //didn't find -> sort + k th largest odd 

    //https://leetcode.com/problems/smallest-string-with-a-given-numeric-value/
    public static void main(String args[]) {
        // int[] s = {5,8,5,0,3,1};
        // int[] e = {9,9,7,6,4,2};
        // unsortedEndActivities(s, e);
        // int[] wt = {20,10,30};
        // int[] val = {100,60,120};
        // fractionalKnapsack(val, wt, 50);
        // int[] A = {4,1,8,7};
        // int[] B = {2,3,6,5};
        // minDiffPairs(A, B);
        // int[] p1 = {5,39,5,27,50};
        // int[] p2 = {24,60,28,40,90};
        // maxChainPairs(p1, p2);
        // Integer[] coinTypes = {1,2,5,10,20,50,100,500,2000};
        // indianCoins(590, coinTypes);
        // int[][] jobsInfo = {{4,20},{1,10},{1,40},{1,30},{2,35}};
        // jobSequencing(jobsInfo);
        Integer[] vertical = {2,1,3,1,4};
        Integer[] horizontal = {2,1,4};
        minCostCutChocolate(vertical, horizontal);

        
    }
}
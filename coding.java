import java.util.*;

public class coding {
    static class FoodRatings {
        public static class FoodNRating implements Comparable<FoodNRating> {
            String food;
            int rating;

            FoodNRating(String food, int rating) {
                this.food = food;
                this.rating = rating;
            }

            @Override
            public int compareTo(FoodNRating x) {
                if (this.rating == x.rating) {
                    return this.food.compareTo(x.food);
                }
                return x.rating - this.rating;
            }
        }
        private HashMap<String, PriorityQueue<FoodNRating>> storage;
        //              Cuisine
        private HashMap<String, FoodNRating> foodRatings;
        //              Food    Node
        private HashMap<String, String> foodCuisine;
        //              Food    Cuisine
        public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
            storage = new HashMap<>();
            foodRatings = new HashMap<>();
            for (int i = 0; i < foods.length; ++i) {
                String f = foods[i], c = cuisines[i];
                int r = ratings[i];
                FoodNRating node = new FoodNRating(f, r);
                if (!storage.containsKey(c)) {
                    PriorityQueue<FoodNRating> pq = new PriorityQueue<>();
                    pq.add(node);
                    storage.put(c, pq);
                } else {
                    storage.get(c).add(node);
                }
                foodRatings.put(f, node);
                foodCuisine.put(f, c);
            }
        }

        public void changeRating(String food, int newRating) {
            FoodNRating newNode = new FoodNRating(food, newRating);
            FoodNRating node = foodRatings.get(food);
            foodRatings.put(food, newNode);
            String c = foodCuisine.get(food);
            storage.get(c).remove(node);
            storage.get(c).add(newNode);
        }
        public String highestRated(String cuisine) {
            return storage.get(cuisine).peek().food;
        }
    }
    public static void main(String[] args) {
        String[] foods = { "miso", "sushi", "ramen" };
        String[] cuisines = { "japanese", "japanese", "japanese" };
        int[] ratings = { 16, 8, 14 };

        FoodRatings obj = new FoodRatings(foods, cuisines, ratings);
        // for (String key : obj.foodRatings.keySet()) {
        //     System.out.println(obj.foodRatings.get(key).food + " " + obj.foodRatings.get(key).rating);
        // }
        System.out.println(obj.storage.get("japanese").peek().food);
        System.out.println(obj.foodRatings.get("miso").rating);
        System.out.println(obj.foodRatings.get("miso"));
        System.out.println(obj.storage.get("japanese").peek());
        obj.changeRating("miso", 13);
        System.out.println(obj.storage.get("japanese").peek().food);
        System.out.println(obj.storage.get("japanese").peek().rating);
        
        
    }
}
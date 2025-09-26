import java.util.*;

public class ShoppingOffers {

    public static int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        
        Map<List<Integer>, Integer> memo = new HashMap<>();
        
        return solve(price, special, needs, memo);
    }

    private static int solve(List<Integer> price, List<List<Integer>> special, List<Integer> currentNeeds, Map<List<Integer>, Integer> memo) {
        // 1. Check if we have already computed the result for this state.
        if (memo.containsKey(currentNeeds)) {
            return memo.get(currentNeeds);
        }

        // 2. Calculate the "base case" cost: buying all items individually.
        // This is our initial best price, which we'll try to beat with special offers.
        int minCost = 0;
        for (int i = 0; i < currentNeeds.size(); i++) {
            minCost += currentNeeds.get(i) * price.get(i);
        }

        // 3. Explore choices: Iterate through each special offer.
        for (List<Integer> offer : special) {
            List<Integer> nextNeeds = new ArrayList<>();
            boolean canApplyOffer = true;

            // Check if this offer is valid for our current needs.
            for (int i = 0; i < currentNeeds.size(); i++) {
                if (currentNeeds.get(i) < offer.get(i)) {
                    canApplyOffer = false;
                    break; // Cannot apply this offer, we don't have enough needs.
                }
                nextNeeds.add(currentNeeds.get(i) - offer.get(i));
            }

            // 4. If the offer is valid, make the recursive call.
            if (canApplyOffer) {
                // The cost for this path is the offer's price plus the minimum cost
                // for the remaining items (found via the recursive call).
                int offerPrice = offer.get(currentNeeds.size());
                int costWithOffer = offerPrice + solve(price, special, nextNeeds, memo);

                // 5. Update our minimum cost if this path is cheaper.
                minCost = Math.min(minCost, costWithOffer);
            }
        }

        // 6. Store the result in our memo map before returning.
        memo.put(currentNeeds, minCost);
        return minCost;
    }

    public static void main(String[] args) {
        List<Integer> price = Arrays.asList(2, 5);
        List<List<Integer>> special = Arrays.asList(
            Arrays.asList(3, 0, 5), Arrays.asList(1, 2, 10));
        List<Integer> needs = Arrays.asList(3, 2);

        System.out.println(shoppingOffers(price, special, needs));
    }
}
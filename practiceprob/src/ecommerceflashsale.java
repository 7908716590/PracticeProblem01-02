import java.util.*;

public class ecommerceflashsale {

    // HashMap to store productId -> stock count
    private HashMap<String, Integer> stockMap = new HashMap<>();

    // LinkedHashMap for waiting list (FIFO)
    private LinkedHashMap<Integer, String> waitingList = new LinkedHashMap<>();

    // Constructor
    public ecommerceflashsale() {
        stockMap.put("IPHONE15_256GB", 100);
    }

    // Check stock availability
    public int checkStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }

    // Purchase item (synchronized to prevent overselling)
    public synchronized String purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {
            stockMap.put(productId, stock - 1);
            return "Success, " + (stock - 1) + " units remaining";
        }
        else {
            waitingList.put(userId, productId);
            return "Added to waiting list, position #" + waitingList.size();
        }
    }

    // Show waiting list
    public void showWaitingList() {
        for (Map.Entry<Integer, String> entry : waitingList.entrySet()) {
            System.out.println("User " + entry.getKey() + " waiting for " + entry.getValue());
        }
    }

    // Main method
    public static void main(String[] args) {

        ecommerceflashsale manager = new ecommerceflashsale();

        System.out.println("Stock: " +
                manager.checkStock("IPHONE15_256GB") + " units available");

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 67890));

        // Simulate stock finishing
        for (int i = 0; i < 100; i++) {
            manager.purchaseItem("IPHONE15_256GB", i);
        }

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 99999));

        manager.showWaitingList();
    }
}
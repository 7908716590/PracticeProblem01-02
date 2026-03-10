import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    long time;

    public Transaction(int id, int amount, String merchant, String account, long time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class TransactionAnalyzer {

    List<Transaction> transactions = new ArrayList<>();

    // Add transaction
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // Classic Two-Sum
    public void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                System.out.println("Two-Sum Found: (" +
                        other.id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }

    // Duplicate detection
    public void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (Map.Entry<String, List<Transaction>> entry : map.entrySet()) {

            if (entry.getValue().size() > 1) {

                System.out.println("Duplicate Transactions:");

                for (Transaction t : entry.getValue()) {
                    System.out.println("ID: " + t.id +
                            " Amount: " + t.amount +
                            " Merchant: " + t.merchant +
                            " Account: " + t.account);
                }
            }
        }
    }

    public static void main(String[] args) {

        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        analyzer.addTransaction(new Transaction(1, 500, "StoreA", "acc1", System.currentTimeMillis()));
        analyzer.addTransaction(new Transaction(2, 300, "StoreB", "acc2", System.currentTimeMillis()));
        analyzer.addTransaction(new Transaction(3, 200, "StoreC", "acc3", System.currentTimeMillis()));
        analyzer.addTransaction(new Transaction(4, 500, "StoreA", "acc4", System.currentTimeMillis()));

        analyzer.findTwoSum(500);

        analyzer.detectDuplicates();
    }
}
import java.util.*;

public class practiceprob {

    // HashMap to store username -> userId
    private HashMap<String, Integer> usernameMap = new HashMap<>();

    // HashMap to track frequency of attempts
    private HashMap<String, Integer> attemptFrequency = new HashMap<>();

    // Constructor (simulate existing users)
    public practiceprob() {
        usernameMap.put("john_doe", 1);
        usernameMap.put("admin", 2);
        usernameMap.put("user123", 3);
    }

    // Check username availability
    public boolean checkAvailability(String username) {

        // Increase attempt count
        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        // Check if username exists
        return !usernameMap.containsKey(username);
    }

    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;

            if (!usernameMap.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        // Replace underscore with dot suggestion
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {

        String mostAttempted = "";
        int maxAttempts = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {

            if (entry.getValue() > maxAttempts) {
                maxAttempts = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + maxAttempts + " attempts)";
    }

    // Main method
    public static void main(String[] args) {

        practiceprob checker = new practiceprob();

        System.out.println("john_doe available: "
                + checker.checkAvailability("john_doe"));

        System.out.println("jane_smith available: "
                + checker.checkAvailability("jane_smith"));

        System.out.println("Suggestions for john_doe: "
                + checker.suggestAlternatives("john_doe"));

        System.out.println("Most attempted username: "
                + checker.getMostAttempted());
    }
}
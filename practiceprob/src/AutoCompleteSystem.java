import java.util.*;

public class AutoCompleteSystem {

    // query -> frequency
    private HashMap<String, Integer> queryFrequency = new HashMap<>();

    // Add or update search query
    public void updateFrequency(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
    }

    // Get top suggestions for prefix
    public List<String> search(String prefix) {

        List<Map.Entry<String, Integer>> matches = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                matches.add(entry);
            }
        }

        // Sort by frequency descending
        matches.sort((a, b) -> b.getValue() - a.getValue());

        List<String> suggestions = new ArrayList<>();

        int count = 0;
        for (Map.Entry<String, Integer> entry : matches) {

            if (count >= 10) break;

            suggestions.add(entry.getKey() + " (" + entry.getValue() + " searches)");
            count++;
        }

        return suggestions;
    }

    public static void main(String[] args) {

        AutoCompleteSystem system = new AutoCompleteSystem();

        // Initial search queries
        system.updateFrequency("java tutorial");
        system.updateFrequency("javascript");
        system.updateFrequency("java download");
        system.updateFrequency("java tutorial");
        system.updateFrequency("java tutorial");
        system.updateFrequency("java 21 features");

        System.out.println("Search results for 'jav':");

        List<String> results = system.search("jav");

        for (String s : results) {
            System.out.println(s);
        }
    }
}
import java.util.*;

public class PlagiarismDetector {

    // n-gram → document IDs
    private HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    private int N = 5; // size of n-gram

    // Extract n-grams from document
    public List<String> extractNGrams(String text) {

        List<String> ngrams = new ArrayList<>();
        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    // Add document to database
    public void addDocument(String docId, String text) {

        List<String> ngrams = extractNGrams(text);

        for (String gram : ngrams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }

    // Analyze document similarity
    public void analyzeDocument(String docId, String text) {

        List<String> ngrams = extractNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            if (ngramIndex.containsKey(gram)) {

                for (String doc : ngramIndex.get(gram)) {

                    if (!doc.equals(docId)) {
                        matchCount.put(doc,
                                matchCount.getOrDefault(doc, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Extracted " + ngrams.size() + " n-grams");

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {

            double similarity =
                    (entry.getValue() * 100.0) / ngrams.size();

            System.out.println("Found " + entry.getValue()
                    + " matching n-grams with \"" + entry.getKey() + "\"");

            System.out.println("Similarity: "
                    + String.format("%.2f", similarity) + "%");

            if (similarity > 60) {
                System.out.println("PLAGIARISM DETECTED");
            }
        }
    }

    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        String essay1 = "Artificial intelligence is transforming the world with advanced technology";
        String essay2 = "Artificial intelligence is transforming the world with new innovations";
        String essay3 = "Sports and fitness are important for a healthy lifestyle";


        detector.addDocument("essay_089.txt", essay1);
        detector.addDocument("essay_092.txt", essay2);

        detector.analyzeDocument("essay_123.txt", essay1);
    }
}
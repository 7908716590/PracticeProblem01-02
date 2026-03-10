import java.util.*;

class VideoData {
    String videoId;
    String content;

    public VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}

public class MultiLevelCache {

    // L1 Cache (10,000 most popular)
    private LinkedHashMap<String, VideoData> L1Cache;

    // L2 Cache (100,000 videos)
    private HashMap<String, VideoData> L2Cache = new HashMap<>();

    // L3 Database simulation
    private HashMap<String, VideoData> database = new HashMap<>();

    private int L1Hits = 0;
    private int L2Hits = 0;
    private int L3Hits = 0;

    public MultiLevelCache() {

        L1Cache = new LinkedHashMap<String, VideoData>(10000, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                return size() > 10000;
            }
        };

        // Simulate database videos
        database.put("video_123", new VideoData("video_123", "Movie Data"));
        database.put("video_999", new VideoData("video_999", "Documentary Data"));
    }

    public VideoData getVideo(String videoId) {

        // L1 Cache
        if (L1Cache.containsKey(videoId)) {
            L1Hits++;
            System.out.println("L1 Cache HIT");
            return L1Cache.get(videoId);
        }

        System.out.println("L1 Cache MISS");

        // L2 Cache
        if (L2Cache.containsKey(videoId)) {
            L2Hits++;
            System.out.println("L2 Cache HIT → Promoting to L1");

            VideoData video = L2Cache.get(videoId);
            L1Cache.put(videoId, video);

            return video;
        }

        System.out.println("L2 Cache MISS");

        // L3 Database
        if (database.containsKey(videoId)) {
            L3Hits++;
            System.out.println("L3 Database HIT → Adding to L2");

            VideoData video = database.get(videoId);
            L2Cache.put(videoId, video);

            return video;
        }

        System.out.println("Video not found");
        return null;
    }

    public void getStatistics() {

        int total = L1Hits + L2Hits + L3Hits;

        System.out.println("L1 Hits: " + L1Hits);
        System.out.println("L2 Hits: " + L2Hits);
        System.out.println("L3 Hits: " + L3Hits);

        if (total > 0) {
            System.out.println("Overall Hit Rate: " +
                    ((L1Hits + L2Hits) * 100.0 / total) + "%");
        }
    }

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video_123");
        cache.getVideo("video_123");

        cache.getVideo("video_999");

        cache.getStatistics();
    }
}
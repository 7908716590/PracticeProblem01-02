import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime;

    public DNSEntry(String domain, String ipAddress, int ttlSeconds) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    // HashMap for DNS cache
    private HashMap<String, DNSEntry> cache = new HashMap<>();

    private int cacheHits = 0;
    private int cacheMisses = 0;

    private int maxSize = 5;

    // Resolve domain
    public String resolve(String domain) {

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                cacheHits++;
                return "Cache HIT → " + entry.ipAddress;
            }
            else {
                cache.remove(domain);
                System.out.println("Cache EXPIRED → Querying upstream...");
            }
        }

        // Cache miss
        cacheMisses++;

        String ip = queryUpstreamDNS(domain);

        if (cache.size() >= maxSize) {
            removeOldestEntry();
        }

        cache.put(domain, new DNSEntry(domain, ip, 300));

        return "Cache MISS → " + ip + " (TTL: 300s)";
    }

    // Simulate upstream DNS lookup
    private String queryUpstreamDNS(String domain) {

        Random rand = new Random();
        return "172.217.14." + rand.nextInt(255);
    }

    // Remove oldest entry (simple LRU simulation)
    private void removeOldestEntry() {

        String firstKey = cache.keySet().iterator().next();
        cache.remove(firstKey);
    }

    // Cache statistics
    public void getCacheStats() {

        int total = cacheHits + cacheMisses;

        double hitRate = total == 0 ? 0 : ((double) cacheHits / total) * 100;

        System.out.println("Cache Hits: " + cacheHits);
        System.out.println("Cache Misses: " + cacheMisses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {

        DNSCache dns = new DNSCache();

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("openai.com"));

        dns.getCacheStats();
    }
}
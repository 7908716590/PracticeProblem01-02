import java.util.*;

class TokenBucket {

    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate; // tokens per hour

    public TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public void refill() {

        long now = System.currentTimeMillis();
        long elapsedTime = now - lastRefillTime;

        int tokensToAdd = (int)((elapsedTime / 3600000.0) * refillRate);

        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    public boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }
}

public class RateLimiter {

    // clientId -> TokenBucket
    private HashMap<String, TokenBucket> clients = new HashMap<>();

    private int maxRequests = 1000;

    // Check rate limit
    public String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(maxRequests, maxRequests));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            return "Allowed (" + bucket.tokens + " requests remaining)";
        }
        else {
            return "Denied (0 requests remaining, try again later)";
        }
    }

    // Get client status
    public void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket != null) {
            int used = bucket.maxTokens - bucket.tokens;

            System.out.println("Used: " + used);
            System.out.println("Limit: " + bucket.maxTokens);
            System.out.println("Remaining: " + bucket.tokens);
        }
    }

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        System.out.println(limiter.checkRateLimit("abc123"));
        System.out.println(limiter.checkRateLimit("abc123"));
        System.out.println(limiter.checkRateLimit("abc123"));

        limiter.getRateLimitStatus("abc123");
    }
}
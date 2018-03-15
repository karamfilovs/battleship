package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {
    private String text;
    private static final Logger LOGGER = LoggerFactory.getLogger(Counter.class);

    public int getMissCount() {
        return missCount;
    }

    public int getHitCount() {
        return hitCount;
    }

    private int missCount = 0;
    private int hitCount = 0;

    public Counter(String text) {
        this.text = text;
        countAll();
    }


    public int countMissed() {
        missCount = text.split("-", -1).length-1;
        LOGGER.info("Missed Count:" + missCount);
        return missCount;
    }


    public int countHit() {
        hitCount = text.split("x", -1).length-1;
        LOGGER.info("Hit Count:" + hitCount);
        return hitCount;
    }


    public void countAll() {
        countMissed();
        countHit();

    }

}

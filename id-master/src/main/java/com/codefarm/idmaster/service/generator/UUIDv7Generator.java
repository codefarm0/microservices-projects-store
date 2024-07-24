package com.codefarm.idmaster.service.generator;

import com.codefarm.idmaster.model.GeneratedId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UUIDv7Generator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public synchronized String generateUUIDv7() {
        long timestamp = System.currentTimeMillis();
        int seq = counter.getAndIncrement() & 0xFFF; // 12-bit sequence

        // Most significant bits (48 bits of timestamp + 4 bits version (0111) + 12 bits sequence)
        long mostSigBits = (timestamp << 16) & 0xFFFFFFFFFFFF0000L;
        mostSigBits |= (0x7 << 12); // Setting the version to 7
        mostSigBits |= seq;

        // Least significant bits (62 bits randomness)
        long leastSigBits = ThreadLocalRandom.current().nextLong() & 0x3FFFFFFFFFFFFFFFL;
        leastSigBits |= (0x2L << 62); // Setting variant to 2 (IETF variant)

        return new UUID(mostSigBits, leastSigBits).toString();
    }
}

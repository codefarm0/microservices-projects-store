package com.codefarm.idmaster.service.generator;

import org.springframework.stereotype.Service;

@Service
public class SnowflakeIdGenerator {

    private final long workerId;
    private final long datacenterId;
    private final long sequence = 0L;

    private final long twepoch = 1288834974657L;

    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequenceNumber = 0L;

    public SnowflakeIdGenerator() {
        this(getWorkerId(), getDatacenterId());
    }

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized Long generateSnowflakeId() {
            long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequenceNumber = (sequenceNumber + 1) & sequenceMask;
            if (sequenceNumber == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequenceNumber = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequenceNumber;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private static long getWorkerId() {
        // Implement logic to get unique worker ID for the node
        return 0L; // Example, replace with actual logic
    }

    private static long getDatacenterId() {
        // Implement logic to get unique datacenter ID for the node
        return 0L; // Example, replace with actual logic
    }
}

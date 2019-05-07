package com.neptune.manager.common.uitl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;

public class CommonIdGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonIdGenerator.class);
    //每台机器分配不同的id
    private static final String WORKER_ID;
    private static final String PID;
    private static final String MILL = "yyMMddHHmmssSSS";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(MILL);
    //yyMMdd HHmmssSSS index
    private static final int DATE_END = 6;
    //sequence 初始值
    private static final String SEQUENCE = "0000";
    // 最大9999
    private static final long MAX_SEQUENCE = 10000;
    private static final Clock CLOCK = Clock.systemUTC();
    private static long lastTimestamp = -1L;
    // 0，并发控制
    private static long sequence = 0L;

    static {
        //根据机器IP和进程ID生成WORKER_ID,分别取ip地址的后三段进行二进制拼接后转为int(8位)，再拼接pid
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        StringBuilder pidBuilder = new StringBuilder("00000");
        try {
            String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
            PID = pid.length() > 5 ? pid.substring(pid.length() - 5) : pid;
            pidBuilder.replace(5 - PID.length(), 5, PID);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot get Process Id!", e);
        }
        LOGGER.info("pid: {}", pidBuilder);
        byte[] ipAddressByteArray = address.getAddress();
        long ip = ((ipAddressByteArray[1] & 0xFF) << 16) | ((ipAddressByteArray[2] & 0xFF) << 8) | (ipAddressByteArray[3] & 0xFF);
        LOGGER.info("ip: {}", ip);
        WORKER_ID = String.format("%08d%s", ip, pidBuilder);
        LOGGER.info("workerId: {}", WORKER_ID);
    }

    public static synchronized String generateId() {
        String seq = String.valueOf(generateSequence());
        StringBuilder code = new StringBuilder(SIMPLE_DATE_FORMAT.format(new Date(lastTimestamp)));
        code.insert(DATE_END, WORKER_ID);
        StringBuilder sequenceValue = new StringBuilder(SEQUENCE);
        sequenceValue.replace(4 - seq.length(), 4, seq);
        code.append(sequenceValue);
        return code.toString();
    }

    private static synchronized long generateSequence() {
        long timestamp = timeGen();
        // 如果上一个timestamp与新产生的相等，则sequence加一(0-9999循环); 对新的timestamp，sequence从0开始
        if (lastTimestamp == timestamp) {
            sequence = sequence + 1;
            if (sequence > MAX_SEQUENCE) {
                // 重新生成timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else if (lastTimestamp > timestamp) {
            LOGGER.error("System.currentTimeMillis() get a old time");
            // 重新生成timestamp
            timestamp = tilNextMillis(lastTimestamp);
        }

        if (lastTimestamp < timestamp) {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return sequence;
    }

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     */
    private static long timeGen() {
        return CLOCK.millis();
    }
}

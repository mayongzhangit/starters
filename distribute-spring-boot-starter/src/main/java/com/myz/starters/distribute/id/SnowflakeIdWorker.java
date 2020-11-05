package com.myz.starters.distribute.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/5 16:03
 * @email 2641007740@qq.com
 */
public class SnowflakeIdWorker {

    private Logger logger = LoggerFactory.getLogger(SnowflakeIdWorker.class);

    // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
    private final static long twepoch = 1604544520000L; // 从此时间往后69年都可以使用这个算法
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    /* 上次生产id时间戳 */
    private static long lastTimestamp = -1L;
    // 0，并发控制
    private long sequence = 0L;

    private final long workerId;
    // 数据标识id部分
    private final long datacenterId;

    public SnowflakeIdWorker(){
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }

    /**
     * @param workerId
     *            工作机器ID
     * @param dataCenterId
     *            序列号
     */
    public SnowflakeIdWorker(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDatacenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = dataCenterId;
    }

    /**
     *
     * @param id
     * @return
     */
    public SnowflakeInfo extraId(long id) {
        SnowflakeInfo snowflakeInfo = new SnowflakeInfo();
        snowflakeInfo.setId(id);
        snowflakeInfo.setTwepoch(SnowflakeIdWorker.twepoch);
        snowflakeInfo.setDataCenterIdBits(SnowflakeIdWorker.datacenterIdBits);
        snowflakeInfo.setWorkerIdBits(SnowflakeIdWorker.workerIdBits);
        snowflakeInfo.setSequenceBits(SnowflakeIdWorker.sequenceBits);

        long timestamp = (id >> (SnowflakeIdWorker.datacenterIdBits+SnowflakeIdWorker.workerIdBits+SnowflakeIdWorker.sequenceBits))+SnowflakeIdWorker.twepoch;
        snowflakeInfo.setTimestamp(timestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        Date time = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String format = sdf.format(time);
        snowflakeInfo.setTimestampFormat(format);

        int timestampEndBits = (int)(64-(SnowflakeIdWorker.datacenterIdBits+SnowflakeIdWorker.workerIdBits+SnowflakeIdWorker.sequenceBits)); // 42
        int dataCenterEndBits = (int)(SnowflakeIdWorker.workerIdBits+SnowflakeIdWorker.sequenceBits); //
        int dataCenterId = (int) ((( (id <<timestampEndBits) >> timestampEndBits )>>dataCenterEndBits ));
        snowflakeInfo.setDataCenterId(dataCenterId);

        int workerStartBits = (int)(timestampEndBits+SnowflakeIdWorker.datacenterIdBits);
        int workerId = (int)(((id <<workerStartBits)>> workerStartBits)>>SnowflakeIdWorker.sequenceBits);
        snowflakeInfo.setWorkerId(workerId);

        int sequenceStartBits = (int)(timestampEndBits+SnowflakeIdWorker.datacenterIdBits+SnowflakeIdWorker.workerIdBits);
        int sequence = (int)(((id <<sequenceStartBits)>> sequenceStartBits));
        snowflakeInfo.setSequence(sequence);

        return snowflakeInfo;
    }

    /**
     * 获取下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
        if (logger.isDebugEnabled()){
            logger.debug("timestamp="+timestamp+"( timestamp - twepoch) ="+(timestamp-twepoch)+" dataCenterId=" +datacenterId+" workerId="+workerId+" sequence="+sequence);
        }
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * 获取 maxWorkerId
     * </p>
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }



    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(2,3);
        long id = idWorker.nextId();
        id=idWorker.nextId();
        id=idWorker.nextId();
        id=idWorker.nextId();
        id=idWorker.nextId();

        SnowflakeInfo snowflakeInfo = idWorker.extraId(id);
        System.out.println(snowflakeInfo);
    }

}

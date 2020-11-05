package com.myz.starters.distribute.id;

import java.io.Serializable;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/5 16:13
 * @email 2641007740@qq.com
 */
public class SnowflakeInfo implements Serializable {
    private long id;
    private long twepoch;
    private long workerIdBits;
    private long dataCenterIdBits;
    private long sequenceBits;

    private long timestamp;
    private String timestampFormat;
	private int dataCenterId;
	private int workerId;
	private int sequence;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTwepoch() {
        return twepoch;
    }

    public void setTwepoch(long twepoch) {
        this.twepoch = twepoch;
    }

    public long getWorkerIdBits() {
        return workerIdBits;
    }

    public void setWorkerIdBits(long workerIdBits) {
        this.workerIdBits = workerIdBits;
    }

    public long getDataCenterIdBits() {
        return dataCenterIdBits;
    }

    public void setDataCenterIdBits(long dataCenterIdBits) {
        this.dataCenterIdBits = dataCenterIdBits;
    }

    public long getSequenceBits() {
        return sequenceBits;
    }

    public void setSequenceBits(long sequenceBits) {
        this.sequenceBits = sequenceBits;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
    }

    public int getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(int dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "SnowflakeInfo{" +
                "id=" + id +
                ", twepoch=" + twepoch +
                ", workerIdBits=" + workerIdBits +
                ", datacenterIdBits=" + dataCenterIdBits +
                ", sequenceBits=" + sequenceBits +
                ", timestamp=" + timestamp +
                ", timestampFormat='" + timestampFormat + '\'' +
                ", dataCenterId=" + dataCenterId +
                ", workerId=" + workerId +
                ", sequence=" + sequence +
                '}';
    }
}

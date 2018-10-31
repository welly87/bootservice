package com.tambunan.bus;

import java.util.HashMap;

public class BuzzHeader {
    private String key;
    private long offset;
    private int partition;
    private long timestamp;
    private String topic;
    private HashMap<String, String> kafkaHeader;

    public BuzzHeader(String key, long offset, int partition, long timestamp, String topic, HashMap<String, String> kafkaHeader) {
        this.key = key;
        this.offset = offset;
        this.partition = partition;
        this.timestamp = timestamp;
        this.topic = topic;
        this.kafkaHeader = kafkaHeader;
    }

    public String getMessageType() {
        return kafkaHeader.get("MessageType");
    }

    public String getMessageId() {
        return kafkaHeader.get("MessageId");
    }

    public String getCorrelationId() {
        return kafkaHeader.get("CorrelationId");
    }

    public String getConversationId() {
        return kafkaHeader.get("ConversationId");
    }

    public String replyToAddress() {
        return kafkaHeader.get("ReplyToAddress");
    }

    public String getKey() {
        return key;
    }

    public long getOffset() {
        return offset;
    }

    public int getPartition() {
        return partition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTopic() {
        return topic;
    }

    public String replyTo() {
        return "";
    }
}

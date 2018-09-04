package com.auvx.melloo.domain;

import java.time.Instant;
import java.util.List;

public class MomentRecord {

    public enum ContentMediaType {
        TEXT(1), PICTURE(2), VIDEO(4);
        ContentMediaType(int value) {
            this.value = value;
        }
        private int value;
        public int getValue() {
            return value;
        }
    }
    private Long accountId;
    private Long id;
    private Integer contentType;
    private List<String> tape;
    private Instant createTime;
    private String uuid;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public List<String> getTape() {
        return tape;
    }

    public void setTape(List<String> tape) {
        this.tape = tape;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "MomentRecord{" +
                "accountId=" + accountId +
                ", id=" + id +
                ", contentType=" + contentType +
                ", tape=" + tape +
                ", createTime=" + createTime +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}

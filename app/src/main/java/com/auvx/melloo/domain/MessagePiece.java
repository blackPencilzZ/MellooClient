package com.auvx.melloo.domain;

import java.time.Instant;

public class MessagePiece {

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

    public enum TransferState {
        RECEIVED(1), SENT(2), TOSEND(4);
        TransferState(int value) {
            this.value = value;
        }
        private int value;
        public int getValue() {
            return value;
        }
    }

    private Long id;
    private Long targetAccountId;
    private Long senderAccountId;
    private Integer created_by_me;
    private String contentRef;//是多媒体流的话还是得先上传致到服务器
    private Integer contentMediaType;
    private Integer transferState;
    private Instant createTime;
    private String uuid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }


    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Integer getCreated_by_me() {
        return created_by_me;
    }

    public void setCreated_by_me(Integer created_by_me) {
        this.created_by_me = created_by_me;
    }

    public String getContentRef() {
        return contentRef;
    }

    public void setContentRef(String contentRef) {
        this.contentRef = contentRef;
    }

    public Integer getContentMediaType() {
        return contentMediaType;
    }

    public void setContentMediaType(Integer contentMediaType) {
        this.contentMediaType = contentMediaType;
    }

    public Integer getTransferState() {
        return transferState;
    }

    public void setTransferState(Integer transferState) {
        this.transferState = transferState;
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
}

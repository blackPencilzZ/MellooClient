package com.auvx.melloo.domain;

import java.util.List;

public class MomentsAccountBinding {
    private Long accountId;
    private List<MomentRecord> moments;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<MomentRecord> getMoments() {
        return moments;
    }

    public void setMoments(List<MomentRecord> moments) {
        this.moments = moments;
    }

    @Override
    public String toString() {
        return "MomentsAccountBinding{" +
                "accountId=" + accountId +
                ", moments=" + moments +
                '}';
    }
}

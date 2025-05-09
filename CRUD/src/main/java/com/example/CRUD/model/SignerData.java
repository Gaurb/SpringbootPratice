package com.example.CRUD.model;

import java.util.List;

public class SignerData {
    private Long signerId;
    private String name;
    private String memberId;
    private List<String> signDocId;
    private String groupName;
    private String groupCondition;
    private String tag;
    private List<SignerAuthority> signerAuthority;
    private String isActive;
    private String createdOn;
    private String deletedOn;
    private String deletedBy;
    private String createdBy;
    private String checkedOn;
    private String failedReason;
    private String ptiCheck;

    public static class SignerAuthority {
        private String memberAuthority;
        private String authorityType;
        private String transferType;
        private int transferLimitLower;
        private int transferLimitUpper;
        private String expression;
        private String currency;

    }
}

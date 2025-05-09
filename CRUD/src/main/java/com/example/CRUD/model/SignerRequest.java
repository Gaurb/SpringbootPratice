package com.example.CRUD.model;

import java.util.Set;

public class SignerRequest{
    public String parentCaseId;
    public String childCaseId;
    public String userId;
    public Set<SignerList> signerList;


    public static class SignerAuthority{
        public String memberAuthority;
        public String authorityType;
        public String transferType;
        public int transferLimitLower;
        public int transferLimitUpper;
        public String expression;
        public String currency;
    }

    public static class SignerList{
        public String signerId;
        public String name;
        public String memberId;
        public Set<String> signDocId;
        public String groupName;
        public String groupCondition;
        public String tag;
        public Set<SignerAuthority> signerAuthority;
    }

}
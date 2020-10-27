package com.myz.starters.distribute.sample.table.route.sample.model;

import lombok.Data;


@Data
public class UserAccount {

    private String gUserId;
    private long balance=0l;
    private int status=0;
    private String country;
    private long currency=0;
    private long freezeCurrency=0;
    private int type=1;
}

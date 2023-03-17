package com.netease.vcloud.qa;

import java.text.NumberFormat;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 21:26
 */
public class CommonData {

    public static final long ONE_DAY = 24 * 60 * 60 * 1000L ;

    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance() ;

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

}

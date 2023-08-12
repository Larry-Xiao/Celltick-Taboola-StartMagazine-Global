package com.celltick.apac.news.util;

import java.util.UUID;

/**
 * Created by Larryx on 11/12/2018.
 */

public class UUIDGenerator {

    public static String getUUID32(){
//        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String uuid = UUID.randomUUID().toString().toLowerCase();
        return uuid;
    }
}

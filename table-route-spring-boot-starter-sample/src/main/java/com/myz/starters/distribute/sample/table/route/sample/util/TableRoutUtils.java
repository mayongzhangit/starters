package com.myz.starters.distribute.sample.table.route.sample.util;

import com.myz.starters.distribute.sample.table.route.autoconfigure.route.TableRouter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 平衡性的一致性hash算法 获取表路由
 */
public class TableRoutUtils implements TableRouter {

    private static MessageDigest md5 = null;

    public static long getTableRout(String key){
        return hash(key)%10;
    }

    public static long hash(String key) {
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("no md5 algrithm found");
            }
        }
        md5.reset();
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();
        long result = ((long) (bKey[3] & 0xFF) << 24)
                | ((long) (bKey[2] & 0xFF) << 16
                | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF));
        return result & 0xffffffffL;
    }

    public static void main(String args[]){

        System.out.println(getTableRout("2KVH9XN"));

    }

    @Override
    public String route(String routeField) {
        return hash(routeField)%10+"";
    }
}

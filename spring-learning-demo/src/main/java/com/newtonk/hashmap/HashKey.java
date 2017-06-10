package com.newtonk.hashmap;

//import com.google.common.base.Objects;

import com.google.common.base.Objects;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2016/10/15 0015
 */
public class HashKey {
    private String key;

    public HashKey(){}

    public HashKey(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashKey hashKey = (HashKey) o;
        return Objects.equal(key, hashKey.key);
    }

    @Override
    public int hashCode() {
        return 2;
    }
}

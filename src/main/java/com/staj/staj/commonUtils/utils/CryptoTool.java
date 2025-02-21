package com.staj.staj.commonUtils.utils;

import org.hashids.Hashids;

public class CryptoTool {
    private final Hashids hashids;

    public CryptoTool(String salt) {
        var minHashLength = 10;
        this.hashids = new Hashids(salt, minHashLength);//использовал перем. чтобы предотвратить magic number
    }

    public String hashOf(Long value){//делает хеш
        return hashids.encode(value);
    }
    public Long idOf(String value){//дешифрует в число
        long[] res = hashids.decode(value);
        if (res != null && res.length > 0){
            return res[0];
        }
        return null;
    }
}

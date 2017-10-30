package com.shiwaixiangcun.customer.utils;

import com.bumptech.glide.load.Key;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2017/9/13.
 */

class OriginalKey implements Key {

    private final String id;
    private final Key signature;

    public OriginalKey(String id, Key signature) {
        this.id = id;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OriginalKey that = (OriginalKey) o;

        if (!id.equals(that.id)) {
            return false;
        }
        return signature.equals(that.signature);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + signature.hashCode();
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        messageDigest.update(id.getBytes(STRING_CHARSET_NAME));
        signature.updateDiskCacheKey(messageDigest);
    }
}

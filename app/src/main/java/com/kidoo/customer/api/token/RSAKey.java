package com.kidoo.customer.api.token;

import java.io.Serializable;

/**
 * Created by Shelley on 2017/11/26.
 */

public class RSAKey implements Serializable {


    private String publickKey;
    private String privateKey;

    public String getPublickKey() {
        return publickKey;
    }

    public void setPublickKey(String publickKey) {
        this.publickKey = publickKey;
    }


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "RSAKey{" +
                "publickKey='" + publickKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}

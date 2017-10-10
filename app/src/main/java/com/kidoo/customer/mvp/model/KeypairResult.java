package com.kidoo.customer.mvp.model;

import java.io.Serializable;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 10:33
 * Company: zx
 * Description:
 * FIXME
 */


public class KeypairResult implements Serializable {

        /**
         * privateKey :
         * publicKey :
         */

        private String privateKey;

        private String publicKey;

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        @Override
        public String toString() {
            return "KeypairResult {" +
                    "privateKey='" + privateKey + '\'' +
                    ", publicKey='" + publicKey + '\'' +
                    '}';

         }
}

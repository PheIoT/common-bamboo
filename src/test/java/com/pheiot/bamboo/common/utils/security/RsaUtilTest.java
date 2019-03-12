package com.pheiot.bamboo.common.utils.security;

import com.pheiot.bamboo.common.utils.text.EncodeUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class RsaUtilTest {

    private String publicKey = "";
    private String privateKey = "";

    @Before
    public void initPair() {
        Map<String, Object> map = RsaUtil.generateKeyPair();
        publicKey = RsaUtil.getPublicKey(map);
        privateKey = RsaUtil.getPrivateKey(map);

        System.out.println("Public key:\n\r" + publicKey);
        System.out.println("Private key:\n\r" + privateKey);
    }

    @Test
    public void encryptByPublic() {
        System.out.println("Encode by PublicKey--------Decode by PrivateKey");
        String str = "Hello，World！";
        byte[] encStr = RsaUtil.encryptByPublicKey(str.getBytes(), publicKey);

        String decStr = new String(RsaUtil.decryptByPrivateKey(encStr, privateKey));

        System.out.println("Source: " + str);
        System.out.println("Encrypt: " + EncodeUtil.encodeBase64(encStr));
        System.out.println("Decode: " + decStr);
        assertThat(decStr).isEqualTo(str);
    }

    @Test
    public void encryptByPrivate() {
        System.out.println("Encode by PrivateKey--------Decode by PublicKey");
        String str = "Hi, Doo!";
        byte[] encStr = RsaUtil.encryptByPrivateKey(str.getBytes(), privateKey);
        String decStr = new String(RsaUtil.decryptByPublicKey(encStr, publicKey));
        System.out.println("Source: " + str);
        System.out.println("Encrypt: " + EncodeUtil.encodeBase64(encStr));
        System.out.println("Decode: " + decStr);
        assertThat(decStr).isEqualTo(str);

        // Generate sign
        String sign = RsaUtil.sign(encStr, privateKey);
        System.out.println("Sign: " + sign);

        // Verify
        boolean status = RsaUtil.verify(encStr, publicKey, sign);
        System.out.println("Status: " + status);
    }


}

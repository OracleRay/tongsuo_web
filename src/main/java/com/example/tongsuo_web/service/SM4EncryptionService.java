package com.example.tongsuo_web.service;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

@Service
public class SM4EncryptionService {
    public String encrypt(String plaintext, String key) {
        // 将密钥转换为byte数组
        byte[] keyBytes = Hex.decode(key);

        // 初始化SM4引擎
        SM4Engine sm4 = new SM4Engine();
        KeyParameter keyParam = new KeyParameter(keyBytes);
        sm4.init(true, keyParam);

        // 初始化加密器
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher((BlockCipher) new PKCS7Padding(), (BlockCipherPadding) sm4);

        // 将明文转换为byte数组
        byte[] plaintextBytes = plaintext.getBytes();

        // 加密数据
        byte[] ciphertextBytes = new byte[cipher.getOutputSize(plaintextBytes.length)];
        int outputLen = cipher.processBytes(plaintextBytes, 0, plaintextBytes.length, ciphertextBytes, 0);
        try {
            outputLen += cipher.doFinal(ciphertextBytes, outputLen);
        } catch (org.bouncycastle.crypto.InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }

        // 将加密后的数据转换为16进制字符串
        String ciphertext = Hex.toHexString(ciphertextBytes, 0, outputLen);
        return ciphertext;
    }
}



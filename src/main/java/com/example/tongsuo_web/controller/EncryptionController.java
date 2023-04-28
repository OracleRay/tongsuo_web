package com.example.tongsuo_web.controller;

import com.example.tongsuo_web.service.SM4EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EncryptionController {

    @Autowired
    private SM4EncryptionService sm4EncryptionService;

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam String plaintext, @RequestParam String key){
        return sm4EncryptionService.encrypt(plaintext, key);
    }
}

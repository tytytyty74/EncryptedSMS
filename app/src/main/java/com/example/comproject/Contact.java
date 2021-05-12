package com.example.comproject;

import android.util.Log;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Contact {
    private final String number;
    private Date lastKeyUpdate;
    private ArrayList<String> unreadMessages;
    private BigInteger key;

    public Contact(String number)
    {
        this.number = number;
    }
    public Contact(String number, BigInteger key)
    {
        this.number = number;
        this.key = key;
    }


    public void setKey(BigInteger key)
    {
        this.key = key;
    }

    public boolean HasKey()
    {
        return this.key == null;
    }

    public BigInteger getKey() {
        return key;
    }

    public Date getLastKeyUpdate() {
        return lastKeyUpdate;
    }


    public String getNumber() {
        return number;
    }

    public ArrayList<String> getUnreadMessages() {
        return unreadMessages;
    }
    public String encryptMessage(String message)
    {
        try {
            Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            encrypt.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.toByteArray(), "AES"));
            return new String(encrypt.doFinal(message.getBytes()));
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e)
        {
            Log.e("MyCode", "Encryption failed");
        }
        return "";
    }
    public String decryptMessage(String message)
    {
        try {
            Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            encrypt.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.toByteArray(), "AES"));
            return new String(encrypt.doFinal(message.getBytes()));
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e)
        {
            Log.e("MyCode", "Encryption failed");
        }
        return "";
    }
}

package com.example.comproject;

import android.util.Base64;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private byte[] keyMagic(byte[] key)
    {
        byte[] retval = new byte[16];
        for (int i = 0; i < 16; i++)
        {
            retval[i] = 0;
        }
        for (int i = retval.length-1; i-(retval.length-key.length) >= 0; i--)
        {
            retval[i] = key[i-(retval.length-key.length)];
        }
        return retval;
    }

    /**
     * Encrypts the message supplied, using the key provided by either the
     * {@link #Contact(String, BigInteger)} constructor or the {@link #setKey(BigInteger)} method.
     * if key has not been set yet, will error.
     * @param message the message to be encrypted. must not be null
     * @return the encrypted message in utf-8 format
     */
    public String encryptMessage(@NotNull String message)
    {
        if (key == null)
        {
            throw new IllegalStateException();
        }
        try {
            Cipher encrypt = Cipher.getInstance("AES");
            encrypt.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyMagic(key.toByteArray()), "AES"));
            return Base64.encodeToString(Base64.encode(encrypt.doFinal(message.getBytes(StandardCharsets.UTF_8)), Base64.DEFAULT), Base64.DEFAULT);
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e)
        {
            Log.e("MyCode", "Encryption failed");
        }
        return "";
    }
    public String decryptMessage(@NotNull String message)
    {
        try {
            Cipher encrypt = Cipher.getInstance("AES");
            encrypt.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyMagic(key.toByteArray()), "AES"));
            return Base64.encodeToString(encrypt.doFinal(Base64.decode(message.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)), Base64.DEFAULT);
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e)
        {
            Log.e("MyCode", "Encryption failed");
        }
        return "";
    }
}

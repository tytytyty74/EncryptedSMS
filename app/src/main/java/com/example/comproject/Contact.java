package com.example.comproject;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class Contact {
    private String number;
    private Date lastKeyUpdate;
    private ArrayList<String> unreadMessages;
    private BigInteger key;

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

    public ArrayList<String> getUnreadMessages() {
        return unreadMessages;
    }
    public String encryptMessage(String message)
    {
        try {
            Cipher encrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        }
        catch (NoSuchPaddingException e)
        {

        }
        catch (NoSuchAlgorithmException e)
        {

        }
        return "";
    }

}

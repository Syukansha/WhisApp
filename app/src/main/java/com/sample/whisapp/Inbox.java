package com.sample.whisapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Inbox extends Activity{
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String name,username,password;
    EditText msg,receiver;
    Button sendmsg, main, decfunct, btnhash, verify;
    TextView uname, inbox,hashMsg, hashAndEnc, status;

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private int p, q, n, z, d = 0, e, i;
    public void rsa() {

    }


    private static int gcd(int e, int z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }

    double encrypt(int msg) {
        //Encrypting  C = msg ^e mod n
        return (Math.pow(msg, e)) % n;
    }

    double[] encrypt(String msg) {
        int[] charactersAsNumbers = new int[msg.length()];
        for(int i = 0; i < msg.length(); i++) {
            charactersAsNumbers[i] = msg.codePointAt(i);
        }
        System.out.println("Plain text as sequence of numbers: " + Arrays.toString(charactersAsNumbers));

        double[] encryptedMsg = new double[msg.length()];
        for(int i = 0; i < charactersAsNumbers.length; i++) {
            encryptedMsg[i] = encrypt(charactersAsNumbers[i]);
        }
        return encryptedMsg;
    }

    BigInteger decrypt(double encrypted) {
        //converting int value of n to BigInteger
        BigInteger N = BigInteger.valueOf(n);
        //converting float value of c to BigInteger
        BigInteger C = BigDecimal.valueOf(encrypted).toBigInteger();

        //Decrypt , P = Cˆd mod N , msgback = P
        return (C.pow(d)).mod(N);
    }

    String decrypt(double[] encrypted) {
        StringBuilder builder = new StringBuilder();
        for(double encryptedCharacter: encrypted) {
            BigInteger decryptedCharacter = decrypt(encryptedCharacter);
            builder.append(Character.toChars(decryptedCharacter.intValue()));
        }
        return builder.toString();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        DatabaseHandler db = new DatabaseHandler(this);

        System.out.println("Enter 1st prime number p");
        p = 53;
        System.out.println("Enter 2nd prime number q");
        q = 59;

        n = p * q;
        z = (p - 1) * (q - 1);
        System.out.println("the value of z = " + z);
        for (e = 2; e < z; e++) {
            if (gcd(e, z) == 1) // e is for public key exponent
            {
                break;
            }
        }
        //e should be in the range 1-z
        System.out.println("the value of e = " + e);

        // calculate d
        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * z);
            if (x % e == 0) //d is for private key exponent
            {
                d = x / e;
                break;
            }
        }
        System.out.println("the value of d = " + d);

        Users user = db.getUser(username,password);
        inbox = (TextView) findViewById(R.id.usermessage);
        inbox.setText(user.getMessage());

        hashAndEnc = (TextView) findViewById(R.id.hashAndEncrypt);
        String encryptionMsg = getMd5(user.getMessage());
        double[] c = encrypt(encryptionMsg);
        hashAndEnc.setText(String.valueOf(c));

        hashMsg= (TextView) findViewById(R.id.hashmessage);
        hashMsg.setText(user.getMessage());

        decfunct = (Button) findViewById(R.id.decryption);
        decfunct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String encryptionMsg = getMd5(user.getMessage());
                double[] c = encrypt(encryptionMsg);
                hashAndEnc.setText(String.valueOf(c));
                String msgBack = decrypt(c);
                hashAndEnc.setText(msgBack);
            }
        });

        btnhash = (Button) findViewById(R.id.btnhash);
        btnhash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hashMsgs = getMd5(user.getMessage());
                hashMsg.setText(hashMsgs);
            }
        });
        verify = (Button) findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = (TextView) findViewById(R.id.status);
                if(hashAndEnc.getText().toString().equalsIgnoreCase(hashMsg.getText().toString())){
                    status.setText("Success");
                }
                else {
                    status.setText("Fail");
                }
            }
        });

    }

}


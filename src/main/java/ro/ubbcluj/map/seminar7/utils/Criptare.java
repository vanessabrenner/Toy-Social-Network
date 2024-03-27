package ro.ubbcluj.map.seminar7.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Criptare {

    private static final String cheieSecretaString = "ocheiesecreta123"; // Cheia secretă constantă
    private static final String ivString = "16ByteIVString16"; // IV-ul constant

    public String criptareParola(String parola) {
        try {
            byte[] cheieBytes = cheieSecretaString.getBytes();
            SecretKey cheieSecreta = new SecretKeySpec(cheieBytes, "AES");

            byte[] ivBytes = ivString.getBytes();
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, cheieSecreta, ivSpec);

            byte[] parolaCriptataBytes = cipher.doFinal(parola.getBytes());
            return Base64.getEncoder().encodeToString(parolaCriptataBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decriptareParola(String parolaCriptata) {
        try {
            byte[] cheieBytes = cheieSecretaString.getBytes();
            SecretKey cheieSecreta = new SecretKeySpec(cheieBytes, "AES");

            byte[] ivBytes = ivString.getBytes();
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, cheieSecreta, ivSpec);

            byte[] parolaDecriptataBytes = cipher.doFinal(Base64.getDecoder().decode(parolaCriptata));
            return new String(parolaDecriptataBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        String parolaOriginala = "abc123";
//
//        // Criptăm parola pentru a o stoca în baza de date
//        // csa/hsMVj39DPincDzS4zg==
//        String parolaCriptata = criptareParola(parolaOriginala);
//        System.out.println("Parola criptata: " + parolaCriptata);
//
//        // Decriptăm parola pentru a o obține înapoi
//        String parolaDecriptata = decriptareParola(parolaCriptata);
//        System.out.println("Parola decriptata: " + parolaDecriptata);
//    }
}


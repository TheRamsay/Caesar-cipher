package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Program na sifrovani a desifrovani Caesarove sifry
        // Funguje na velka i mala pismena
        // Posun muze byt libovolne velke cele cislo, kladne i zaporne
        // Funkce brute force zkousi vsechny mozne posuny a vypise ten nejpravdepodobnejsi pomoci frekvencni analyzy
        // Funguje jen na anglicky text, cim delsi text tim lepsi presnost.
        // Priklad pro zpravu: "This is an encrypted message"

        Scanner sc = new Scanner(System.in);

        System.out.println("Text to encrypt: ");
        String text = sc.nextLine();

        System.out.println("Shift: ");
        int shift = sc.nextInt();

        System.out.println("Encryption");
        System.out.printf("Original text: %s \n", text);
        String encrypted = cipher(text, shift, "e");
        System.out.printf("Encrypted text: %s \n", encrypted);
        System.out.println();

        System.out.println("Decryption");
        System.out.printf("Original text: %s \n", encrypted);
        String decrypted = cipher(encrypted, shift, "d");
        System.out.printf("Decrypted text: %s \n", decrypted);
        System.out.println();

        System.out.println("Brute force");
        bruteForce(encrypted);


    }

    public static String cipher(String input, int shift, String operation){

        shift = shift % 26;

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);

            if (character != 32) {

                int n = Character.isLowerCase(character) ? 97 : 65;
                int temp;

                if (operation.toLowerCase().equals("e")) {
                    temp = character + shift - n;

                } else {
                    temp = character - shift - n;

                }

                character = (char) (temp < 0 ? temp + 26 + n : temp % 26 + n);
            }
            output.append(character);

        }

        return output.toString();

    }

    public static void bruteForce(String input){

        ArrayList<Double> shifts = new ArrayList<>();
        final HashMap<Character, Double> lettersEN = new HashMap<>();
        lettersEN.put('a', 0.081);
        lettersEN.put('b', 0.015);
        lettersEN.put('c', 0.027);
        lettersEN.put('d', 0.042);
        lettersEN.put('e', 0.127);
        lettersEN.put('f', 0.022);
        lettersEN.put('g', 0.020);
        lettersEN.put('h', 0.060);
        lettersEN.put('i', 0.070);
        lettersEN.put('j', 0.0015);
        lettersEN.put('k', 0.0077);
        lettersEN.put('l', 0.040);
        lettersEN.put('m', 0.024);
        lettersEN.put('n', 0.067);
        lettersEN.put('o', 0.075);
        lettersEN.put('p', 0.019);
        lettersEN.put('q', 0.001);
        lettersEN.put('r', 0.060);
        lettersEN.put('s', 0.063);
        lettersEN.put('t', 0.091);
        lettersEN.put('u', 0.027);
        lettersEN.put('v', 0.01);
        lettersEN.put('w', 0.024);
        lettersEN.put('x', 0.0015);
        lettersEN.put('y', 0.019);
        lettersEN.put('z', 0.001);

        for (int i = 1; i < 27; i++) {

            HashMap<Character, Integer> charCount = new HashMap<>();
            char[] chars = cipher(input, i, "d").toCharArray();

            // Pocitani kolikrat je kazdy znak v textu
            for (char ch: chars ) {
                if (charCount.containsKey(ch)){
                   charCount.put(ch, charCount.get(ch) + 1);
                } else {
                    charCount.put(ch, 1);
                }

            }

            // Vynasobeni vyskytu znaku v nasem textu s vyskytem v beznem anglickem textu
            double acc = 0;
            for (Map.Entry<Character, Integer> freq : charCount.entrySet()){
                double n;
                try{
                    n = lettersEN.get(freq.getKey());
                } catch (Exception e){
                    n = 0.00;
                }
                acc += freq.getValue() * n;
            }
            shifts.add(acc);


        }
        // Prochazeni hodnot ze vsech posunu, nejvetsi hodnota je nejpresnejsi
        int biggestShift = 0;
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i) > shifts.get(biggestShift)){
                biggestShift = i;
            }
        }

        System.out.println(cipher(input, biggestShift+1, "d"));
        System.out.println("Shift is " + (biggestShift + 1));

    }

}

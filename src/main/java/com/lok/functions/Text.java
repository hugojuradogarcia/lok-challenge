package com.lok.functions;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Text {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce una cadena de texto:");
        try {
            String text = in.nextLine();
            System.out.println(reverseString(text));
        } catch (InputMismatchException e) {
            System.out.println("Entrada incorrecta! Ingrese solo cadenas de texto.");
        }

    }

    public static String reverseString(String text){
        String result = "";

        for (int i = text.length() - 1 ; i >= 0 ; i--){
            result += text.charAt(i);
        }

        return result;
    }
}

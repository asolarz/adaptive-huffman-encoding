package sol;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // double avgLength = 0;
        Tree tree = new Tree();
        String text;

        StringBuilder userInput = new StringBuilder();
        StringBuilder encodedText = new StringBuilder();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            text = scanner.nextLine();
            if (text.equals("EXIT"))
                break;

            userInput.append(text);
            char[] characters = text.toCharArray();
            for (char c : characters) {
                tree.addCharacter(c);
                encodedText.append(tree.code(c));

                System.out.println("Drzewo\n");
                tree.print();

                System.out.println("Tablica znaków\n");
                tree.printCodes();

                System.out.println("\nOdczytany tekst: " + userInput);
                System.out.println("Zakodowany tekst: " + encodedText.toString() + "\n");

                System.out.println("\nEntropia: " + tree.getEntropy());


            }


        }


    }


}

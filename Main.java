//package readability;
import java.util.*;
import java.io.*;

public class Main {
    private static int sentenceCount;
    private static int wordCount;
    private static int characterCount;
    private static int syllableCount;
    private static int polysyllableCount;
    private static int averageAge;

    private static int findAge(double score) {
        switch((int) Math.round(score)) {
            case 1:
                return 6;                
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            case 13:
                return 24;
            default:
                return 25; // 24+
        }
    }

    private static void colemanLiauIndex() {
        double score = 0.0588 * (1.0 * characterCount / wordCount * 100) - 0.296 * (1.0 * sentenceCount / wordCount * 100) - 15.8;
    
        int age = findAge(score);

        averageAge += age;

        if (age == 25) {
            System.out.printf("Coleman–Liau index: %f (about 24+ year olds).%n", score);
        } else {
            System.out.printf("Coleman–Liau index: %f (about %d year olds).%n", score, age);
        }
    }

    private static void smogIndex() {
        double score = 1.043 * Math.sqrt(polysyllableCount * 30 / sentenceCount) + 3.1291;
    
        int age = findAge(score);

        averageAge += age;

        if (age == 25) {
            System.out.printf("Simple Measure of Gobbledygook: %f (about 24+ year olds).%n", score);
        } else {
            System.out.printf("Simple Measure of Gobbledygook: %f (about %d year olds).%n", score, age);
        }
    }
        
    private static void fleschKincaidIndex() {
        double score = 0.39 * wordCount / sentenceCount + 11.8 * syllableCount / wordCount - 15.59;
    
        int age = findAge(score);

        averageAge += age;

        if (age == 25) {
            System.out.printf("Flesch–Kincaid readability tests: %f (about 24+ year olds).%n", score);
        } else {
            System.out.printf("Flesch–Kincaid readability tests: %f (about %d year olds).%n", score, age);
        }
    }

    private static void automatedReadabilityIndex() {
        double score = 4.71 * characterCount / wordCount + 0.5 * wordCount / sentenceCount - 21.43;
    
        int age = findAge(score);

        averageAge += age;

        if (age == 25) {
            System.out.printf("Automated Readability Index: %f (about 24+ year olds).%n", score);
        } else {
            System.out.printf("Automated Readability Index: %f (about %d year olds).%n", score, age);
        }
    }

    private static boolean isVowel(char l) {
        return (l == 'a' || l == 'e' || l == 'i' || l == 'o' || l == 'u' || l == 'y');
    }

    private static void determineSyllables(String[] words) {
        for (int i = 0; i < wordCount; i++) {
            // removing all non alphanumerical characters from current word
            String word = words[i].replaceAll("[\\W]", "").toLowerCase();

            int vowelCount = 0;

            for (int j = 0; j < word.length(); j++) {
                if (isVowel(word.charAt(j)) && !(j - 1 >= 0 && isVowel(word.charAt(j - 1)))) vowelCount++;
                if (j == word.length() - 1 && word.charAt(j) == 'e') vowelCount--;
            }

            if (vowelCount <= 0) vowelCount = 1;
            if (vowelCount > 2) polysyllableCount++;
            syllableCount += vowelCount;
        }
    }
    
    public static void main(String[] args) {
        File f = new File(args[0]);
        Scanner scan = new Scanner(System.in);
        
        String text = "";
        
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNext()) {
                text += scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found:");
        }
        
        String[] words = text.split("\\s");
        
        String regex = ".*[\\?\\.!]";

        wordCount = words.length;
        
        for (int i = 0; i < words.length; i++) {
            if (words[i].matches(regex) || i == words.length - 1) {
                sentenceCount++;
            }
            characterCount += words[i].length();
        }
        
        determineSyllables(words);

        System.out.println("Words: " + wordCount);
        System.out.println("Sentences: " + sentenceCount);
        System.out.println("Characters: " + characterCount);
        System.out.println("Syllables: " + syllableCount);
        System.out.println("Polysyllables: " + polysyllableCount);
        
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = scan.next();

        System.out.println();

        double num = 1.0;

        switch (choice) {
            case "ARI":
                automatedReadabilityIndex();
                break;
            case "FK":
                fleschKincaidIndex();
                break;
            case "SMOG":
                smogIndex();
                break;
            case "CL":
                colemanLiauIndex();
                break;
            case "all":
                automatedReadabilityIndex();
                fleschKincaidIndex();
                smogIndex();
                colemanLiauIndex();
                num = 4.0;
                break;
            default: 
                System.out.println("ERROR!");
                break;
        }

        System.out.printf("This text should be understood in average by %f year olds.%n", (double) averageAge / num);
    }
}

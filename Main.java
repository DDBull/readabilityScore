//package readability;
import java.util.*;
import java.io.*;

public class Main {
    private static int sentenceCount;
    private static int wordCount;
    private static int characterCount;
    private static int syllableCount;
    private static int polysyllableCount;
        
    private static void readabilityIndex() {
        double score = 4.71 * characterCount / wordCount + 0.5 * wordCount / sentenceCount - 21.43;
    
        System.out.println("The score is: " + score);
        
        
    /*  Automated readability index
        Score	Age	Grade Level
        1	5-6	Kindergarten
        2	6-7	First/Second Grade
        3	7-9	Third Grade
        4	9-10	Fourth Grade
        5	10-11	Fifth Grade
        6	11-12	Sixth Grade
        7	12-13	Seventh Grade
        8	13-14	Eighth Grade
        9	14-15	Ninth Grade
        10	15-16	Tenth Grade
        11	16-17	Eleventh Grade
        12	17-18	Twelfth grade
        13	18-24	College student
        14	24+	Professor
    */
        switch((int) Math.ceil(score)) {
            case 1:
                System.out.println("This text should be understood by 5-6 year olds.");
                break;
            case 2:
                System.out.println("This text should be understood by 6-7 year olds.");
                break;
            case 3:
                System.out.println("This text should be understood by 7-9 year olds.");
                break;
            case 4:
                System.out.println("This text should be understood by 9-10 year olds.");
                break;
            case 5:
                System.out.println("This text should be understood by 10-11 year olds.");
                break;
            case 6:
                System.out.println("This text should be understood by 11-12 year olds.");
                break;
            case 7:
                System.out.println("This text should be understood by 12-13 year olds.");
                break;
            case 8:
                System.out.println("This text should be understood by 13-14 year olds.");
                break;
            case 9:
                System.out.println("This text should be understood by 14-15 year olds.");
                break;    
            case 10:
                System.out.println("This text should be understood by 15-16 year olds.");
                break;
            case 11:
                System.out.println("This text should be understood by 16-17 year olds.");
                break;
            case 12:
                System.out.println("This text should be understood by 17-18 year olds.");
                break;
            case 13:
                System.out.println("This text should be understood by 18-24 year olds.");
                break;
            default:
                System.out.println("This text should be understood by 24+ year olds.");
                break;
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
                if (isVowel(word.charAt(j)) && !(j - 1 > 0 && isVowel(word.charAt(j - 1)))) vowelCount++;
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

        readabilityIndex();
    }
}

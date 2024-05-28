import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Input:
        String text;
        final int THREAD_COUNT;
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            THREAD_COUNT = Integer.parseInt(bufferedReader.readLine());
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line.equals("!end"))
                    break;
                builder.append(line).append("\n");
            }
            text = builder.substring(0, builder.length() - 1);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Process:
        ArrayList<String> words = new ArrayList<>();
        String longest = "";
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            StringBuilder word = new StringBuilder();
            while (Character.isAlphabetic(c) && c != ' ') {
                word.append(c);
                i++;
                if (i < text.length())
                    c = text.charAt(i);
                else
                    break;
            }
            if (!word.toString().isEmpty()) {
                if (word.length() > longest.length())
                    longest = word.toString();
                words.add(word.toString().toLowerCase());
            }
        }
        ArrayList<ArrayList<String>> wordsPartition = new ArrayList<>();
        ArrayList<String> textPartition = new ArrayList<>();
        ArrayList<Friend> friends = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++)
            wordsPartition.add(new ArrayList<>());
        for (String word : words)
            wordsPartition.get(Math.abs(word.hashCode() % THREAD_COUNT)).add(word.toLowerCase());
        text = (new StringBuilder(text)).reverse().toString();
        int module = text.length() / THREAD_COUNT;
        int start = -module;
        for (int i = 0; i < THREAD_COUNT - 1; i++) {
            start += module;
            textPartition.add(text.substring(start, start + module));
        }
        start += module;
        textPartition.add(text.substring(start));
        int wordCount = 0;
        int mostFrequency = 0;
        String mostFrequent = "";
        double hackCodeQuantity = 0;
        for (int i = 0; i < THREAD_COUNT; i++)
            friends.add(new Friend(wordsPartition.get(i), textPartition.get(i), module * i));
        for (Friend friend : friends) {
            friend.start();
            try {
                friend.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (Friend friend : friends) {
            wordCount += friend.wordCount();
            hackCodeQuantity += friend.getHackCode();
            if (friend.getMostFrequency() > mostFrequency) {
                mostFrequency = friend.getMostFrequency();
                mostFrequent = friend.getMostFrequent();
            }
        }
        System.out.printf("Word count: %d \nThe longest word is \"%s\" with a length of %d. \nThe most frequent word is " +
                "\"%s\" with %d appearances. \nHackCode: %.3f \n",
                wordCount, longest, longest.length(), mostFrequent, mostFrequency, hackCodeQuantity);
    }
}
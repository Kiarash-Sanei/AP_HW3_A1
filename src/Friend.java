import java.util.ArrayList;
import java.util.HashMap;

public class Friend extends Thread {
    private final ArrayList<String> words;
    private final HashMap<String, Integer> frequency = new HashMap<>();
    private String mostFrequent = "";
    private int mostFrequency = 0;
    private final String text;
    private final int position;
    private double hackCode = 0;

    public Friend(ArrayList<String> words, String text, int position) {
        this.words = words;
        this.text = text;
        this.position = position;
    }

    @Override
    public void run() {
        for (String word : words)
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        for (String word : frequency.keySet()) {
            if (frequency.get(word) > mostFrequency) {
                mostFrequency = frequency.get(word);
                mostFrequent = word;
            }
        }
        for (int i = 0; i < text.length(); i++)
            hackCode += text.charAt(i) * Math.pow(0.99998, i + position);
    }

    public String getMostFrequent() {
        return mostFrequent;
    }

    public int getMostFrequency() {
        return mostFrequency;
    }

    public int wordCount() {
        return words.size();
    }

    public double getHackCode() {
        return hackCode;
    }
}

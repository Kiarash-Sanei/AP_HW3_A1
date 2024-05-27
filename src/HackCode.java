public class HackCode extends Thread {
    private final String text;
    private double hackCode = 0;

    public HackCode(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length(); i++) {
            hackCode += text.charAt(i) * Math.pow(0.99998, text.length() - 1 - i);
        }
    }
    public double getHackCode() {
        return hackCode;
    }
}

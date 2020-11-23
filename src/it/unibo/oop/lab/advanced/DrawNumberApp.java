package it.unibo.oop.lab.advanced;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static final int MIN = 0;
    private static final int MAX = 100;
    private static final int ATTEMPTS = 10;

    private int min;
    private int max;
    private int attempts;

    private static final String FILE_DA_LEGGERE = "res"
            + System.getProperty("file.separator")
            + "config.yml";

    private static final String FILE = System.getProperty("user.home")
            + System.getProperty("file.separator")
            + "output.txt";

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * 
     * 
     */
    public DrawNumberApp() {
        this.views = new ArrayList<>();

        this.views.add(new DrawNumberViewImpl());
        this.views.add(new DrawNumberViewStampa(System.out));
        try {
            this.views.add(new DrawNumberViewStampa(new PrintStream(this.FILE)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (final DrawNumberView view: this.views) {
            view.setObserver(this);
            view.start();
        }
        this.leggiLimitiTentativi(FILE_DA_LEGGERE);
        this.model = new DrawNumberImpl(this.min, this.max, this.attempts);
    }

    private void leggiLimitiTentativi(final String fileName) {
        try {
            final File fileLeggere = new File(fileName);
            String linesFile = FileUtils.readFileToString(fileLeggere, StandardCharsets.UTF_8);

            String[] spitStr = linesFile.split(":");
            String numberOne = spitStr[1].replaceAll("[^0-9]", "");
            String numberSecond = spitStr[2].replaceAll("[^0-9]", "");
            String numberThird = spitStr[3].replaceAll("[^0-9]", "");

            this.min = Integer.parseInt(numberOne);
            this.max = Integer.parseInt(numberSecond);
            this.attempts = Integer.parseInt(numberThird);
        } catch (FileNotFoundException a) {
            for (final DrawNumberView view: this.views) {
                view.displayError("File not found");
            }
            this.min = MIN;
            this.max = MAX;
            this.attempts = ATTEMPTS;
        } catch (IOException e) {
            for (final DrawNumberView view: this.views) {
                view.displayError("IO Error");
            }
        } 
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: this.views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: this.views) {
                view.numberIncorrect();
            }
        } catch (AttemptsLimitReachedException e) {
            for (final DrawNumberView view: this.views) {
                view.limitsReached();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * 
     */
    public static void main(final String... args) throws IOException {
        new DrawNumberApp();
    }

}

package it.unibo.oop.lab.advanced;

import java.io.PrintStream;

public class DrawNumberViewStampa implements DrawNumberView {

    private final PrintStream stampa;
    private static final String NEW_GAME = ": a new game starts!";

    public DrawNumberViewStampa(final PrintStream stampa) {
        this.stampa = stampa;
    }

    @Override
    public void setObserver(final DrawNumberViewObserver observer) { }

    @Override
    public void start() { }

    @Override
    public void numberIncorrect() {
        stampa.println("Incorrect Number.. try again");
    }

    @Override
    public void result(final DrawResult res) {
        switch (res) {
        case YOURS_HIGH:
        case YOURS_LOW:
            stampa.println(res.getDescription());
            return;
        case YOU_WON:
            stampa.println(res.getDescription() + NEW_GAME);
            break;
        default:
            throw new IllegalStateException("Unexpected result: " + res);
        }
    }

    @Override
    public void limitsReached() {
        stampa.println("Hai perso : " + NEW_GAME);
    }

    @Override
    public void displayError(final String message) {
        stampa.println("C'è stato un errore : " + message);
    }
}

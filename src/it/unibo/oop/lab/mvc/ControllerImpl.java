package it.unibo.oop.lab.mvc;

import java.util.*;

public final class ControllerImpl implements Controller {
    private List<String> listString = new ArrayList<>();
    private String lastString;

    public void setString(final String arg) {
        this.lastString = Objects.requireNonNull(arg, "L'argomento non c'è");
    }

    public String nextString() {
        return this.lastString;
    }

    public List<String> historyString() {
        return new ArrayList<>(listString);
    }

    public void correntString() {
        this.listString.add(this.lastString);
        System.out.println(this.lastString);
    }
}

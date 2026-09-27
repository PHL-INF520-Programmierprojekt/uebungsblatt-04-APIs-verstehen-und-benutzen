package de.phl.programmingproject.candyproduction;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CandyFactory {

    private final List<String> sugarMixFlavors;
    private final List<String> juicyCoreFlavors;

    /**
     * Creates a new candy factory.
     */
    public CandyFactory() {
        sugarMixFlavors = new LinkedList<>();
        juicyCoreFlavors = new LinkedList<>();
    }

    /**
     * Adds a list of sugar mix flavors.
     *
     * @param sugarMixFlavors The list of sugar mix flavors.
     * @throws IllegalArgumentException if the list is null, empty or contains null.
     */
    public void addSugarMixFlavors(final List<String> sugarMixFlavors) {
        if (sugarMixFlavors == null || sugarMixFlavors.isEmpty() || sugarMixFlavors.stream().anyMatch(java.util.Objects::isNull)) {
            throw new IllegalArgumentException("Flavors is null, empty or contains null.");
        }
        this.sugarMixFlavors.addAll(sugarMixFlavors);
    }

    /**
     * Adds a list of juicy core flavors.
     *
     * @param juicyCoreFlavors The list of juicy core flavors.
     * @throws IllegalArgumentException if the list is null, empty or contains null.
     */
    public void addJuicyCoreFlavors(final List<String> juicyCoreFlavors) {
        if (juicyCoreFlavors == null || juicyCoreFlavors.isEmpty() || juicyCoreFlavors.stream().anyMatch(java.util.Objects::isNull)) {
            throw new IllegalArgumentException("Flavors is null, empty or contains null.");
        }
        this.juicyCoreFlavors.addAll(juicyCoreFlavors);
    }

    /**
     * Produziert einzelne Bonbons nach einem festen Rezept: die ersten beiden
     * Zuckeraromen und das erste Kernaroma der zuvor hinzugefügten Listen.
     * Gleicher Geschmack ist erlaubt; jeder Listeneintrag ist ein neues Bonbon.
     * @param amount positive Anzahl der Bonbons
     * @return Liste mit genau amount Bonbons
     * @throws IllegalArgumentException wenn amount kleiner als 1 ist
     * @throws IllegalStateException wenn weniger als zwei Zuckeraromen oder kein Kernaroma vorhanden sind
     */
    public List<Candy> produceCandies(final int amount) {
        // TODO: Vertrag prüfen, Bonbons in einer Schleife erzeugen und die Liste zurückgeben.
        return null;
    }
}

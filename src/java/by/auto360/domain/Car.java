package by.auto360.domain;

import java.util.Optional;
import javax.annotation.concurrent.Immutable;

@Immutable
@Demo
public class Car {
    private final Person owner;
    private final int year;
    private final Model model;
    private final String modification;

    private final Optional<String> vin;

    public Car(Person owner, int year, Model model, String modification) {
        this(owner, year, model, modification, Optional.empty());
    }

    public Car(Person owner, int year, Model model, String modification, Optional<String> vin) {
        this.owner = owner;
        this.year = year;
        this.model = model;
        this.modification = modification;
        this.vin = vin;
    }

    public Person getOwner() {
        return this.owner;
    }

    public int getYear() {
        return this.year;
    }

    public Model getModel() {
        return this.model;
    }

    public String getModification() {
        return this.modification;
    }

    public Optional<String> getVin() {
        return this.vin;
    }

    public enum Model {
        Peugeot,
        Huyndai,
        Mini
    }
}

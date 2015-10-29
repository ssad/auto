package by.auto360.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.concurrent.Immutable;

@Immutable
@Demo
public class Person {
    private final String first;
    private final String last;

    private final String birth;

    @JsonCreator
    public Person(
            @JsonProperty("first") String first,
            @JsonProperty("last") String last,
            @JsonProperty("birth") String birth) {
        this.first = first;
        this.last = last;
        this.birth = birth;
    }

    public String getFirst() {
        return this.first;
    }

    public String getLast() {
        return this.last;
    }

    public String getBirth() {
        return this.birth;
    }
}

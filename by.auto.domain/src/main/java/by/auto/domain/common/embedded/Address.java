package by.auto.domain.common.embedded;

import java.io.Serializable;

public class Address implements Serializable {
    private String name;
    private String zip;

    public Address(final String name, final String zip) {
        this.name = name;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getZip() {
        return zip;
    }
}

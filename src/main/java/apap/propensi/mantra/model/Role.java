package apap.propensi.mantra.model;


import lombok.Getter;

@Getter
public enum Role {

    ADMIN ("Admin"),
    MANAGER ("Manager"),
    CUSTOMER ("Customer"),
    DRIVER ("Driver"),
    CUSTOMERSERVICE ("Customer Service");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}


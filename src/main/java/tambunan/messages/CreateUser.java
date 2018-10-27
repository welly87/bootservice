package tambunan.messages;

import tambunan.bus.BuzzMessage;

public class CreateUser implements BuzzMessage {
    private String id;

    private String name;

    public CreateUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

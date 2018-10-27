package tambunan.handlers;

import tambunan.messages.CreateUser;
import tambunan.bus.BuzzHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeHandler implements BuzzHandler<CreateUser> {
    @Override
    public void handle(CreateUser message) {
        System.out.println(message.getId());

        System.out.println(message.getName());
    }
}

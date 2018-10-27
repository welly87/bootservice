package tambunan.bus;

public interface BuzzHandler<T extends BuzzMessage> {
    void handle(T message);
}

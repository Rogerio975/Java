import java.util.ArrayList;
import java.util.List;

public class Newsletter {
    private final List<Subscriber> subscribers = new ArrayList<>();
    private String latestEdition;

    public void subscribe(Subscriber s) {
        if (!subscribers.contains(s)) {
            subscribers.add(s);
        }
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    public void publish(String edition) {
        this.latestEdition = edition;
        notifySubscribers();
    }

    private void notifySubscribers() {
        // Itera sobre uma cópia para evitar ConcurrentModificationException se um assinante sair durante o update
        for (Subscriber s : new ArrayList<>(subscribers)) {
            s.update(latestEdition);
        }
    }

    public String getLatestEdition() {
        return latestEdition;
    }
}

public class App {
    public static void main(String[] args) {
        Newsletter newsletter = new Newsletter();

        Subscriber alice = new ConcreteSubscriber("Alice");
        Subscriber bob = new ConcreteSubscriber("Bob");
        Subscriber carlos = new ConcreteSubscriber("Carlos");

        newsletter.subscribe(alice);
        newsletter.subscribe(bob);
        newsletter.subscribe(carlos);

        System.out.println("Publicando edição 1...");
        newsletter.publish("Edição 1 - Boas vindas");

        System.out.println();
        System.out.println("Carlos cancela a assinatura.");
        newsletter.unsubscribe(carlos);

        System.out.println("Publicando edição 2...");
        newsletter.publish("Edição 2 - Novidades do mês");
    }
}

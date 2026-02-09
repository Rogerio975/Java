public class ConcreteSubscriber implements Subscriber {
    private final String name;

    public ConcreteSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String edition) {
        System.out.println("[" + name + "] recebeu notificação: nova edição -> " + edition);
    }

    @Override
    public String getName() {
        return name;
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("Estado #1");
        originator.setState("Estado #2");
        caretaker.add(originator.saveStateToMemento());

        originator.setState("Estado #3");
        caretaker.add(originator.saveStateToMemento());

        originator.setState("Estado #4");
        System.out.println("Estado Atual: " + originator.getState());

        originator.getStateFromMemento(caretaker.get(0));
        System.out.println("Primeiro estado salvo: " + originator.getState());
        originator.getStateFromMemento(caretaker.get(1));
        System.out.println("Segundo estado salvo: " + originator.getState());
    }
}

import java.util.Stack;

// 1. MEMENTO
// Utilizamos um Java Record (Java 14+) para garantir imutabilidade e reduzir código boilerplate.
// Este objeto guarda o estado do editor em um momento específico.
record EditorMemento(String content) {
}

// 2. ORIGINATOR
// Esta é a classe principal que terá seu estado alterado e salvo.
class TextEditor {
    private String content;

    public TextEditor() {
        this.content = "";
    }

    public void write(String text) {
        this.content += text;
    }

    public String getContent() {
        return content;
    }

    // Cria o Memento (Salva o estado atual)
    public EditorMemento save() {
        return new EditorMemento(this.content);
    }

    // Restaura o estado a partir de um Memento
    public void restore(EditorMemento memento) {
        this.content = memento.content();
    }
}

// 3. CARETAKER
// Gerencia o histórico de estados (Mementos).
class History {
    // Uma Stack (Pilha) é a estrutura de dados ideal para LIFO (Last In, First Out)
    private final Stack<EditorMemento> historyStack = new Stack<>();

    public void saveHistory(EditorMemento memento) {
        historyStack.push(memento);
    }

    public EditorMemento undo() {
        if (historyStack.isEmpty()) {
            System.out.println("Nada para desfazer.");
            return null;
        }
        // Remove o estado atual e retorna o anterior
        return historyStack.pop();
    }
}

// CLASSE PRINCIPAL PARA TESTE
public class MementoDemo {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        History history = new History();

        // 1. Escreve algo e salva
        editor.write("Olá, ");
        history.saveHistory(editor.save());
        System.out.println("Estado 1: " + editor.getContent());

        // 2. Escreve mais e salva
        editor.write("Mundo! ");
        history.saveHistory(editor.save());
        System.out.println("Estado 2: " + editor.getContent());

        // 3. Escreve algo que vamos nos arrepender (não salvamos este passo no histórico propositalmente para o exemplo)
        editor.write("Erro de digitação...");
        System.out.println("Estado 3 (Atual): " + editor.getContent());

        System.out.println("\n--- Executando Undo (Desfazer) ---");

        // Desfazer para o Estado 2
        EditorMemento lastSavedState = history.undo();
        if (lastSavedState != null) {
            editor.restore(lastSavedState);
        }
        System.out.println("Restaurado para: " + editor.getContent());

        // Desfazer para o Estado 1
        lastSavedState = history.undo();
        if (lastSavedState != null) {
            editor.restore(lastSavedState);
        }
        System.out.println("Restaurado para: " + editor.getContent());
    }
}

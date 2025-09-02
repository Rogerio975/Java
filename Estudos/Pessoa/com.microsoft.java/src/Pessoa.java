public class Pessoa {
    private String nome;
    private String email;
    private String cpf;

    public Pessoa(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa("João Silva", "joao.silva@outlook.com", "123.456.789-00");
        System.out.println("\nNome: " + pessoa.getNome());
        System.out.println("Email: " + pessoa.getEmail());
        System.out.println("CPF: " + pessoa.getCpf());
    }
}
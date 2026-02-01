public class Pedido {
    private String lanche;
    private boolean queijo;
    private boolean bacon;
    private boolean alface;
    private boolean tomate;
    private boolean cebola;
    private String bebida;

    // Construtor privado - só pode ser acessado pelo Builder
    private Pedido(PedidoBuilder builder) {
        this.lanche = builder.lanche;
        this.queijo = builder.queijo;
        this.bacon = builder.bacon;
        this.alface = builder.alface;
        this.tomate = builder.tomate;
        this.cebola = builder.cebola;
        this.bebida = builder.bebida;
    }

    // Getters
    public String getLanche() {
        return lanche;
    }

    public boolean temQueijo() {
        return queijo;
    }

    public boolean temBacon() {
        return bacon;
    }

    public boolean temAlface() {
        return alface;
    }

    public boolean temTomate() {
        return tomate;
    }

    public boolean temCebola() {
        return cebola;
    }

    public String getBebida() {
        return bebida;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PEDIDO ===\n");
        sb.append("Lanche: ").append(lanche).append("\n");
        sb.append("Ingredientes: ");
        
        java.util.List<String> ingredientes = new java.util.ArrayList<>();
        if (queijo) ingredientes.add("queijo");
        if (bacon) ingredientes.add("bacon");
        if (alface) ingredientes.add("alface");
        if (tomate) ingredientes.add("tomate");
        if (cebola) ingredientes.add("cebola");
        
        if (ingredientes.isEmpty()) {
            sb.append("nenhum");
        } else {
            sb.append(String.join(", ", ingredientes));
        }
        sb.append("\n");
        
        if (bebida != null && !bebida.isEmpty()) {
            sb.append("Bebida: ").append(bebida).append("\n");
        }
        
        return sb.toString();
    }

    // Builder Pattern
    public static class PedidoBuilder {
        private String lanche;
        private boolean queijo = false;
        private boolean bacon = false;
        private boolean alface = false;
        private boolean tomate = false;
        private boolean cebola = false;
        private String bebida = "";

        public PedidoBuilder(String lanche) {
            this.lanche = lanche;
        }

        public PedidoBuilder comQueijo() {
            this.queijo = true;
            return this;
        }

        public PedidoBuilder comBacon() {
            this.bacon = true;
            return this;
        }

        public PedidoBuilder comAlface() {
            this.alface = true;
            return this;
        }

        public PedidoBuilder comTomate() {
            this.tomate = true;
            return this;
        }

        public PedidoBuilder comCebola() {
            this.cebola = true;
            return this;
        }

        public PedidoBuilder comBebida(String bebida) {
            this.bebida = bebida;
            return this;
        }

        public Pedido build() {
            return new Pedido(this);
        }
    }
}

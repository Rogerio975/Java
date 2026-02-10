public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("========== LANCHONETE - PADRÃO BUILDER ==========\n");

        // Cliente 1: Quer hambúrguer com queijo e bacon
        Pedido pedido1 = new Pedido.PedidoBuilder("Hambúrguer")
                .comQueijo()
                .comBacon()
                .comBebida("Refrigerante")
                .build();
        System.out.println(pedido1);

        // Cliente 2: Quer hot dog com alface, tomate e cebola
        Pedido pedido2 = new Pedido.PedidoBuilder("Hot Dog")
                .comAlface()
                .comTomate()
                .comCebola()
                .comBebida("Suco")
                .build();
        System.out.println(pedido2);

        // Cliente 3: Quer sanduíche simples, sem extras
        Pedido pedido3 = new Pedido.PedidoBuilder("Sanduíche")
                .comBebida("Água")
                .build();
        System.out.println(pedido3);

        // Cliente 4: Quer hambúrguer com tudo
        Pedido pedido4 = new Pedido.PedidoBuilder("Hambúrguer Completo")
                .comQueijo()
                .comBacon()
                .comAlface()
                .comTomate()
                .comCebola()
                .comBebida("Milkshake")
                .build();
        System.out.println(pedido4);

        // Cliente 5: Quer apenas alface no lanche
        Pedido pedido5 = new Pedido.PedidoBuilder("Sanduíche Vegetariano")
                .comAlface()
                .comTomate()
                .build();
        System.out.println(pedido5);
    }
}

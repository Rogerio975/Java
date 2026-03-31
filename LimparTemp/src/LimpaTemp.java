import java.io.File;

public class LimpaTemp {

    public static void deletarConteudo(File pasta) {
        if (pasta == null || !pasta.exists()) return;

        File[] arquivos = pasta.listFiles();
        if (arquivos == null) return;

        for (File f : arquivos) {
            try {
                if (f.isDirectory()) {
                    deletarConteudo(f);
                }
                f.delete();
            } catch (Exception e) {
                // Ignora erros (arquivo em uso / permissão)
            }
        }
    }

    public static void main(String[] args) {

        // Temp do Windows
        File tempWindows = new File("C:\\Temp");
        deletarConteudo(tempWindows);

        // Temp dos usuários
        File usuarios = new File("C:\\Users");
        File[] listaUsuarios = usuarios.listFiles();

        if (listaUsuarios != null) {
            for (File usuario : listaUsuarios) {
                File tempUsuario = new File(
                        usuario.getAbsolutePath()
                                + "\\AppData\\Local\\Temp"
                );
                deletarConteudo(tempUsuario);
            }
        }
    }
}
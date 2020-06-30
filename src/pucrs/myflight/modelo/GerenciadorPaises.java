package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GerenciadorPaises {
    private Set<Pais> paises;
    private static GerenciadorPaises instance;
    private GerenciadorPaises() {
        paises = new HashSet<>();
    }

    public static GerenciadorPaises getInstance(){
        if(instance == null) instance = new GerenciadorPaises();
        return instance;
    }

    public void adicionar(Pais p){
        paises.add(p);
    }

    public Pais buscaCodigo(String codigo){
        return paises.stream().filter(p -> p.getCodigo().equals(codigo))
                .findFirst().get();
    }

    public void carregaDados(String nomeArq){
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                Pais nova = new Pais(dados[0], dados[1]);
                adicionar(nova);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public Set<Pais> listarTodos(){
        return new HashSet<>(paises);
    }

    public String toString(){
        StringBuilder msg = new StringBuilder("Gerenciador de Paises\n--------------------\n");

        paises.forEach(msg::append);
        msg.append("- - - - - - - - - - -\n");
        return msg.toString();
    }
}

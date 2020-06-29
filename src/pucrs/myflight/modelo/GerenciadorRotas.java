package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class GerenciadorRotas {

    private ArrayList<Rota> rotas;
    private static  GerenciadorRotas instance;
    private GerenciadorRotas() {
        this.rotas = new ArrayList<>();
    }

    public static GerenciadorRotas getInstance(){
        if(instance == null) instance = new GerenciadorRotas();
        return instance;
    }

    public void ordenarCias() {
        Collections.sort(rotas);
    }

    public void ordenarNomesCias() {
        rotas.sort( (Rota r1, Rota r2) ->
          r1.getCia().getNome().compareTo(
          r2.getCia().getNome()));
    }

    public void ordenarNomesAeroportos() {
        rotas.sort( (Rota r1, Rota r2) ->
                r1.getOrigem().getNome().compareTo(
                r2.getOrigem().getNome()));
    }

    public void carregaDados(String nomeArq){
        GerenciadorAeroportos gerA = GerenciadorAeroportos.getInstance();
        GerenciadorCias gerCia = GerenciadorCias.getInstance();
        GerenciadorAeronaves gerAero = GerenciadorAeronaves.getInstance();

        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                Rota nova = new Rota(gerCia.buscarCodigo(dados[0]),
                        gerA.buscarCodigo(dados[1]),
                        gerA.buscarCodigo(dados[2]),
                        gerAero.buscarCodigo(dados[5]));
                adicionar(nova);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public void ordenarNomesAeroportosCias() {
        rotas.sort( (Rota r1, Rota r2) -> {
           int result = r1.getOrigem().getNome().compareTo(
                   r2.getOrigem().getNome());
           if(result != 0)
               return result;
           return r1.getCia().getNome().compareTo(
                   r2.getCia().getNome());
        });
    }
    public void adicionar(Rota r) {
        rotas.add(r);
    }

    public ArrayList<Rota> listarTodas() {
        return new ArrayList<>(rotas);
    }

    public ArrayList<Rota> buscarOrigem(String codigo) {
        ArrayList<Rota> result = new ArrayList<>();
        for(Rota r: rotas)
            if(r.getOrigem().getCodigo().equals(codigo))
                result.add(r);
        return result;
    }

    public ArrayList<Rota> buscaPorCia(String codigo){
        ArrayList<Rota> result = new ArrayList<>();
        rotas.forEach( r -> {
            if(r.getCia().getCodigo().equals(codigo)){
                result.add(r);
            }
                }
                );
        return result;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder("Gerenciador de Rotas\n--------------------\n");

        rotas.forEach(msg::append);

        msg.append("\n- - - - - - - - - - -\n");

        return msg.toString();
    }
}

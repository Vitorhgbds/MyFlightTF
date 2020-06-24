package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class GerenciadorAeroportos {

    private ArrayList<Aeroporto> aeroportos;
    private static  GerenciadorAeroportos instance;
    private GerenciadorAeroportos() {
        this.aeroportos = new ArrayList<>();
    }
    public static GerenciadorAeroportos getInstance(){
        if(instance == null) instance = new GerenciadorAeroportos();
        return instance;
    }
    public void ordenarNomes() {
        Collections.sort(aeroportos);
    }

    public void adicionar(Aeroporto aero) {
        aeroportos.add(aero);
    }

    public ArrayList<Aeroporto> listarTodos() {
        return new ArrayList<>(aeroportos);
    }

    public Aeroporto buscarCodigo(String codigo) {
        for(Aeroporto a: aeroportos)
            if(a.getCodigo().equals(codigo))
                return a;
        return null;
    }

    public void carregaDados(String nomeArq){
        GerenciadorPaises gerPaises = GerenciadorPaises.getInstance();
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                Aeroporto nova = new Aeroporto(dados[0], dados[3],
                        new Geo(Double.parseDouble(dados[1]), Double.parseDouble(dados[2])),
                        gerPaises.buscaCodigo("BR"));
                adicionar(nova);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public String toString(){
        StringBuilder msg = new StringBuilder("Gerenciador de Aeroportos\n--------------------\n");

        aeroportos.forEach(msg::append);

        msg.append("\n- - - - - - - - - - -\n");

        return msg.toString();
    }
}

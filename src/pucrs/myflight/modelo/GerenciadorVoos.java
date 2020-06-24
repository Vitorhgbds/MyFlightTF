package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;

public class GerenciadorVoos {

    private ArrayList<Voo> voos;
    private static GerenciadorVoos instance;
    private GerenciadorVoos() {
        this.voos = new ArrayList<>();
    }
    public static GerenciadorVoos getInstance(){
        if(instance == null) instance = new GerenciadorVoos();
        return instance;
    }
    public void ordenarDataHora() {
        //voos.sort(Comparator.comparing(v -> v.getDatahora()));
        voos.sort(Comparator.comparing(Voo::getDatahora));
    }

    public void carregaDados(String nomeArq){
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                CiaAerea nova = new CiaAerea(dados[0], dados[1]);
                adicionar(nova);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public void ordenarDataHoraDuracao() {
        voos.sort(Comparator.comparing(Voo::getDatahora).
                thenComparing(Voo::getDuracao));
    }

    public void adicionar(Voo r) {
        voos.add(r);
    }

    public ArrayList<Voo> listarTodos() {
        return new ArrayList<>(voos);
    }

    public ArrayList<Voo> buscarData(LocalDate data) {
       ArrayList<Voo> result = new ArrayList<>();
       for(Voo v: voos)
           if(v.getDatahora().toLocalDate().equals(data))
               result.add(v);
       return result;
    }

    // Tarefa 1: listar os dados de vôos cuja origem é informada
    public ArrayList<Voo> buscarOrigem(String cod) {
        ArrayList<Voo> result = new ArrayList<>();
        for(Voo v: voos)
            if(v.getRota().getOrigem().getCodigo().equals(cod))
                result.add(v);
        return result;
    }

    // Tarefa 1: listar os dados de vôos que operam em determinado período do dia
    public ArrayList<Voo> buscarPeriodo(LocalTime inicio, LocalTime fim) {
        ArrayList<Voo> result = new ArrayList<>();
        for(Voo v: voos) {
            if(v.getDatahora().toLocalTime().compareTo(inicio) >= 0 &&
                    v.getDatahora().toLocalTime().compareTo(fim) <= 0)
                result.add(v);
        }
        return result;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder("Gerenciador de Voos\n--------------------\n");

        voos.forEach(msg::append);

        msg.append("\n- - - - - - - - - - -\n");

        return msg.toString();
    }
}

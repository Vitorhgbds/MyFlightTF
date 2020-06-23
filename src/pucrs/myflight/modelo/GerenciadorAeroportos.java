package pucrs.myflight.modelo;

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
}

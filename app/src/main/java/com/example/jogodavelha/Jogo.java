package com.example.jogodavelha;

import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.Map;

public class Jogo {

    public enum Status {
        EMPTY, CROSS, CIRCLE, NONE
    }

    private SparseArray<Status> tabuleiro = new SparseArray<>();
    private Status proximo;
    private boolean terminou = false;
    private VitoriaCallback onTerminoCallback;

    public Jogo(VitoriaCallback onTermino) {
        onTerminoCallback = onTermino;
        iniciaJogo();
    }

    private void iniciaJogo() {
        for(int i=0; i<9; i++){
            tabuleiro.append(i, Status.EMPTY);
        }
        proximo = Status.CROSS;
        terminou = false;
    }

    private void AtualizaProximo(){
        proximo = proximo == Status.CROSS ? Status.CIRCLE : Status.CROSS;
    }

    public Status ProcessaJogada(int pos) {
        /* Se ultimo clique terminou o jogo, reinicia */
        if (terminou){
            iniciaJogo();
        }
        /* Se clicou numa posicao não vazia, ignora */
        if (tabuleiro.get(pos) != Status.EMPTY){
            return Status.EMPTY;
        }
        Status marca = proximo;
        tabuleiro.setValueAt(pos, marca);
        if (Terminou()){
            terminou = true;
            return Status.EMPTY;
        }
        else {
            AtualizaProximo();
        }
        /* Retorna o valor marcado */
        return marca;
    }

    private boolean Terminou(){
        /* Linhas */
        if (tabuleiro.get(0) != Status.EMPTY && tabuleiro.get(0) == tabuleiro.get(1) &&  tabuleiro.get(1) == tabuleiro.get(2)){
            onTerminoCallback.run(tabuleiro.get(0));
            return true;
        }
        else if (tabuleiro.get(3) != Status.EMPTY && tabuleiro.get(3) == tabuleiro.get(4) &&  tabuleiro.get(4) == tabuleiro.get(5)){
            onTerminoCallback.run(tabuleiro.get(3));
            return true;
        }
        else if (tabuleiro.get(6) != Status.EMPTY && tabuleiro.get(6) == tabuleiro.get(7) &&  tabuleiro.get(7) == tabuleiro.get(8)){
            onTerminoCallback.run(tabuleiro.get(6));
            return true;
        }
        /* Colunas */
        else if (tabuleiro.get(0) != Status.EMPTY && tabuleiro.get(0) == tabuleiro.get(3) &&  tabuleiro.get(3) == tabuleiro.get(6)){
            onTerminoCallback.run(tabuleiro.get(0));
            return true;
        }
        else if (tabuleiro.get(1) != Status.EMPTY && tabuleiro.get(1) == tabuleiro.get(4) &&  tabuleiro.get(4) == tabuleiro.get(7)){
            onTerminoCallback.run(tabuleiro.get(1));
            return true;
        }
        else if (tabuleiro.get(2) != Status.EMPTY && tabuleiro.get(2) == tabuleiro.get(5) &&  tabuleiro.get(5) == tabuleiro.get(8)){
            onTerminoCallback.run(tabuleiro.get(2));
            return true;
        }
        /* Diagonais */
        else if (tabuleiro.get(0) != Status.EMPTY && tabuleiro.get(0) == tabuleiro.get(4) &&  tabuleiro.get(4) == tabuleiro.get(8)){
            onTerminoCallback.run(tabuleiro.get(0));
            return true;
        }
        else if (tabuleiro.get(2) != Status.EMPTY && tabuleiro.get(2) == tabuleiro.get(4) &&  tabuleiro.get(4) == tabuleiro.get(6)){
            onTerminoCallback.run(tabuleiro.get(2));
            return true;
        }
        else {
            for (int i=0; i < 9; i++){
                if (tabuleiro.get(i) == Status.EMPTY){
                    return false;
                }
            }
            /* Tabuleiro cheio */
            onTerminoCallback.run(Status.EMPTY);
            return true;
        }
    }
}

package main;

import dao.EventoDAO;
import model.Evento;
import servico.EventoServico;
import util.geradorDados.*;

public class Teste {
    EventoServico eventoServico = new EventoServico();
    EventoDAO eventoDAO = new EventoDAO();


    public static void main(String[] args) {
        Teste teste = new Teste();

        GeraEventos.geraEventos(100);

        GeraPessoas.geraPessoas(1000);

        GeraSalas.geraSalas(1000);

        //GeraSessoes.geraSessoes(1000);

        //GeraInscricoes.geraInscricoes(1000);



    }
}

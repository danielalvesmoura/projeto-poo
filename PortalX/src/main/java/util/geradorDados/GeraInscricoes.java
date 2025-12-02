package util.geradorDados;

import dao.EventoDAO;
import dao.PessoaDAO;
import model.Enum.StatusInscricao;
import model.Enum.TipoIngresso;
import model.Evento;
import model.Inscricao;
import model.Pessoa;
import servico.InscricaoServico;
import servico.PessoaServico;
import util.Global;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GeraInscricoes {
    static InscricaoServico inscricaoServico = new InscricaoServico();
    static PessoaDAO pessoaDAO = new PessoaDAO();
    static EventoDAO eventoDAO = new EventoDAO();

    public static void geraInscricoes(int qntd) {
        System.out.println("Gerando...");

        pessoas = pessoaDAO.buscarTodos(Pessoa.class);
        eventos = eventoDAO.buscarTodos(Evento.class);



        for(int i = 0; i < qntd; i++) {
            try {

                Inscricao inscricao = GeraInscricoes.gerarInscricao();
                List<Pessoa> pessoas = new ArrayList<>();
                pessoas.add(pegarPessoaAleatoria());
                inscricaoServico.cadastrar(pegarEventoAleatorio(),pessoas,gerarTipoIngresso().toString());

            } catch (Exception e) {

            }
        }

        System.out.println("Concluído.");
    }

    private static List<Pessoa> pessoas = pessoaDAO.buscarTodos(Pessoa.class);
    private static List<Evento> eventos = eventoDAO.buscarTodos(Evento.class);

    public static Inscricao gerarInscricao() {

        Pessoa pessoa = pegarPessoaAleatoria();
        Evento evento = pegarEventoAleatorio();

        StatusInscricao status = gerarStatus();
        TipoIngresso tipo = gerarTipoIngresso();

        return new Inscricao(pessoa, evento, status, tipo);
    }

    private static Pessoa pegarPessoaAleatoria() {
        int index = Global.faker.number().numberBetween(0, pessoas.size());
        return pessoas.get(index);
    }

    private static Evento pegarEventoAleatorio() {
        int index = Global.faker.number().numberBetween(0, eventos.size());
        return eventos.get(index);
    }

    private static StatusInscricao gerarStatus() {
        return StatusInscricao.values()[
                Global.faker.number().numberBetween(0, StatusInscricao.values().length)
                ];
    }

    private static TipoIngresso gerarTipoIngresso() {
        return TipoIngresso.values()[
                Global.faker.number().numberBetween(0, TipoIngresso.values().length)
                ];
    }


}

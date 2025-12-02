package util.geradorDados;

import model.Pessoa;
import model.Sala;
import servico.PessoaServico;
import servico.SalaServico;
import util.Global;

import java.time.LocalDate;

public class GeraSalas {
    static SalaServico salaServico = new SalaServico();

    public static void geraSalas(int qntd) {
        System.out.println("Gerando...");

        for(int i = 0; i < qntd; i++) {
            Sala sala = GeraSalas.gerarSala();

            salaServico.cadastrar(sala.getNome(),sala.getCapacidade(),sala.getLocalizacao());
        }

        System.out.println("Concluído.");
    }

    public static Sala gerarSala() {

        Sala sala = new Sala();

        sala.setNome(gerarNome());
        sala.setCapacidade(gerarCapacidade());
        sala.setLocalizacao(gerarLocalizacao());

        return sala;
    }

    public static String gerarNome() {
        return "Sala " + Global.faker.letterify("??").toUpperCase();
    }

    public static int gerarCapacidade() {
        return Global.faker.number().numberBetween(10, 200);
    }

    public static String gerarLocalizacao() {
        return Global.faker.address().streetAddress()
                + " - " + Global.faker.address().city();
    }


}

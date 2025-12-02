package util.geradorDados;

import model.Evento;
import model.Pessoa;
import servico.PessoaServico;
import util.Global;

import java.time.LocalDate;

public class GeraPessoas {
    static PessoaServico pessoaServico = new PessoaServico();

    public static void geraPessoas(int qntd) {
        System.out.println("Gerando...");

        for(int i = 0; i < qntd; i++) {
            Pessoa pessoa = GeraPessoas.gerarPessoa();

            pessoaServico.cadastrar(pessoa.getNome(),pessoa.getEmail(),pessoa.getTelefone(),pessoa.getDataNascimento());
        }

        System.out.println("Concluído.");
    }

    public static Pessoa gerarPessoa() {
        Pessoa pessoa = new Pessoa();

        // Nome
        String nome = Global.faker.name().fullName();
        pessoa.setNome(nome);

        // Email baseado no nome
        String email = gerarEmail(nome);
        pessoa.setEmail(email);

        // Telefone
        String telefone = gerarTelefone();
        pessoa.setTelefone(telefone);

        // Data de nascimento
        pessoa.setDataNascimento(gerarDataNascimento());

        return pessoa;
    }

    private static String gerarEmail(String nome) {
        String base = nome.toLowerCase()
                .replace(" ", ".")
                .replace("'", "")
                .replace("ç", "c")
                .replace("ã", "a")
                .replace("õ", "o")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");

        return base + "@" + Global.faker.internet().domainName();
    }

    private static String gerarTelefone() {
        return Global.faker.phoneNumber().cellPhone()
                .replace(" ", "")
                .replace("-", "")
                .replace("(", "")
                .replace(")", "");
    }

    private static LocalDate gerarDataNascimento() {
        int idade = Global.faker.number().numberBetween(18, 80);
        return LocalDate.now()
                .minusYears(idade)
                .minusDays(Global.faker.number().numberBetween(0, 365));
    }
}

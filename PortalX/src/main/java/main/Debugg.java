package main;

import model.Feedback;
import model.Palestrante;
import model.Participante;
import servico.*;

import java.time.LocalDate;

public class Debugg {
    public static void main(String[] args) {
        EventoServico eventoServico = new EventoServico();
        FeedbackServico feedbackServico = new FeedbackServico();
        PessoaServico pessoaServico = new PessoaServico();
        PalestranteServico palestranteServico = new PalestranteServico();
        ParticipanteServico participanteServico = new ParticipanteServico();

        eventoServico.cadastrar("Festival da Lua","Celebração cultural com música e gastronomia artesanal","Praça Central, 120 - Centro",LocalDate.parse("2025-03-14"),"18:00",LocalDate.parse("2025-03-14"),"23:30",500);
        eventoServico.cadastrar("Corrida Verde","Competição esportiva para incentivar práticas sustentáveis","Parque das Águas, 45 - Jardim Verde",LocalDate.parse("2025-04-02"),"07:30",LocalDate.parse("2025-04-02"),"12:00",800);
        eventoServico.cadastrar("Feira Literária Jovem","Encontro de autores, leitores e editoras independentes","Av. das Letras, 900 - Bela Vista",LocalDate.parse("2025-05-20"),"09:00",LocalDate.parse("2025-05-20"),"18:00",300);
        eventoServico.cadastrar("TechDay Inovação","Palestras e workshops sobre novas tecnologias","Rua dos Inventores, 210 - Distrito Industrial",LocalDate.parse("2025-06-10"),"10:00",LocalDate.parse("2025-06-10"),"17:00",450);
        eventoServico.cadastrar("Encontro de Vinhos do Sul","Degustações e palestras sobre produção vinícola","Estrada do Vale, km 8 - Vinhedos",LocalDate.parse("2025-07-05"),"16:00",LocalDate.parse("2025-07-05"),"22:00",250);
        eventoServico.cadastrar("Mostra de Cinema Independente","Exibição de curtas e longas de novos cineastas","Cine Art, Rua Aurora, 77 - Centro",LocalDate.parse("2025-08-12"),"14:00",LocalDate.parse("2025-08-12"),"22:00",350);
        eventoServico.cadastrar("Congresso de Medicina Holística","Debates sobre terapias integrativas e bem-estar","Centro de Convenções Horizonte, 300 - Universitário",LocalDate.parse("2025-09-18"),"08:00",LocalDate.parse("2025-09-19"),"18:00",600);
        eventoServico.cadastrar("Semana da Fotografia","Exposições, concursos e aulas abertas","Galeria Luz & Arte, Rua Amarela, 333 - Antigo",LocalDate.parse("2025-10-02"),"10:00",LocalDate.parse("2025-10-07"),"20:00",200);
        eventoServico.cadastrar("Festival Gourmet de Primavera","Pratos sazonais preparados por chefs locais","Boulevard Gastronômico, 12 - Lago Norte",LocalDate.parse("2025-11-15"),"11:00",LocalDate.parse("2025-11-15"),"21:00",700);
        eventoServico.cadastrar("Hackathon Social","Maratona de programação para criar soluções comunitárias","Espaço InovaLab, Rua Projetada, 500 - TechPark",LocalDate.parse("2025-12-05"),"09:00",LocalDate.parse("2025-12-06"),"09:00",120);

        /*
        palestranteServico.cadastrar("Marcos Silva","marcos.silva@example.com","(11) 91234-5678",LocalDate.parse("1985-03-12"));
        palestranteServico.cadastrar("Ana Rodrigues","ana.rodrigues@example.com","(21) 99876-5432",LocalDate.parse("1990-07-25"));
        palestranteServico.cadastrar("João Pereira","joao.pereira@example.com","(31) 98765-4321",LocalDate.parse("1978-11-05"));
        palestranteServico.cadastrar("Clara Souza","clara.souza@example.com","(41) 95544-3322",LocalDate.parse("1992-02-28"));
        palestranteServico.cadastrar("Ricardo Matos","ricardo.matos@example.com","(51) 91212-3434",LocalDate.parse("1988-09-19"));
        palestranteServico.cadastrar("Fernanda Alves","fernanda.alves@example.com","(61) 93456-7890",LocalDate.parse("1983-04-03"));
        palestranteServico.cadastrar("Paulo Menezes","paulo.menezes@example.com","(62) 92345-1122",LocalDate.parse("1975-12-14"));
        palestranteServico.cadastrar("Juliana Rocha","juliana.rocha@example.com","(71) 98877-6655",LocalDate.parse("1991-01-09"));
        palestranteServico.cadastrar("Victor Lima","victor.lima@example.com","(81) 97766-5544",LocalDate.parse("1986-06-21"));
        palestranteServico.cadastrar("Beatriz Castro","beatriz.castro@example.com","(91) 96655-4433",LocalDate.parse("1993-08-30"));
        palestranteServico.cadastrar("Henrique Duarte","henrique.duarte@example.com","(11) 94567-8899",LocalDate.parse("1980-05-11"));
        palestranteServico.cadastrar("Carolina Campos","carolina.campos@example.com","(21) 93445-6677",LocalDate.parse("1987-10-27"));
        palestranteServico.cadastrar("Fábio Torres","fabio.torres@example.com","(31) 95678-1234",LocalDate.parse("1979-09-17"));
        palestranteServico.cadastrar("Natália Freitas","natalia.freitas@example.com","(41) 94433-2211",LocalDate.parse("1994-12-02"));
        palestranteServico.cadastrar("Rafael Nogueira","rafael.nogueira@example.com","(51) 93322-1100",LocalDate.parse("1982-03-29"));
        palestranteServico.cadastrar("Letícia Carvalho","leticia.carvalho@example.com","(61) 96677-8899",LocalDate.parse("1995-07-14"));
        palestranteServico.cadastrar("Gustavo Ramos","gustavo.ramos@example.com","(62) 95566-7788",LocalDate.parse("1984-11-08"));
        palestranteServico.cadastrar("Tatiane Moreira","tatiane.moreira@example.com","(71) 97788-6655",LocalDate.parse("1989-04-22"));
        palestranteServico.cadastrar("Marcelo Tavares","marcelo.tavares@example.com","(81) 98822-3344",LocalDate.parse("1976-01-26"));
        palestranteServico.cadastrar("Aline Barros","aline.barros@example.com","(91) 94512-7788",LocalDate.parse("1992-09-03"));

        participanteServico.cadastrar("Lucas Almeida","lucas.almeida@example.com","(11) 92211-0044",LocalDate.parse("1997-05-18"));
        participanteServico.cadastrar("Mariana Lopes","mariana.lopes@example.com","(21) 93322-4455",LocalDate.parse("1999-10-02"));
        participanteServico.cadastrar("Thiago Rocha","thiago.rocha@example.com","(31) 94433-5566",LocalDate.parse("2001-02-11"));
        participanteServico.cadastrar("Camila Cunha","camila.cunha@example.com","(41) 98899-7766",LocalDate.parse("1996-07-30"));
        participanteServico.cadastrar("Eduardo Paiva","eduardo.paiva@example.com","(51) 97766-5544",LocalDate.parse("1998-12-23"));
        participanteServico.cadastrar("Priscila Antunes","priscila.antunes@example.com","(61) 95555-3322",LocalDate.parse("2000-08-09"));
        participanteServico.cadastrar("André Cardoso","andre.cardoso@example.com","(62) 91234-5566",LocalDate.parse("1995-03-04"));
        participanteServico.cadastrar("Larissa Gomes","larissa.gomes@example.com","(71) 92345-6789",LocalDate.parse("1997-11-16"));
        participanteServico.cadastrar("Felipe Duarte","felipe.duarte@example.com","(81) 93456-7891",LocalDate.parse("1996-09-01"));
        participanteServico.cadastrar("Bruna Monteiro","bruna.monteiro@example.com","(91) 94567-1234",LocalDate.parse("1998-04-27"));
        participanteServico.cadastrar("Igor Batista","igor.batista@example.com","(11) 96678-8899",LocalDate.parse("2002-06-03"));
        participanteServico.cadastrar("Sabrina Farias","sabrina.farias@example.com","(21) 95544-2211",LocalDate.parse("1999-01-20"));
        participanteServico.cadastrar("Renato Vieira","renato.vieira@example.com","(31) 93311-4422",LocalDate.parse("1997-10-11"));
        participanteServico.cadastrar("Daniela Correia","daniela.correia@example.com","(41) 94422-3344",LocalDate.parse("2001-12-14"));
        participanteServico.cadastrar("Alexandre Ribeiro","alexandre.ribeiro@example.com","(51) 97733-1122",LocalDate.parse("1996-07-08"));
        participanteServico.cadastrar("Patrícia Mendes","patricia.mendes@example.com","(61) 96655-4433",LocalDate.parse("1998-05-05"));
        participanteServico.cadastrar("Roberto Lima","roberto.lima@example.com","(62) 95544-6677",LocalDate.parse("1995-09-22"));
        participanteServico.cadastrar("Isabela Pires","isabela.pires@example.com","(71) 93322-1188",LocalDate.parse("1999-03-29"));
        participanteServico.cadastrar("Caio Santos","caio.santos@example.com","(81) 98877-4433",LocalDate.parse("2000-10-12"));
        participanteServico.cadastrar("Viviane Rocha","viviane.rocha@example.com","(91) 91223-5566",LocalDate.parse("1997-02-06"));
        */


    }
}

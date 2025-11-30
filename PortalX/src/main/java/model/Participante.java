package model;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "participante")
public class Participante extends Pessoa {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private long id;

    //@Column(length = 100)
    //private String nome;
    //private String email;
    //private String telefone;
    //private LocalDate dataNascimento;

    public Participante(){};


    public Participante(String nome, String email, String telefone, LocalDate dataNascimento) {
        super(nome, email, telefone, dataNascimento);
    }

    public String getTipo() {
        return "Participante";
    }


}
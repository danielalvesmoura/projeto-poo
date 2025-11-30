package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.REMOVE)
    private List<Inscricao> inscricoes;



    // CHECKBOX INSCRIÇÃO

    @Transient
    private BooleanProperty selecionado = new SimpleBooleanProperty(false);
    public boolean isSelecionado() { return selecionado.get(); }
    public void setSelecionado(boolean valor) { selecionado.set(valor); }
    public BooleanProperty selecionadoProperty() { return selecionado; }

    //


    public Pessoa(String nome, String email, String telefone, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public long getId() {
        return id;
    }

    public Pessoa(){};

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
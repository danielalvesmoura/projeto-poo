package model;

import org.apache.commons.math3.analysis.function.Exp;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sala")
public class Sala implements Exportavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sessao> sessoes;

    @Column(length = 100)
    private String nome;
    private int capacidade;
    private String localizacao;

    public Sala() {};

    public Sala(String nome, int capacidade, String localizacao) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
    }


    @Override
    public String getCabecalho() {
        return "ID, NOTA, NOME, CAPACIDADE, LOCALIZAÇÃO";
    }

    @Override
    public String getCorpo() {
        return id + ", " + nome + ", " +capacidade + ", " +localizacao;
    }

    @Override
    public String[] getCorpoVetor() {
        String[] corpo = {Long.toString(id),nome,Integer.toString(capacidade),localizacao};
        return corpo;
    }

    @Override
    public String[] getCabecalhoVetor() {
        String[] cabecalho = {"ID", "Nome", "Nota", "Capacidade", "Localização"};
        return cabecalho;
    }


    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
package model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sessao> sessoes;

    @Column(length = 100)
    private LocalDate dataFim;
    private LocalDate dataInicio;
    private String endereco;
    private String descricao;
    private String nome;

    public Evento(String nome, String descricao, String endereco, LocalDate dataInicio, LocalDate dataFim) {
        setDataFim(dataFim);
        setDataInicio(dataInicio);
        setEndereco(endereco);
        setDescricao(descricao);
        setNome(nome);
    }

    public Evento() {};

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
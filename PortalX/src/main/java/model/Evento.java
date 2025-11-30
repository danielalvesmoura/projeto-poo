package model;

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
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String endereco;
    private String descricao;
    private String nome;
    private int capacidade;

    public Evento(String nome, String descricao, String endereco, LocalDate dataInicio, String horaInicio, LocalDate dataFim, String horaFim, int capacidade) {
        setDataFim(dataFim);
        setDataInicio(dataInicio);
        setEndereco(endereco);
        setDescricao(descricao);
        setNome(nome);
        setHoraFim(LocalTime.parse(horaFim));
        setHoraInicio(LocalTime.parse(horaInicio));
        setCapacidade(capacidade);
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public String getCapacidade() {
        return String.valueOf(capacidade);
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
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
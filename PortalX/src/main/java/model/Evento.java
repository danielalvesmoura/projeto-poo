package model;

import servico.EventoServico;
import servico.InscricaoServico;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento implements Exportavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Sessao> sessoes;

    //@Transient
    //private ListaEncadeadaInscricoes inscricoes = new ListaEncadeadaInscricoes();

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

    @Override
    public String getCabecalho() {
        return "ID, NOME, DESCRICAO, ENDERECO, DATA INICIO, HORA INICIO, DATA FIM, HORA FIM, CAPACIDADE";
    }

    @Override
    public String getCorpo() {
        return id + ", " + nome + ", " + descricao + ", " +endereco + ", " +dataInicio + ", " +horaInicio + ", " +dataFim + ", " +horaFim + ", " +capacidade;
    }

    @Override
    public String[] getCorpoVetor() {
        String[] corpo = {Long.toString(id),nome,descricao,endereco,dataInicio.toString(),horaInicio.toString(),dataFim.toString(),horaFim.toString(),Integer.toString(capacidade)};
        return corpo;
    }

    @Override
    public String[] getCabecalhoVetor() {
        String[] cabecalho = {"ID", "Nome", "Descrição", "Endereço", "Data Início", "Hora Início", "Data Fim", "Hora Fim", "Capacidade"};
        return cabecalho;
    }

    //public ListaEncadeadaInscricoes getInscricoes() {
    //    return inscricoes;
    //}

    //public void setInscricoes(ListaEncadeadaInscricoes inscricoes) {
    //    this.inscricoes = inscricoes;
    //}

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

    /*
    public String getCapacidadeView() {

        InscricaoServico inscricaoServico = new InscricaoServico();
        return inscricaoServico.vagasPreenchidas(this) + " / " + capacidade;


        return null;
    }

     */

}
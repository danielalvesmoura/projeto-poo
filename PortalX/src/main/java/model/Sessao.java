package model;

import javax.persistence.*;

import model.Enum.StatusSessao;
import model.Enum.TipoSessao;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sessao")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @Column(length = 100)
    private String titulo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoSessao tipo;

    private LocalDate dataInicio;
    private LocalTime horaInicio;
    private LocalDate dataFim;
    private LocalTime horaFim;

    @Enumerated(EnumType.STRING)
    private StatusSessao status;

    public Sessao(Evento evento, String titulo, String descricao, TipoSessao tipo, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim, StatusSessao status) {
        this.evento = evento;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.dataFim = dataFim;
        this.horaFim = horaFim;
        this.status = status;
    }

    public Sessao(){};

    public long getId() {
        return id;
    }

    public StatusSessao getStatus() {
        return status;
    }

    public void setStatus(StatusSessao status) {
        this.status = status;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public TipoSessao getTipo() {
        return tipo;
    }

    public void setTipo(TipoSessao tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
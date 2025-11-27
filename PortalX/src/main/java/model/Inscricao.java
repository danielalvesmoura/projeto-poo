package model;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

import model.Enum.StatusInscricao;
import model.Enum.TipoIngresso;

@Entity
@Table(name = "inscricao")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    //@MapsId("pessoaId")
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    //@MapsId("eventoId")
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @Column(length = 100)

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;
    @Enumerated(EnumType.STRING)
    private TipoIngresso tipoIngresso;

    private LocalDate dataCriacao;

    public Inscricao(Pessoa pessoa, Evento evento, StatusInscricao statusInscricao, TipoIngresso tipoIngresso) {
        this.pessoa = pessoa;
        this.evento = evento;
        this.status = statusInscricao;
        this.tipoIngresso = tipoIngresso;
    }


    public Inscricao(){};

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }


    public long getId() {
        return id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }

    public TipoIngresso getTipoIngresso() {
        return tipoIngresso;
    }

    public void setTipoIngresso(TipoIngresso tipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }
}
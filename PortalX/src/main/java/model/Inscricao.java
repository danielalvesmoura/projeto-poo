package model;

import javax.persistence.*;

import java.time.LocalDate;

import model.Enum.StatusInscricao;
import model.Enum.TipoIngresso;

@Entity
@Table(name = "inscricao")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;
    private TipoIngresso tipoIngresso;
    private LocalDate dataCriacao;

    public long getId() {
        return id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }

    public TipoIngresso getTipoIngresso() {
        return tipoIngresso;
    }

    public void setTipoIngresso(TipoIngresso TipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }
}
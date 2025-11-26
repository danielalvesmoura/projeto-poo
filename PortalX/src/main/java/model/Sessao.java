package model;

import javax.persistence.*;

import model.Enum.StatusSessao;
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
    private Enum tipo;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private StatusSessao status;

}
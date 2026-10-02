package com.web2.lab1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "locacao")
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "Gerado automaticamente")
    private Integer id;

    @NotNull(message = "O imóvel é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_imovel")
    private Imovel imovel;

    @NotNull(message = "O inquilino é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_inquilino")
    private Cliente inquilino;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @NotNull(message = "A data de início é obrigatória")
    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    @Column(name = "data_fim")
    private LocalDate dataFim;

    @NotNull(message = "O dia de vencimento é obrigatório")
    @Min(value = 1, message = "O dia de vencimento deve estar entre 1 e 31")
    @Max(value = 31, message = "O dia de vencimento deve estar entre 1 e 31")
    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @NotNull(message = "O percentual de multa é obrigatório")
    @DecimalMin(value = "0.0", message = "O percentual de multa deve ser maior ou igual a 0")
    @Column(name = "perc_multa", precision = 5, scale = 2)
    private BigDecimal percMulta;

    @NotNull(message = "O valor do aluguel é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor do aluguel deve ser maior que 0")
    @Column(name = "valor_aluguel", precision = 10, scale = 2)
    private BigDecimal valorAluguel;

    @Column(name = "obs", columnDefinition = "text")
    private String obs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Cliente getInquilino() {
        return inquilino;
    }

    public void setInquilino(Cliente inquilino) {
        this.inquilino = inquilino;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public BigDecimal getPercMulta() {
        return percMulta;
    }

    public void setPercMulta(BigDecimal percMulta) {
        this.percMulta = percMulta;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locacao locacao = (Locacao) o;
        return Objects.equals(id, locacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Locacao{" +
                "id=" + id +
                ", imovel=" + imovel +
                ", inquilino=" + inquilino +
                ", ativo=" + ativo +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", diaVencimento=" + diaVencimento +
                ", percMulta=" + percMulta +
                ", valorAluguel=" + valorAluguel +
                '}';
    }
}
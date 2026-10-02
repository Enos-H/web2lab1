package com.web2.lab1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "imoveis")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "Gerado automaticamente")
    private Integer id;

    @NotBlank(message = "O tipo do imóvel é obrigatório")
    @Size(max = 50, message = "O tipo do imóvel deve ter no máximo 50 caracteres")
    @Column(name = "tipo_imovel", length = 50)
    private String tipoImovel;

    @NotBlank(message = "O endereço é obrigatório")
    @Column(name = "endereco")
    private String endereco;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
    @Column(name = "cep", length = 10)
    private String cep;

    @NotNull(message = "A quantidade de dormitórios é obrigatória")
    @Min(value = 0, message = "A quantidade de dormitórios deve ser maior ou igual a 0")
    @Column(name = "dormitorios")
    private Integer dormitorios;

    @NotNull(message = "A quantidade de banheiros é obrigatória")
    @Min(value = 0, message = "A quantidade de banheiros deve ser maior ou igual a 0")
    @Column(name = "banheiros")
    private Integer banheiros;

    @NotNull(message = "A quantidade de suítes é obrigatória")
    @Min(value = 0, message = "A quantidade de suítes deve ser maior ou igual a 0")
    @Column(name = "suites")
    private Integer suites;

    @NotNull(message = "A metragem é obrigatória")
    @Min(value = 1, message = "A metragem deve ser maior que 0")
    @Column(name = "metragem")
    private Integer metragem;

    @NotNull(message = "O valor do aluguel sugerido é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor do aluguel sugerido deve ser maior que 0")
    @Column(name = "valor_aluguel_sug", precision = 10, scale = 2)
    private BigDecimal valorAluguelSug;

    @Column(name = "obs", columnDefinition = "text")
    private String obs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(Integer dormitorios) {
        this.dormitorios = dormitorios;
    }

    public Integer getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(Integer banheiros) {
        this.banheiros = banheiros;
    }

    public Integer getSuites() {
        return suites;
    }

    public void setSuites(Integer suites) {
        this.suites = suites;
    }

    public Integer getMetragem() {
        return metragem;
    }

    public void setMetragem(Integer metragem) {
        this.metragem = metragem;
    }

    public BigDecimal getValorAluguelSug() {
        return valorAluguelSug;
    }

    public void setValorAluguelSug(BigDecimal valorAluguelSug) {
        this.valorAluguelSug = valorAluguelSug;
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
        Imovel imovel = (Imovel) o;
        return Objects.equals(id, imovel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Imovel{" +
                "id=" + id +
                ", tipoImovel='" + tipoImovel + '\'' +
                ", endereco='" + endereco + '\'' +
                ", cep='" + cep + '\'' +
                ", dormitorios=" + dormitorios +
                ", banheiros=" + banheiros +
                ", suites=" + suites +
                ", metragem=" + metragem +
                ", valorAluguelSug=" + valorAluguelSug +
                '}';
    }
}
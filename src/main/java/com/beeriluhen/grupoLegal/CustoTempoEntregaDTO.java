package com.beeriluhen.grupoLegal;

public class CustoTempoEntregaDTO {
    private String cepOrigem;
    private String cidadeOrigem;
    private String cepDestino;
    private String cidadeDestino;
    private double peso;
    private double custoBasico;
    private double desconto;
    private double impostostaxas;
    private double custoFinal;
    private int diasUteisParaEntrega;

    public CustoTempoEntregaDTO(Builder build) {
        this.cepOrigem = build.cepOrigem;
        this.cidadeOrigem = build.cidadeOrigem;
        this.cepDestino = build.cepDestino;
        this.cidadeDestino = build.cidadeDestino;
        this.peso = build.peso;
        this.custoBasico = build.custoBasico;
        this.desconto = build.desconto;
        this.impostostaxas = build.impostostaxas;
        this.custoFinal = build.custoFinal;
        this.diasUteisParaEntrega = build.diasUteisParaEntrega;
    }

    public static class Builder {
        String cepOrigem = "01031970";
        String cidadeOrigem = "São Paulo";
        String cepDestino = "";
        String cidadeDestino = "";
        double peso = 0;
        double custoBasico = 0;
        double desconto = 0;
        double impostostaxas = 0;
        double custoFinal = 0;
        int diasUteisParaEntrega = -1;

        public Builder() {
        }

        public Builder cepOrigem(String cep) {
            this.cepOrigem = cep;
            return this;
        }

        public Builder cidadeOrigem(String cidade) {
            this.cidadeOrigem = cidade;
            return this;
        }

        public Builder cepDestino(String cep) {
            this.cepDestino = cep;
            return this;
        }

        public Builder cidadeDestino(String cidade) {
            this.cidadeDestino = cidade;
            return this;
        }

        public Builder peso(double p) {
            this.peso = p;
            return this;
        }

        public Builder custoBasico(double custo) {
            this.custoBasico = custo;
            return this;
        }

        public Builder desconto(double valor) {
            this.desconto = valor;
            return this;
        }

        public Builder impostosTaxas(double valor) {
            this.impostostaxas = valor;
            return this;
        }

        public Builder custoFinal(double valor) {
            this.custoFinal = valor;
            return this;
        }

        public Builder diasUteisParaEntrega(int dias) {
            this.diasUteisParaEntrega = dias;
            return this;
        }

        public CustoTempoEntregaDTO build() {
            return new CustoTempoEntregaDTO(this);
        }
    }

    public String getCepOrigem() {
        return cepOrigem;
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public String getCepDestino() {
        return cepDestino;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

}
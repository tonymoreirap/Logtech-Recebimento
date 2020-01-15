package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class Produtos implements Serializable {

    private Long id;
    private String CodBarra;
    private String nomeProduto;
    private String descricao;
    private String quantidade_pedida;
    private String fator;
    private String embalagem;
    private int quantidade;

    @Override
    public String toString() {
        return nomeProduto.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuantidade_pedida() { return quantidade_pedida; }

    public void setQuantidade_pedida(String quantidade_pedida) { this.quantidade_pedida = quantidade_pedida; }

    public String getFator() { return fator; }

    public void setFator(String fator) { this.fator = fator; }

    public String getEmbalagem() { return embalagem; }

    public void setEmbalagem(String embalagem) { this.embalagem = embalagem; }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCodBarra() {
        return CodBarra;
    }

    public void setCodBarra(String codbarra) {
        this.CodBarra = codbarra;
    }
}

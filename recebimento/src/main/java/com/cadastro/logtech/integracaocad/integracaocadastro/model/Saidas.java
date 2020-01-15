package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class Saidas implements Serializable {

    private Long id;
    private String codproduto;
    private String tipomovimento;
    private String qtdemov;

    @Override
    public String toString() {
        return tipomovimento.toString();
    }

    public String getCodproduto() { return codproduto; }

    public void setCodproduto(String codproduto) { this.codproduto = codproduto; }

    public String getTipomovimento() { return tipomovimento; }

    public void setTipomovimento(String tipomovimento) { this.tipomovimento = tipomovimento; }

    public String getQtdemov() { return qtdemov; }

    public void setQtdemov(String qtdemov) { this.qtdemov = qtdemov; }

    public Long getId() { return id;  }

    public void setId(Long id) { this.id = id; }

}

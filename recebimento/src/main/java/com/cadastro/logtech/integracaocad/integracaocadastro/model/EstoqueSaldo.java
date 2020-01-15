package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class EstoqueSaldo implements Serializable {

    private String codproduto;
    private String tipomovimento;
    private String nomeproduto;
    private String saldoest;

    public String getCodproduto() {
        return codproduto;
    }

    public void setCodproduto(String codproduto) {
        this.codproduto = codproduto;
    }

    public String getTipomovimento() {
        return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {
        this.tipomovimento = tipomovimento;
    }

    public String getNomeproduto() {
        return nomeproduto;
    }

    public void setNomeproduto(String nomeproduto) {
        this.nomeproduto = nomeproduto;
    }

    public String getSaldoest(String saldoEst) {
        return saldoest;
    }

    public void setSaldoest(String saldoest) {
        this.saldoest = saldoest;
    }
}

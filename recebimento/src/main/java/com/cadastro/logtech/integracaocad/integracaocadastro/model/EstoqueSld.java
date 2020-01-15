package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class EstoqueSld implements Serializable {  //tabela de movimentação de estoque
    private Long id;
    private String codproduto;
    private String tipomovimento;
    private String nomeproduto;
    private String saldoest;
    private String datamov;

    @Override
    public String toString() {
        return nomeproduto.toString();
    }

    public String getCodproduto() { return codproduto; }

    public void setCodproduto(String codproduto) { this.codproduto = codproduto; }

    public String getTipomovimento() { return tipomovimento; }

    public void setTipomovimento(String tipomovimento) { this.tipomovimento = tipomovimento; }

    public String getSaldoEst() { return saldoest; }

    public void setSaldoEst(String saldoest) { this.saldoest = saldoest; }

    public String getDatamov() { return datamov; }

    public void setDatamov(String datamov) { this.datamov = datamov; }

    public String getNomeproduto() { return nomeproduto; }

    public void setNomeproduto(String nomeproduto) { this.nomeproduto = nomeproduto; }

    public Long getId() { return id;  }

    public void setId(Long id) { this.id = id; }


}

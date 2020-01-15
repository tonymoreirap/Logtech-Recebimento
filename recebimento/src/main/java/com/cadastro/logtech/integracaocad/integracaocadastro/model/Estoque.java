package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class Estoque implements Serializable {

    private Long id;
    private String codproduto;
    private String tipomovimento;
    private String nomeproduto;
    private String qtdemov;
    private String datamov;

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

    public String getDatamov() { return datamov; }

    public void setDatamov(String datamov) { this.datamov = datamov; }

    public String getNomeproduto() { return nomeproduto; }

    public void setNomeproduto(String nomeproduto) { this.nomeproduto = nomeproduto; }

    public Long getId() { return id;  }

    public void setId(Long id) { this.id = id; }


}

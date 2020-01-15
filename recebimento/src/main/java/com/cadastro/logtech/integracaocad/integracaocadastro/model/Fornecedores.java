package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class Fornecedores implements Serializable {

    private Long id;
    private String nomeFornecedor;
    private String RazaoSocial;
    private String CNPJ;


    @Override
    public String toString() {
        return nomeFornecedor.toString();
    }

    public String getnomeFornecedor() {
        return nomeFornecedor;
    }

    public void setnomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String RazaoSocial) {
        this.RazaoSocial = RazaoSocial;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

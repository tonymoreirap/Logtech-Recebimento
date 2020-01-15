package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class Clientes implements Serializable {
    private Long id;
    private String nomeCliente;
    private String RazaoSocial;
    private String CNPJCPF;


    @Override
    public String toString() {
        return nomeCliente.toString();
    }

    public String getnomeCliente() {
        return nomeCliente;
    }

    public void setnomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String RazaoSocial) {
        this.RazaoSocial = RazaoSocial;
    }

    public String getCNPJCPF() {
        return CNPJCPF;
    }

    public void setCNPJCPF(String CNPJCPF) {
        this.CNPJCPF = CNPJCPF;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

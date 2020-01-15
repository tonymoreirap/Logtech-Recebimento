package com.cadastro.logtech.integracaocad.integracaocadastro.model;

import java.io.Serializable;

public class Usuarios implements Serializable {
    private Long id;
    private String nome;
    private String celular;
    private String senha;

    public Usuarios(Long id, String usuario, String senha) {
        this.id = id;
        this.nome = usuario;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nome.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}


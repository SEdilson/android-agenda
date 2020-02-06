package com.example.agenda.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
public class Aluno implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String nome;
    private String email;
    private Calendar momentoDoCadastro = Calendar.getInstance();

    public Calendar getMomentoDoCadastro() {
        return momentoDoCadastro;
    }

    public void setMomentoDoCadastro(Calendar momentoDoCadastro) {
        this.momentoDoCadastro = momentoDoCadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    public boolean temIdValido() {
        return id > 0;
    }
}

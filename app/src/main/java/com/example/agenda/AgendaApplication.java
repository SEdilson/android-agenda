package com.example.agenda;

import android.app.Application;

import com.example.agenda.dao.AlunoDAO;
import com.example.agenda.models.Aluno;

@SuppressWarnings("WeakerAccess")
public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosTeste();
    }

    private void criaAlunosTeste() {
        AlunoDAO dao = new AlunoDAO();
        dao.salva(new Aluno("Edilson", "984100476", "edilson.silva00@hotmail.com"));
        dao.salva(new Aluno("Maria", "984134476", "maria@hotmail.com"));
    }
}

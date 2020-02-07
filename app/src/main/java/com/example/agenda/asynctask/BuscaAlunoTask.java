package com.example.agenda.asynctask;

import android.os.AsyncTask;

import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.ui.adapter.ListaAlunosAdapter;

import java.util.List;

public class BuscaAlunoTask extends AsyncTask<Void, Void, List<Aluno>> {
    private ListaAlunosAdapter adapter;
    private RoomAlunoDAO dao;

    public BuscaAlunoTask(ListaAlunosAdapter adapter, RoomAlunoDAO dao) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected List<Aluno> doInBackground(Void[] objects) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Aluno> alunos) {
        super.onPostExecute(alunos);
        adapter.atualizaView(alunos);
    }
}
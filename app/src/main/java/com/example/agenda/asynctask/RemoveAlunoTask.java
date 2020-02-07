package com.example.agenda.asynctask;

import android.os.AsyncTask;

import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.ui.adapter.ListaAlunosAdapter;

public class RemoveAlunoTask extends AsyncTask<Void, Void, Void> {

    private ListaAlunosAdapter adapter;
    private Aluno aluno;
    private RoomAlunoDAO dao;

    public RemoveAlunoTask(ListaAlunosAdapter adapter, Aluno aluno, RoomAlunoDAO dao) {
        this.adapter = adapter;
        this.aluno = aluno;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(aluno);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.remove(aluno);
    }
}

package com.example.agenda.asynctask;

import android.os.AsyncTask;

import com.example.agenda.database.dao.RoomTelefoneDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.models.Telefone;

import java.util.List;

public class BuscaTodosOsTelefonesDoAlunoTask extends AsyncTask<Void, Void, List<Telefone>> {

    private RoomTelefoneDAO telefoneDAO;
    private Aluno aluno;
    private TelefonesEncontradosDoAluno listener;

    public BuscaTodosOsTelefonesDoAlunoTask(RoomTelefoneDAO telefoneDAO,
                                            Aluno aluno,
                                            TelefonesEncontradosDoAluno listener) {
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.listener = listener;
    }

    @Override
    protected List<Telefone> doInBackground(Void... voids) {
        return telefoneDAO.retornaTodosOsTelefones(aluno.getId());
    }

    @Override
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoEncontrados(telefones);
    }

    public interface TelefonesEncontradosDoAluno {
        void quandoEncontrados(List<Telefone> telefones);
    }
}

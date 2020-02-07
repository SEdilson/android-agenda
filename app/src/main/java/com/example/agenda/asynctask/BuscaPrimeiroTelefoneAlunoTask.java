package com.example.agenda.asynctask;

import android.os.AsyncTask;

import com.example.agenda.database.dao.RoomTelefoneDAO;
import com.example.agenda.models.Telefone;

public class BuscaPrimeiroTelefoneAlunoTask extends AsyncTask<Void, Void, Telefone> {

    private final RoomTelefoneDAO dao;
    private final int alunoId;
    private final PrimeiroTelefoneListener listener;

    public BuscaPrimeiroTelefoneAlunoTask(RoomTelefoneDAO dao,
                                          int alunoId,
                                          PrimeiroTelefoneListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.retornaPrimeiroTelefone(alunoId);
    }

    @Override
    protected void onPostExecute(Telefone primeiroTelefone) {
        super.onPostExecute(primeiroTelefone);
        listener.quandoEncontrado(primeiroTelefone);
    }

    public interface PrimeiroTelefoneListener {
        void quandoEncontrado(Telefone telefoneEncontrado);
    }
}

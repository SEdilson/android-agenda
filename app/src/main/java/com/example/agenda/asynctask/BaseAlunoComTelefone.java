package com.example.agenda.asynctask;

import android.os.AsyncTask;

import com.example.agenda.models.Telefone;

abstract class BaseAlunoComTelefone extends AsyncTask<Void, Void, Void> {

    private TaskFinalizadaListener listener;

    protected BaseAlunoComTelefone(TaskFinalizadaListener listener) {
        this.listener = listener;
    }

    void vinculaTelefones(int alunoId, Telefone... telefones) {
        for (Telefone telefone:
                telefones) {
            telefone.setAlunoId(alunoId);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizar();
    }

    public interface TaskFinalizadaListener {
        void quandoFinalizar();
    }
}

package com.example.agenda.asynctask;

import android.os.AsyncTask;

import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.database.dao.RoomTelefoneDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.models.Telefone;

public class SalvaAlunoTask extends BaseAlunoComTelefone {
    private RoomAlunoDAO alunoDAO;
    private Aluno aluno;
    private RoomTelefoneDAO telefoneDAO;
    private Telefone telefoneFixo;
    private Telefone telefoneCelular;

    public SalvaAlunoTask(RoomAlunoDAO alunoDAO,
                          Aluno aluno,
                          RoomTelefoneDAO telefoneDAO,
                          Telefone telefoneFixo,
                          Telefone telefoneCelular,
                          TaskFinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.aluno = aluno;
        this.telefoneDAO = telefoneDAO;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int alunoId = alunoDAO.salva(aluno).intValue();
        vinculaTelefones(alunoId, telefoneFixo, telefoneCelular);
        telefoneDAO.salva(telefoneFixo, telefoneCelular);
        return null;
    }
}

package com.example.agenda.asynctask;

import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.database.dao.RoomTelefoneDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.models.Telefone;
import com.example.agenda.models.TipoTelefone;

import java.util.List;

public class EditaAlunoTask extends BaseAlunoComTelefone {

    private RoomAlunoDAO alunoDAO;
    private Aluno aluno;
    private List<Telefone> telefonesDoAluno;
    private Telefone telefoneFixo;
    private Telefone telefoneCelular;
    private RoomTelefoneDAO telefoneDAO;

    public EditaAlunoTask(RoomAlunoDAO alunoDAO, Aluno aluno, List<Telefone> telefonesDoAluno,
                          Telefone telefoneFixo, Telefone telefoneCelular,
                          RoomTelefoneDAO telefoneDAO, TaskFinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.aluno = aluno;
        this.telefonesDoAluno = telefonesDoAluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.telefoneDAO = telefoneDAO;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        alunoDAO.edita(aluno);
        atualizaIdsDosTelefones(telefoneFixo, telefoneCelular);
        vinculaTelefones(aluno.getId(), telefoneFixo, telefoneCelular);
        telefoneDAO.atualiza(telefoneFixo, telefoneCelular);
        return null;
    }

    private void atualizaIdsDosTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone:
                telefonesDoAluno) {
            if(telefone.getTipo() == TipoTelefone.FIXO) {
                telefoneFixo.setId(telefone.getId());
            } else {
                telefoneCelular.setId(telefone.getId());
            }
        }
    }
}

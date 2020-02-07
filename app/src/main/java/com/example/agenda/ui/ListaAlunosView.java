package com.example.agenda.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.agenda.asynctask.BuscaAlunoTask;
import com.example.agenda.asynctask.RemoveAlunoTask;
import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {

    private final Context context;
    private final ListaAlunosAdapter adapter;
    private final RoomAlunoDAO dao;

    public ListaAlunosView(Context context) {
        this.context = context;
        adapter = new ListaAlunosAdapter(this.context);
        dao = AgendaDatabase.getInstance(context)
                .getRoomAlunoDAO();
    }

    public void confirmaRemocao(@NonNull final MenuItem item) {
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que deseja remover o aluno?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdapterView.AdapterContextMenuInfo menuInfo =
                                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
                        removeAluno(alunoEscolhido);
                        configuraDialogDeConfirmacao();
                    }
                })
                .setNegativeButton("Não", null).
                show();
    }

    public void configuraDialogDeConfirmacao() {
        new AlertDialog.Builder(this.context)
                .setMessage("Aluno removido com sucesso.")
                .setPositiveButton("OK", null)
                .show();
    }

    public void atualizaAlunos() {
        new BuscaAlunoTask(adapter, dao).execute();
    }

    public void removeAluno(Aluno aluno) {
        new RemoveAlunoTask(adapter, aluno, dao).execute();
    }

    public void configuraAdapter(ListView listaAlunos) {
        listaAlunos.setAdapter(this.adapter);
    }
}

package com.example.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.asynctask.BuscaPrimeiroTelefoneAlunoTask;
import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.RoomTelefoneDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.models.Telefone;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos = new ArrayList<>();
    private final Context context;
    private RoomTelefoneDAO dao;

    public ListaAlunosAdapter(Context context) {
        this.context = context;
        dao = AgendaDatabase.getInstance(context).getRoomTelefoneDAO();
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View viewCriada = criaView(viewGroup);
        Aluno alunoRetornado = alunos.get(position);
        vinculaAlunoComViewCriada(viewCriada, alunoRetornado);
        return viewCriada;
    }

    private void vinculaAlunoComViewCriada(View viewCriada, Aluno alunoRetornado) {
        TextView nome = viewCriada.findViewById(R.id.activity_lista_alunos_nome);
        nome.setText(alunoRetornado.getNome());
        final TextView campoTelefone = viewCriada.findViewById(R.id.activity_lista_alunos_telefone);
        new BuscaPrimeiroTelefoneAlunoTask(dao, alunoRetornado.getId(), new BuscaPrimeiroTelefoneAlunoTask.PrimeiroTelefoneListener() {
            @Override
            public void quandoEncontrado(Telefone telefoneEncontrado) {
                campoTelefone.setText(telefoneEncontrado.getNumero());
            }
        }).execute();
    }

    private View criaView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_aluno, viewGroup, false);
    }

    public void atualizaView(List<Aluno> todos) {
        alunos.clear();
        this.alunos.addAll(todos);
        notifyDataSetChanged();
    }

    public void remove(Aluno aluno) {
        alunos.remove(aluno);
        notifyDataSetChanged();
    }
}

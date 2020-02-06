package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.models.Aluno;

import static com.example.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

@SuppressWarnings("ConstantConditions")
public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_NOVO_ALUNO_APPBAR = "Novo aluno";
    private static final String TITULO_EDITA_ALUNO_APPBAR = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private EditText campoEmail;
    private RoomAlunoDAO dao;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        dao = AgendaDatabase.getInstance(this).getRoomAlunoDAO();
        inicializacaoDosCampos();
        carregaAluno();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu_salvar_aluno, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dadosAluno = getIntent();
        if(dadosAluno.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_EDITA_ALUNO_APPBAR);
            aluno = (Aluno) dadosAluno.getSerializableExtra(CHAVE_ALUNO);
            campoNome.setText(aluno.getNome());
//            campoTelefoneFixo.setText(aluno.getTelefoneFixo());
//            campoTelefoneCelular.setText(aluno.getTelefoneCelular());
            campoEmail.setText(aluno.getEmail());
        } else {
            setTitle(TITULO_NOVO_ALUNO_APPBAR);
            aluno = new Aluno();
        }
    }

    private void finalizaFormulario() {
        preencheAluno();
        if(aluno.temIdValido()) {
            dao.edita(aluno);
        } else {
            dao.salva(aluno);
        }
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_aluno_telefone_fixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_aluno_telefone_celular);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefoneFixo = campoTelefoneFixo.getText().toString();
        String telefoneCelular = campoTelefoneCelular.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
//        aluno.setTelefoneFixo(telefoneFixo);
//        aluno.setTelefoneCelular(telefoneCelular);
        aluno.setEmail(email);
    }
}

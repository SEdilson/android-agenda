package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.asynctask.BuscaTodosOsTelefonesDoAlunoTask;
import com.example.agenda.asynctask.EditaAlunoTask;
import com.example.agenda.asynctask.SalvaAlunoTask;
import com.example.agenda.database.AgendaDatabase;
import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.database.dao.RoomTelefoneDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.models.Telefone;
import com.example.agenda.models.TipoTelefone;

import java.util.List;

import static com.example.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

@SuppressWarnings("ConstantConditions")
public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_NOVO_ALUNO_APPBAR = "Novo aluno";
    private static final String TITULO_EDITA_ALUNO_APPBAR = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private EditText campoEmail;
    private RoomAlunoDAO alunoDAO;
    private RoomTelefoneDAO telefoneDAO;
    private Aluno aluno;
    private List<Telefone> telefonesDoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        alunoDAO = AgendaDatabase.getInstance(this).getRoomAlunoDAO();
        telefoneDAO = AgendaDatabase.getInstance(this).getRoomTelefoneDAO();
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
            preencheCamposTelefone();
            campoEmail.setText(aluno.getEmail());
        } else {
            setTitle(TITULO_NOVO_ALUNO_APPBAR);
            aluno = new Aluno();
        }
    }

    private void preencheCamposTelefone() {
        new BuscaTodosOsTelefonesDoAlunoTask(telefoneDAO, aluno, telefones -> {
            this.telefonesDoAluno = telefones;
            for (Telefone telefone :
                    telefonesDoAluno) {
                if(telefone.getTipo() == TipoTelefone.FIXO) {
                    campoTelefoneFixo.setText(telefone.getNumero());
                } else {
                    campoTelefoneCelular.setText(telefone.getNumero());
                }
            }
        }).execute();
    }

    private void finalizaFormulario() {
        preencheAluno();

        Telefone telefoneFixo = criaTelefone(campoTelefoneFixo, TipoTelefone.FIXO);
        Telefone telefoneCelular = criaTelefone(campoTelefoneCelular, TipoTelefone.CELULAR);

        if(aluno.temIdValido()) {
            editaAluno(telefoneFixo, telefoneCelular);
        } else {
            salvaAluno(telefoneFixo, telefoneCelular);
        }
    }

    private void salvaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new SalvaAlunoTask(alunoDAO, aluno, telefoneDAO,
                telefoneFixo, telefoneCelular, this::finish).execute();
    }

    private void editaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new EditaAlunoTask(alunoDAO, aluno, telefonesDoAluno,
                telefoneFixo, telefoneCelular, telefoneDAO, this::finish).execute();
    }

    private Telefone criaTelefone(EditText campoTelefone, TipoTelefone tipo) {
        String numero = campoTelefone.getText().toString();
        return new Telefone(numero, tipo);
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_aluno_telefone_fixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_aluno_telefone_celular);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setEmail(email);
    }
}

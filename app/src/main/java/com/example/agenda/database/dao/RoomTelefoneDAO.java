package com.example.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.agenda.models.Telefone;

import java.util.List;

@Dao
public interface RoomTelefoneDAO {
    @Query("SELECT * FROM Telefone " +
            "WHERE aluno_id = :alunoId LIMIT 1")
    Telefone retornaPrimeiroTelefone(int alunoId);

    @Insert
    void salva(Telefone... telefones);

    @Query("SELECT * FROM Telefone " +
            "WHERE aluno_id = :alunoId")
    List<Telefone> retornaTodosOsTelefones(int alunoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void atualiza(Telefone... telefones);
}

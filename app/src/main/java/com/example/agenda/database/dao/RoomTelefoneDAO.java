package com.example.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.agenda.models.Telefone;

@Dao
public interface RoomTelefoneDAO {
    @Query("SELECT * FROM Telefone " +
            "WHERE aluno_id = :alunoId LIMIT 1")
    Telefone retornaPrimeiroTelefone(int alunoId);

}

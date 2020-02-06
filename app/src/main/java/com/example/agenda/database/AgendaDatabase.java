package com.example.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.agenda.database.converter.ConversorCalendar;
import com.example.agenda.database.converter.ConversorTelefone;
import com.example.agenda.database.dao.RoomAlunoDAO;
import com.example.agenda.database.dao.RoomTelefoneDAO;
import com.example.agenda.models.Aluno;
import com.example.agenda.models.Telefone;

import static com.example.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTelefone.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String AGENDA_DB = "agenda.db";

    public abstract RoomAlunoDAO getRoomAlunoDAO();

    public abstract RoomTelefoneDAO getRoomTelefoneDAO();

    public static AgendaDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDatabase.class, AGENDA_DB)
                .allowMainThreadQueries()
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }
}

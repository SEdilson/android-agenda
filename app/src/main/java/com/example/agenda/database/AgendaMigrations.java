package com.example.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.agenda.models.TipoTelefone;

class AgendaMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN sobrenome TEXT");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefone` TEXT, " +
                    "`email` TEXT)");
            database.execSQL("INSERT INTO Aluno_novo (id, nome, telefone, email)" +
                    "SELECT id, nome, telefone, email FROM Aluno");
            database.execSQL("DROP TABLE Aluno");
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
        }
    };
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN momentoDoCadastro INTEGER");
        }
    };
    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefoneFixo` TEXT, " +
                    "`telefoneCelular` TEXT, " +
                    "`email` TEXT, " +
                    "`momentoDoCadastro` INTEGER)");
            database.execSQL("INSERT INTO Aluno_novo (id, nome, telefoneFixo, email, momentoDoCadastro)" +
                    "SELECT id, nome, telefone, email, momentoDoCadastro FROM Aluno");
            database.execSQL("DROP TABLE Aluno");
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
        }
    };
    private static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`email` TEXT, " +
                    "`momentoDoCadastro` INTEGER)");
            database.execSQL("INSERT INTO Aluno_novo (id, nome, email, momentoDoCadastro)" +
                    "SELECT id, nome, email, momentoDoCadastro FROM Aluno");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Telefone` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`numero` TEXT, " +
                    "`tipo` TEXT, " +
                    "`aluno_id` INTEGER NOT NULL)");
            database.execSQL("INSERT INTO Telefone (numero, aluno_id)" +
                    "SELECT telefoneFixo, id FROM Aluno");
            database.execSQL("UPDATE Telefone SET tipo = ?", new TipoTelefone[] {TipoTelefone.FIXO});
            database.execSQL("DROP TABLE Aluno");
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
        }
    };
    public static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6};
}

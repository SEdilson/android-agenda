package com.example.agenda.database.converter;

import androidx.room.TypeConverter;

import com.example.agenda.models.TipoTelefone;

public class ConversorTelefone {

    @TypeConverter
    public String paraString(TipoTelefone tipo) {
        return tipo.name();
    }

    @TypeConverter
    public TipoTelefone paraTipoTelefone(String valor) {
        if(valor != null) {
            return TipoTelefone.valueOf(valor);
        }
        return TipoTelefone.FIXO;
    }
}

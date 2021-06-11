package br.com.alura.ceep.database;

import br.com.alura.ceep.database.converter.ConverterColorEnum;
import br.com.alura.ceep.database.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * @author Cristian Urbainski
 * @since 1.0 (04/06/21)
 */
@Database(entities = {
        Nota.class
}, exportSchema = false, version = 1)
@TypeConverters({
        ConverterColorEnum.class
})
public abstract class CeepDatabase extends RoomDatabase {

    private final static String DATABASE_NAME = "ceep-database";
    private static CeepDatabase INSTANCE;

    public static synchronized CeepDatabase getInstance(@NonNull Application application) {

        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(application, CeepDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    public static CeepDatabase getInstance() {

        if (INSTANCE == null) {

            throw new IllegalStateException("Database not created!");
        }

        return INSTANCE;
    }

    public abstract NotaDAO notaDAO();
}
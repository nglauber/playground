package ngvl.android.exrecyclerview.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "dbNotas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ MensagemContract.TABELA_MENSAGEM +" ("+
                MensagemContract._ID +" INTEGER NOT NULL PRIMARY KEY, " +
                MensagemContract.TITULO +" TEXT NOT NULL, " +
                MensagemContract.DESCRICAO +" TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

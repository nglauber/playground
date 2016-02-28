package ngvl.android.exrecyclerview.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MensagemProvider extends ContentProvider {

    public static final int MENSAGENS = 1;
    public static final int MENSAGENS_POR_ID = 2;

    UriMatcher mUriMatcher;
    DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(MensagemContract.AUTHORITY, "msgs", MENSAGENS);
        mUriMatcher.addURI(MensagemContract.AUTHORITY, "msgs/#", MENSAGENS_POR_ID);

        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Não implementada.");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (mUriMatcher.match(uri) == MENSAGENS){
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            long id = db.insert(MensagemContract.TABELA_MENSAGEM, null, values);
            Uri insertUri = Uri.withAppendedPath(MensagemContract.BASE_URI, String.valueOf(id));
            db.close();
            notifyChanges(uri);
            return insertUri;
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (mUriMatcher.match(uri) == MENSAGENS_POR_ID){
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            int linhasAfetadas = db.delete(MensagemContract.TABELA_MENSAGEM,
                    MensagemContract._ID +" = ?",
                    new String[]{ uri.getLastPathSegment() });
            db.close();
            notifyChanges(uri);
            return linhasAfetadas;

        } else {
            throw new UnsupportedOperationException("Uri inválida para exclusão.");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (mUriMatcher.match(uri) == MENSAGENS_POR_ID){
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            int linhasAfetadas = db.update(MensagemContract.TABELA_MENSAGEM,
                    values,
                    MensagemContract._ID +" = ?",
                    new String[]{ uri.getLastPathSegment() });
            db.close();
            notifyChanges(uri);
            return linhasAfetadas;

        } else {
            throw new UnsupportedOperationException("Uri inválida para exclusão.");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (mUriMatcher.match(uri) == MENSAGENS){
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            Cursor cursor = db.query(MensagemContract.TABELA_MENSAGEM,
                    projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;

        } else if (mUriMatcher.match(uri) == MENSAGENS_POR_ID) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            Cursor cursor = db.query(MensagemContract.TABELA_MENSAGEM,
                    projection,
                    MensagemContract._ID +" = ?",
                    new String[]{ uri.getLastPathSegment() }, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;

        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    private void notifyChanges(Uri uri){
        if (getContext() != null && getContext().getContentResolver() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}

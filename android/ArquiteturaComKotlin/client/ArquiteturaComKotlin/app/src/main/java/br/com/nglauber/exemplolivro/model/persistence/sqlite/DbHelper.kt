package br.com.nglauber.exemplolivro.model.persistence.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.nglauber.exemplolivro.App
import org.jetbrains.anko.db.*

class DbHelper(var ctx : Context = App.instance) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(PostTable.TABLE_NAME, true,
                PostTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                PostTable.TEXT to TEXT,
                PostTable.DATE to INTEGER,
                PostTable.PHOTO_URL to TEXT,
                PostTable.LATITUDE to REAL,
                PostTable.LONGITUDE to REAL);
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        @JvmField
        val DB_VERSION = 1
        @JvmField
        val DB_NAME = "dbPosts"
    }
}
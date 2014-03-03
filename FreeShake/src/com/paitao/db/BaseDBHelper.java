package com.paitao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDBHelper extends SQLiteOpenHelper {
	private Class<?>[] modelClasses;

	public BaseDBHelper(Context context, String databaseName,
			SQLiteDatabase.CursorFactory factory, int databaseVersion,
			Class<?>[] modelClasses) {
		super(context, databaseName, factory, databaseVersion);
		this.modelClasses = modelClasses;
	}
 
	public void onCreate(SQLiteDatabase db) {
		TableUtils.createTablesByClasses(db, this.modelClasses);
	}
    
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TableUtils.dropTablesByClasses(db, this.modelClasses);
		onCreate(db);
	}
}
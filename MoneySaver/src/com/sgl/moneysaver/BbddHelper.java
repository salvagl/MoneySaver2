package com.sgl.moneysaver;

import java.sql.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BbddHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MoneySaver.db";
    
	public BbddHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("CREATE TABLE TIPO ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT ," +
                "created_at DATETIME)");
		
		db.execSQL("CREATE TABLE CATEGORIA ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT, " +
                "nombre_en TEXT, "+
                "created_at datetime)");
		
		db.execSQL("CREATE TABLE CONCEPTO ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT, "+
                "periodicidad TEXT in ('mensual', 'bimensual', 'trimestral','semestral','anual'), "+
                "id_categoria INTEGER, "+
                "id_tipo INTEGER, "+
                "valoracion INTEGER,"+
                "fecha DATE, "+
                "nombre_en TEXT,"+
                "created_at DATETIME," +
                "FOREIGN KEY(id_categoria) REFERENCES CATEGORIA(_id)," +
                "FOREIGN KEY(id_tipo) REFERENCES TIPO(_id))");
		
		db.execSQL("INSERT into TIPO values (null, 'INGRESO',null)");
		db.execSQL("INSERT into TIPO values (null, 'GASTO',null)");
		db.execSQL("INSERT into CATEGORIA values (null, 'HOGAR',null,null)");
		db.execSQL("INSERT into CATEGORIA values (null, 'TRABAJO',null,null)");
		db.execSQL("INSERT into CATEGORIA values (null, 'FINANZAS',null,null)");
		db.execSQL("INSERT into CATEGORIA values (null, 'OCIO',null,null)");
		db.execSQL("INSERT into CATEGORIA values (null, 'TRANSPORTE',null,null)");
		db.execSQL("INSERT into CATEGORIA values (null, 'Hipoteca','mensual',0,1,5,null,'Hipoteca',null)");
		db.execSQL("INSERT into CATEGORIA values (null, 'Nomina','mensual',1,0,5,null,'Hipoteca',null)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

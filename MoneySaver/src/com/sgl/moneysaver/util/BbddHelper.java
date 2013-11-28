package com.sgl.moneysaver.util;

import java.sql.Date;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

public class BbddHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MoneySaver.db";
    public static final int INPUT=1;
    public static final int OUTPUT=2;
    private Context mCtx = null;
    private SQLiteDatabase mDb=null;
    
    private static final String CREATE_TABLE_TIPO = 
    		"CREATE TABLE TIPO ("+
		            "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
		            "nombre TEXT ," +
		            "nombre_en TEXT,"+
		            "created_at DATETIME)";
    
    private static final String CREATE_TABLE_CATEGORIA = 
    		"CREATE TABLE CATEGORIA ("+
		            "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
		            "nombre TEXT, " +
		            "nombre_en TEXT, "+
		            "created_at datetime)";
    
    private static final String CREATE_TABLE_CONCEPTOS=
    		"CREATE TABLE CONCEPTO ("+
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "nombre TEXT, "+
                    "importe DECIMAL(8,2),"+
                    "periodicidad TEXT, "+
                    "id_categoria INTEGER, "+
                    "id_tipo INTEGER, "+
                    "valoracion INTEGER,"+
                    "fecha DATE, "+
                    "nombre_en TEXT,"+
                    "created_at DATETIME," +
                    "activado boolean,"+
                    "FOREIGN KEY(id_categoria) REFERENCES CATEGORIA(_id)," +
                    "FOREIGN KEY(id_tipo) REFERENCES TIPO(_id))";
    
    private static final String DROP_TABLE_TIPO= 		"DROP TABLE IF EXISTS TIPO";
    private static final String DROP_TABLE_CATEGORIA= 	"DROP TABLE IF EXISTS CATEGORIA";
    private static final String DROP_TABLE_CONCEPTO=	"DROP TABLE IF EXISTS CONCEPTO";
    
	public BbddHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.mCtx=context;
	}

	public void open() throws SQLException{
		this.mDb = this.getWritableDatabase();
	}
	
	public void close(){
		
		this.mDb.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.mDb=db;
		this.createTables(db);
		this.insertDefaultData();
		
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		dropTables(db);
		createTables(db);
		
	}
	
	private void createTables(SQLiteDatabase db){
		db.execSQL(CREATE_TABLE_TIPO);
		db.execSQL(CREATE_TABLE_CATEGORIA);
		db.execSQL(CREATE_TABLE_CONCEPTOS);
		
	}
	
	private void dropTables (SQLiteDatabase db){
		db.execSQL(DROP_TABLE_CONCEPTO);
		db.execSQL(DROP_TABLE_CATEGORIA);
		db.execSQL(DROP_TABLE_TIPO);
	}
	
	public void insertDefaultData(){
		Time now = new Time();
		now.setToNow();
		
		insertTipo("INGRESO", "INGRESO", now);
		insertTipo("GASTO", "GASTO", now);
		
		insertCategoria("HOGAR", "HOGAR", now);
		insertCategoria("TRABAJO", "TRABAJO", now);
		insertCategoria("FINANZAS", "FINANZAS", now);
		insertCategoria("OCIO", "OCIO", now);
		insertCategoria("TRANSPORTE", "TRANSPORTE", now);
				
		insertConcepto("Hipoteca",590.32f, "1m", 1, OUTPUT, 5, now, "Hipoteca_en", now, true);
		insertConcepto("Recibo Luz",180.50f, "1m", 1, OUTPUT, 4, now, "luz_en", now, true);
		insertConcepto("Recibo Agua",30.00f, "1m", 1, OUTPUT, 4, now, "agua_en", now, true);
		insertConcepto("Recibo Internet",30.75f, "1m", 1, OUTPUT, 4, now, "Internet_en", now, true);
		
		insertConcepto("Hipoteca2",590.32f, "1m", 2, OUTPUT, 5, now, "Hipoteca_en", now, true);
		insertConcepto("Recibo Luz3",180.50f, "1m", 3, OUTPUT, 4, now, "luz_en", now, true);
		insertConcepto("Recibo Agua3",30.00f, "1m", 3, OUTPUT, 4, now, "agua_en", now, true);
		insertConcepto("Recibo Internet3",30.75f, "1m", 3, OUTPUT, 4, now, "Internet_en", now, true);
		
		insertConcepto("Recibo Internet4",30.75f, "1m", 4, OUTPUT, 4, now, "Internet_en", now, true);
		
		
		insertConcepto("Nomina",1000.50f, "1m", 1, INPUT, 5, now, "Nomina_en", now, true);
		
		
	}
	public long insertConcepto(String nombre,float importe,String periodicidad,int idCategoria, int idTipo, int valoracion, Time fecha,String nombreEn,Time createdAt,Boolean activado){
		
		ContentValues initialValues = new ContentValues();
		initialValues.put("nombre",nombre);
		initialValues.put("importe",importe);
		initialValues.put("periodicidad",periodicidad);
		initialValues.put("id_categoria",idCategoria);
		initialValues.put("id_tipo",idTipo);
		initialValues.put("valoracion",valoracion);
		initialValues.put("fecha",fecha.toString());
		initialValues.put("nombre_en",nombreEn);
		initialValues.put("created_at",createdAt.toString());
		initialValues.put("activado",activado);
		return mDb.insert("CONCEPTO", null, initialValues);
	};
	
	public long insertTipo(String nombre,String nombreEn,Time createdAt){
		ContentValues initialValues = new ContentValues();
		initialValues.put("nombre",nombre);
		initialValues.put("nombre_en",nombreEn);
		initialValues.put("created_at",createdAt.toString());
		//initialValues.put("created_at","2013/11/11 10:10:10");
		return mDb.insert("TIPO", null, initialValues);
	}
	
	public long insertCategoria(String nombre,String nombreEn,Time createdAt){
		ContentValues initialValues = new ContentValues();
		initialValues.put("nombre",nombre);
		initialValues.put("nombre_en",nombreEn);
		initialValues.put("created_at",createdAt.toString());
		//initialValues.put("created_at","2013/11/11 10:10:10");
		return mDb.insert("CATEGORIA", null, initialValues);
	}	
	
	public Cursor getAllInputs(){
		return mDb.query("CONCEPTO", new String[] {"_id","nombre","importe","periodicidad","id_categoria","id_tipo","valoracion","fecha","nombre_en","created_at","activado"},"id_tipo="+INPUT , null, null, null, "id_categoria");
	}	
	
	public Cursor getAllOutputs(){
		return mDb.query("CONCEPTO", new String[] {"_id","nombre","importe","periodicidad","id_categoria","id_tipo","valoracion","fecha","nombre_en","created_at","activado"},"id_tipo="+OUTPUT , null, null, null, "id_categoria");
	}
	
	public Cursor getCategoriaById(int id_categoria){
		return mDb.query("CATEGORIA", new String[] {"_id","nombre","nombre_en","created_at"},"id_=?" , new String[]{""+id_categoria}, null, null, null);
	}	
	

}

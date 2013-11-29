package com.sgl.moneysaver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sgl.moneysaver.util.BbddHelper;
import com.sgl.moneysaver.util.SeparatedListAdapter;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class InputActivity extends ListActivity {

	public final static String ITEM_TITLE = "title";  
    public final static String ITEM_CAPTION = "caption"; 
    
    
    public final static String CONCEPTO_NOMBRE = "nombre";
    public final static String CONCEPTO_IMPORTE = "importe";
    public final static String CONCEPTO_PERIODICIDAD = "periodicidad";
    public final static String CONCEPTO_CATEGORIA = "id_categoria";
    public final static String CONCEPTO_TIPO = "id_tipo";
    public final static String CONCEPTO_VALORACION = "valoracion";
    public final static String CONCEPTO_FECHA = "fecha";
    public final static String CONCEPTO_NOMBRE_EN = "nombre_en";
    public final static String CONCEPTO_ACTIVADO = "activado";
    public final static String CONCEPTO_CREATED_AT = "created_at";
    
    
    BbddHelper bbddHelper = null;	

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	//Conectamos y abrimos la BBDD
	bbddHelper = new BbddHelper(this);
	bbddHelper.open();
		
/*	this.setContentView(R.layout.activity_input);
	
	//Conectamos y abrimos la BBDD
	bbddHelper = new BbddHelper(this);
	bbddHelper.open();
	fillData();
	
	
	String txtTitulo="";
	
	Intent i=this.getIntent();
	
	if (savedInstanceState != null) {}
	
	if(i!=null){
		
		txtTitulo = i.getStringExtra("title");
		
	}
	
	
	TextView titulo = (TextView) this.findViewById(R.id.titulo);
	titulo.setText(txtTitulo);
	*/
	//******************************************************************************
	
	Cursor conceptosCursor = null;
	
	conceptosCursor = bbddHelper.getAllOutputs();
	
	startManagingCursor(conceptosCursor);
	
	String[] from = new String[]{"nombre","importe","periodicidad"};
	int[] to   = new int[]{R.id.listActivado,R.id.listImporte,R.id.listPeriodicidad};
	
	InputAdapter ia = new InputAdapter(this,R.layout.list_row,conceptosCursor,from,to);
	
	//Empiezo el recorrido por el cursor desmembrando sus categorias:
	conceptosCursor.moveToFirst();
	String a,b,c=null;
	int d=0; //id_categoria
	int ultimaCategoria=0;
	SeparatedListAdapter adapter2 = new SeparatedListAdapter(this);  
	List<Map<String,?>> seccion = new LinkedList<Map<String,?>>(); 
	do{
		
		a=conceptosCursor.getString(1); //nombre
		b=conceptosCursor.getString(2); //importe
		c=conceptosCursor.getString(3); //periodicidad
		d=conceptosCursor.getInt(4); //idCategoria
		
		if(ultimaCategoria==0 || ultimaCategoria==d){
			
			
			ultimaCategoria=d;
			//listado.add(createItem(a,b+c+d));
			seccion.add(createConceptoFromCursor(conceptosCursor));
			
		}else{
			//hay que recuperar el "nombre" de la categoria AQUI
			adapter2.addSection("CATEGORIA"+ultimaCategoria, new ListConceptosAdapter(this, seccion, R.layout.list_row,   
					new String[] { CONCEPTO_ACTIVADO, CONCEPTO_IMPORTE,CONCEPTO_VALORACION,CONCEPTO_FECHA,CONCEPTO_PERIODICIDAD }, new int[] { R.id.listActivado,R.id.listImporte,R.id.listValoracion,R.id.listFecha,R.id.listPeriodicidad }));
			
			/*adapter2.addSection("CATEGORIA"+ultimaCategoria, new SimpleAdapter(this, listado, R.layout.list_complex,   
			        new String[] { ITEM_TITLE, ITEM_CAPTION }, new int[] { R.id.list_complex_title, R.id.list_complex_caption }));*/
			
			//reseteamos el listado para empezar nueva seccion:
			seccion= new LinkedList<Map<String,?>>();
			ultimaCategoria=d;
			seccion.add(createConceptoFromCursor(conceptosCursor));
			//listado.add(createItem(a,b+c+d));
		}
		
	}while(conceptosCursor.moveToNext());
	
	//La última sección se debe montar fuera del bucle cuando termina de procesarse el cursor 
	//hay que recuperar el "nombre" de la categoria AQUI 
	adapter2.addSection("CATEGORIA"+ultimaCategoria, new ListConceptosAdapter(this, seccion, R.layout.list_row,   
	        new String[] { CONCEPTO_ACTIVADO, CONCEPTO_IMPORTE,CONCEPTO_VALORACION,CONCEPTO_FECHA,CONCEPTO_PERIODICIDAD }, new int[] { R.id.listActivado,R.id.listImporte,R.id.listValoracion,R.id.listFecha,R.id.listPeriodicidad }));
	
      
    ListView list = new ListView(this);  
    list.setId(android.R.id.list);
    list.setAdapter(adapter2);  
    this.setContentView(list); 
    //******************************************************************************
}

private void fillData(){
	Cursor conceptosCursor = null;
	
	conceptosCursor = bbddHelper.getAllInputs();
	
	startManagingCursor(conceptosCursor);
	
	String[] from = new String[]{"nombre","importe","periodicidad"};
	int[] to   = new int[]{R.id.listActivado,R.id.listImporte,R.id.listValoracion,R.id.listFecha,R.id.listPeriodicidad};
	
	InputAdapter ia = new InputAdapter(this,R.layout.list_row,conceptosCursor,from,to);
	
	setListAdapter(ia);
	
}

public Map<String,?> createItem(String title, String caption) {  
    Map<String,String> item = new HashMap<String,String>();  
    item.put(ITEM_TITLE, title);  
    item.put(ITEM_CAPTION, caption);  
    return item;  
}  


public Map<String,?> createConceptoFromCursor(Cursor cursor) {  
    Map<String,String> concepto = new HashMap<String,String>();  
        
    concepto.put(CONCEPTO_NOMBRE, cursor.getString(1));  //nombre 
    concepto.put(CONCEPTO_IMPORTE, cursor.getString(2));  //importe
    concepto.put(CONCEPTO_PERIODICIDAD, cursor.getString(3));  //periodicidad
    concepto.put(CONCEPTO_CATEGORIA, cursor.getString(4));  //categoria
    concepto.put(CONCEPTO_TIPO, cursor.getString(5));  //tipo
    concepto.put(CONCEPTO_VALORACION, cursor.getString(6));  //valoracion
    concepto.put(CONCEPTO_FECHA, cursor.getString(7));  //fecha
    concepto.put(CONCEPTO_NOMBRE_EN, cursor.getString(8));  //nombre_en
    concepto.put(CONCEPTO_ACTIVADO, cursor.getString(9));  //activado
    concepto.put(CONCEPTO_CREATED_AT, cursor.getString(10));  //created_at
       
    return concepto;
}




private class ListConceptosAdapter extends SimpleAdapter{
	
	private List<? extends Map<String, ?>> listConceptos =null;
	private LayoutInflater mInflater;
	private int layout =0;
	
	
	public ListConceptosAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		
		this.listConceptos = data;
		this.layout=resource;
		mInflater=LayoutInflater.from(context);
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Map<String,String> concepto = (Map<String, String>) listConceptos.get(position);
		
		View row = mInflater.inflate(this.layout,null);
		
		CheckBox cbNombre = (CheckBox)row.findViewById(R.id.listActivado);
		TextView tvImporte = (TextView)row.findViewById(R.id.listImporte);
		TextView tvPeriodicidad = (TextView)row.findViewById(R.id.listPeriodicidad);
		RatingBar rbValoracion = (RatingBar)row.findViewById(R.id.listValoracion);
		TextView tvFecha = (TextView)row.findViewById(R.id.listFecha);
		
		float numStar = Float.parseFloat(concepto.get(CONCEPTO_VALORACION));
		
		cbNombre.setText(concepto.get(CONCEPTO_NOMBRE));
		tvImporte.setText(concepto.get(CONCEPTO_IMPORTE));
		rbValoracion.setRating(numStar);
		tvPeriodicidad.setText(concepto.get(CONCEPTO_PERIODICIDAD));
		tvFecha.setText(concepto.get(CONCEPTO_FECHA));
		
		return row;
	}

	  
	
	
}


private class InputAdapter extends SimpleCursorAdapter{

	private LayoutInflater mInflater;
	private Cursor cursor;
	
	public InputAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to) {
		super(context, layout, c, from, to);
		// TODO Auto-generated constructor stub
		
		cursor=c;
		cursor.moveToFirst();
		mInflater=LayoutInflater.from(context);
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
	
		if(cursor.getPosition()<0){
			cursor.moveToFirst();
		}else{
			cursor.moveToPosition(position);
		}
		View row = mInflater.inflate(R.layout.list_row,null);
		
		TextView tvNombre = (TextView)row.findViewById(R.id.listActivado);
		TextView tvImporte = (TextView)row.findViewById(R.id.listImporte);
		TextView tvPeriodicidad = (TextView)row.findViewById(R.id.listPeriodicidad);
		
		tvNombre.setText(cursor.getString(1));
		tvImporte.setText(cursor.getString(2));
		tvPeriodicidad.setText(cursor.getString(3));
		
		return row;
		
	}
	
	
}
@Override
	protected void onDestroy() {
		bbddHelper.close();
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

}

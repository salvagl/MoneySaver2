package com.sgl.moneysaver;

import com.sgl.moneysaver.util.BbddHelper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class InputActivity extends ListActivity {
	
BbddHelper bbddHelper = null;	

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	this.setContentView(R.layout.activity_input);
	
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
	
	
	
}

private void fillData(){
	Cursor conceptosCursor = null;
	
	conceptosCursor = bbddHelper.getAllInputs();
	
	startManagingCursor(conceptosCursor);
	
	String[] from = new String[]{"nombre","importe","periodicidad"};
	int[] to   = new int[]{R.id.listNombre,R.id.listImporte,R.id.listPeriodicidad};
	
	InputAdapter ia = new InputAdapter(this,R.layout.list_row,conceptosCursor,from,to);
	
	setListAdapter(ia);
	
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
		
		TextView tvNombre = (TextView)row.findViewById(R.id.listNombre);
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

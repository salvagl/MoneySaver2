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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class InputActivity extends ListActivity {

	public final static String ITEM_TITLE = "title";  
    public final static String ITEM_CAPTION = "caption"; 
    
    BbddHelper bbddHelper = null;	

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
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
	List<Map<String,?>> security = new LinkedList<Map<String,?>>();  
    security.add(createItem("Remember passwords", "Save usernames and passwords for Web sites"));  
    security.add(createItem("Clear passwords", "Save usernames and passwords for Web sites"));  
    security.add(createItem("Show security warnings", "Show warning if there is a problem with a site's security"));  
      
    // create our list and custom adapter  
    SeparatedListAdapter adapter = new SeparatedListAdapter(this);  
    adapter.addSection("Array test", new ArrayAdapter<String>(this,  
        R.layout.list_item, new String[] { "First item", "Item two" }));  
    adapter.addSection("Security", new SimpleAdapter(this, security, R.layout.list_complex,   
        new String[] { ITEM_TITLE, ITEM_CAPTION }, new int[] { R.id.list_complex_title, R.id.list_complex_caption }));  
      
    ListView list = new ListView(this);  
    list.setId(android.R.id.list);
    list.setAdapter(adapter);  
    this.setContentView(list); 
    //******************************************************************************
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

public Map<String,?> createItem(String title, String caption) {  
    Map<String,String> item = new HashMap<String,String>();  
    item.put(ITEM_TITLE, title);  
    item.put(ITEM_CAPTION, caption);  
    return item;  
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

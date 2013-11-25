package com.sgl.moneysaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InputActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	this.setContentView(R.layout.activity_input);
	String txtTitulo="";
	
	Intent i=this.getIntent();
	
	if (savedInstanceState != null) {}
	
	if(i!=null){
		
		txtTitulo = i.getStringExtra("title");
		
	}
	
	
	TextView titulo = (TextView) this.findViewById(R.id.titulo);
	titulo.setText(txtTitulo);
	
	
	
}
}

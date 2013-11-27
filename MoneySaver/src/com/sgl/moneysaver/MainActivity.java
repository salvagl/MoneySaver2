package com.sgl.moneysaver;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.sgl.moneysaver.InputActivity;
import com.sgl.moneysaver.util.BbddHelper;

public class MainActivity extends Activity {

	private static final int ACTIVITY_INPUT = 1;
	private static final int ACTIVITY_OUTPUT = 2;
	private static final int ACTIVITY_CONFIG = 3;
	private static final int ACTIVITY_CALENDAR = 4;
	
	BbddHelper bdHelper = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        bdHelper  = new BbddHelper(this.getApplicationContext());
        
        //Obtenemos referencias a los 4 botones de la Activity principal
        ImageButton btInput = (ImageButton) this.findViewById(R.id.bt_input);
        ImageButton btOutput = (ImageButton) this.findViewById(R.id.bt_output);
        ImageButton btConfig = (ImageButton) this.findViewById(R.id.bt_config);
        ImageButton btCalendar = (ImageButton) this.findViewById(R.id.bt_calendar);
        
        
        //Asignamos el evento al evento on-clic de cada botón.
        btInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(),InputActivity.class);
				i.putExtra("title",v.getResources().getString(R.string.txt_input));
				startActivityForResult(i,ACTIVITY_INPUT );
				
			}
		});
        
 
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

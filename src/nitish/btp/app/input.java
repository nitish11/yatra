package nitish.btp.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class input extends Activity {
	
	
	int[] value= new int[100];   
	String[] items;
	int [][]time_id = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30,},{31,32,33,34,35}};
	String day[] = {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
    String time[] = {"7:00-8:00","8:00-9:00","9:00-10:00","10:00-11:00","11:00-12:00"};
	
    int i,sr,dest,tim,din,count=0;
   
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		setContentView(R.layout.input);
	
		    Button btpath = (Button) findViewById(R.id.button1);
			final Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
			final Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
			final Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
			final Spinner spin4 = (Spinner) findViewById(R.id.spinner4);
		
			
			try
	        {
				//URL place_info = new URL("http://iiitm.in/tests/BTP/place_info.txt");
				URL place_info = new URL("http://10.0.2.2/btp/place_info.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(place_info.openStream()));
				
	                while((br.readLine())!= null)
	                {
	                    count++;
	                } 
	                br.close();
	            
	            items=new String[count];
	            
	            BufferedReader brsecond = new BufferedReader(new InputStreamReader(place_info.openStream()));
				
                i=0;
                String str2 = null;
                while((str2 = brsecond.readLine())!= null)
                {
                    String[] num = str2.split(" ");
                    value[i] = Integer.parseInt(num[0]);
                    items[i] = num[1];
                    i++;
                } 
                brsecond.close();
	   	
	        
			ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,items);
			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin1.setAdapter(aa); 
			spin2.setAdapter(aa);
			
			
			ArrayAdapter<?> bb = new ArrayAdapter<Object>(this, android.R.layout.simple_spinner_item,day);
			bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin3.setAdapter(bb);
			
			ArrayAdapter<?> cc = new ArrayAdapter<Object>(this, android.R.layout.simple_spinner_item,time);
			cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin4.setAdapter(cc);
			
		
			btpath.setOnClickListener(new View.OnClickListener() {
				
				
				public void onClick(View v) {
					
					Intent  result = new Intent("nitish.btp.app.OUTPUT");
					Bundle bundle = new Bundle();
					sr = spin1.getSelectedItemPosition();
			        dest = spin2.getSelectedItemPosition();
			        din = spin3.getSelectedItemPosition();
			        tim = spin4.getSelectedItemPosition();

			        int tid = time_id[din][tim];
			        
			        bundle.putInt("source", sr+1);
			        bundle.putInt("destination", dest+1);
			        bundle.putInt("time_id", tid);
			        bundle.putInt("nodes", count+1);
			        bundle.putStringArray("places", items);
					result.putExtras(bundle);
					startActivity(result);
				}
			}); 
	        }
			catch (Exception e) 
			{
				Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG);
				Log.e("ERROR",e.toString());
			}
	}
}
		
	
	
	



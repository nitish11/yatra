package nitish.btp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppbtpActivity extends Activity {
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btapp = (Button) findViewById(R.id.btapp);
    	btapp.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
						startActivity(new Intent("nitish.btp.app.INPUT"));
			}
		}); 
		
					
    }
}
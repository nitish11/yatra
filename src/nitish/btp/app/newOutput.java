package nitish.btp.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

public class newOutput  extends Activity{
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
	        setContentView(R.layout.output);
	        
	        EditText path = (EditText) findViewById(R.id.editText1);
	        Bundle bundle = getIntent().getExtras();
			
			int source = bundle.getInt("source");
			int destination = bundle.getInt("destination");
			int time_id = bundle.getInt("time_id");
			String places[]= bundle.getStringArray("places");
			int num_nodes  = bundle.getInt("nodes");
	
			  
			 int num_timeid = 36;
			 int total_distance =0;
			 float total_time= (float) 0.0;
	         
			 int[][] road_id = new int[num_nodes][num_nodes];
	         float[][] road_cost = new float[num_nodes][num_nodes];
			 int[][] road_flag = new int[num_nodes][num_nodes];
			 int[][] road_distance = new int[num_nodes][num_nodes];
			 int[][] road_medium = new int[num_nodes][num_nodes];
			 //float[][] road_delay = new float[num_roads][num_timeid];
			 float[][] road_delay = new float[100][100];

	         int i,x,y,id,distance;
	         float cost;
	         float avgspeed=(float) 0.5;
	                 
	         String str;
	         
			 try
	         {
	             //fetches the delay data from the server
				 //URL input_delay = new URL("http://iiitm.in/tests/BTP/delay_input.txt");
				 URL input_delay = new URL("http://10.0.2.2/btp/delay_input.txt");
				 BufferedReader in = new BufferedReader(new InputStreamReader(input_delay.openStream()));
				 i=1;
	             while((str = in.readLine())!= null)
	             {
	                 String[] numdelay = str.split(" ");
					 for(int r=0; r<num_timeid; r++)
					 {
						road_delay[i][r+1] = Float.parseFloat(numdelay[r]); 
					 }
	 				 i++;
	             }
	             in.close();
	         }
	         catch (IOException e)
	         {
			     System.err.println("Error in Reading: 11 *" + e.getMessage());
		     }
			 
			 
			 try
	         {
			      //fetches the road map information from server
				 //URL input_area = new URL("http://iiitm.in/tests/BTP/road_map.txt");
				 URL input_area = new URL("http://10.0.2.2/btp/road_map.txt");
				 BufferedReader in = new BufferedReader(new InputStreamReader(input_area.openStream()));
	             for(int k=0;k<num_nodes;k++ )
	             {
	                 for(int p=0;p<num_nodes;p++)
	                 {
	                     if(k==p)
	                     {
	                         road_flag[k][p]=0;
	                         road_cost[k][p]=0;
	                         continue;
	                     }
	                     road_flag[k][p]=-1;
	                     road_cost[k][p]=100000;
			             road_medium[k][p]=0;
	                 }

	             }


	             while((str = in.readLine())!= null)
	             {
	                 String[] num = str.split(" ");
	                 id = Integer.parseInt(num[0]);
	                 x = Integer.parseInt(num[1]);
	                 y = Integer.parseInt(num[2]);
	                 distance = Integer.parseInt(num[3]);
	                 cost = road_delay[id][time_id];
	                 road_id[x][y] = id;
	                 road_distance[x][y] = distance; 
	                 road_cost[x][y] = (float)Math.round((cost+(distance/avgspeed))*100.0)/100.0f;
	                 road_flag[x][y] = 1;
	             }
	             in.close();
	         }
	         catch (IOException  e)
	         {
			     System.err.println("Error in Reading: 12 *" + e.getMessage());
		     }
			 
			 
			 
	         for(int vk=1;vk<num_nodes;vk++)
	         {
	             for(int vi=1;vi<num_nodes;vi++)
	             {
	                 for(int vj=1;vj<num_nodes;vj++)
	                 {
	                     if(road_cost[vi][vj]>road_cost[vi][vk]+road_cost[vk][vj])
						     {
							     road_cost[vi][vj]=road_cost[vi][vk]+road_cost[vk][vj];
								 road_medium[vi][vj]=vk;
							 }
	                 }
	             }
	         }
		    
		    int snode= source;
			int dnode = destination;	
			int med = road_medium[snode][dnode];	
			
		 try
	     {
			int lt;	
			String stpath ="";
			while(med>0)
			{
				stpath += (places[dnode-1]+" (");
				if(road_cost[med][dnode]>=1000)
				stpath += " NO PATH EXISTS ";
				else
				{
					stpath += (road_cost[med][dnode]+".min)("+road_distance[med][dnode]+"km) ");
					total_distance +=road_distance[med][dnode];
					total_time += road_cost[med][dnode];
				}
				dnode = med; 
				if(road_medium[snode][dnode]==0) break;
				med=road_medium[snode][dnode];	
			}
			stpath += (places[dnode-1]+" (");
			
			if(road_cost[snode][dnode]>=1000)
				stpath += "NO-PATH-EXISTS) ";
			else
				{
				stpath += (road_cost[snode][dnode]+".min)("+road_distance[snode][dnode]+"km) ");
				total_distance +=road_distance[snode][dnode];
				total_time += road_cost[snode][dnode];
				}
			
			stpath += places[snode-1]; 
			stpath += (" Total-time="+total_time+".min"+" Total-distance="+total_distance+"km ");
			
			String[] spath = stpath.split(" ");
			lt = spath.length;
			while(lt>0)
			{
				lt--;
				path.append(spath[lt]+"\n");
			}
	      }
	      
		  catch (Exception e)
	      {
			     System.err.println("Error in Writing: 13 *" + e.getMessage());
		  }
		

	        
	    }
	

}

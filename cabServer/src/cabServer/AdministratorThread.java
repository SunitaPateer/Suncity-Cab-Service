package cabServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.Timer;







public class AdministratorThread {
    private Socket          socket   = null;
   private Administrator      server   = null;
   private int             ID       = -1;
   private DataInputStream streamIn =  null;
   private DataOutputStream streamout = null;
           String msg;
           int n = 10 ;
           
           String mbno,nme, src,destination ,type,prsn ;
           int[][] path ;
           int[][] shortpath ;
           int[][] m ;
            
          
           String[] str = new String [6];
           int [] minarr = new int[15];
           int des;
           int start;
           int speed = 10;
           
     public void setm(){
       try{
           BufferedReader br = null;
			String strLine;
 
			br = new BufferedReader(new FileReader("Adjacency.txt"));
 
			//while ((sCurrentLine = br.readLine()) != null) {
			//	System.out.println(sCurrentLine);
			//}
 
		
   n=Integer.parseInt(br.readLine());
   m = new int[n][n];
  //Read File Line By Line
   int i=0;
    StringTokenizer s;
     
  while ((strLine = br.readLine()) != null)   {
  // Print the content on the console
   s = new StringTokenizer(strLine," ");
   int j=0;
      while(s.hasMoreTokens())
      {
          m[i][j]=Integer.parseInt(s.nextToken());
          j++;  
      }
       i++;   
  }
  //Close the input stream
  br.close();
    }catch (Exception e){//Catch exception if any
  System.err.println("Error: " + e.getMessage());
  }
      
  }
 
           
   public AdministratorThread(Administrator _server, Socket _socket)
   {  server = _server;  socket = _socket;  ID = socket.getPort();
   }
   public void run() throws IOException
   {  System.out.println("Server Thread " + ID + " running.");
       
    }
   public void open() throws IOException
   {  streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      streamout = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
   }
   public void close() throws IOException
   {  if (socket != null)    socket.close();
      if (streamIn != null)  streamIn.close();
   }

    void start() throws IOException {
           BufferedWriter output = null;
       try{
            output= new BufferedWriter(new FileWriter("cabServiceRecord.txt", true));
           }
       catch(IOException ioe){
       ioe.printStackTrace();
         }
        msg=streamIn.readUTF();
       System.out.println(msg);
      output.newLine();
      output.append(msg);
      output.close();
      setm();
          int l=0;
         StringTokenizer st = new StringTokenizer(msg,"\t");
     while (st.hasMoreTokens()) {
         str[l]=st.nextToken();
         l=l+1;
        
     }
     
     nme=str[0];
     mbno=str[1];
     src=str[2];
     destination=str[3];
     type=str[4];
     prsn=str[5];
     des=(int)(destination.charAt(0))-(int)('A');
            start=(int)(src.charAt(0))-(int)('A');
            
           for(int i=0; i<6; i++){
           System.out.println(str[i]);
           }
          // System.out.println(start+" "+des);
           
     findPath();
     findCab();
    }
    
    void findPath(){
        
    path=new int[n][n];
    shortpath=new int[n][n];
     
     String myPath = des + "";
     
    for (int i=0; i<m.length; i++)
        {
            for (int j=0; j<m.length; j++)
                {
                    if (m[i][j] == 10000)
    				path[i][j] = -1;
    			else
    				path[i][j] = i;
                }
        }
    	// This means that we don't have to go anywhere to go from i to i.
    	
     
     for (int i=0; i<m.length; i++)
     {path[i][i] = i;}
    	
     shortpath = FloydWarshall.shortestpath(m, path);
    	// Loop through each previous vertex until you get back to start.
    	 while (path[start][des] != start) {
    		myPath = path[start][des] + " -> " + myPath;
    		des = path[start][des];
    	}
        // Just add start to our string and print.
    	myPath = start + " -> " + myPath;
       
         System.out.println(n);
         System.out.println("Adjacency Matrix:");
          for(int i=0; i<m.length; i++){
            for(int j=0; j<m.length; j++)
            {System.out.print(m[i][j]+" ");}
    	System.out.println();
          }
         
          System.out.println("Shortest distance matrix:");
          for(int i=0; i<shortpath.length; i++){
            for(int j=0; j<shortpath.length; j++)
            {System.out.print(shortpath[i][j]+" ");}
    	System.out.println(); 
          }	
    }
    
    
    void findCab() throws IOException{
        Boolean available = false;
        int cab_place;
         int x = 16;
        for(int i=0; i<15; i++){
        minarr[i]=100000;
        }
        
        for(int i=0; i<15; i++){
            cab_place = (int)(((server.Cab[i]).getPlace()).charAt(0))-(int)('A');
           // System.out.println(cab_place);
            if(type.equals((server.Cab[i]).getType()))
            {
                if(((server.Cab[i]).busy)==false){
                    minarr[i]=shortpath[cab_place][start];
                    available=true;
                }
            }
        }
        
        for(int i=0; i<15; i++){
        System.out.println(minarr[i]);
        }
        System.out.println(available);
        String message = null;
        
        if(available==false){
            
            message="Sorry, there is no cab available of required type right now. Try after some time.";
            streamout.writeUTF(message);
            streamout.flush();
        }
         else{
        int min=100000;
       
        for(int i=0;i<15;i++){
            if(minarr[i] < min){
                min= minarr[i];
                x=i;
            }
        }
        message = "Cab is available. Name of Driver: " + ((server.Cab[x]).getName())+" Registration no: " + ((server.Cab[x]).getRN());
        streamout.writeUTF(message);
        (server.Cab[x]).busy = true;
        streamout.flush();
         int delay = ((min+shortpath[start][des])/speed)*1000; //milliseconds
         
       
         
 
        }
    }
}

    
    

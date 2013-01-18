package cabServer;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;





class Administrator implements Runnable
{ 
   private ServerSocket     server = null;
   private Thread           thread = null;
   private AdministratorThread client = null;
   
   public cab[] Cab = new cab[]{new cab("three-wheeler","Pooh","C15","H",3),new cab("AC","Tom","C01","A",3),new cab("AC","Harry","C02","E",3), new cab("AC","Ron","C03","H",3), new cab("AC","Jack","C04","A",3), new cab("Non-AC","Jim","C05","E",2), new cab("Non-AC","Jorge","C06","H",2),new cab("Non-AC","Sem","C07","A",2), new cab("Non-AC","Sau","C08","E",2), new cab("N0n-AC","Albus","C09","H",2), new cab("Non-AC","Sue","C10","A",2), new cab("three-wheeler","Hugo","C11","E",3), new cab("three-wheeler","Ram","C12","H",3), new cab("three-wheeler","Krishna","C13","A",3), new cab("three-wheeler","Tina","C14","E",3)};
   
    public Administrator(int port)
   {  try
      {  System.out.println("Binding to port " + port + ", please wait  ...");
         server = new ServerSocket(port);  
         System.out.println("Server started: " + server);
         start();
      }
      catch(IOException ioe)
      {  System.out.println(ioe); }
   }
    
    @Override
    public void run() {
         while (thread != null)
      {  try
         {  System.out.println("Waiting for a client ..."); 
            addThread(server.accept());
         }
         catch(IOException ie)
         {  System.out.println("Acceptance Error: " + ie); }
      }
    }
    
    
    public void addThread(Socket socket)
   {  System.out.println("Client accepted: " + socket);
      client = new AdministratorThread(this, socket);
      try
      {  client.open();
         client.start();
      }
      catch(IOException ioe)
      {  System.out.println("Error opening thread: " + ioe); }
   }

     public void start()                   
     {
         if (thread == null)
      {  thread = new Thread(this); 
         thread.start();
      }
     }
     
     
   public void stop() {  
       if (thread != null)
      {  thread.stop(); 
         thread = null;
      }  
   }
   public static void main(String args[]){ 
       Administrator server = new Administrator(2363);
       
   }
}
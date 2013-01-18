package cabServer;






class cab{
  private String type_of_vehicle,driver,Reg_no,place_of_cab;
   float distance_day, distance;
  int seating_capacity;
  Boolean busy=false;
   
   public cab(String type, String dvr, String Rn , String poc, int sc){
       type_of_vehicle=type;
       driver=dvr;
       Reg_no=Rn;
       seating_capacity=sc;
       place_of_cab=poc;
       distance=0;
       distance_day=0;
   }
   
  
   
   public void addDistance(){
       distance_day=distance_day+distance;
       
   }
   
   public float getDistanceDay(){
       return distance_day;
   }
   
   public String getName(){
       return driver;
   }
   
   public String getType()
   {
       return type_of_vehicle;
       
   }
   
   public String getRN(){
       return Reg_no;
   }
   
   
   public String getPlace(){
       return place_of_cab;
   }
   
   public void setPlace(String p){
       place_of_cab=p;
   }
  
}
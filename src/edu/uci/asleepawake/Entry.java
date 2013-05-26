package edu.uci.asleepawake;

public class Entry {
	 //private variables
    int _id;
    String _date;
    String _sleep;
    String _wake;
    String _motion;
    String _monitor;
     
    // Empty constructor
    public Entry(){
         
    }
    // constructor
    public Entry(int id, String date, String sleep, String wake, String motion, String monitor){
        this._id = id;
        this._date = date;
        this._sleep = sleep;
        this._wake = wake;
        this._motion = motion;
        this._monitor = monitor;
    }
     
    // constructor
//    public Entry(String date, String sleep, String wake, String motion, String monitor){
//        this._date = date;
//        this._sleep = sleep;
//        this._wake = wake;
//        this._motion = motion;
//        this._monitor = monitor;
//    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting date
    public String getDate(){
        return this._date;
    }
     
    // setting date
    public void setDate(String date){
        this._date = date;
    }
     
    // getting sleep
    public String getSleep(){
        return this._date;
    }
     
    // setting sleep
    public void setSleep(String sleep){
        this._sleep = sleep;
    }
    
    // getting wake
    public String getWake(){
        return this._wake;
    }
     
    // setting wake
    public void setWake(String wake){
        this._wake = wake;
    }
    
    // getting motion
    public String getMotion(){
        return this._motion;
    }
     
    // setting motion
    public void setMotion(String motion){
        this._motion = motion;
    }
    
    // getting monitor
    public String getMonitor(){
        return this._monitor;
    }
     
    // setting monitor
    public void setMonitor(String monitor){
        this._monitor = monitor;
    }
}

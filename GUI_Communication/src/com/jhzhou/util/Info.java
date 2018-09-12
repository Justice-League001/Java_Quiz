package com.jhzhou.util;


public class Info {
	public final String IP;
	public String NAME;
	public final int PORT;
	public final int PID;
	public Object MSG;
	public boolean STATE;
	public Info(String IP, int PORT, String NAME, int PID, Object MSG, boolean STATE){
		this.IP = IP;
		this.PORT = PORT;
		this.NAME = NAME;
		this.PID = PID;
		this.MSG = MSG;
		this.STATE = STATE;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + PID;
        result = prime * result + PORT;
    	return IP.hashCode()+result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Info other = (Info) obj;
        if (PID != other.PID)
            return false;
        if (PORT != other.PORT)
            return false;
        if(IP.equals(other.IP)==false) 	
        	return false;
        return true;
    }
}

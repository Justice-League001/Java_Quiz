package com.jhzhou.util;

public class FileMsg {
	
	public final String FileName;
	public final long FileSize;
	
	public FileMsg(String FileName, long FileSize) {
		this.FileName = FileName;
		this.FileSize = FileSize;
	}
	@Override  
    public Object clone() {  
        FileMsg obj = null; 
        
        try{ 
            obj = (FileMsg)super.clone(); 
            
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return obj;  
    }  
}

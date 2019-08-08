package cl.banchile.camelfix.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PruebameLeeArchivoMain {

	public static void main(String[] args) {
		File sessionLog;
		sessionLog=new File("C:\\javadev\\workspace\\2015\\camellFix\\svn\\camellFix.data\\logs\\FIX.4.4-BCSG-MDBANCERTBACKUP.messages.log");
		sessionLog.getAbsolutePath();
		if(sessionLog.exists()){
			System.out.println("EXISTE! archivo: "+sessionLog.getAbsolutePath());
		}
		
		StringBuffer fileContent=null;
		try{
			int len = (int) sessionLog.length();
			FileInputStream fis = new FileInputStream(sessionLog);
			
			int limit=0;
			fis.skip(len);
			byte buf[] = new byte[100];
			int b;
			while(limit++<=100){
				b=fis.read();
				System.out.print((byte)b);
			}
			
			
			/*byte buf[] = new byte[len];
			fis.read(buf);
			fis.close();
			fileContent = new StringBuffer(new String(buf, 0, len));
			System.out.println(fileContent);*/
	    }
	    catch (FileNotFoundException e){
	    	System.out.println("FileNotFoundException" + e);
	    }catch (IOException e){
			System.out.println("IOException" + e);
	    }
	}

}

package solengine.zz.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Storage {
	
	
	public static void saveProgress(String fileName, int progress){
		try {
			File file  = new File(fileName);
			
			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.write(progress);
				out.close();
				fileOut.close();
			}
			else{
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				String text = progress + "registros";
				out.writeUTF(text );
				out.close();
				fileOut.close();
			}
//			System.out.println("\nSerialization Successful... Checkout your specified output file..\n");
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

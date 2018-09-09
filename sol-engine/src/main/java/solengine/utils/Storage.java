package solengine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import solengine.model.QueryResult;

public class Storage {
	
	public static void save(String fileName, Map<List<String>,QueryResult> results){
		try {
			File file  = new File(fileName);
			
			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(results);
				out.close();
				fileOut.close();
			}
			else{
				Map<List<String>, QueryResult> map = Storage.read(fileName);
				map.putAll(results);
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(map);
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
	

	public static Map<List<String>,QueryResult> read(String fileName){
		try {
			File file  = new File(fileName);
			Map<List<String>,QueryResult> readObjects =  new ConcurrentHashMap<List<String>,QueryResult>();
			if(file.exists()){
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				readObjects =  (Map<List<String>,QueryResult>) in.readObject();
//				for(QueryResult message : readObjects.values()){
//					System.out.println(message.text);
//				}
				in.close();
				fileIn.close();			
			}
			else{
				
			}
			return readObjects;	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void print(List<QueryResult> results){
		for(QueryResult resultItem : results){
			System.out.println(resultItem.toString());
		}
	}
	
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

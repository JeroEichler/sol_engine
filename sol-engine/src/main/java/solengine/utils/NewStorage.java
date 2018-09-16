package solengine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import solengine.model.QueryResult;
import solengine.utils.dto.QueryResultDto;

public class NewStorage {

	static ObjectMapper mapper = MapperFactory.initMapper(); 
	
	public static void save(Map<List<String>,QueryResult> results){
		for(QueryResult result : results.values()) {
			NewStorage.saveSingleResult2(result);
		}
	}
	
	public static void saveSingleResult(QueryResult result){
		String fileName = Config.baseFolder;
		for(String uri : result.getResult()) {
			fileName = fileName.concat(StringFormatter.clean(uri));
		}
		try {
			File file  = new File(fileName);
			
			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(result);
				out.close();
				fileOut.close();
			}
			else{
				// overwriting previously saved results
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(result);
				out.close();
				fileOut.close();
			}
			NewStorage.saveProgress(fileName);
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static QueryResult read(String fileName){
		try {
			File file  = new File(fileName);
			QueryResult readObjects =  null;
			if(file.exists()){
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				readObjects =  (QueryResult) in.readObject();
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
	
	public static void saveProgress(String savedResult){
		String fileName = Config.baseFolder.concat("progress.txt");

		try {
			File file  = new File(fileName);
			
			
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);			 
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write(savedResult);
			osw.close();
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**************************************************************************************************************/
	public static void saveSingleResult2(QueryResult result){
		String fileName = "";//Config.baseFolder;
		for(String uri : result.getResult()) {
			fileName = fileName.concat(StringFormatter.clean(uri));
		}
		
		//fileName = fileName.concat(".json");
		
		QueryResultDto exportedResult = ModelConverter.convert(result);
		
		try {
			mapper.writeValue(new File(
					Config.baseFolder2 +
					fileName +".json"), exportedResult);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//			File file  = new File(fileName);
//			
//			if (!file.exists()) {
//				file.createNewFile();
//				FileOutputStream fileOut = new FileOutputStream(file);
//				ObjectOutputStream out = new ObjectOutputStream(fileOut);
//				out.writeObject(result);
//				out.close();
//				fileOut.close();
//			}
//			else{
//				// overwriting previously saved results
//				FileOutputStream fileOut = new FileOutputStream(file);
//				ObjectOutputStream out = new ObjectOutputStream(fileOut);
//				out.writeObject(result);
//				out.close();
//				fileOut.close();
//			}
//			NewStorage.saveProgress(fileName);
// 
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}

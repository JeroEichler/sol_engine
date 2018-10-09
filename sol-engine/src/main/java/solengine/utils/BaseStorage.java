package solengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import solengine.configuration.Config;

public class BaseStorage {
	
	static ObjectMapper mapper = MapperFactory.initMapper(); 
	

	public static void saveEntity(String fileName, Object entity) {		
		try {
			mapper.writeValue(new File(
					Config.baseFolder2 +
					fileName +".json"), entity);			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	

	public static void saveEntity(String folder, String fileName, Object entity) {		
		try {
			mapper.writeValue(new File(
					Config.root + "analysis//full//" +
					folder + "//" +
					fileName +".json"), entity);			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	

	public static List<String> readList(String folder, String fileName) {
		List<String> list = new ArrayList<String>();
		
		try {
			String[] tempRead = mapper.readValue(new File(
					Config.root + "full//" +
					folder + "//" +
					fileName +".json"), String[].class);
			list = Arrays.asList(tempRead);
		} 
		catch (JsonParseException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	

	public static void updateList(String folder, String fileName, String title){	
		List<String> savedList = NewNewStorage.readList(folder, fileName);
		savedList.add(title);
		NewStorage.saveEntity(fileName, savedList);
	}


}

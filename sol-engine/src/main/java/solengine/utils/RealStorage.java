package solengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import solengine.configuration.Config;

public class RealStorage extends BaseStorage {
	
	
	public static List<List<String>> readBaseList(String fileName) {
		List<List<String>> savedList = new ArrayList<List<String>>();
		
		try {
			String[][] tempRead = mapper.readValue(new File(
					Config.baseFolder2 +
					fileName +".json"), String[][].class);
			for(String[] tempy: tempRead) {
				List<String> x = Arrays.asList(tempy);
				savedList.add(x);
			}
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
		return savedList;
	}
	
	public static void reduceBaseList(String fileName, List<String> itemSaved) {
		List<List<String>> baseList = readBaseList(fileName);
		baseList.remove(itemSaved);
		try {
			mapper.writeValue(new File(
					Config.baseFolder2 +
					fileName +".json"), baseList);			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

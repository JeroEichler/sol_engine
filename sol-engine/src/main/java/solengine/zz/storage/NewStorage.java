package solengine.zz.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import solengine.configuration.Config;
import solengine.model.QueryResponse;
import solengine.model.dto.QueryResponseDto;
import solengine.utils.MapperFactory;
import solengine.utils.ModelConverter;
import solengine.utils.StringFormatter;

public class NewStorage {

	static ObjectMapper mapper = MapperFactory.initMapper(); 
	
	public static void saveResults(Map<List<String>,QueryResponse> results){
		for(QueryResponse result : results.values()) {
			NewStorage.saveSingleResult(result);
		}
		// to save the list of persisted titles.
		NewStorage.saveProgress("progressX", results);
	}
	
	public static void saveSingleResult(QueryResponse result){	
		String title = StringFormatter.clean(result.getResult());	
		QueryResponseDto exportedResult = ModelConverter.convert(result);		
		NewStorage.saveEntity(title, exportedResult);
	}
	

	public static void saveProgress(String fileName, Map<List<String>,QueryResponse> results){	
		List<String> savedTitles = new ArrayList<String>();
		for(QueryResponse result : results.values()) {
			String title = StringFormatter.clean(result.getResult());
			savedTitles.add(title);
		}
		// to save the list of persisted titles.
		NewStorage.saveList(fileName, savedTitles);
	}
	
	public static void saveList(String fileName, List<String> results){	
		List<String> savedList = NewStorage.readProgress();
		for(String result : results) {
			String title = StringFormatter.clean(result);
			savedList.add(title);
		}
		NewStorage.saveEntity(fileName, savedList);
	}
	

	public static void saveListItem(String fileName, String result){	
		List<String> savedList = NewStorage.readProgress();
		String title = StringFormatter.clean(result);
		savedList.add(title);
		NewStorage.saveEntity(fileName, savedList);
	}
	
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
	
	public static List<String> readProgress() {
		List<String> savedList = new ArrayList<String>();
		
		try {
			String[] tempRead = mapper.readValue(new File(
					Config.baseFolder2 +
					"progressX.json"), String[].class);
			savedList.addAll(Arrays.asList(tempRead));
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
	

	
	public static List<List<String>> readPersistedResults(String fileName) {
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


}

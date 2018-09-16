package solengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		List<String> savedTitles = new ArrayList<String>();
		for(QueryResult result : results.values()) {
			String title = StringFormatter.clean(result.getResult());
			NewStorage.saveSingleResult(title, result);
			savedTitles.add(title);
		}
		NewStorage.saveEntity("progressX", savedTitles);
	}
	
	public static void saveSingleResult(String fileName, QueryResult result){		
		QueryResultDto exportedResult = ModelConverter.convert(result);		
		NewStorage.saveEntity(fileName, exportedResult);
	}
	

	
	public static void savProgress(String fileName, List<String> results){		
		QueryResultDto exportedResult = ModelConverter.convert(result);		
		NewStorage.saveEntity(fileName, exportedResult);
	}
	
	private static void saveEntity(String fileName, Object entity) {		
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
	
	public static List<String> readProgress(String folder, String genre) {
		List<String> movieList = new ArrayList<String>();
		
		try {
			String[] tempRead = mapper.readValue(new File(
					Config.baseFolder2 +
					".json"), String[].class);
			movieList.addAll(Arrays.asList(tempRead));
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
		return movieList;
	}

}

package solengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import solengine.model.QueryResponse;
import solengine.utils.dto.QueryResultDto;

public class NewStorage {

	static ObjectMapper mapper = MapperFactory.initMapper(); 
	
	public static void save(Map<List<String>,QueryResponse> results){
		List<String> savedTitles = new ArrayList<String>();
		for(QueryResponse result : results.values()) {
			String title = StringFormatter.clean(result.getResult());
			NewStorage.saveSingleResult(title, result);
			savedTitles.add(title);
		}
		NewStorage.saveProgress("progressX", savedTitles);
	}
	
	public static void saveSingleResult(String fileName, QueryResponse result){		
		QueryResultDto exportedResult = ModelConverter.convert(result);		
		NewStorage.saveEntity(fileName, exportedResult);
	}
	

	
	public static void saveProgress(String fileName, List<String> results){	
		List<String> savedList = NewStorage.readProgress();
		savedList.addAll(results);
		NewStorage.saveEntity("progressX", savedList);
	}
	
	public static void saveEntity(String fileName, Object entity) {		
		try {
//			mapper.writeValue(new File(
//					Config.baseFolder2 +
//					fileName +".json"), entity);
			
			DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
			prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.instance);
			
			mapper.writer(prettyPrinter).writeValue(new File(
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
				ArrayList<String> y = new ArrayList<String>(x);
				y.add("jeroonimo");
				savedList.add(y);
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

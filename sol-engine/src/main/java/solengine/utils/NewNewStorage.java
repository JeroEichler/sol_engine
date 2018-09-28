package solengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import solengine.model.QueryResponse;
import solengine.utils.dto.QueryResponseDto;

public class NewNewStorage {

	static ObjectMapper mapper = MapperFactory.initMapper(); 
	
	
	
	public static void saveSingleResult(QueryResponse result){	
		String title = StringFormatter.clean(result.getResult());	
		QueryResponseDto exportedResult = ModelConverter.convert(result);		
		NewStorage.saveEntity(title, exportedResult);
	}
	

	
	public static void updateControlList(String fileName, QueryResponse result){	
		List<String> savedList = NewNewStorage.readControlList(fileName);
		String title = StringFormatter.clean(result.getResult());
		savedList.add(title);
		NewStorage.saveEntity(fileName, savedList);
	}
	
	public static List<String> readControlList(String fileName) {
		List<String> savedList = new ArrayList<String>();
		
		try {
			String[] tempRead = mapper.readValue(new File(
					Config.baseFolder2 +
					fileName + ".json"), 
					String[].class);
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

	
	public static QueryResponseDto readQResponse(String fileName) {
		QueryResponseDto savedList = null;
		
		try {
			savedList = mapper.readValue(new File(
					Config.baseFolder2 +
					fileName + ".json"), QueryResponseDto.class);
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

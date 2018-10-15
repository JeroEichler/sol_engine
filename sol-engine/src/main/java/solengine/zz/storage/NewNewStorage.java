package solengine.zz.storage;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import solengine.configuration.Config;
import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.dto.AnalyzedQueryResponseDto;
import solengine.model.dto.QueryResponseDto;
import solengine.utils.ModelConverter;
import solengine.utils.StringFormatter;

public class NewNewStorage extends BaseStorage{
	
	public static String saveSingleResult(String folder, String title, QueryResponse result){		
		QueryResponseDto exportedResult = ModelConverter.convert(result);		
		saveEntity(folder, title, exportedResult);
		return title;
	}
	

	

	
	public static QueryResponseDto readQResponseDto(String folder, String fileName) {
		QueryResponseDto savedList = null;
		
		try {
			savedList = mapper.readValue(new File(
					Config.root + 
					folder + "//" +
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
	
	public static QueryResponse readQResponse(String folder, String fileName) {
		QueryResponseDto dto = readQResponseDto(folder, fileName);
		
		QueryResponse qr = ModelConverter.convert(dto);
		return qr;
	}
	


	public static String saveSingleAnalysis(String folder, AnalyzedQueryResponse result){	
		String title = StringFormatter.clean(result.queryResponse.getResult());	
		AnalyzedQueryResponseDto exportedResult = ModelConverter.convert(result);		
		saveEntity(folder, title, exportedResult);
		return title;
	}
	
	public static AnalyzedQueryResponseDto readAQRDto(String folder, String fileName) {
		AnalyzedQueryResponseDto savedList = null;
		
		try {
			savedList = mapper.readValue(new File(
					Config.root + 
					folder + "//" +
					fileName + ".json"), AnalyzedQueryResponseDto.class);
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
	
	public static AnalyzedQueryResponse readAnalysis(String folder, String fileName) {
		AnalyzedQueryResponseDto dto = readAQRDto(folder, fileName);
		
		AnalyzedQueryResponse qr = ModelConverter.convert(dto);
		return qr;
	}

}

package solengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import solengine.configuration.Config;
import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.dto.AnalyzedQueryResponseDto;
import solengine.model.dto.QueryResponseDto;

public class NewNewStorage extends BaseStorage{
	
	public static String saveSingleResult(String title, QueryResponse result){		
		QueryResponseDto exportedResult = ModelConverter.convert(result);		
		NewStorage.saveEntity(title, exportedResult);
		return title;
	}
	

	

	
	public static QueryResponseDto readQResponseDto(String fileName) {
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
	
	public static QueryResponse readQResponse(String fileName) {
		QueryResponseDto dto = readQResponseDto(fileName);
		
		QueryResponse qr = ModelConverter.convert(dto);
		return qr;
	}
	


	public static String saveSingleAnalysis(String folder, AnalyzedQueryResponse result){	
		String title = StringFormatter.clean(result.queryResponse.getResult());	
		AnalyzedQueryResponseDto exportedResult = ModelConverter.convert(result);		
		saveEntity(folder, title, exportedResult);
		return title;
	}
	
	public static AnalyzedQueryResponseDto readAQRDto(String fileName) {
		AnalyzedQueryResponseDto savedList = null;
		
		try {
			savedList = mapper.readValue(new File(
					Config.baseFolder2 +
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
	
	public static AnalyzedQueryResponse readAnalisise(String fileName) {
		AnalyzedQueryResponseDto dto = readAQRDto(fileName);
		
		AnalyzedQueryResponse qr = ModelConverter.convert(dto);
		return qr;
	}

}

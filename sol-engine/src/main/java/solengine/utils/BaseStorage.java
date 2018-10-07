package solengine.utils;

import java.io.File;
import java.io.IOException;

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

}

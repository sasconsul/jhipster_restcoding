/**
 * 
 */
package com.sasconsul.restcoding.domain;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * Ignores the field when deserializing
 * @author sasconsul
 *
 */
public class LongIgnoreDeserializer extends JsonDeserializer<Long> {

	/**
	 * Don't deserialize this the field.
	 */
	@Override
	public Long deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		return null;
	}

}

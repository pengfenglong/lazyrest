package com.smartdp.lazyrest.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smartdp.lazyrest.sqlbuilder.DeleteBuilder;
import com.smartdp.lazyrest.sqlbuilder.InsertBuilder;
import com.smartdp.lazyrest.sqlbuilder.SelectBuilder;
import com.smartdp.lazyrest.sqlbuilder.UpdateBuilder;

@Component("restAPIService")
public class RestAPIService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> select(String entity,String id){
		
		SelectBuilder selectBuilder = new SelectBuilder(entity).where("id = '"+id+"'");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(selectBuilder.toString());
		
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	public List<Map<String, Object>> query(String entity,String filter){
		Map<String, Object> map = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			map = objectMapper.readValue(filter, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SelectBuilder selectBuilder = new SelectBuilder(entity);
		List<String> fields = (List<String>)map.get("fields");
		String order = (String)map.get("order");
		Integer limit = (Integer)map.get("limit");
		List<String> wheres = (List<String>)map.get("where");
		if(fields!=null){
			for(String field : fields){
				selectBuilder.column(field);
			}
		}
		if(order!=null){
			selectBuilder.orderBy(order);
		}
		if(wheres!=null){
			for(String where : wheres){
				selectBuilder.where(where);
			}
		}
		
		String sql = selectBuilder.toString();
		if(limit!=null){
			sql += " limit " + limit + ",20";
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		return list;
	}
	
	public Object create(String entity,String body){
		
		Map<String, Object> map = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			map = objectMapper.readValue(body, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InsertBuilder builder;
	    builder = new InsertBuilder(entity);
	    
	    builder.set("id", "'"+UUID.randomUUID().toString()+"'");
	    Set<String> keys = map.keySet();
	    
	    for(String key : keys){
	    	builder.set(key, "'"+(String)map.get(key)+"'");
	    }
		
	    jdbcTemplate.update(builder.toString());
		return true;
		
	}
	
	public Object delete(String entity,String id){
		
		DeleteBuilder deleteBuilder=new DeleteBuilder(entity);
		
		deleteBuilder.set("id = '"+id+"'");
		
		jdbcTemplate.execute(deleteBuilder.toString());
		
		return true;
		
	}
	
	public Object update(String entity,String id,String body){
		
		Map<String, Object> map = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			map = objectMapper.readValue(body, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UpdateBuilder updateBuilder = new UpdateBuilder(entity);
		
		Set<String> keys = map.keySet();
		
		for(String key : keys){
			updateBuilder.set(key + " = '"+map.get(key)+"'");
		}
		
		updateBuilder.wheres("id = '"+id+"'");
		
		jdbcTemplate.update(updateBuilder.toString());
		
		return true;
		
	}

}

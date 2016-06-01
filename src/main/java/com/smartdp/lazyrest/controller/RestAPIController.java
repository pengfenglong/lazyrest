package com.smartdp.lazyrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartdp.lazyrest.service.RestAPIService;

/**
@RestController
@RequestMapping("/rest/api")
*/
public class RestAPIController {
	
	@Autowired
	private RestAPIService restAPIService;

	/**
	 * 根据id查询 eg:/rest/User/1
	 * @param entity 实体表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{entity}/{id}",method=RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable String entity,@PathVariable String id) {
		
		return restAPIService.select(entity, id);

	}
	
	/**
	 * 根据条件查询 eg:/rest/User?filter={ "fields": ["id", "name"],"limit": 1 ,"order": "name DESC","where":["name='a'"]}
	 * @param entity
	 * @param filter
	 * @return
	 */
	@RequestMapping(value="/{entity}",method=RequestMethod.GET)
	public @ResponseBody Object query(@PathVariable String entity,@RequestParam String filter) {
		
		return restAPIService.query(entity, filter);
		
	}
	
	@RequestMapping(value="/{entity}",method=RequestMethod.POST)
	public @ResponseBody Object create(@PathVariable String entity,@RequestBody String body) {
		
		restAPIService.create(entity, body);
	
		return true;
		
	}
	
	@RequestMapping(value="/{entity}/{id}",method=RequestMethod.DELETE)
	public @ResponseBody Object delete(@PathVariable String entity,@PathVariable String id) {
		
		restAPIService.delete(entity, id);
		
		return true;
		
	}
	
	@RequestMapping(value="/{entity}/{id}",method=RequestMethod.PUT)
	public @ResponseBody Object update(@PathVariable String entity,@PathVariable String id,@RequestBody String body) {
		
		restAPIService.update(entity, id, body);
		
		return true;
		
	}

}

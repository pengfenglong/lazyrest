package com.smartdp.lazyrest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/test")
public class TestController {

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getMovie(@PathVariable("id") Long id, Model model) {

		model.addAttribute("movie", id);
		Map<String, String> m = new HashMap<String, String>();
		m.put("a", "a");
		m.put("b", "b");
		return m;

	}
	
}
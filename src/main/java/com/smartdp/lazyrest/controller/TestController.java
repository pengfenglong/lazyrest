package com.smartdp.lazyrest.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.applet.Main;

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
	
	public static void main(String[] args) {
		String s = "%7B%22userName%22%3A%22AAAAAAA%22%2C%22email%22%3A%22150521330%40qq.com%22%2C%22password%22%3A%22123%22%2C%22remark%22%3A%22AAAA%22%7D=";
		System.out.println(URLDecoder.decode(s));
	}
	
}
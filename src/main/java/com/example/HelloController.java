package com.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Team;

@Controller
public class HelloController {

	@RequestMapping(value="/post/{msj}", method = RequestMethod.POST)
	public @ResponseBody String helloPost(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return msj + " " + name;
	}

	@RequestMapping(value="/get/{msj}", method = RequestMethod.GET)
	public @ResponseBody String helloGet(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return msj + " " + name;
	}
	
	@RequestMapping(value="/put/{msj}", method = RequestMethod.PUT)
	public @ResponseBody String helloPut(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return msj + " " + name;
	}
	
	@RequestMapping(value="/delete/{msj}", method = RequestMethod.DELETE)
	public @ResponseBody String helloDelete(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return msj + " " + name;
	}
	
	
//	@RequestMapping(method = RequestMethod.POST)
//	public String newGame(@RequestParam String name, ModelMap model,  HttpServletRequest request, HttpServletResponse response) {
//		System.out.println(name);;
//		model.put("name", name);
//		return "hello";
//	}

}
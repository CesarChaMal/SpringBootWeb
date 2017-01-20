package com.example;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Team;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
public class HelloController {

	private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	@RequestMapping(value="/post/{msj}", method = RequestMethod.POST)
	public @ResponseBody String helloPost(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		Map<String, String> result = new HashMap<>();
		result.put(msj, name);
		
		return gson.toJson(result, type);
//		return msj + " " + name;
	}

	@RequestMapping(value="/get/{msj}", method = RequestMethod.GET)
	public @ResponseBody String helloGet(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		Map<String, String> result = new HashMap<>();
		result.put(msj, name);
		
		return gson.toJson(result, type);
//		return msj + " " + name;
	}
	
	@RequestMapping(value="/put/{msj}", method = RequestMethod.PUT)
	public @ResponseBody String helloPut(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		Map<String, String> result = new HashMap<>();
		result.put(msj, name);
		
		return gson.toJson(result, type);
//		return msj + " " + name;
	}
	
	@RequestMapping(value="/delete/{msj}", method = RequestMethod.DELETE)
	public @ResponseBody String helloDelete(@RequestParam String name, @PathVariable("msj") String msj, HttpServletRequest request, HttpServletResponse response){
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		Map<String, String> result = new HashMap<>();
		result.put(msj, name);
		
		return gson.toJson(result, type);
//		return msj + " " + name;
	}
	
	
//	@RequestMapping(method = RequestMethod.POST)
//	public String newGame(@RequestParam String name, ModelMap model,  HttpServletRequest request, HttpServletResponse response) {
//		System.out.println(name);;
//		model.put("name", name);
//		return "hello";
//	}

}
package com.example;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.helpers.HttpRequest;
import rest.helpers.HttpRequestType;

@Controller
public class MyController1 {

	private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	@RequestMapping("/hi/{name}")
	public String hiThere(Map model, @PathVariable String name){
		model.put("name", name);

		JsonStub stub = new JsonStub();
		stub.name = "Cesar Chavez";
		
		String json = gson.toJson(stub, JsonStub.class);
		String url1 = "http://localhost:8080/SpringBootWeb/post/hello";
		HttpRequest newRequest = new HttpRequest(url1, HttpRequestType.POST, json);
		String res1 = newRequest.toString();
		System.out.println("POST:" + res1);

		String url2 = "http://localhost:8080/SpringBootWeb/get/hello?name=Cesar";
		HttpRequest newRequest2 = new HttpRequest(url2, HttpRequestType.GET, json);
		String res2 = newRequest2.toString();
		System.out.println("GET:" + res2);

		String url3 = "http://localhost:8080/SpringBootWeb/put/hello";
		String json2 = gson.toJson(stub, JsonStub.class);
		HttpRequest newRequest3 = new HttpRequest(url3, HttpRequestType.PUT, json);
		String res3 = newRequest3.toString();
		System.out.println("PUT:" + res3);
		
		String url4 = "http://localhost:8080/SpringBootWeb/delete/hello?name=Cesar";
		HttpRequest newRequest4 = new HttpRequest(url4, HttpRequestType.DELETE, json);
		String res4 = newRequest4.toString();
		System.out.println("DELETE: " + res4);
		
		return "hello";
	}

	public class JSON_STUBS {}
	public class JsonStub extends JSON_STUBS {String name;};
}

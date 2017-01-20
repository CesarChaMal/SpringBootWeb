package com.example;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.helpers.strategy.HttpRequestDelete;
import rest.helpers.strategy.HttpRequestGet;
import rest.helpers.strategy.HttpRequestManager;
import rest.helpers.strategy.HttpRequestPost;
import rest.helpers.strategy.HttpRequestPut;

@Controller
public class MyController2 {

	private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	@RequestMapping("/hi2/{name}")
	public String hiThere(Map model, @PathVariable String name){
		model.put("name", name);

		HttpRequestManager manager = new HttpRequestManager();

		JsonStub stub = new JsonStub();
		stub.name = "Cesar Chavez";
		
		String json = gson.toJson(stub, JsonStub.class);
		String url1 = "http://localhost:8080/SpringBootWeb/post/hello";
		HttpRequestPost newRequestPost = new HttpRequestPost(url1, json);
		manager.setMethod(newRequestPost);
		manager.makeRequest();
		String res1 = newRequestPost.toString();
		System.out.println("POST:" + res1);

		String url2 = "http://localhost:8080/SpringBootWeb/get/hello?name=Cesar";
		HttpRequestGet newRequestGet = new HttpRequestGet(url2, json);
		manager.setMethod(newRequestGet);
		manager.makeRequest();
		String res2 = newRequestGet.toString();
		System.out.println("GET:" + res2);

		String url3 = "http://localhost:8080/SpringBootWeb/put/hello";
		String json2 = gson.toJson(stub, JsonStub.class);
		HttpRequestPut newRequestPut = new HttpRequestPut(url3, json);
		manager.setMethod(newRequestPut);
		manager.makeRequest();
		String res3 = newRequestPut.toString();
		System.out.println("PUT:" + res3);
		
		String url4 = "http://localhost:8080/SpringBootWeb/delete/hello?name=Cesar";
		HttpRequestDelete newRequestDelete = new HttpRequestDelete(url4, json);
		manager.setMethod(newRequestDelete);
		manager.makeRequest();
		String res4 = newRequestDelete.toString();
		System.out.println("DELETE: " + res4);
		
		return "hello";
	}

	public class JSON_STUBS {}
	public class JsonStub extends JSON_STUBS {String name;};
}

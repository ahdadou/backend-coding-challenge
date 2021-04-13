package com.shop.demo.service;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.demo.dao.NetworkDao;
import com.shop.demo.model.Item;
import com.shop.demo.model.Language;




@Service
public class RepoService {
	
	@Autowired
	NetworkDao networkDao;
	
	
	public List<Language> getData() throws Exception{
		
//		READ API
		String rawJson = networkDao.request("https://api.github.com/search/repositories?q=created:%3E2015&sort=stars&order=desc");
		JSONObject root = new JSONObject(rawJson);
		JSONArray itemsarray = root.getJSONArray("items");
	

//		REMPLIR ITEM LIST 
		List<Item> items = new ArrayList<Item>();
		
		for(int i = 0; i < itemsarray.length(); i++) {
			// the JSON data
			JSONObject jsonItem = itemsarray.getJSONObject(i);
			// item object that we will populate from JSON data.
			Item item = new Item();
			int id = jsonItem.getInt("id");
			String html_url = jsonItem.getString("html_url");
			String language = jsonItem.getString("language");
			// set item information,
			item.setId(id);
			item.setHtml_url(html_url);
			item.setLanguage(language);
			// add the item to our collection.
			items.add(item);			
		}
		
		
		
//		REMPLIR LANGUAGE NAME LIST
		Set<String> languagesName = new HashSet<String>();
		
		for(int i = 0; i < itemsarray.length(); i++) {
		// the JSON data
		JSONObject jsonItem = itemsarray.getJSONObject(i);
		// item object that we will populate from JSON data.
		String lang = jsonItem.getString("language");
		if(lang != "null")
			languagesName.add(lang);			
		}
		
		
		
//		REMPLIR  LANGUAGE INFO LIST
		List<Language> languages = new ArrayList<Language>();
		
		for (Iterator<String> it = languagesName.iterator(); it.hasNext();) {
//			name
			Language l = new Language();
		    String languageName = it.next();
		    l.setName(languageName);
		    
//		    repos 
		    List<String> urls = new ArrayList<String>();
		    for(int j=0;j<items.size();j++) {
		    	
		    	if(items.get(j).getLanguage().equals(languageName)) {
		    		urls.add(items.get(j).getHtml_url());
		    	}
		    }
		    
		    l.setRepositories(urls);
		    l.setCount(urls.size());
		      
		    languages.add(l);
		
		}
		
		
	
		
		
		
		

		
		
		
		
		
		
		return languages;
	}
	
	

}

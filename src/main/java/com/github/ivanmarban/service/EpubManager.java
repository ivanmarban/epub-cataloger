package com.github.ivanmarban.service;

import java.util.List;

import com.github.ivanmarban.model.Epub;

public interface EpubManager extends GenericManager<Epub,Long>{
	
	List<Epub> getEpubsByUser(String username, String searchTerm);
	
	List<Epub> getAllEpubsByUser(String username);
	
	Epub getEpubByUser(Long epubId, String username);
	
	void deleteEpubsByUser (String username); 
		
}
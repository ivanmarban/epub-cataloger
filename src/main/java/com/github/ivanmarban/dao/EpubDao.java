package com.github.ivanmarban.dao;

import java.util.List;

import com.github.ivanmarban.model.Epub;

public interface EpubDao extends GenericDao<Epub, Long> {

	List<Epub> getEpubsByUser(String username, String searchTerm);

	List<Epub> getAllEpubsByUser(String username);
	
	Epub getEpubByUser(Long epubId, String username);
	
	void deleteEpubsByUser (String username);

}

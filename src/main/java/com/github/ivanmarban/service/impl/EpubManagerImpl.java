package com.github.ivanmarban.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ivanmarban.dao.EpubDao;
import com.github.ivanmarban.model.Epub;
import com.github.ivanmarban.service.EpubManager;

@Service("epubManager")
public class EpubManagerImpl extends GenericManagerImpl<Epub, Long> implements EpubManager {

	EpubDao epubDao;

	@Autowired
	public EpubManagerImpl(EpubDao epubDao) {
		super(epubDao);
		this.epubDao = epubDao;
	}

	public List<Epub> getEpubsByUser(String username, String searchTerm) {
		
		if (searchTerm == null || "".equals(searchTerm.trim())) {
            return getAllEpubsByUser(username);
        }
		
		return epubDao.getEpubsByUser(username, searchTerm);
	}

	
	public List<Epub> getAllEpubsByUser (String username){
		return epubDao.getAllEpubsByUser(username);
	}

	@Override
	public Epub getEpubByUser(Long epubId, String username) {
		return epubDao.getEpubByUser(epubId, username);
	}

	@Override
	public void deleteEpubsByUser(String username) {
		// TODO Auto-generated method stub
		epubDao.deleteEpubsByUser(username);
	}
	
	

}

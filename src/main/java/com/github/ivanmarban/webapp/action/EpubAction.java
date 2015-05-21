package com.github.ivanmarban.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date.Event;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ivanmarban.dao.SearchException;
import com.github.ivanmarban.model.Epub;
import com.github.ivanmarban.service.EpubManager;
import com.github.ivanmarban.service.GenericManager;
import com.github.ivanmarban.webapp.util.MD5Utils;
import com.opensymphony.xwork2.Preparable;

public class EpubAction extends BaseAction implements Preparable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private GenericManager<Epub, Long> epubManager;
    @SuppressWarnings("rawtypes")
	private List epubs;
    private Epub epub;
    private Long id;
    private String query;
    private String[] deleteList;
    
    @Autowired
	private EpubManager epubManager;
    
    public void setEpubManager(GenericManager<Epub, Long> epubManager) {
        this.epubManager = (EpubManager) epubManager;
    }

    public String[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(String[] deleteList) {
		this.deleteList = deleteList;
	}

	@SuppressWarnings("rawtypes")
	public List getEpubs() {
        return epubs;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    public void prepare() {
        if (getRequest().getMethod().equalsIgnoreCase("post")) {
            // prevent failures on new
            String epubId = getRequest().getParameter("epub.id");
            if (epubId != null && !epubId.equals("")) {
                //epub = epubManager.get(new Long(epubId));
            	epub = epubManager.getEpubByUser(new Long(epubId), this.getRequest().getRemoteUser());
            }
        }
    }

    public void setQ(String q) {
        this.query = q;
    }

    public String list() {
        try {
        	epubs = epubManager.getEpubsByUser(this.getRequest().getRemoteUser(), query);
        } catch (SearchException se) {
            addActionError(se.getMessage());
            epubs = epubManager.getAllEpubsByUser(this.getRequest().getRemoteUser());
        }
        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Epub getEpub() {
        return epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }
    
    public String deleteList(){
    	
    	String key = "";
    	
    	if (deleteList.length == 1){
    		key = "epub.deleted";
    	}else{
    		key = "epubs.deleted";
    	}
    	
    	String dir =  ServletActionContext.getServletContext()
				.getRealPath("//resources")
				+ "/"
				+ getRequest().getRemoteUser()
				+ "/";
    	
    	for (int x=0;x<deleteList.length;x++){
    		
    		StringTokenizer stringTokenizer = new StringTokenizer(deleteList[x], "|");
    		
    		String id = (String) stringTokenizer.nextElement();
    		String md5 = (String) stringTokenizer.nextElement();
  
    		epubManager.remove(Long.parseLong(id));
    		
    		File file = new File(dir + md5);
    		
    		if (file.exists()){ 
    			file.delete();
    		}		
    	}
    	
    	saveMessage(getText(key));
    	
    	return "deleteList";
    }

    public String delete() {
    	
    	String dir =  ServletActionContext.getServletContext()
				.getRealPath("//resources")
				+ "/"
				+ getRequest().getRemoteUser()
				+ "/";
    	
    	File fileBook = new File(dir + epub.getMd5());
		
		if (fileBook.exists()){ 
			fileBook.delete();
		}
    	
        epubManager.remove(epub.getId());
        saveMessage(getText("epub.deleted"));

        return SUCCESS;
    }

    public String edit() {
        if (id != null) {
            //epub = epubManager.get(id);
        	epub = epubManager.getEpubByUser(id, this.getRequest().getRemoteUser());
        	
        	if (epub == null){
        		return "cancel";
        	}
        } else {
            //epub = new Epub();
        	return "cancel";
        }

        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null) {
            return "cancel";
        }

        if (delete != null) {
            return delete();
        }

        boolean isNew = (epub.getId() == null);
        
        //modificamos el ePub fisico
        if (!isNew){
	        String file = ServletActionContext.getServletContext()
					.getRealPath("//resources")
					+ "/"
					+ getRequest().getRemoteUser()
					+ "/"
					+ epub.getMd5();
	        
	        String dir =  ServletActionContext.getServletContext()
					.getRealPath("//resources")
					+ "/"
					+ getRequest().getRemoteUser()
					+ "/";
	        
	        InputStream stream = new FileInputStream(file);
	        
	        EpubReader epubReader = new EpubReader();
			Book book = epubReader.readEpub(stream);
			
			stream.close();
			
			List<Author> autores = new ArrayList<Author>();
			
			StringTokenizer token = new StringTokenizer(epub.getAuthors(), "&");
	        do{
	        	Author autor = new Author(token.nextToken().trim());
	        	autores.add(autor);
	        }while(token.hasMoreTokens());
			
			List<String> descripciones = new ArrayList<String>();
			descripciones.add(epub.getDescription());
			
			List<String> titulos = new ArrayList<String>();
			titulos.add(epub.getTitle());
			
			List<String> etiquetas = new ArrayList<String>();
			
			if ( !epub.getSubjects().equals("") && epub.getSubjects() != null){
				StringTokenizer token2 = new StringTokenizer(epub.getSubjects(), ",");
		        do{
		        	etiquetas.add(token2.nextToken().trim());
		        }while(token2.hasMoreTokens());
			}/*else{
				etiquetas.add("");
			}*/
		        
	        nl.siegmann.epublib.domain.Date publicacion = null;
	        List<nl.siegmann.epublib.domain.Date> publicaciones = new ArrayList<nl.siegmann.epublib.domain.Date>();
	        
	        if (epub.getPublication() != null){
	        	publicacion = new nl.siegmann.epublib.domain.Date(epub.getPublication(), Event.PUBLICATION);
	        	publicaciones = new ArrayList<nl.siegmann.epublib.domain.Date>();
	        	publicaciones.add(publicacion);
	        }/*else{
	        	publicacion = new nl.siegmann.epublib.domain.Date( new java.util.Date(), Event.MODIFICATION);
	        }*/
	
			book.getMetadata().setAuthors(autores);
			book.getMetadata().setDescriptions(descripciones);
			book.getMetadata().setTitles(titulos);
			book.getMetadata().setSubjects(etiquetas);
			book.getMetadata().setDates(publicaciones);
			
			EpubWriter epubWriter = new EpubWriter();
			epubWriter.write(book,new FileOutputStream(dir + "tmp.epub"));
			
			InputStream bookStream = new FileInputStream(dir + "tmp.epub");
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			String digest = MD5Utils.getDigest(bookStream, md, 2048);
			
			bookStream.close();
			
			File fileBook = new File(dir + epub.getMd5());
			
			if (fileBook.exists()){ 
				fileBook.delete();
			}
			
			fileBook = new File(dir + "tmp.epub");
			
			fileBook.renameTo(new File(dir + digest));
			
			epub.setMd5(digest);
			
        }

        epubManager.save(epub);

        String key = (isNew) ? "epub.added" : "epub.updated";
        saveMessage(getText(key));

        if (!isNew) {
            return INPUT;
        } else {
            return SUCCESS;
        }
    }
    
}
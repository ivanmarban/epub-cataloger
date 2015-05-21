package com.github.ivanmarban.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date.Event;
import nl.siegmann.epublib.epub.EpubReader;

import org.apache.struts2.ServletActionContext;

import com.github.ivanmarban.model.Epub;
import com.github.ivanmarban.service.EpubManager;
import com.github.ivanmarban.service.GenericManager;
import com.github.ivanmarban.webapp.util.MD5Utils;

import org.jsoup.Jsoup;

/**
 * Sample action that shows how to do file upload with Struts 2.
 */
public class FileUploadAction extends BaseAction {
	
	private static final long serialVersionUID = -9208910183310010569L;
	private File file;
	private String fileContentType;
	private String fileFileName;
	private String name;
	private Epub epub;
	private EpubManager epubManager;
	private String autores;
	private String descripcion;
	private String titulo;
	private String etiquetas;
	private Date publicacion;

	public void setEpubManager(GenericManager<Epub, Long> epubManager) {
		this.epubManager = (EpubManager) epubManager;
	}

	/**
	 * Upload the file
	 * 
	 * @return String with result (cancel, input or sucess)
	 * @throws Exception
	 *             if something goes wrong
	 */
	public String upload() throws Exception {
		if (this.cancel != null) {
			return "cancel";
		}

		// the directory to upload to
		String uploadDir = ServletActionContext.getServletContext()
				.getRealPath("/resources")
				+ "/"
				+ getRequest().getRemoteUser()
				+ "/";

		// write the file to the file specified
		File dirPath = new File(uploadDir);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		// retrieve the file data
		InputStream stream = new FileInputStream(file);
		InputStream bookStream = new FileInputStream(file);
		
		EpubReader epubReader = new EpubReader();
		Book book = epubReader.readEpub(bookStream);

		MessageDigest md = MessageDigest.getInstance("MD5");
		String digest = MD5Utils.getDigest(bookStream, md, 2048);
		
		autores = "";
		etiquetas = "";
		
		List <nl.siegmann.epublib.domain.Author> authors = new ArrayList<nl.siegmann.epublib.domain.Author>();
		authors = book.getMetadata().getAuthors();
		for (nl.siegmann.epublib.domain.Author author : authors){
			autores += author.getFirstname() +  " " + author.getLastname()+ " & ";
		}
		
		autores = autores.substring(0,autores.length()-3);
		
		List<String> subjects = book.getMetadata().getSubjects();
		Iterator<String> iterador = subjects.iterator();
		
		if (subjects.size() > 0) {
			while (iterador.hasNext()){
				etiquetas += iterador.next().toString() + ", ";
			}
			etiquetas = etiquetas.substring(0,etiquetas.length()-2);
		}else{
			etiquetas = null;
		}
			
		List<nl.siegmann.epublib.domain.Date> dates = book.getMetadata().getDates();
		
		publicacion = null;
		
		for (nl.siegmann.epublib.domain.Date date : dates) { 
			Event event = date.getEvent();
			if(event != null && Event.PUBLICATION.equals(event)) {
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				publicacion = formato.parse(date.getValue());
			}
			else if(event != null && !Event.CREATION.equals(event) && !Event.MODIFICATION.equals(event)) {
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				publicacion = formato.parse(date.getValue());
			}
			else if (event == null && date!= null){
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				publicacion = formato.parse(date.getValue());
			}
		}
		
		titulo = book.getTitle();
		descripcion = book.getMetadata().getDescriptions().get(0);
		
		if (descripcion != null){
			//descripcion.replace("<p class=\"description\">", "").replace("</p>", "");
			descripcion = Jsoup.parse(descripcion).text();
		}
		
		epub = new Epub();
		
		epub.setAuthors(autores);
		epub.setTitle(titulo);
		epub.setUsername(getRequest().getRemoteUser());
		epub.setDescription(descripcion);
		epub.setSubjects(etiquetas);
		epub.setPublication(publicacion);
		epub.setMd5(digest);

		epubManager.save(epub);
		bookStream.close();
		
		// write the file to the file specified
		//OutputStream bos = new FileOutputStream(uploadDir + fileFileName);
		
		File f = new File(uploadDir + digest);
		
		if (!f.exists()){
			OutputStream bos = new FileOutputStream(uploadDir + digest);
			int bytesRead;
			byte[] buffer = new byte[8192];
	
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
	
			bos.close();
			stream.close();
		}

		// place the data into the request for retrieval on next page
		/*
		getRequest().setAttribute("location",
				dirPath.getAbsolutePath() + Constants.FILE_SEP + fileFileName);

		String link = getRequest().getContextPath() + "/resources" + "/"
				+ getRequest().getRemoteUser() + "/";

		getRequest().setAttribute("link", link + fileFileName);
		*/

		return SUCCESS;
	}

	/**
	 * Default method - returns "input"
	 * 
	 * @return "input"
	 */
	public String execute() {
		return INPUT;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public File getFile() {
		return file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() {
		if (getRequest().getMethod().equalsIgnoreCase("post")) {
			getFieldErrors().clear();
			
			if ("".equals(fileFileName) || file == null) {
				//super.addFieldError("file",getText("errors.requiredField",new String[] { getText("uploadForm.file") }));
				addActionError(getText("uploadForm.file"));
			} else if (file.length() > 2097152) {
				addActionError(getText("maxLengthExceeded"));
			} else if (!"".equals(fileFileName) || file != null){
				if (!checkEpub(file))
					addActionError(getText("fileNotValidEpub"));
				}
			}
	}
	
	public boolean checkEpub(File file){		
		boolean resultado = true;
		
		EpubReader epubReader = new EpubReader();
		try {
			Book book = epubReader.readEpub(new FileInputStream(file));
			if (book.getTableOfContents().size() == 0 ){
				resultado = false;
			}
		} catch (Exception e) {
			resultado = false;
		}		
		return resultado;	
	}
}

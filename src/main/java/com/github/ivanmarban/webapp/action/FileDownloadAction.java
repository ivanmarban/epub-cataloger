package com.github.ivanmarban.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ivanmarban.model.Epub;
import com.github.ivanmarban.service.EpubManager;
import com.github.ivanmarban.service.GenericManager;

public class FileDownloadAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InputStream fileInputStream;
	private String downloadFileName;
	private String contentDisposition;
	private Long contentLength ;
	private String contentType;
	private Long id;
	private Epub epub;
	
	@Autowired
	private EpubManager epubManager;
    
    public void setEpubManager(GenericManager<Epub, Long> epubManager) {
        this.epubManager = (EpubManager) epubManager;
    }

	/**
	 * Will override the default in struts.xml.
	 * 
	 * @return
	 */
	public String getContentDisposition() {
		return this.contentDisposition;
	}
	
	public void setContentDisposition(String content){
		this.contentDisposition = content;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public Long getContentLength () {
		return contentLength ;
	}

	public void setContentLength (Long contentLength ) {
		this.contentLength  = contentLength ;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String download() throws Exception {

		try {
			//String file = filesPath + File.separator + getDownloadFileName();
			//setFileInputStream(new FileInputStream(file));
			
			epub = epubManager.getEpubByUser(id, getRequest().getRemoteUser());
			
			
			String downloadDir = ServletActionContext.getServletContext().getRealPath("/resources")
								+ "/"
								+ getRequest().getRemoteUser()
								+ "/";
			String fileName = downloadDir + epub.getMd5();
			
			File f = new File(fileName);
			FileInputStream fStream = new FileInputStream(fileName);
			
			setFileInputStream(fStream);
			//fStream.close();
			
			//System.out.println(id);
			
			//byte[] buffer = new byte[2048];
			//int bytesRead = fStream.read(buffer);
			
			//System.out.println(f.length());
			//System.out.println(ServletActionContext.getServletContext().getMimeType(fileName));
			
			setContentLength(f.length());
			setContentDisposition("attachment;filename=\"" + epub.getTitle() + " - " + epub.getAuthors()+".epub\"");
			setContentType("application/epub+zip");
	
			return SUCCESS;
		}catch (FileNotFoundException e){
			addActionError(getText("epubNotFound.message"));
			return "error";
		}catch (Exception e) {	
			//addActionError(e.getMessage());
			return "error";
		}
	}
}

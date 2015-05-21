package com.github.ivanmarban.webapp.action;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

public class ImageAction extends BaseAction implements ServletRequestAware {

	private static final long serialVersionUID = 1L;
	byte[] imageInByte = null;
	String imageId;
	String contentType;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public ImageAction() {
		//System.out.println("ImageAction");
	}

	public String execute() {
		return SUCCESS;
	}

	public byte[] getImageInBytes() throws Exception  {
		
		String file = ServletActionContext.getServletContext()
				.getRealPath("//resources")
				+ "/"
				+ getRequest().getRemoteUser()
				+ "/"
				+ this.imageId;
        
        InputStream stream = new FileInputStream(file);
        
        EpubReader epubReader = new EpubReader();
		Book book = epubReader.readEpub(stream);
		
		Resource coverImage = book.getCoverImage();
		
		setContentType(coverImage.getMediaType().toString());
		//System.out.println();
		
		if(coverImage != null) {
			final byte[] data = coverImage.getData();
			imageInByte = data;
		}
		
		stream.close();

		return imageInByte;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {

	}

}

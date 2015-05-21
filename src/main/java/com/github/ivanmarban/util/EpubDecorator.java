package com.github.ivanmarban.util;

import java.util.Locale;

import org.displaytag.decorator.TableDecorator;

import com.github.ivanmarban.model.Epub;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class EpubDecorator extends TableDecorator{

	public String getActions(){
		
		Locale loc = getPageContext().getRequest().getLocale();
		
		String download = LocalizedTextUtil.findDefaultText("epub.download", loc);
		String detail = LocalizedTextUtil.findDefaultText("epub.detail", loc);;
		//String delete = LocalizedTextUtil.findDefaultText("epub.delete", loc);;
		
		Epub objeto = (Epub)getCurrentRowObject();

		return "<a href=\"downloadEpub?id=" + objeto.getId() + "\">" + download + "</a> "
				+
				"<a href=\"editEpub?id=" + objeto.getId() + "\">" + detail + "</a>";

	}

}

package com.github.ivanmarban.webapp.action;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import org.apache.struts2.util.StrutsTypeConverter;

import com.github.ivanmarban.util.DateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * This class implements a Struts Type Converter and can be used by struts to convert Date's to Strings
 *
 * @author mraible
 */
public class DateConverter extends StrutsTypeConverter {

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "rawtypes" )
    public Object convertFromString(Map ctx, String[] value, Class arg2) {
        if (value[0] == null || value[0].trim().equals("")) {
            return null;
        }

        try {
            return DateUtil.convertStringToDate(value[0]);
        } catch (ParseException pe) {
            throw new TypeConversionException(pe.getMessage(), pe);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("rawtypes")
    public String convertToString(Map ctx, Object data) {
        return DateUtil.convertDateToString((Date) data);
    }
} 

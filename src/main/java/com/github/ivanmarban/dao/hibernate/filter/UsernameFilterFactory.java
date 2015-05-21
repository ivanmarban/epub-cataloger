package com.github.ivanmarban.dao.hibernate.filter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.annotations.Key;
import org.hibernate.search.filter.FilterKey;
import org.hibernate.search.filter.StandardFilterKey;

public class UsernameFilterFactory {

	private String username;

	public void setUsername(String username) {
		this.username = username;
	}

	@Key
	public FilterKey getKey() {
		StandardFilterKey key = new StandardFilterKey();
		key.addParameter(username);
		return key;
	}

	@Factory
	public Filter getFilter() {
		Query query = new TermQuery(new Term("username", username));
		return new CachingWrapperFilter(new QueryWrapperFilter(query));
	}

}

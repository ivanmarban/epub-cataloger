package com.github.ivanmarban.dao.hibernate;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.stereotype.Repository;

import com.github.ivanmarban.dao.EpubDao;
import com.github.ivanmarban.dao.SearchException;
import com.github.ivanmarban.model.Epub;

@Repository("epubDao")
public class EpubDaoHibernate extends GenericDaoHibernate<Epub, Long> implements EpubDao {
	
	public EpubDaoHibernate() {
		super(Epub.class);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Epub> getEpubsByUser(String username, String searchTerm) {
	
		Session sess = getSession();
        FullTextSession txtSession = Search.getFullTextSession(sess);

        org.apache.lucene.search.Query qry;
        try {
            qry = HibernateSearchTools.generateQuery(searchTerm, Epub.class, sess, new StandardAnalyzer(Version.LUCENE_35));
        } catch (ParseException ex) {
            throw new SearchException(ex);
        }
        org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
                Epub.class);
        
        hibQuery.enableFullTextFilter("userName").setParameter("username", username);

        return hibQuery.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Epub> getAllEpubsByUser(String username) {
		Query query = getSession().createQuery("from Epub where username = :username ");
		query.setParameter("username", username);
		return query.list();
	}


	@Override
	public Epub getEpubByUser(Long epubId, String username) {
		Query query = getSession().createQuery("from Epub where id = :id and username = :user");
		query.setParameter("id", epubId);
		query.setParameter("user",username);
		return (Epub) query.uniqueResult();
	}


	@Override
	public void deleteEpubsByUser(String username) {
		// TODO Auto-generated method stub
		Session session=getSession();  
		String hql = "delete from Epub where username= :username"; 
		session.createQuery(hql).setString("username", username).executeUpdate();
		
	}
}
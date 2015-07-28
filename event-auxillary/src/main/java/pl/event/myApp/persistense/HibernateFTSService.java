package pl.event.myApp.persistense;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pl.event.base.learning.Student;

@Service
@Qualifier("hibernateFTSService")
public class HibernateFTSService {

	@PersistenceContext
	EntityManager em;

	public List search(Class clazz, String searchItem, String... searchFields) {
		FullTextQuery fullTextQuery;
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(em);
		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(clazz).get();
		org.apache.lucene.search.Query luceneQuery = qb.keyword().fuzzy()
				.onFields(searchFields).matching(searchItem).createQuery();

		// wrap Lucene query in a javax.persistence.Query
		/*
		 * javax.persistence.Query jpaQuery = fullTextEntityManager
		 * .createFullTextQuery(luceneQuery, Student.class);
		 */
		fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery,
				clazz);
		fullTextQuery.setProjection(FullTextQuery.ID);
		// execute search
		List result = fullTextQuery.getResultList();
		return result;
	}
}

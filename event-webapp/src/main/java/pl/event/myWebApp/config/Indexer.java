package pl.event.myWebApp.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Indexer {

	@Value("${index-at-startup}")
	private Boolean indexDecision;

	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	void startIndexing() throws InterruptedException {
		System.out.println("\nIndexing Launched Index Decision : "
				+ indexDecision);
		if (indexDecision.equals(true)) {
			FullTextEntityManager fullTextEntityManager = Search
					.getFullTextEntityManager(em);
			fullTextEntityManager.createIndexer().startAndWait();
			System.out.println("\nIndexing Completed");
			return;
		}
		System.out.println("\nIndexing Aborted.");
	}
}

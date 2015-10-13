package pl.event.myApp.persistense;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pl.event.base.Base;
import pl.event.myApp.exceptions.DaoLevelException;

@Component
@Qualifier("dao")
public class DaoImpl implements Dao {

	@PersistenceContext
	EntityManager em;

	@Override
	public <T extends Base> T find(Class<T> baseClass, Long id) {
		if (baseClass == null || id == null) {
			throw new DaoLevelException("Entity class or id cannot be null");
		}
		return em.find(baseClass, id);
	}

	@Override
	public void persist(Base base) {
		if (base != null) {
			throw new DaoLevelException("Entity class or id cannot be null");
		}
		em.persist(base);
	}

	@Override
	public <T extends Base> T update(T base) {
		if (base.getId() == null) {
			throw new DaoLevelException("Entity class or id cannot be null");
		} else {
			return em.merge(base);
		}
	}

	@Override
	public <T extends Base> T saveOrUpdate(T base) {
		if (base.getId() == null) {
			this.persist(base);
			return base;
		} else {
			return update(base);
		}
	}

	@Override
	public <T extends Base> void detach(T base) {
		em.detach(base);

	}

	@Override
	public void delete(Base base) {
		em.remove(base);
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public <T extends Base> List<T> findAll(Class<T> clazz) {

		if (clazz == null) {
			throw new DaoLevelException("Class cannot be null.");
		}
		String qlString = "FROM " + clazz.getSimpleName();

		Query query = getEntityManager().createQuery(qlString);
		List entities = new ArrayList();
		List resultList = query.getResultList();
		if (CollectionUtils.isNotEmpty(resultList)) {
			Iterator<Base> it = resultList.iterator();
			while (it.hasNext()) {
				Object entity = it.next(); //
				entity = em.find(clazz, ((Base) entity).getId());
				if (entity instanceof HibernateProxy) {
					entity = ((HibernateProxy) entity)
							.getHibernateLazyInitializer().getImplementation();
				}
				entities.add(entity);
			}
		}
		return entities;
	}

	@Override
	public boolean baseExists(Class<? extends Base> clazz, Long id) {
		if (id == null) {
			throw new DaoLevelException("EntityId cannot be null");
		}
		Query query = em.createQuery("select count(entity.id) from "
				+ clazz.getSimpleName() + " entity where entity.id = ?1");
		query.setParameter(1, id);
		return ((Number) query.getSingleResult()).intValue() > 0;
	}

	@Override
	public <T extends Base> List<T> searchBaseOnFieldAndValue(
			Class<T> baseClass, String field, String value) {

		javax.persistence.criteria.CriteriaBuilder criteriaBuilder = em
				.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(baseClass);
		Root<T> fromClause = criteriaQuery.from(baseClass);
		criteriaQuery.select(fromClause);

		List<Predicate> criteria = new ArrayList<Predicate>();
		Predicate valuePredicate/* approvedPredicate */;

		valuePredicate = criteriaBuilder.like(fromClause.<String> get(field),
				"%" + value + "%");
		criteria.add(valuePredicate);

		Predicate[] predicates = new Predicate[criteria.size()];
		criteria.toArray(predicates);
		criteriaQuery.where(predicates);

		return em.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void clearEntireCache() {
		em.getEntityManagerFactory().getCache().evictAll();

	}

	@Override
	public EntityManager getEntityManager() {

		return em;
	}

	@Override
	public void clearEntityLevelCache(Class clazz) {
		em.getEntityManagerFactory().getCache().evict(clazz);

	}

	public Session getSession() {
		Session s = (Session) getEntityManager().unwrap(Session.class);
		return s;
	}

	@Override
	public boolean baseExists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends Base> List<T> executeQuery(Class clazz, String query) {
		// TODO Auto-generated method stub
		return null;
	}

}

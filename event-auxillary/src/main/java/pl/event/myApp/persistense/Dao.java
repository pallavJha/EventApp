package pl.event.myApp.persistense;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import pl.event.base.Base;

public interface Dao {

	public <T extends Base> T find(Class<T> baseClass, Long id);

	public void persist(Base base);

	/**
	 * Updates the Base. <b>NOTE:</b> The passed instance will get detached from
	 * persistence context and returned instance should be only used by the
	 * client code. Also note that passed and returned Base instance are not
	 * same.
	 * 
	 * @param Base
	 *            The Base instance which is to be updated.
	 * @return Base instance which is result of merge operation on original Base
	 *         object.
	 * @throws SystemException
	 *             if passed Base instance doesn't contain 'id' (which implies
	 *             that it is not yet saved).
	 */
	public <T extends Base> T update(T base);

	/**
	 * Either save/persists or updates the passed Base instance.
	 * 
	 * @see {@link #persist(Base)} and {@link #update(Base)}
	 * @param Base
	 * @return managed Base
	 */
	public <T extends Base> T saveOrUpdate(T base);

	public <T extends Base> void detach(T base);

	/**
	 * Deletes the Base from database.
	 */
	public void delete(Base base);

	/**
	 * Flushes the current state of BaseManager.<br/>
	 * see {@link BaseManager#flush()} for more details
	 */
	public void flush();

	/**
	 * Returns all instances of passed class from database.<br/>
	 */
	public <T extends Base> List<T> findAll(Class<T> clazz);

	public boolean baseExists(Class<? extends Base> clazz, Long id);

	/**
	 * 
	 * @param BaseClass
	 * @param field
	 * @param value
	 * @return
	 */
	public <T extends Base> List<T> searchBaseOnFieldAndValue(
			Class<T> BaseClass, String field, String value);

	public void clearEntireCache();

	/**
	 * Gets the Base manager from the persistence context.
	 * 
	 * @return the Base manager
	 */
	public EntityManager getEntityManager();

	/**
	 * 
	 * Method to clear cache for particular Base only
	 * 
	 * @param clazz
	 */
	public void clearEntityLevelCache(Class clazz);

	boolean baseExists();

	public Session getSession();
	/*
	 * 
	 * 
	 * Execute full text search query for multiple fields.
	 * 
	 * @see MultiFieldQueryParser#parse(org.apache.lucene.util.Version,
	 * String[], String[], org.apache.lucene.analysis.standard.Analyzer)
	 * 
	 * @param <T> the generic type
	 * 
	 * @param queries the queries
	 * 
	 * @param fields the fields
	 * 
	 * @param BaseClass the Base class
	 * 
	 * @return the list <T extends Base> List<T>
	 * executeFullTextSearchQueryForMultipleFields( String[] queries, String[]
	 * fields, Class<T> BaseClass);
	 * 
	 * Updates a particular Base using the passed queryString with the values
	 * entered in the passed map
	 * 
	 * @description You can look at following example to use this method :
	 * String queryString =
	 * "UPDATE Base e SET e.field1 = :param1 WHERE e.field2 = :param2" ;
	 * Map<String, Object> map = new HashMap<String, Object>();
	 * map.put("param1", object1); map.put("param2", object2); int count =
	 * BaseDao.updateQuery(queryString,map);
	 * 
	 * 
	 * @param queryString String
	 * 
	 * @param map Map<String, Object>
	 * 
	 * @return number of rows updated
	 * 
	 * public int updateQuery(String queryString, Map<String, Object> map);
	 * List<Map<String, Object>> executeSingleInClauseHQLQuery(String hqlQuery,
	 * String inParamName, Collection<?> values, Map<String, ?> otherParams);
	 * 
	 * <T> List<T> executeSingleInClauseHQLQuery(String hqlQuery, String
	 * inParamName, Collection<?> values, Class<T> returnClass);
	 * 
	 * <T extends Base> T saveOrHibernateUpdate(T Base);
	 * 
	 * <T extends Base> T hibernateSaveOrUpdate(T Base); /** Returns the next
	 * value for sequence name. <b>NOTE</b> This will throw an unchecked
	 * exception if sequence doesn't exist in database.
	 * 
	 * @param sequenceName the name of sequence for which the value is to be
	 * returned.
	 * 
	 * @return The returned value.
	 * 
	 * public Long getNextValue(String sequenceName);
	 * 
	 * public Long getNextValue(String sequenceName, int seqIncr);
	 */

}

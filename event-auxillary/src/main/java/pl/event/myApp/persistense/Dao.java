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

	public <T extends Base> T update(T base);

	public <T extends Base> T saveOrUpdate(T base);

	public <T extends Base> void detach(T base);

	public void delete(Base base);

	public void flush();

	public <T extends Base> List<T> findAll(Class<T> clazz);

	public boolean baseExists(Class<? extends Base> clazz, Long id);

	public <T extends Base> List<T> searchBaseOnFieldAndValue(
			Class<T> BaseClass, String field, String value);

	public void clearEntireCache();

	public EntityManager getEntityManager();

	public void clearEntityLevelCache(Class clazz);

	boolean baseExists();

	public Session getSession();

	public <T extends Base> List<T> executeQuery(Class clazz, String query);

}

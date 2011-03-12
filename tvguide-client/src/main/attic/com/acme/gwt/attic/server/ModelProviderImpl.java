import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;

import com.google.gwt.user.server.rpc.HybridServiceServlet;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import net.sf.cglib.beans.BeanMap;

public class ModelProviderImpl extends HybridServiceServlet
		implements
			ModelProvider {
	public static final EntityManagerFactory EMF = Persistence
			.createEntityManagerFactory("laptopsanytime-ops");

	/**
	 * security rules:
	 * if the id contains slashes, then the object is secure.
	 * the PartnerAccess must be admin, or have a KioskAssignment prefix the same as a real Kiosk with a real slash based path Id.
	 *
	 * @param classname
	 * @param id
	 * @param sid
	 * @return
	 */
	@Override
	public Map<String, String> find(String classname, String id, String sid) {
		//establishes a security root here via app-sepcific model objects in
		// embedded-land

		final PartnerAccess partnerAccess;
		ValidationMethod validationMethod = new ValidationMethod(sid).invoke();
		PartnerSession session = validationMethod.getSession();
		partnerAccess = validationMethod.getPartnerAccess();
		EntityManager em = validationMethod.getEm();
		boolean permitted = !id.contains("/") || partnerAccess.isAdmin();

		secure : if (!permitted) {
			for (KioskAssignment kioskAssignment : partnerAccess.kioskAssignments) {

				for (Kiosk o : kioskAssignment.kiosks) {
					if (id.startsWith(o.getId())) {
						permitted = true;
						break secure;
					}
				}
			}
		}

		final Object o;
		Class<?> entityClass;
		try {
			try {
				entityClass = Class.forName(classname);
			} catch (ClassNotFoundException e) {
				entityClass = Class
						.forName("laptopsanytime.model." + classname);
			}
			o = em.find(entityClass, id);
		} catch (ClassNotFoundException e) {
			Logger.getAnonymousLogger().warning(
					Arrays.toString(e.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
			return null;
		}
		Map<String, String> r = new TreeMap<String, String>();
		final Set refSet = new TreeSet();

		Map<String, Class> aggregates = new TreeMap<String, Class>();
		Map<String, Class> linkMap = new TreeMap<String, Class>();
		for (Method method : entityClass.getDeclaredMethods()) {
			try {
				final String mName = method.getName();
				if (mName.startsWith("get")) {
					final String baseFieldName = new StringBuilder().append(
							Character.toLowerCase(mName.charAt(3))).append(
							mName.substring(4)).toString();

					Field field = entityClass.getDeclaredField(baseFieldName);
					if (field.getAnnotation(OneToOne.class) == null
							&& field.getAnnotation(ManyToOne.class) == null
							&& field.getAnnotation(OneToMany.class) == null
							&& field.getAnnotation(ManyToMany.class) == null
							&& field.getAnnotation(XStreamOmitField.class) == null) {
						refSet.add(field.getName());
					} else if (field.getAnnotation(ManyToMany.class) != null
							|| field.getAnnotation(OneToMany.class) != null) {

						try {

							Type genericFieldType = field.getGenericType();

							if (genericFieldType instanceof ParameterizedType) {
								ParameterizedType aType = (ParameterizedType) genericFieldType;
								Type[] fieldArgTypes = aType
										.getActualTypeArguments();
								for (Type fieldArgType : fieldArgTypes) {
									Class fieldArgClass = (Class) fieldArgType;
									System.err.println("@" + field.getName()
											+ "@" + fieldArgClass.getName());
									aggregates.put(field.getName(),
											fieldArgClass);
								}
							}

						} catch (Exception e) {
							Logger.getAnonymousLogger().warning(
									Arrays.toString(e.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
						}
					} else if (field.getAnnotation(OneToOne.class) != null) {
						linkMap.put(field.getName(), field.getType());

					}
				}
			} catch (NoSuchFieldException e) {
			}
		}

		if (o != null) {
			final Map<?, ?> map = new BeanMap(o);

			for (Object k : refSet) {
				if (map.containsKey(k)) {
					try {
						final Object value = map.get(k);
						if (!(value instanceof Iterable))
							r.put(String.valueOf(k), String
									.valueOf(value == null ? "" : value));
					} catch (Exception e) {
						Logger.getAnonymousLogger().warning(
								Arrays.toString(e.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
					}
				}
			}
			for (Map.Entry<String, Class> entry : aggregates.entrySet()) {
				final String k = entry.getKey();
				final Class entity = entry.getValue();
				final Iterable value = (Iterable) map.get(k);

				List s = new ArrayList();
				for (Object o1 : value) {
					try {
						final Method declaredMethod = entity
								.getDeclaredMethod("getId");
						s.add(String.valueOf(declaredMethod.invoke(o1)));
					} catch (NoSuchMethodException e) {
						Logger.getAnonymousLogger().warning(
								Arrays.toString(e.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
					} catch (InvocationTargetException e) {
						Logger.getAnonymousLogger().warning(
								Arrays.toString(e.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
					} catch (IllegalAccessException e) {
						Logger.getAnonymousLogger().warning(
								Arrays.toString(e.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
					}
				}

				r.put("@" + k + "@" + entity.getSimpleName(), Arrays.toString(s
						.toArray()));
			}
			for (Map.Entry<String, Class> entry : linkMap.entrySet()) {
				final Method declaredMethod;
				try {
					final Class entity = entry.getValue();
					declaredMethod = entity.getDeclaredMethod("getId");
					final String k = entry.getKey();
					r.put("+" + k + "+" + entity.getSimpleName(), String
							.valueOf(declaredMethod.invoke(map.get(k))));

				} catch (NoSuchMethodException e1) {
					Logger.getAnonymousLogger().warning(
							Arrays.toString(e1.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
				} catch (InvocationTargetException e1) {
					Logger.getAnonymousLogger().warning(
							Arrays.toString(e1.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
				} catch (IllegalAccessException e1) {
					Logger.getAnonymousLogger().warning(
							Arrays.toString(e1.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
				}

			}
		}
		return r;
	}

	@Override
	public boolean merge(String tabTitle, String id,
			Map<String, String> updateMap, String sid) {
		final PartnerAccess partnerAccess;
		ValidationMethod validationMethod = new ValidationMethod(sid).invoke();
		PartnerSession session = validationMethod.getSession();
		partnerAccess = validationMethod.getPartnerAccess();
		EntityManager em = validationMethod.getEm();
		em.getTransaction().begin();

		try {
			String s = MessageFormat.format("UPDATE {0} t SET ", tabTitle);
			boolean first = true;
			for (Map.Entry<String, ?> e : updateMap.entrySet()) {
				final String key = e.getKey();
				if (!first)
					s += ",";
				else
					first = false;

				s += MessageFormat.format(" t.{0} = :{0} ", key);
			}
			s += " WHERE t.id=:id";

			final Query q = em.createQuery(s);

			for (Map.Entry<String, ?> e : updateMap.entrySet()) {
				final String key = e.getKey();
				q.setParameter(key, e.getValue());
			}
			q.setParameter("id", id);
			if (1 == q.executeUpdate()) {
				em.getTransaction().commit();
				em.close();
				return true;
			}

		} catch (Exception e) {
			Logger.getAnonymousLogger().warning(
					Arrays.toString(e.getStackTrace())); //To change body of catch statement use File | Settings | File Templates.
		}
		em.getTransaction().rollback();
		em.close();
		return false;
	}

}
class ValidationMethod {
	private String sid;
	//    private PartnerAccess partnerAccess;
	private EntityManager em;
	//    private PartnerSession session;

	public ValidationMethod(String sid) {
		this.sid = sid;
	}

	//    public PartnerAccess getPartnerAccess() {
	//        return partnerAccess;
	//    }

	public EntityManager getEm() {
		return em;
	}

	//    public PartnerSession getSession() {
	//        return session;
	//    }

	public ValidationMethod invoke() {
		em = ModelProviderImpl.EMF.createEntityManager();
		//        session = em.find(PartnerSession.class, sid);
		//        if (session.getExpire().after(new Timestamp(System.currentTimeMillis())))
		//            partnerAccess = session.getPartnerAccess();
		//        else throw new Error("Session invalid");
		return this;
	}
}
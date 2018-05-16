package gkfire.hibernate.generic;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.hibernate.criterion.AssociationCriterion;
import gkfire.hibernate.generic.interfac.IGenericDao;
import gkfire.model.interfac.EntityActivate;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GenericDao<T, ID extends Serializable>
        implements IGenericDao<T, ID> {

    @Autowired
    @Qualifier("sessionFactory")
    protected SessionFactory sessionFactory;
    private final Class<T> oClass;

    public GenericDao() {
        this.oClass = ((Class) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public Class<T> getObjectClass() throws HibernateException {
        return this.oClass;
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    @Override
    public int save(T objeto) throws HibernateException {
        return ((Number) getSessionFactory().getCurrentSession().save(objeto)).intValue();
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    @Override
    public void update(T objeto) throws HibernateException {
        getSessionFactory().getCurrentSession().update(objeto);
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    @Override
    public int updateHQL(String hql) throws Exception {
        return getSessionFactory().getCurrentSession().createQuery(hql).executeUpdate();
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    @Override
    public void saveOrUpdate(T object) throws HibernateException {
        getSessionFactory().getCurrentSession().saveOrUpdate(object);
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    @Override
    public void delete(T objeto) throws HibernateException {
        if ((objeto instanceof EntityActivate)) {
            ((EntityActivate) objeto).setActive(Boolean.FALSE);
            getSessionFactory().getCurrentSession().update(objeto);
        } else {
            getSessionFactory().getCurrentSession().delete(objeto);
        }
    }

    @Override
    public List list() throws HibernateException {
        List lista = getSessionFactory().getCurrentSession().createCriteria(this.oClass).list();
        return lista;
    }

    @Override
    public List listHQL(String hql)
            throws HibernateException {
        List lista = getSessionFactory().getCurrentSession().createQuery(hql).list();
        return lista;
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        if (asc) {
            for (String nameColumn : nameColumns) {
                criteria = criteria.addOrder(Order.asc(nameColumn));
            }
        } else {
            for (String nameColumn : nameColumns) {
                criteria = criteria.addOrder(Order.desc(nameColumn));
            }
        }

        return criteria.list();
    }

    @Override
    public T getById(ID id) throws HibernateException {
        return (T) getSessionFactory().getCurrentSession().get(this.oClass, id);
    }

    @Override
    public Integer count() {
        return (Integer) getSessionFactory().getCurrentSession().createCriteria(this.oClass).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        for (Criterion item : listCriterion) {
            if ((item instanceof AssociationCriterion)) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        return criteria.list();
    }

    public Number countRestrictions(List<Criterion> listCriterion) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        for (Criterion item : listCriterion) {
            if ((item instanceof AssociationCriterion)) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        Number rowCount = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return rowCount;
    }

    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        for (Criterion item : listCriterion) {
            if ((item instanceof AssociationCriterion)) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        criteria.setMaxResults(rows);
        criteria.setFirstResult((page - 1) * rows);
        return criteria.list();
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        ProjectionList projectionList = Projections.projectionList();
        for (Projection projection : projections) {
            projectionList.add(projection);
        }

        criteria.setProjection(projectionList);
        for (Criterion item : listCriterion) {
            if ((item instanceof AssociationCriterion)) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        return criteria.list();
    }

    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections, int page, int rows) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        ProjectionList projectionList = Projections.projectionList();
        for (Projection projection : projections) {
            projectionList.add(projection);
        }

        criteria.setProjection(projectionList);
        for (Criterion item : listCriterion) {
            if ((item instanceof AssociationCriterion)) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        criteria.setMaxResults(rows);
        criteria.setFirstResult((page - 1) * rows);
        return criteria.list();
    }

    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Order> orders) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        for (Criterion item : listCriterion) {
            if ((item instanceof AssociationCriterion)) {
                AssociationCriterion association = (AssociationCriterion) item;
                criteria.createAlias(association.getProperty(), association.getAlias()).add(association.getCriterion());
            } else {
                criteria.add(item);
            }
        }
        for (Order order : orders) {
            criteria.addOrder(order);
        }
        return criteria.list();
    }

    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Projection> projections, List<Order> orders) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Order> orders, int page, int rows) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List addRestrictionsOrder(List<Criterion> listCriterion, List<Projection> projections, List<Order> orders, int page, int rows) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List addRestrictionsVariant(List variant) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);
        for (Object c : variant) {
            if ((c instanceof ProjectionList)) {
                criteria.setProjection((ProjectionList) c);
            } else {
                AliasList a;
                if ((c instanceof AliasList)) {
                    a = (AliasList) c;
                    Set<String> properties = a.keySet();
                    for (String property : properties) {
                        AliasList.AliasItem item = (AliasList.AliasItem) a.get(property);
                        criteria.createAlias(property, item.getAlias(), item.getJoinType());
                    }
                } else if ((c instanceof CriterionList)) {
                    for (Criterion item : (CriterionList) c) {
                        criteria.add(item);
                    }
                } else if ((c instanceof OrderList)) {
                    for (Order item : (OrderList) c) {
                        criteria.addOrder(item);
                    }
                } else {
                    throw new IllegalArgumentException("Illegal argument");
                }
            }
        }
        return criteria.list();
    }

    public List addRestrictionsVariant(Object... variant) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);
        for (Object c : variant) {
            if ((c instanceof ProjectionList)) {
                criteria.setProjection((ProjectionList) c);
            } else {
                AliasList a;
                if ((c instanceof AliasList)) {
                    a = (AliasList) c;
                    Set<String> properties = a.keySet();
                    for (String property : properties) {
                        AliasList.AliasItem item = (AliasList.AliasItem) a.get(property);
                        criteria.createAlias(property, item.getAlias(), item.getJoinType());
                    }
                } else if ((c instanceof CriterionList)) {
                    for (Criterion item : (CriterionList) c) {
                        criteria.add(item);
                    }
                } else if ((c instanceof OrderList)) {
                    for (Order item : (OrderList) c) {
                        criteria.addOrder(item);
                    }
                } else {
                    throw new IllegalArgumentException("Illegal argument");
                }
            }
        }
        return criteria.list();
    }

    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);
        for (Object c : variant) {
            if ((c instanceof ProjectionList)) {
                criteria.setProjection((ProjectionList) c);
            } else {
                AliasList a;
                if ((c instanceof AliasList)) {
                    a = (AliasList) c;
                    for (String property : a.keySet()) {
                        AliasList.AliasItem item = (AliasList.AliasItem) a.get(property);
                        criteria.createAlias(property, item.getAlias(), item.getJoinType());
                    }
                } else if ((c instanceof CriterionList)) {
                    for (Criterion item : (CriterionList) c) {
                        criteria.add(item);
                    }
                } else if ((c instanceof OrderList)) {
                    for (Order item : (OrderList) c) {
                        criteria.addOrder(item);
                    }
                } else {
                    throw new IllegalArgumentException("Illegal argument");
                }
            }
        }
        criteria.setMaxResults(rows);
        criteria.setFirstResult((page - 1) * rows);
        return criteria.list();
    }

    public ID nextId(ID id, String idName, boolean withDisabled) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);
        criteria.setProjection(Projections.id());
        criteria.add(Restrictions.gt(idName, id));
        if (!withDisabled) {
            criteria.add(Restrictions.eq("active", Boolean.valueOf(true)));
        }
        criteria.addOrder(Order.asc(idName));
        criteria.setMaxResults(1);
        return (ID) criteria.uniqueResult();
    }

    public ID previousId(ID id, String idName, boolean withDisabled) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);
        criteria.setProjection(Projections.id());
        criteria.add(Restrictions.lt(idName, id));
        if (!withDisabled) {
            criteria.add(Restrictions.eq("active", Boolean.valueOf(true)));
        }
        criteria.addOrder(Order.desc(idName));
        criteria.setMaxResults(1);
        return (ID) criteria.uniqueResult();
    }

    public Number rowNumber(ID id, boolean withDisabled) {
        String tableName = "";
        System.out.println(getSessionFactory().getClassMetadata(this.oClass).getClass());
        tableName = ((AbstractEntityPersister) getSessionFactory().getClassMetadata(this.oClass)).getTableName();
        SQLQuery sq = getSessionFactory().getCurrentSession().createSQLQuery("SELECT sq.rnum FROM (SELECT id,(row_number() OVER())  as rnum FROM " + tableName + " " + (!withDisabled ? "WHERE active=true" : "") + ") sq WHERE sq.id = :id");
        sq.setParameter("id", id);
        return (Number) sq.uniqueResult();
    }

    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(this.oClass);

        for (Criterion item : criterionList) {
            criteria.add(item);
            
        }
        if (aliasList != null) {
            for (String property : aliasList.keySet()) {
                AliasList.AliasItem item = (AliasList.AliasItem) aliasList.get(property);
                criteria.createAlias(property, item.getAlias(), item.getJoinType());
            }
        }
        Number rowCount = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return rowCount;
    }

    public List listHQL(String hql, Object... parameters) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        return query.list();
    }

    public Object getByHQL(String hql) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        return query.uniqueResult();
    }

    public Object getByHQL(String hql, Object... parameters) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        return query.uniqueResult();
    }
}

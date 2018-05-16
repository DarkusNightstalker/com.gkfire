package gkfire.hibernate.generic.interfac;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

public interface IGenericDao<T, ID extends Serializable>  extends Serializable{

    public Class<T> getObjectClass();

    public Number countRestrictions(CriterionList paramCriterionList, AliasList paramAliasList);

    public int save(T paramT);

    public void update(T paramT);

    public void saveOrUpdate(T paramT);

    public void delete(T paramT);

    public List list();

    public List listHQL(String paramString);

    public List listHQL(String paramString, Object... paramVarArgs);

    public Object getByHQL(String paramString);

    public Object getByHQL(String paramString, Object... paramVarArgs);

    public List listOrderByColumns(String[] paramArrayOfString, boolean paramBoolean);

    public T getById(ID paramID);

    public Integer count();

    public List addRestrictions(List<Criterion> paramList);

    public List addRestrictions(List<Criterion> paramList, List<Projection> paramList1);

    public List addRestrictionsOrder(List<Criterion> paramList, List<Order> paramList1);

    public List addRestrictionsOrder(List<Criterion> paramList, List<Projection> paramList1, List<Order> paramList2);

    public List addRestrictions(List<Criterion> paramList, int paramInt1, int paramInt2);

    public List addRestrictionsOrder(List<Criterion> paramList, List<Order> paramList1, int paramInt1, int paramInt2);

    public List addRestrictionsOrder(List<Criterion> paramList, List<Projection> paramList1, List<Order> paramList2, int paramInt1, int paramInt2);

    public List addRestrictions(List<Criterion> paramList, List<Projection> paramList1, int paramInt1, int paramInt2);

    public List addRestrictionsVariant(List paramList);

    public Number countRestrictions(List<Criterion> paramList);

    public List addRestrictionsVariant(Object... paramVarArgs);

    public ID nextId(ID paramID, String paramString, boolean paramBoolean);

    public ID previousId(ID paramID, String paramString, boolean paramBoolean);

    public Number rowNumber(ID paramID, boolean paramBoolean);

    public int updateHQL(String paramString) throws Exception;

    public List addRestrictionsVariant(int paramInt1, int paramInt2, Object... paramVarArgs);
}

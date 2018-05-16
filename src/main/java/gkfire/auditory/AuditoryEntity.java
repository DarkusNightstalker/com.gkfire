package gkfire.auditory;

import java.io.Serializable;
import java.util.Date;

public abstract interface AuditoryEntity<T, USER> extends Serializable
{
  public abstract T getId();
  
  public abstract void setId(T paramT);
  
  public abstract USER getCreateUser();
  
  public abstract void setCreateUser(USER paramUSER);
  
  public abstract USER getEditUser();
  
  public abstract void setEditUser(USER paramUSER);
  
  public abstract Date getCreateDate();
  
  public abstract void setCreateDate(Date paramDate);
  
  public abstract Date getEditDate();
  
  public abstract void setEditDate(Date paramDate);
}


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\auditory\AuditoryEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
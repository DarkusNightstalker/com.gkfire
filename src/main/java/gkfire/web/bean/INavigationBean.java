package gkfire.web.bean;

import java.io.Serializable;

public abstract interface INavigationBean
  extends Serializable
{
  public abstract String getContent();
  
  public abstract void setContent(String paramString);
  
  public abstract String getJavascriptMenu();
  
  public abstract void setJavascriptMenu(String paramString);
}


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\web\bean\INavigationBean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
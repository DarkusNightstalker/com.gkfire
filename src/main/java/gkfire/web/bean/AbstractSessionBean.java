/*     */ package gkfire.web.bean;
/*     */ 
/*     */ import gkfire.web.util.AbstractImport;
/*     */ import gkfire.web.util.BeanUtil;
/*     */ import gkfire.web.util.JavaScriptMessage;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSessionBean
/*     */   implements Serializable
/*     */ {
/*     */   protected ILoadable loadable;
/*     */   protected List<JavaScriptMessage> messages;
/*     */   protected List<String> permissions;
/*     */   protected AbstractImport abstractImport;
/*     */   
/*     */   public AbstractSessionBean()
/*     */   {
/*  26 */     this.messages = new ArrayList();
/*     */   }
/*     */   
/*     */   public void onLoad() {
/*     */     try {
/*  31 */       this.loadable.onLoad(false);
/*     */     }
/*     */     catch (NullPointerException localNullPointerException) {}
/*  34 */     if (BeanUtil.isAjaxRequest()) {
/*  35 */       return;
/*     */     }
/*  37 */     loadPermissions();
/*     */   }
/*     */   
/*     */   public boolean authorize(String code) {
/*  41 */     for (String item : this.permissions) {
/*  42 */       if (item.equals(code)) {
/*  43 */         return true;
/*     */       }
/*     */     }
/*  46 */     return true;
/*     */   }
/*     */   
/*     */   public String messagesToJS() {
/*  50 */     String js = "";
/*  51 */     while (!this.messages.isEmpty()) {
/*  52 */       js = js + ((JavaScriptMessage)getMessages().remove(0)).toJavaScript();
/*     */     }
/*  54 */     return js;
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract void loadPermissions();
/*     */   
/*     */ 
/*     */   public List<JavaScriptMessage> getMessages()
/*     */   {
/*  63 */     return this.messages;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMessages(List<JavaScriptMessage> messages)
/*     */   {
/*  70 */     this.messages = messages;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ILoadable getLoadable()
/*     */   {
/*  77 */     return this.loadable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLoadable(ILoadable loadable)
/*     */   {
/*  84 */     this.loadable = loadable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<String> getPermissions()
/*     */   {
/*  91 */     return this.permissions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPermissions(List<String> permissions)
/*     */   {
/*  98 */     this.permissions = permissions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public AbstractImport getAbstractImport()
/*     */   {
/* 105 */     return this.abstractImport;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAbstractImport(AbstractImport abstractImport)
/*     */   {
/* 112 */     this.abstractImport = abstractImport;
/*     */   }
/*     */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\web\bean\AbstractSessionBean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
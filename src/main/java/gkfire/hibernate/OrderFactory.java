/*     */ package gkfire.hibernate;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.faces.context.ExternalContext;
/*     */ import javax.faces.context.FacesContext;
/*     */ import org.hibernate.criterion.Order;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrderFactory
/*     */   implements Serializable
/*     */ {
/*     */   private final OrderList orderList;
/*     */   private OrderList defaultOrder;
/*     */   private final Map<String, Boolean> properties;
/*     */   private List<String> keys;
/*     */   
/*     */   public OrderFactory(OrderList initialOrderList)
/*     */   {
/*  29 */     this.defaultOrder = new OrderList();
/*  30 */     this.orderList = initialOrderList;
/*  31 */     this.properties = new HashMap();
/*  32 */     this.keys = new ArrayList();
/*     */   }
/*     */   
/*     */   public Boolean showState(String propertyName)
/*     */   {
/*  37 */     return (Boolean)this.properties.get(propertyName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int orderNumber(String propertyName)
/*     */   {
/*  48 */     return this.keys.indexOf(propertyName) + 1;
/*     */   }
/*     */   
/*     */   public void changeFromRequest() {
/*  52 */     Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
/*  53 */     String propertyName = (String)params.get("property_name");
/*  54 */     change(propertyName);
/*     */   }
/*     */   
/*     */   public void change(String propertyName) {
/*  58 */     Boolean value = (Boolean)this.properties.get(propertyName);
/*  59 */     if (value == null) {
/*  60 */       this.properties.put(propertyName, Boolean.TRUE);
/*  61 */       this.keys.add(propertyName);
/*  62 */     } else if (value.booleanValue()) {
/*  63 */       this.properties.put(propertyName, Boolean.FALSE);
/*     */     } else {
/*  65 */       this.properties.remove(propertyName);
/*  66 */       this.keys.remove(propertyName);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(String propertyName)
/*     */   {
/*  83 */     this.properties.remove(propertyName);
/*  84 */     this.keys.remove(propertyName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAll()
/*     */   {
/*  96 */     this.properties.clear();
/*  97 */     this.keys.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public OrderList make()
/*     */   {
/* 104 */     if (this.keys.isEmpty()) {
/* 105 */       return this.defaultOrder;
/*     */     }
/* 107 */     OrderList orderList = new OrderList(this.orderList);
/* 108 */     for (String property : this.keys) {
/* 109 */       orderList.add(((Boolean)this.properties.get(property)).booleanValue() ? Order.asc(property) : Order.desc(property));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 118 */     return orderList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public OrderList getDefaultOrder()
/*     */   {
/* 125 */     return this.defaultOrder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDefaultOrder(OrderList defaultOrder)
/*     */   {
/* 132 */     this.defaultOrder = defaultOrder;
/*     */   }
/*     */   
/*     */   public void setDefaultOrder(Order... defaultOrder) {
/* 136 */     this.defaultOrder = new OrderList(Arrays.asList(defaultOrder));
/*     */   }
/*     */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\hibernate\OrderFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
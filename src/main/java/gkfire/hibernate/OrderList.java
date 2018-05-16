/*    */ package gkfire.hibernate;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.hibernate.criterion.Order;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OrderList
/*    */   extends ArrayList<Order>
/*    */ {
/*    */   public OrderList() {}
/*    */   
/*    */   public OrderList(Collection<? extends Order> c)
/*    */   {
/* 23 */     super(c);
/*    */   }
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\hibernate\OrderList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
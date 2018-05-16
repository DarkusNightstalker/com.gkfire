/*    */ package gkfire.hibernate;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.hibernate.criterion.Criterion;
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
/*    */ public class CriterionList
/*    */   extends ArrayList<Criterion>
/*    */ {
/*    */   public CriterionList _add(Criterion e)
/*    */   {
/* 20 */     super.add(e);
/* 21 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\hibernate\CriterionList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
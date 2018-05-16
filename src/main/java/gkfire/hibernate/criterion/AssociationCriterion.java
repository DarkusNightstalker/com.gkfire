/*    */ package gkfire.hibernate.criterion;
/*    */ 
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.HibernateException;
/*    */ import org.hibernate.criterion.CriteriaQuery;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.engine.spi.TypedValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssociationCriterion
/*    */   implements Criterion
/*    */ {
/*    */   private boolean use;
/*    */   private final Criterion criterion;
/*    */   private final String property;
/*    */   private final String alias;
/*    */   
/*    */   public static AssociationCriterion create(Criterion criterion, String property, String alias)
/*    */   {
/* 21 */     return new AssociationCriterion(criterion, property, alias);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private AssociationCriterion(Criterion criterion, String property, String alias)
/*    */   {
/* 29 */     this.criterion = criterion;
/* 30 */     this.property = property;
/* 31 */     this.alias = alias;
/* 32 */     this.use = false;
/*    */   }
/*    */   
/*    */   public Criterion getCriterion() {
/* 36 */     return this.criterion;
/*    */   }
/*    */   
/*    */   public String getAlias() {
/* 40 */     return this.alias;
/*    */   }
/*    */   
/*    */   public String getProperty() {
/* 44 */     return this.property;
/*    */   }
/*    */   
/*    */   public String toSqlString(Criteria crtr, CriteriaQuery cq) throws HibernateException {
/* 48 */     return this.criterion.toSqlString(crtr, cq);
/*    */   }
/*    */   
/*    */   public TypedValue[] getTypedValues(Criteria crtr, CriteriaQuery cq) throws HibernateException
/*    */   {
/* 53 */     return this.criterion.getTypedValues(crtr, cq);
/*    */   }
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\hibernate\criterion\AssociationCriterion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
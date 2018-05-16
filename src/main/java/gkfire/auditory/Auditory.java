/*    */ package gkfire.auditory;
/*    */ 
/*    */ import java.util.Calendar;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Auditory
/*    */ {
/*    */   public static void make(AuditoryEntity entity, Object user)
/*    */   {
/* 22 */     if (entity.getId() == null) {
/* 23 */       entity.setCreateUser(user);
/* 24 */       entity.setCreateDate(Calendar.getInstance().getTime());
/* 25 */       entity.setEditDate(null);
/* 26 */       entity.setEditUser(null);
/*    */     } else {
/* 28 */       entity.setEditUser(user);
/* 29 */       entity.setEditDate(Calendar.getInstance().getTime());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\auditory\Auditory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
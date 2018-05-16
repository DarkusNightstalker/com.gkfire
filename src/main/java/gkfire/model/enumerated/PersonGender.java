/*    */ package gkfire.model.enumerated;
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
/*    */ public enum PersonGender
/*    */ {
/* 14 */   M("Hombre"),  F("Mujer");
/*    */   
/*    */   private final String description;
/*    */   
/*    */   private PersonGender(String description) {
/* 19 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 23 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\model\enumerated\PersonGender.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
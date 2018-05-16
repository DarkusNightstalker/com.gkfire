/*    */ package gkfire.web.converter;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.UUID;
/*    */ import java.util.WeakHashMap;
/*    */ import javax.faces.component.UIComponent;
/*    */ import javax.faces.context.FacesContext;
/*    */ import javax.faces.convert.Converter;
/*    */ import javax.faces.convert.FacesConverter;
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
/*    */ @FacesConverter("converter")
/*    */ public class GenericConverter
/*    */   implements Converter
/*    */ {
/* 25 */   private static Map<Object, String> entities = new WeakHashMap();
/*    */   
/*    */   public String getAsString(FacesContext context, UIComponent component, Object entity)
/*    */   {
/* 29 */     synchronized (entities) {
/* 30 */       if (!entities.containsKey(entity)) {
/* 31 */         String uuid = UUID.randomUUID().toString();
/* 32 */         entities.put(entity, uuid);
/* 33 */         return uuid;
/*    */       }
/* 35 */       return (String)entities.get(entity);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public Object getAsObject(FacesContext context, UIComponent component, String uuid)
/*    */   {
/* 42 */     for (Map.Entry<Object, String> entry : entities.entrySet()) {
/* 43 */       if (((String)entry.getValue()).equals(uuid)) {
/* 44 */         return entry.getKey();
/*    */       }
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\web\converter\GenericConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
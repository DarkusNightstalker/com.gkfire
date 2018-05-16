/*    */ package gkfire.web.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UTF8Filter
/*    */   implements Filter
/*    */ {
/*    */   public void init(FilterConfig fc)
/*    */     throws ServletException
/*    */   {}
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 31 */     request.setCharacterEncoding("UTF-8");
/* 32 */     response.setCharacterEncoding("UTF-8");
/*    */     try {
/* 34 */       chain.doFilter(request, response);
/*    */     } catch (Exception e) {
/* 36 */       Logger.getLogger(UTF8Filter.class.getName()).log(Level.SEVERE, null, e);
/*    */     }
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\web\filter\UTF8Filter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
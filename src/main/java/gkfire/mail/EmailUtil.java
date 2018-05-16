/*    */ package gkfire.mail;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Properties;
/*    */ import javax.mail.Authenticator;
/*    */ import javax.mail.MessagingException;
/*    */ import javax.mail.PasswordAuthentication;
/*    */ import javax.mail.Session;
/*    */ import javax.mail.Transport;
/*    */ import javax.mail.internet.InternetAddress;
/*    */ import javax.mail.internet.MimeMessage;
/*    */ import javax.mail.internet.MimeMessage.RecipientType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmailUtil
/*    */ {
/* 24 */   private String user = "danny.lopez@unas.edu.pe";
/* 25 */   private String password = "Bker1989";
/*    */   private String title;
/*    */   private String content;
/*    */   
/*    */   public EmailUtil()
/*    */   {
/* 31 */     this.user = "danny.lopez@unas.edu.pe";
/* 32 */     this.password = "Bker1989";
/*    */   }
/*    */   
/*    */   public EmailUtil(String user, String password) {
/* 36 */     this.user = user;
/* 37 */     this.password = password;
/*    */   }
/*    */   
/*    */   public void setContent(String content) {
/* 41 */     this.content = content;
/*    */   }
/*    */   
/*    */   public void setTitleMessage(String title) {
/* 45 */     this.title = title;
/*    */   }
/*    */   
/*    */   public void sendEmailMultiple(String[] emails)
/*    */   {
/* 50 */     Properties props = new Properties();
/* 51 */     props.put("mail.smtp.host", "smtp.outlook.com");
/* 52 */     props.put("mail.smtp.auth", "true");
/* 53 */     props.put("mail.smtp.starttls.enable", "true");
/* 54 */     props.put("mail.smtp.port", "587");
/*    */     
/* 56 */     Session session = Session.getDefaultInstance(props, new Authenticator()
/*    */     {
/*    */       protected PasswordAuthentication getPasswordAuthentication() {
/* 59 */         String username = EmailUtil.this.user;
/* 60 */         String pass = EmailUtil.this.password;
/* 61 */         return new PasswordAuthentication(username, pass);
/*    */       }
/*    */     });
/*    */     
/*    */     try
/*    */     {
/* 67 */       MimeMessage message = new MimeMessage(session);
/*    */       
/*    */ 
/* 70 */       message.setFrom(new InternetAddress(this.user));
/*    */       
/* 72 */       InternetAddress[] addressTo = new InternetAddress[emails.length];
/* 73 */       for (int i = 0; i < emails.length; i++) {
/* 74 */         addressTo[i] = new InternetAddress(emails[i]);
/*    */       }
/* 76 */       message.setRecipients(MimeMessage.RecipientType.TO, addressTo);
/*    */       
/*    */ 
/*    */ 
/* 80 */       message.setSubject(this.title);
/*    */       
/*    */ 
/* 83 */       message.setContent("<html><body>ESTUPIDO</body></html>", "text/html");
/*    */       
/*    */ 
/*    */ 
/*    */ 
/* 88 */       Transport.send(message);
/* 89 */       System.out.println("Sent message successfully....");
/*    */     } catch (MessagingException mex) {
/* 91 */       mex.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\com.gkfire-1.0.jar!\gkfire\mail\EmailUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
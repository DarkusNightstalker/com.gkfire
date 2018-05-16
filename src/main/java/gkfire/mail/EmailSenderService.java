 package gkfire.mail;
 
 import java.io.PrintStream;
 import java.util.Properties;
 import javax.mail.Address;
 import javax.mail.Message.RecipientType;
 import javax.mail.MessagingException;
 import javax.mail.Session;
 import javax.mail.Transport;
 import javax.mail.internet.AddressException;
 import javax.mail.internet.InternetAddress;
 import javax.mail.internet.MimeMessage;
 
 
 
 
 public class EmailSenderService
 {
   private Properties properties;
   private String password = "70060965";
   private String titleMessage = "CLIENTE POTENCIAL";
   private String contentHTML = "<h1>HELLO</h1>";
   private String[] emails = new String[0];
   private Session session;
   
   private void init() {
     this.properties = new Properties();
     this.properties.put("mail.smtp.host", "smtp.gmail.com");
     this.properties.put("mail.smtp.starttls.enable", "true");
     this.properties.put("mail.smtp.port", Integer.valueOf(587));
     this.properties.put("mail.smtp.mail.sender", "bker1989@gmail.com");
     this.properties.put("mail.smtp.user", "bker1989@gmail.com");
     this.properties.put("mail.smtp.auth", "true");
     
     this.session = Session.getDefaultInstance(this.properties);
   }
   
 
 
 
 
   public void setEmails(String[] emails)
   {
     this.emails = emails;
   }
   
   private Address[] getAddresses() throws AddressException {
     Address[] add = new Address[this.emails.length];
     for (int i = 0; i < add.length; i++) {
       add[i] = new InternetAddress(this.emails[i]);
     }
     return add;
   }
   
   public void setTitleMessage(String titleMessage) {
     this.titleMessage = titleMessage;
   }
   
   public void setContentHTML(String contentHTML) {
     this.contentHTML = contentHTML;
   }
   
   public void sendEmail() {
     System.out.println("Enviando Correo");
     init();
     System.out.println("Inicializando");
     try {
       MimeMessage message = new MimeMessage(this.session);
       message.setFrom(new InternetAddress((String)this.properties.get("mail.smtp.mail.sender")));
       
       message.addRecipients(RecipientType.TO, getAddresses());
       message.setSubject(this.titleMessage);
       message.setContent(this.contentHTML, "text/html");
       Transport t = this.session.getTransport("smtp");
       t.connect((String)this.properties.get("mail.smtp.user"), this.password);
       t.sendMessage(message, message.getAllRecipients());
       t.close();
       System.out.println("FinalizandoEnvio");
     } catch (MessagingException me) {
       System.out.println("Error");
       me.printStackTrace();
     }
   }
 }

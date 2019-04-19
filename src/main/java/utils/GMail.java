package utils;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class GMail {
	
	//Author: Shadab Ansari, www.TeachMeSelenium.com
	//Method to read emails from GMail without opening GUI by using IMAP protocol.
	//Modify code accordingly as per your requirements
	//First download JavaMail API - https://java.net/projects/javamail/pages/Home
	//Include javax.mail.jar in Project's build path
	public void automateGmailUsingImap(final String USERNAME, final String PASSWORD) {
	 Folder folder = null;
	    Store store = null;
	    System.out.println("***Reading mailbox...");
	    try {
	      Properties props = new Properties();
	      props.put("mail.store.protocol", "imaps");
	      Session session = Session.getInstance(props);
	      store = session.getStore("imaps");
	      store.connect("imap.gmail.com", USERNAME, PASSWORD);
	      folder = store.getFolder("INBOX");
	      folder.open(Folder.READ_ONLY);
	      Message[] messages = folder.getMessages();
	      System.out.println("No of Messages : " + folder.getMessageCount());
	      System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
	      for (int i=0; i < messages.length-1; i++) {
	        System.out.println("Reading MESSAGE # " + (i + 1) + "...");
	        Message msg = messages[i];
	        String strMailSubject ="", strMailBody ="";
	        //Getting mail subject
	        Object subject = msg.getSubject();
	        //Getting mail body
	        Object content = msg.getContent();
	        //Casting objects of mail subject and body into String
	        strMailSubject = (String)subject;
	        strMailBody = (String)content;
	        //Printing mail subject and body
	        System.out.println(strMailSubject);
	        System.out.println(strMailBody);
	        //For debugging get only 10 emails
	        //if(i>=9)
	        // break;
	      }
	    }catch(MessagingException messagingException){
	    	messagingException.printStackTrace();
	    }catch(IOException ioException){
	    	ioException.printStackTrace();
	    }finally {
	      if (folder != null) {
	    	  try {
				folder.close(true);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      if (store != null) {
	    	  try {
				store.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	    }
	}}
 
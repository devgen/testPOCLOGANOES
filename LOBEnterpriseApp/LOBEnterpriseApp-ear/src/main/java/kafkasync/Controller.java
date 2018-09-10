package kafkasync;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class Controller {
	
    public void saveText(String message) {
    	SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        create(session, message);
        session.close();
    }
    
    private void create(Session session, String message) {
        Text text = new Text();        
        text.setText(message);      
        session.beginTransaction();        
        session.save(text);
        session.getTransaction().commit();
    }
}
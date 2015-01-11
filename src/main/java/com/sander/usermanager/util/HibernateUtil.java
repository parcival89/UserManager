package com.sander.usermanager.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by Sander on 10/01/2015.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static final String CONFIG_URL = "hibernate.cfg.xml";

    private static SessionFactory buildSessionFactory() {
        try{
            Configuration configuration = new Configuration();
            //create the sessionFactory from the hibernate.cfg.xml
            configuration.configure(CONFIG_URL);
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            return configuration.buildSessionFactory(ssrb.build());
        }catch(Throwable ex){
            //Make sure you log the exception as it might be swallowed
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void shutdown() {
        //Close caches and connection pools
        getSessionFactory().close();
    }
}
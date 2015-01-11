package com.sander.usermanager.repository;

import com.sander.usermanager.domain.Gebruiker;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static com.sander.usermanager.util.HibernateUtil.*;

/**
 * Created by Sander on 10/01/2015.
 */
public class GebruikerRepository implements UserManagerRepository<Gebruiker> {

    @Override
    public List<Gebruiker> zoek(Gebruiker o) {
        Session sessie = getSessieEnOpenTransactie();

        List<Gebruiker> gevonden = sessie.createCriteria(Gebruiker.class)
                .add(Restrictions.eq("id", o.getId()))
                .add(Restrictions.or(Restrictions.like("naam", o.getNaam())))
                .list();

        commitEnSluitSessie(sessie);

        return gevonden;
    }

    @Override
    public Gebruiker zoekMetId(Long id) {
        Session sessie = getSessieEnOpenTransactie();

        Gebruiker gezocht = (Gebruiker) sessie.createCriteria(Gebruiker.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        commitEnSluitSessie(sessie);

        return gezocht;
    }

    @Override
    public boolean bestaat(Gebruiker o) {
        Session sessie = getSessieEnOpenTransactie();
        boolean gevonden = !sessie
                .createCriteria(Gebruiker.class)
                .add(Restrictions.eq("naam", o.getNaam())).list()
                .isEmpty();

        commitEnSluitSessie(sessie);

        return gevonden;
    }

    @Override
    public void verwijder(Gebruiker o) {
        Session sessie = getSessieEnOpenTransactie();

        sessie.delete(o);

        commitEnSluitSessie(sessie);
    }

    @Override
    public void bewaarOfWerkBij(Gebruiker o) {
        Session sessie = getSessieEnOpenTransactie();

        //TODO (Sander Paulus: 10/1/2015): Exception Handling
        sessie.saveOrUpdate(o);

        commitEnSluitSessie(sessie);
    }

    // **********************************************
    // *  Klein beetje abstractie voor DB functies  *
    // **********************************************
    private Session getSessieEnOpenTransactie(){
        Session sessie = getSessionFactory().openSession();
        sessie.beginTransaction();

        return sessie;
    }

    private void commitEnSluitSessie(Session sessie){
        sessie.getTransaction().commit();

        sessie.close();
    }
}

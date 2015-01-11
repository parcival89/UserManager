package com.sander.usermanager.repository;

import com.sander.usermanager.domain.Gebruiker;
import com.sander.usermanager.util.HibernateUtil;
import junit.framework.TestCase;
import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.sander.usermanager.util.HibernateUtil.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GebruikerRepositoryTest {

    private GebruikerRepository repository = new GebruikerRepository();
    private Gebruiker sander;

    @Before
    public void setUp() throws Exception {
        maakGebruikerTabelLeeg();
        sander = new Gebruiker("Sander");
    }

    @After
    public void tearDown() throws Exception {
        maakGebruikerTabelLeeg();
    }

    private void maakGebruikerTabelLeeg(){
        List<Gebruiker> bewaarderGebruikers = getSessionFactory().openSession().createCriteria(Gebruiker.class).list();

        getSessionFactory().getCurrentSession().beginTransaction();

        for(Gebruiker gebruiker: bewaarderGebruikers){
            getSessionFactory().getCurrentSession().delete(gebruiker);
        }

        getSessionFactory().getCurrentSession().getTransaction().commit();

        getSessionFactory().getCurrentSession().close();
    }

    @Test
    public void testZoek() throws Exception {
        List<Gebruiker> gevonden = null;

        //voeg testgebruiker toe om op te kunnen zoeken
        Gebruiker toegevoegd = voegTestGebruikerAanDBToe();

        gevonden = repository.zoek(toegevoegd);
        assertThat(gevonden.isEmpty(), is(false));
        assertThat(gevonden.get(0).equals(toegevoegd), is(true));
    }

    @Test
    public void testZoekMetId() throws Exception {
        Gebruiker tester = null;

        tester = repository.zoekMetId(1L);
        assertThat(tester, is(nullValue()));

        //voeg testgebruiker toe om op te kunnen zoeken
        Gebruiker toegevoegd = voegTestGebruikerAanDBToe();

        tester = repository.zoekMetId(1L);
        assertThat(tester, not(is(nullValue())));
        assertThat(tester.equals(toegevoegd), is(true));
    }

    private Gebruiker voegTestGebruikerAanDBToe(){
        Gebruiker joren = new Gebruiker("Joren");

        repository.bewaarOfWerkBij(joren);

        return joren;
    }

    @Test
    public void testBestaat() throws Exception {
        //Eerst controleren dat sander niet gevonden wordt zonder toevoegen
        boolean gevonden = repository.bestaat(sander);
        assertThat(gevonden, is(false));

        //bewaar sander
        repository.bewaarOfWerkBij(sander);

        gevonden = repository.bestaat(sander);
        assertThat(gevonden, is(true));
    }

    @Test
    public void testBewaarNieuweGebruiker() throws Exception {
        repository.bewaarOfWerkBij(sander);

        assertThat(sander.getId(), is(not(nullValue())));

        //Kijk DB na op nieuw Gebruiker-object
        List<Gebruiker> bewaardeGebruikers = getSessionFactory().openSession().createCriteria(Gebruiker.class).list();
        assertThat(bewaardeGebruikers.size(), is(1));
        assertThat(bewaardeGebruikers.get(0).getNaam(), is("Sander"));
        getSessionFactory().getCurrentSession().close();
    }

    @Test
    public void testWerkBestaandeGebruikerBij() throws Exception{
        repository.bewaarOfWerkBij(sander);

        sander.setNaam("Joren");

        repository.bewaarOfWerkBij(sander);

        //Kijk DB na op nieuw Gebruiker-object
        List<Gebruiker> bewaardeGebruikers = getSessionFactory().openSession().createCriteria(Gebruiker.class).list();
        assertThat(bewaardeGebruikers.size(), is(1));
        assertThat(bewaardeGebruikers.get(0).getNaam(), is("Joren"));
        getSessionFactory().getCurrentSession().close();
    }

    @Test
    public void testVerwijder() throws Exception {
        //Maak nieuwe gebruiker aan in DB
        repository.bewaarOfWerkBij(sander);

        //Verwijder gebruiker
        repository.verwijder(sander);

        //Kijk DB na op nieuw Gebruiker-object
        assertThat(getSessionFactory().openSession().createCriteria(Gebruiker.class).list().isEmpty(), is(true));
        getSessionFactory().getCurrentSession().close();
    }
}
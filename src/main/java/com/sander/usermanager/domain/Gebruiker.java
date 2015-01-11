package com.sander.usermanager.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Sander on 10/01/2015.
 * Basic User Class
 */

@Entity
public class Gebruiker {

    @Id
    @GeneratedValue
    private Long id;

    private String naam;

    //Nodig voor Hibernate
    public Gebruiker() {
    }

    public Gebruiker(String naam) {
        this.naam = naam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.naam)
                .toHashCode();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != getClass()) {
            return false;
        }

        Gebruiker andere = (Gebruiker) o;
        return new EqualsBuilder()
                .append(id, andere.id)
                .append(naam, andere.naam)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("naam", this.naam)
                .toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AdhityaWP
 */
@Entity
@Table(name = "tb_m_national_holiday")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NationalHoliday.findAll", query = "SELECT n FROM NationalHoliday n")
    , @NamedQuery(name = "NationalHoliday.findById", query = "SELECT n FROM NationalHoliday n WHERE n.id = :id")
    , @NamedQuery(name = "NationalHoliday.findByDate", query = "SELECT n FROM NationalHoliday n WHERE n.date = :date")
    , @NamedQuery(name = "NationalHoliday.findByDescription", query = "SELECT n FROM NationalHoliday n WHERE n.description = :description")})
public class NationalHoliday implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Size(max = 80)
    @Column(name = "DESCRIPTION")
    private String description;

    public NationalHoliday() {
    }

    public NationalHoliday(String id) {
        this.id = id;
    }

    public NationalHoliday(String id, Date date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NationalHoliday)) {
            return false;
        }
        NationalHoliday other = (NationalHoliday) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.LeaveRequest.LeaveRequest.entities.NationalHoliday[ id=" + id + " ]";
    }
    
}

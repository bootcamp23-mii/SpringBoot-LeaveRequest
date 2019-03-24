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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_t_request_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestStatus.findAll", query = "SELECT r FROM RequestStatus r")
    , @NamedQuery(name = "RequestStatus.findById", query = "SELECT r FROM RequestStatus r WHERE r.id = :id")
    , @NamedQuery(name = "RequestStatus.findByDatetime", query = "SELECT r FROM RequestStatus r WHERE r.datetime = :datetime")
    , @NamedQuery(name = "RequestStatus.findByDescription", query = "SELECT r FROM RequestStatus r WHERE r.description = :description")})
public class RequestStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ID")
    private String id;
    @Column(name = "DATETIME")
    @Temporal(TemporalType.DATE)
    private Date datetime;
    @Size(max = 80)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "REQUEST", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Request request;
    @JoinColumn(name = "STATUS", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    public RequestStatus() {
    }

    public RequestStatus(String id) {
        this.id = id;
    }

    public RequestStatus(String id, Date datetime, String description, Request request, Status status) {
        this.id = id;
        this.datetime = datetime;
        this.description = description;
        this.request = request;
        this.status = status;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        if (!(object instanceof RequestStatus)) {
            return false;
        }
        RequestStatus other = (RequestStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.LeaveRequest.LeaveRequest.entities.RequestStatus[ id=" + id + " ]";
    }
    
}

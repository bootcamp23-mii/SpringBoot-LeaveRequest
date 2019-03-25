/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Panji Sadewo
 */
@Entity
@Table(name = "tb_m_married_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarriedStatus.findAll", query = "SELECT m FROM MarriedStatus m")
    , @NamedQuery(name = "MarriedStatus.findById", query = "SELECT m FROM MarriedStatus m WHERE m.id = :id")
    , @NamedQuery(name = "MarriedStatus.findByStatus", query = "SELECT m FROM MarriedStatus m WHERE m.status = :status")})
public class MarriedStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ID")
    private String id;
    @Size(max = 30)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "marriedstatus", fetch = FetchType.LAZY)
    private List<Employee> employeeList;

    public MarriedStatus() {
    }

    public MarriedStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
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
        if (!(object instanceof MarriedStatus)) {
            return false;
        }
        MarriedStatus other = (MarriedStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.LeaveRequest.LeaveRequest.entities.MarriedStatus[ id=" + id + " ]";
    }
    
}

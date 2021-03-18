/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anacastillo
 */
@Entity
@Table(name = "cultivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cultivo.findAll", query = "SELECT c FROM Cultivo c")
    , @NamedQuery(name = "Cultivo.findByIdCultivo", query = "SELECT c FROM Cultivo c WHERE c.idCultivo = :idCultivo")
    , @NamedQuery(name = "Cultivo.findByTipoCultivo", query = "SELECT c FROM Cultivo c WHERE c.tipoCultivo = :tipoCultivo")
    , @NamedQuery(name = "Cultivo.findByCiclo", query = "SELECT c FROM Cultivo c WHERE c.ciclo = :ciclo")})
public class Cultivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cultivo")
    private Integer idCultivo;
    @Basic(optional = false)
    @Column(name = "tipo_cultivo")
    private String tipoCultivo;
    @Basic(optional = false)
    @Column(name = "ciclo")
    private String ciclo;

    public Cultivo() {
    }

    public Cultivo(Integer idCultivo) {
        this.idCultivo = idCultivo;
    }

    public Cultivo(Integer idCultivo, String tipoCultivo, String ciclo) {
        this.idCultivo = idCultivo;
        this.tipoCultivo = tipoCultivo;
        this.ciclo = ciclo;
    }

    public Integer getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(Integer idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(String tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCultivo != null ? idCultivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cultivo)) {
            return false;
        }
        Cultivo other = (Cultivo) object;
        if ((this.idCultivo == null && other.idCultivo != null) || (this.idCultivo != null && !this.idCultivo.equals(other.idCultivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cultivo[ idCultivo=" + idCultivo + " ]";
    }
    
}

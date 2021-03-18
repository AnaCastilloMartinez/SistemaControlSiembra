/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author anacastillo
 */
@Entity
@Table(name = "control_deshierbe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlDeshierbe.findAll", query = "SELECT c FROM ControlDeshierbe c")
    , @NamedQuery(name = "ControlDeshierbe.findByIdDeshierbe", query = "SELECT c FROM ControlDeshierbe c WHERE c.idDeshierbe = :idDeshierbe")
    , @NamedQuery(name = "ControlDeshierbe.findByNumeroHectareas", query = "SELECT c FROM ControlDeshierbe c WHERE c.numeroHectareas = :numeroHectareas")
    , @NamedQuery(name = "ControlDeshierbe.findByUbicacion", query = "SELECT c FROM ControlDeshierbe c WHERE c.ubicacion = :ubicacion")
    , @NamedQuery(name = "ControlDeshierbe.findByIdUsuario", query = "SELECT c FROM ControlDeshierbe c WHERE c.idUsuario = :idUsuario")})
public class ControlDeshierbe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_deshierbe")
    private Integer idDeshierbe;
    @Basic(optional = false)
    @Column(name = "numero_hectareas")
    private int numeroHectareas;
    @Basic(optional = false)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private int idUsuario;
    @JoinColumn(name = "id_detalle_deshierbe", referencedColumnName = "id_detalle_deshierbe")
    @ManyToOne(optional = false)
    private DetalleDeshierbe idDetalleDeshierbe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDeshierbe")
    private List<DetalleDeshierbe> detalleDeshierbeList;

    public ControlDeshierbe() {
    }

    public ControlDeshierbe(Integer idDeshierbe) {
        this.idDeshierbe = idDeshierbe;
    }

    public ControlDeshierbe(Integer idDeshierbe, int numeroHectareas, String ubicacion, int idUsuario) {
        this.idDeshierbe = idDeshierbe;
        this.numeroHectareas = numeroHectareas;
        this.ubicacion = ubicacion;
        this.idUsuario = idUsuario;
    }

    public Integer getIdDeshierbe() {
        return idDeshierbe;
    }

    public void setIdDeshierbe(Integer idDeshierbe) {
        this.idDeshierbe = idDeshierbe;
    }

    public int getNumeroHectareas() {
        return numeroHectareas;
    }

    public void setNumeroHectareas(int numeroHectareas) {
        this.numeroHectareas = numeroHectareas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public DetalleDeshierbe getIdDetalleDeshierbe() {
        return idDetalleDeshierbe;
    }

    public void setIdDetalleDeshierbe(DetalleDeshierbe idDetalleDeshierbe) {
        this.idDetalleDeshierbe = idDetalleDeshierbe;
    }

    @XmlTransient
    public List<DetalleDeshierbe> getDetalleDeshierbeList() {
        return detalleDeshierbeList;
    }

    public void setDetalleDeshierbeList(List<DetalleDeshierbe> detalleDeshierbeList) {
        this.detalleDeshierbeList = detalleDeshierbeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeshierbe != null ? idDeshierbe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlDeshierbe)) {
            return false;
        }
        ControlDeshierbe other = (ControlDeshierbe) object;
        if ((this.idDeshierbe == null && other.idDeshierbe != null) || (this.idDeshierbe != null && !this.idDeshierbe.equals(other.idDeshierbe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ControlDeshierbe[ idDeshierbe=" + idDeshierbe + " ]";
    }
    
}

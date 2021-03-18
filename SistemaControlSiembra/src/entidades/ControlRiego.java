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
@Table(name = "control_riego")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlRiego.findAll", query = "SELECT c FROM ControlRiego c")
    , @NamedQuery(name = "ControlRiego.findByIdRiego", query = "SELECT c FROM ControlRiego c WHERE c.idRiego = :idRiego")
    , @NamedQuery(name = "ControlRiego.findByNumeroHectareas", query = "SELECT c FROM ControlRiego c WHERE c.numeroHectareas = :numeroHectareas")
    , @NamedQuery(name = "ControlRiego.findByTipoRiego", query = "SELECT c FROM ControlRiego c WHERE c.tipoRiego = :tipoRiego")
    , @NamedQuery(name = "ControlRiego.findByUbicacion", query = "SELECT c FROM ControlRiego c WHERE c.ubicacion = :ubicacion")
    , @NamedQuery(name = "ControlRiego.findByCapacidadAgua", query = "SELECT c FROM ControlRiego c WHERE c.capacidadAgua = :capacidadAgua")
    , @NamedQuery(name = "ControlRiego.findByEstado", query = "SELECT c FROM ControlRiego c WHERE c.estado = :estado")})
public class ControlRiego implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_riego")
    private Integer idRiego;
    @Basic(optional = false)
    @Column(name = "numero_hectareas")
    private int numeroHectareas;
    @Basic(optional = false)
    @Column(name = "tipo_riego")
    private String tipoRiego;
    @Basic(optional = false)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @Column(name = "capacidad_agua")
    private float capacidadAgua;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_detalle_riego", referencedColumnName = "id_detalle_riego")
    @ManyToOne(optional = false)
    private DetalleRiego idDetalleRiego;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRiego")
    private List<DetalleRiego> detalleRiegoList;

    public ControlRiego() {
    }

    public ControlRiego(Integer idRiego) {
        this.idRiego = idRiego;
    }

    public ControlRiego(Integer idRiego, int numeroHectareas, String tipoRiego, String ubicacion, float capacidadAgua) {
        this.idRiego = idRiego;
        this.numeroHectareas = numeroHectareas;
        this.tipoRiego = tipoRiego;
        this.ubicacion = ubicacion;
        this.capacidadAgua = capacidadAgua;
    }

    public Integer getIdRiego() {
        return idRiego;
    }

    public void setIdRiego(Integer idRiego) {
        this.idRiego = idRiego;
    }

    public int getNumeroHectareas() {
        return numeroHectareas;
    }

    public void setNumeroHectareas(int numeroHectareas) {
        this.numeroHectareas = numeroHectareas;
    }

    public String getTipoRiego() {
        return tipoRiego;
    }

    public void setTipoRiego(String tipoRiego) {
        this.tipoRiego = tipoRiego;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public float getCapacidadAgua() {
        return capacidadAgua;
    }

    public void setCapacidadAgua(float capacidadAgua) {
        this.capacidadAgua = capacidadAgua;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DetalleRiego getIdDetalleRiego() {
        return idDetalleRiego;
    }

    public void setIdDetalleRiego(DetalleRiego idDetalleRiego) {
        this.idDetalleRiego = idDetalleRiego;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<DetalleRiego> getDetalleRiegoList() {
        return detalleRiegoList;
    }

    public void setDetalleRiegoList(List<DetalleRiego> detalleRiegoList) {
        this.detalleRiegoList = detalleRiegoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRiego != null ? idRiego.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlRiego)) {
            return false;
        }
        ControlRiego other = (ControlRiego) object;
        if ((this.idRiego == null && other.idRiego != null) || (this.idRiego != null && !this.idRiego.equals(other.idRiego))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ControlRiego[ idRiego=" + idRiego + " ]";
    }
    
}

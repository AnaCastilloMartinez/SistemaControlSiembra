/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author anacastillo
 */
@Entity
@Table(name = "detalle_riego")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleRiego.findAll", query = "SELECT d FROM DetalleRiego d")
    , @NamedQuery(name = "DetalleRiego.findByIdDetalleRiego", query = "SELECT d FROM DetalleRiego d WHERE d.idDetalleRiego = :idDetalleRiego")
    , @NamedQuery(name = "DetalleRiego.findByFehca", query = "SELECT d FROM DetalleRiego d WHERE d.fehca = :fehca")
    , @NamedQuery(name = "DetalleRiego.findByHora", query = "SELECT d FROM DetalleRiego d WHERE d.hora = :hora")
    , @NamedQuery(name = "DetalleRiego.findByNombreTrabajador", query = "SELECT d FROM DetalleRiego d WHERE d.nombreTrabajador = :nombreTrabajador")
    , @NamedQuery(name = "DetalleRiego.findByObservaciones", query = "SELECT d FROM DetalleRiego d WHERE d.observaciones = :observaciones")})
public class DetalleRiego implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_riego")
    private Integer idDetalleRiego;
    @Basic(optional = false)
    @Column(name = "fehca")
    @Temporal(TemporalType.DATE)
    private Date fehca;
    @Basic(optional = false)
    @Column(name = "hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;
    @Basic(optional = false)
    @Column(name = "nombre_trabajador")
    private String nombreTrabajador;
    @Basic(optional = false)
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetalleRiego")
    private List<ControlRiego> controlRiegoList;
    @JoinColumn(name = "id_riego", referencedColumnName = "id_riego")
    @ManyToOne(optional = false)
    private ControlRiego idRiego;
    @JoinColumn(name = "id_hectarea", referencedColumnName = "id_hectarea")
    @ManyToOne(optional = false)
    private Hectarea idHectarea;

    public DetalleRiego() {
    }

    public DetalleRiego(Integer idDetalleRiego) {
        this.idDetalleRiego = idDetalleRiego;
    }

    public DetalleRiego(Integer idDetalleRiego, Date fehca, Date hora, String nombreTrabajador, String observaciones) {
        this.idDetalleRiego = idDetalleRiego;
        this.fehca = fehca;
        this.hora = hora;
        this.nombreTrabajador = nombreTrabajador;
        this.observaciones = observaciones;
    }

    public Integer getIdDetalleRiego() {
        return idDetalleRiego;
    }

    public void setIdDetalleRiego(Integer idDetalleRiego) {
        this.idDetalleRiego = idDetalleRiego;
    }

    public Date getFehca() {
        return fehca;
    }

    public void setFehca(Date fehca) {
        this.fehca = fehca;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<ControlRiego> getControlRiegoList() {
        return controlRiegoList;
    }

    public void setControlRiegoList(List<ControlRiego> controlRiegoList) {
        this.controlRiegoList = controlRiegoList;
    }

    public ControlRiego getIdRiego() {
        return idRiego;
    }

    public void setIdRiego(ControlRiego idRiego) {
        this.idRiego = idRiego;
    }

    public Hectarea getIdHectarea() {
        return idHectarea;
    }

    public void setIdHectarea(Hectarea idHectarea) {
        this.idHectarea = idHectarea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleRiego != null ? idDetalleRiego.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleRiego)) {
            return false;
        }
        DetalleRiego other = (DetalleRiego) object;
        if ((this.idDetalleRiego == null && other.idDetalleRiego != null) || (this.idDetalleRiego != null && !this.idDetalleRiego.equals(other.idDetalleRiego))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleRiego[ idDetalleRiego=" + idDetalleRiego + " ]";
    }
    
}

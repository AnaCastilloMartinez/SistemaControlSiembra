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
@Table(name = "detalle_deshierbe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleDeshierbe.findAll", query = "SELECT d FROM DetalleDeshierbe d")
    , @NamedQuery(name = "DetalleDeshierbe.findByIdDetalleDeshierbe", query = "SELECT d FROM DetalleDeshierbe d WHERE d.idDetalleDeshierbe = :idDetalleDeshierbe")
    , @NamedQuery(name = "DetalleDeshierbe.findByFecha", query = "SELECT d FROM DetalleDeshierbe d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DetalleDeshierbe.findByHora", query = "SELECT d FROM DetalleDeshierbe d WHERE d.hora = :hora")
    , @NamedQuery(name = "DetalleDeshierbe.findByNombreTrabajador", query = "SELECT d FROM DetalleDeshierbe d WHERE d.nombreTrabajador = :nombreTrabajador")
    , @NamedQuery(name = "DetalleDeshierbe.findByObservaciones", query = "SELECT d FROM DetalleDeshierbe d WHERE d.observaciones = :observaciones")})
public class DetalleDeshierbe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_deshierbe")
    private Integer idDetalleDeshierbe;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetalleDeshierbe")
    private List<ControlDeshierbe> controlDeshierbeList;
    @JoinColumn(name = "id_deshierbe", referencedColumnName = "id_deshierbe")
    @ManyToOne(optional = false)
    private ControlDeshierbe idDeshierbe;
    @JoinColumn(name = "id_hectarea", referencedColumnName = "id_hectarea")
    @ManyToOne(optional = false)
    private Hectarea idHectarea;

    public DetalleDeshierbe() {
    }

    public DetalleDeshierbe(Integer idDetalleDeshierbe) {
        this.idDetalleDeshierbe = idDetalleDeshierbe;
    }

    public DetalleDeshierbe(Integer idDetalleDeshierbe, Date fecha, Date hora, String nombreTrabajador, String observaciones) {
        this.idDetalleDeshierbe = idDetalleDeshierbe;
        this.fecha = fecha;
        this.hora = hora;
        this.nombreTrabajador = nombreTrabajador;
        this.observaciones = observaciones;
    }

    public Integer getIdDetalleDeshierbe() {
        return idDetalleDeshierbe;
    }

    public void setIdDetalleDeshierbe(Integer idDetalleDeshierbe) {
        this.idDetalleDeshierbe = idDetalleDeshierbe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    public List<ControlDeshierbe> getControlDeshierbeList() {
        return controlDeshierbeList;
    }

    public void setControlDeshierbeList(List<ControlDeshierbe> controlDeshierbeList) {
        this.controlDeshierbeList = controlDeshierbeList;
    }

    public ControlDeshierbe getIdDeshierbe() {
        return idDeshierbe;
    }

    public void setIdDeshierbe(ControlDeshierbe idDeshierbe) {
        this.idDeshierbe = idDeshierbe;
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
        hash += (idDetalleDeshierbe != null ? idDetalleDeshierbe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleDeshierbe)) {
            return false;
        }
        DetalleDeshierbe other = (DetalleDeshierbe) object;
        if ((this.idDetalleDeshierbe == null && other.idDetalleDeshierbe != null) || (this.idDetalleDeshierbe != null && !this.idDetalleDeshierbe.equals(other.idDetalleDeshierbe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleDeshierbe[ idDetalleDeshierbe=" + idDetalleDeshierbe + " ]";
    }
    
}

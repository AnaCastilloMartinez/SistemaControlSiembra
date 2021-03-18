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
@Table(name = "detalle_fertilizado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFertilizado.findAll", query = "SELECT d FROM DetalleFertilizado d")
    , @NamedQuery(name = "DetalleFertilizado.findByIdDetalleFertilizado", query = "SELECT d FROM DetalleFertilizado d WHERE d.idDetalleFertilizado = :idDetalleFertilizado")
    , @NamedQuery(name = "DetalleFertilizado.findByFecha", query = "SELECT d FROM DetalleFertilizado d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DetalleFertilizado.findByHora", query = "SELECT d FROM DetalleFertilizado d WHERE d.hora = :hora")
    , @NamedQuery(name = "DetalleFertilizado.findByNombreTrabajador", query = "SELECT d FROM DetalleFertilizado d WHERE d.nombreTrabajador = :nombreTrabajador")
    , @NamedQuery(name = "DetalleFertilizado.findByObservaciones", query = "SELECT d FROM DetalleFertilizado d WHERE d.observaciones = :observaciones")})
public class DetalleFertilizado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_fertilizado")
    private Integer idDetalleFertilizado;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "hora")
    @Temporal(TemporalType.DATE)
    private Date hora;
    @Basic(optional = false)
    @Column(name = "nombre_trabajador")
    private String nombreTrabajador;
    @Basic(optional = false)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_fertilizado", referencedColumnName = "id_fertilizado")
    @ManyToOne(optional = false)
    private ControlFertilizado idFertilizado;
    @JoinColumn(name = "id_hectarea", referencedColumnName = "id_hectarea")
    @ManyToOne(optional = false)
    private Hectarea idHectarea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetalleFertilizado")
    private List<ControlFertilizado> controlFertilizadoList;

    public DetalleFertilizado() {
    }

    public DetalleFertilizado(Integer idDetalleFertilizado) {
        this.idDetalleFertilizado = idDetalleFertilizado;
    }

    public DetalleFertilizado(Integer idDetalleFertilizado, Date fecha, Date hora, String nombreTrabajador, String observaciones) {
        this.idDetalleFertilizado = idDetalleFertilizado;
        this.fecha = fecha;
        this.hora = hora;
        this.nombreTrabajador = nombreTrabajador;
        this.observaciones = observaciones;
    }

    public Integer getIdDetalleFertilizado() {
        return idDetalleFertilizado;
    }

    public void setIdDetalleFertilizado(Integer idDetalleFertilizado) {
        this.idDetalleFertilizado = idDetalleFertilizado;
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

    public ControlFertilizado getIdFertilizado() {
        return idFertilizado;
    }

    public void setIdFertilizado(ControlFertilizado idFertilizado) {
        this.idFertilizado = idFertilizado;
    }

    public Hectarea getIdHectarea() {
        return idHectarea;
    }

    public void setIdHectarea(Hectarea idHectarea) {
        this.idHectarea = idHectarea;
    }

    @XmlTransient
    public List<ControlFertilizado> getControlFertilizadoList() {
        return controlFertilizadoList;
    }

    public void setControlFertilizadoList(List<ControlFertilizado> controlFertilizadoList) {
        this.controlFertilizadoList = controlFertilizadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleFertilizado != null ? idDetalleFertilizado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFertilizado)) {
            return false;
        }
        DetalleFertilizado other = (DetalleFertilizado) object;
        if ((this.idDetalleFertilizado == null && other.idDetalleFertilizado != null) || (this.idDetalleFertilizado != null && !this.idDetalleFertilizado.equals(other.idDetalleFertilizado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleFertilizado[ idDetalleFertilizado=" + idDetalleFertilizado + " ]";
    }
    
}

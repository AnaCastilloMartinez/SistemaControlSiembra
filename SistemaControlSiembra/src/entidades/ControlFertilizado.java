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
@Table(name = "control_fertilizado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlFertilizado.findAll", query = "SELECT c FROM ControlFertilizado c")
    , @NamedQuery(name = "ControlFertilizado.findByIdFertilizado", query = "SELECT c FROM ControlFertilizado c WHERE c.idFertilizado = :idFertilizado")
    , @NamedQuery(name = "ControlFertilizado.findByNumeroHectareas", query = "SELECT c FROM ControlFertilizado c WHERE c.numeroHectareas = :numeroHectareas")
    , @NamedQuery(name = "ControlFertilizado.findByUbicacion", query = "SELECT c FROM ControlFertilizado c WHERE c.ubicacion = :ubicacion")})
public class ControlFertilizado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fertilizado")
    private Integer idFertilizado;
    @Basic(optional = false)
    @Column(name = "numero_hectareas")
    private int numeroHectareas;
    @Basic(optional = false)
    @Column(name = "ubicacion")
    private String ubicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFertilizado")
    private List<DetalleFertilizado> detalleFertilizadoList;
    @JoinColumn(name = "id_detalle_fertilizado", referencedColumnName = "id_detalle_fertilizado")
    @ManyToOne(optional = false)
    private DetalleFertilizado idDetalleFertilizado;
    @JoinColumn(name = "id_insumo", referencedColumnName = "id_insumo")
    @ManyToOne(optional = false)
    private Insumo idInsumo;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public ControlFertilizado() {
    }

    public ControlFertilizado(Integer idFertilizado) {
        this.idFertilizado = idFertilizado;
    }

    public ControlFertilizado(Integer idFertilizado, int numeroHectareas, String ubicacion) {
        this.idFertilizado = idFertilizado;
        this.numeroHectareas = numeroHectareas;
        this.ubicacion = ubicacion;
    }

    public Integer getIdFertilizado() {
        return idFertilizado;
    }

    public void setIdFertilizado(Integer idFertilizado) {
        this.idFertilizado = idFertilizado;
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

    @XmlTransient
    public List<DetalleFertilizado> getDetalleFertilizadoList() {
        return detalleFertilizadoList;
    }

    public void setDetalleFertilizadoList(List<DetalleFertilizado> detalleFertilizadoList) {
        this.detalleFertilizadoList = detalleFertilizadoList;
    }

    public DetalleFertilizado getIdDetalleFertilizado() {
        return idDetalleFertilizado;
    }

    public void setIdDetalleFertilizado(DetalleFertilizado idDetalleFertilizado) {
        this.idDetalleFertilizado = idDetalleFertilizado;
    }

    public Insumo getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Insumo idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFertilizado != null ? idFertilizado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlFertilizado)) {
            return false;
        }
        ControlFertilizado other = (ControlFertilizado) object;
        if ((this.idFertilizado == null && other.idFertilizado != null) || (this.idFertilizado != null && !this.idFertilizado.equals(other.idFertilizado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ControlFertilizado[ idFertilizado=" + idFertilizado + " ]";
    }
    
}

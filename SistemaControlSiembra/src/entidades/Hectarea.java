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
@Table(name = "hectarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hectarea.findAll", query = "SELECT h FROM Hectarea h")
    , @NamedQuery(name = "Hectarea.findByIdHectarea", query = "SELECT h FROM Hectarea h WHERE h.idHectarea = :idHectarea")
    , @NamedQuery(name = "Hectarea.findByNumeroPredio", query = "SELECT h FROM Hectarea h WHERE h.numeroPredio = :numeroPredio")
    , @NamedQuery(name = "Hectarea.findByUbicaci\u00f3n", query = "SELECT h FROM Hectarea h WHERE h.ubicaci\u00f3n = :ubicaci\u00f3n")})
public class Hectarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_hectarea")
    private Integer idHectarea;
    @Basic(optional = false)
    @Column(name = "numero_predio")
    private int numeroPredio;
    @Basic(optional = false)
    @Column(name = "ubicaci\u00f3n")
    private String ubicación;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idHectarea")
    private List<DetalleFertilizado> detalleFertilizadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idHectarea")
    private List<DetalleDeshierbe> detalleDeshierbeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idHectarea")
    private List<DetalleRiego> detalleRiegoList;

    public Hectarea() {
    }

    public Hectarea(Integer idHectarea) {
        this.idHectarea = idHectarea;
    }

    public Hectarea(Integer idHectarea, int numeroPredio, String ubicación) {
        this.idHectarea = idHectarea;
        this.numeroPredio = numeroPredio;
        this.ubicación = ubicación;
    }

    public Integer getIdHectarea() {
        return idHectarea;
    }

    public void setIdHectarea(Integer idHectarea) {
        this.idHectarea = idHectarea;
    }

    public int getNumeroPredio() {
        return numeroPredio;
    }

    public void setNumeroPredio(int numeroPredio) {
        this.numeroPredio = numeroPredio;
    }

    public String getUbicación() {
        return ubicación;
    }

    public void setUbicación(String ubicación) {
        this.ubicación = ubicación;
    }

    @XmlTransient
    public List<DetalleFertilizado> getDetalleFertilizadoList() {
        return detalleFertilizadoList;
    }

    public void setDetalleFertilizadoList(List<DetalleFertilizado> detalleFertilizadoList) {
        this.detalleFertilizadoList = detalleFertilizadoList;
    }

    @XmlTransient
    public List<DetalleDeshierbe> getDetalleDeshierbeList() {
        return detalleDeshierbeList;
    }

    public void setDetalleDeshierbeList(List<DetalleDeshierbe> detalleDeshierbeList) {
        this.detalleDeshierbeList = detalleDeshierbeList;
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
        hash += (idHectarea != null ? idHectarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hectarea)) {
            return false;
        }
        Hectarea other = (Hectarea) object;
        if ((this.idHectarea == null && other.idHectarea != null) || (this.idHectarea != null && !this.idHectarea.equals(other.idHectarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Hectarea[ idHectarea=" + idHectarea + " ]";
    }
    
}

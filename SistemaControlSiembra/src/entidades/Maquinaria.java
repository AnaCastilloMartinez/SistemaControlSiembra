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
@Table(name = "maquinaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maquinaria.findAll", query = "SELECT m FROM Maquinaria m")
    , @NamedQuery(name = "Maquinaria.findByIdMaquinaria", query = "SELECT m FROM Maquinaria m WHERE m.idMaquinaria = :idMaquinaria")
    , @NamedQuery(name = "Maquinaria.findByPlaca", query = "SELECT m FROM Maquinaria m WHERE m.placa = :placa")
    , @NamedQuery(name = "Maquinaria.findByNumeroSeguro", query = "SELECT m FROM Maquinaria m WHERE m.numeroSeguro = :numeroSeguro")
    , @NamedQuery(name = "Maquinaria.findByMarca", query = "SELECT m FROM Maquinaria m WHERE m.marca = :marca")
    , @NamedQuery(name = "Maquinaria.findByTipoMaquinaria", query = "SELECT m FROM Maquinaria m WHERE m.tipoMaquinaria = :tipoMaquinaria")})
public class Maquinaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_maquinaria")
    private Integer idMaquinaria;
    @Basic(optional = false)
    @Column(name = "placa")
    private String placa;
    @Basic(optional = false)
    @Column(name = "numero_seguro")
    private int numeroSeguro;
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @Column(name = "tipo_maquinaria")
    private String tipoMaquinaria;

    public Maquinaria() {
    }

    public Maquinaria(Integer idMaquinaria) {
        this.idMaquinaria = idMaquinaria;
    }

    public Maquinaria(Integer idMaquinaria, String placa, int numeroSeguro, String marca, String tipoMaquinaria) {
        this.idMaquinaria = idMaquinaria;
        this.placa = placa;
        this.numeroSeguro = numeroSeguro;
        this.marca = marca;
        this.tipoMaquinaria = tipoMaquinaria;
    }

    public Integer getIdMaquinaria() {
        return idMaquinaria;
    }

    public void setIdMaquinaria(Integer idMaquinaria) {
        this.idMaquinaria = idMaquinaria;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getNumeroSeguro() {
        return numeroSeguro;
    }

    public void setNumeroSeguro(int numeroSeguro) {
        this.numeroSeguro = numeroSeguro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipoMaquinaria() {
        return tipoMaquinaria;
    }

    public void setTipoMaquinaria(String tipoMaquinaria) {
        this.tipoMaquinaria = tipoMaquinaria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaquinaria != null ? idMaquinaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maquinaria)) {
            return false;
        }
        Maquinaria other = (Maquinaria) object;
        if ((this.idMaquinaria == null && other.idMaquinaria != null) || (this.idMaquinaria != null && !this.idMaquinaria.equals(other.idMaquinaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Maquinaria[ idMaquinaria=" + idMaquinaria + " ]";
    }
    
}

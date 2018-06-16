package com.sensei.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Items.
 */
@Entity
@Table(name = "items")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * This is the entity id for the item 
     * this should be changed back to long to avoid conversion when used in the other service
     */
    @Column(name = "item_id")
    private Integer itemId;

    @Size(max = 250)
    @Column(name = "type", length = 250)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Items itemId(Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public Items type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Items items = (Items) o;
        if (items.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), items.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Items{" +
            "id=" + getId() +
            ", itemId='" + getItemId() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

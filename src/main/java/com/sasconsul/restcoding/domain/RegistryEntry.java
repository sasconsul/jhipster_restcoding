package com.sasconsul.restcoding.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Registry Entry.
 */
@Entity
@Table(name = "registry")
public class RegistryEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @JsonDeserialize(using = LongIgnoreDeserializer.class)
    @Column(name = "string_id")
    private Long stringId;

    @NotNull
    @Column(name = "string")
    private String string;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStringId() {
        return stringId;
    }

    public RegistryEntry stringId(Long stringId) {
        this.stringId = stringId;
        return this;
    }

    public void setStringId(Long stringId) {
        this.stringId = stringId;
    }

    public String getString() {
        return string;
    }

    public RegistryEntry string(String string) {
        this.string = string;
        return this;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistryEntry registry = (RegistryEntry) o;
        if(registry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, registry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RegistryEntry{" +
            "id=" + id +
            ", stringId='" + stringId + "'" +
            ", string='" + string + "'" +
            '}';
    }
}

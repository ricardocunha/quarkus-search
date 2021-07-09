package org.acme.search.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Immutable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity(name="vw_employee")
@Immutable
public class VwEmployee extends PanacheEntity {

    @Column
    public String fullName;

    public static List<VwEmployee> getEmployees(String fullName) {
        return VwEmployee.list("fullName like ?1", "%"+fullName+"%");
    }



}
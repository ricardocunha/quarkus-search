package org.acme.search.model;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;

@Entity
@Table
@Indexed
public class Charger extends Equipment {

    @FullTextField(analyzer = "name")
    @Column(nullable = false)
    public String chargerType;
}

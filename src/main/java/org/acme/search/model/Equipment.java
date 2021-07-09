package org.acme.search.model;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;

@Entity
@Table
@Inheritance(
        strategy = InheritanceType.JOINED
)
@Indexed
public class Equipment {

    @Id
    @GeneratedValue
    public Long id;

    @FullTextField(analyzer = "name")
    @Column(nullable = false)
    public String make;

    @FullTextField(analyzer = "name")
    @Column(nullable = false)
    public String model;

}

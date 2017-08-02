package com.joe.dating.domain;

import javax.persistence.*;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@MappedSuperclass
public class DatingEntity {
    @Column(name = "id", updatable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.uy.jlarrayoz.genericrestapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseGenericEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;
}

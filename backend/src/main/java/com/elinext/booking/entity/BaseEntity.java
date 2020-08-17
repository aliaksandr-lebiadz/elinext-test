package com.elinext.booking.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

}

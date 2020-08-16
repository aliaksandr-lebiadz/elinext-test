package com.elinext.booking.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Room extends BaseEntity{

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RoomType type;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();

    public Room(String name, RoomType type) {
        this.name = name;
        this.type = type;
    }

}

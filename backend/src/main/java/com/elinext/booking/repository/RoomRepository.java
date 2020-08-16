package com.elinext.booking.repository;

import com.elinext.booking.entity.Room;
import com.elinext.booking.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(
            "SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.reservations res " +
            "WHERE r.type = :type AND (r.reservations IS EMPTY OR (res.canceled = FALSE AND res.endDate >= CURRENT_TIMESTAMP))"
    )
    List<Room> findByType(RoomType type);

}

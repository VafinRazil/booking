package com.telegram_bot.services.booking.model.entity;

import com.telegram_bot.services.booking.util.enums.BookingStatus;
import com.telegram_bot.services.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime bookingDateTime;

    private LocalDateTime dateTimeSent;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_user"
            , joinColumns = @JoinColumn(name = "booking_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> whoBooked = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;

    public void removeUserWhoBooked(UserEntity userEntity){
        whoBooked.removeIf(user -> user.getId() == userEntity.getId());
    }

    public void addUserWhoBooked(UserEntity userEntity){
        if (whoBooked.stream().noneMatch(user -> user.getId() == userEntity.getId())){
            whoBooked.add(userEntity);
        }
    }
}

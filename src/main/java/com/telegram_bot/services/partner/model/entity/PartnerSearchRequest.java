package com.telegram_bot.services.partner.model.entity;

import com.telegram_bot.services.partner.util.enums.ApplicationStatus;
import com.telegram_bot.services.user.model.entity.UserEntity;
import com.telegram_bot.services.booking.model.entity.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class PartnerSearchRequest {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    private LocalDateTime dateTimeDispatch;

    private LocalDateTime dateTimeChangeStatus;

    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private UserEntity creator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    private UserEntity partner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

}

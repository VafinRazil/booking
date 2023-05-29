package com.telegram_bot.services.user.model.entity;

import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private long userIdInTelegram;

    @ManyToMany
    @JoinTable(name = "partner_search_request_user"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "partner_search_request_id"))
    private Set<PartnerSearchRequest> partnerSearchRequests = new HashSet<>();

    @ManyToMany(mappedBy = "whoBooked")
    private Set<Booking> booking = new HashSet<>();

    public void addPartnerSearchRequest(PartnerSearchRequest partnerSearchRequest){
        partnerSearchRequests.add(partnerSearchRequest);
    }
}

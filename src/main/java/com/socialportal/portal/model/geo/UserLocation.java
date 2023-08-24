package com.socialportal.portal.model.geo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "user_location")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLocation extends Location {
    @NotNull
    private Double radiusOfInterest;

}

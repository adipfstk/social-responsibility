package com.socialportal.portal.model.geo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

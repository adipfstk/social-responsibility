package com.socialportal.portal.pojo.request;
import com.socialportal.portal.model.geo.UserLocation;
import com.socialportal.portal.model.user.UserEntity;
import lombok.Data;


@Data
public class SignUpRequest {
   private UserEntity userEntity;
   private UserLocation geoLocation;

}

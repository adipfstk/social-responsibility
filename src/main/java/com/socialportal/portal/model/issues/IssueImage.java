package com.socialportal.portal.model.issues;

import com.socialportal.portal.model.image.ImageData;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IssueImage extends ImageData {

}

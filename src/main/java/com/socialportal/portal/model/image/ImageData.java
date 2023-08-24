package com.socialportal.portal.model.image;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    protected String type;

    @Lob
    @Column(name = "imagedata", length = 500000000)
    protected byte[] imageData;


}
package com.cg.domain.dto;

import com.cg.domain.entity.Publisher;
import com.cg.domain.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PublisherDTO {
    private Long id;
    private String title;

    public Publisher toPublisher() {
        return new Publisher()
                .setId(id)
                .setTitle(title);
    }
}

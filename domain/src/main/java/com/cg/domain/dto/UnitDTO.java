package com.cg.domain.dto;

import com.cg.domain.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UnitDTO {

    private Long id;
    private String title;

    public Unit toUnit(){
        return new Unit()
                .setId(id)
                .setTitle(title);
    }
}

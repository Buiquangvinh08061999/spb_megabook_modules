package com.cg.domain.dto;

import com.cg.domain.entity.ImportOrderStatus;
import com.cg.domain.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImportOrderStatusDTO {
    private Long id;
    private String titleEn;
    private String titleVi;

    public ImportOrderStatus toImportOrderStatus() {
        return new ImportOrderStatus()
                .setId(id)
                .setTitleEn(titleEn)
                .setTitleVi(titleVi);
    }
}

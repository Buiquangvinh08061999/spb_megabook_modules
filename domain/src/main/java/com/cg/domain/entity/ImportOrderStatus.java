package com.cg.domain.entity;

import com.cg.domain.dto.ImportOrderStatusDTO;
import com.cg.domain.dto.OrderStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "import_order_status")
public class ImportOrderStatus extends BaseEntities{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleEn;

    private String titleVi;

    @OneToMany(targetEntity = ImportOrder.class, mappedBy = "importOrderStatus" , fetch = FetchType.EAGER)
    private Set<ImportOrder> importOrders;


    public ImportOrderStatusDTO toImportOrderStatusDTO() {
        return new ImportOrderStatusDTO()
                .setId(id)
                .setTitleEn(titleEn)
                .setTitleVi(titleVi);
    }
}

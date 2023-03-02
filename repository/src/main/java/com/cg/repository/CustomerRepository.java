package com.cg.repository;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //1.Hiển thị tất cả danh sách Customer
    @Query("SELECT NEW com.cg.domain.dto.CustomerDTO(" +
                "c.id, " +
                "c.email, " +
                "c.fullName, " +
                "c.phone, " +
                "c.urlImage, " +
                "c.note, " +
                "c.createAt, " +
                "c.updateAt, " +
                "c.locationRegion" +
            ") " +
            "FROM Customer AS c " +
            "WHERE c.deleted = false " +
            "ORDER BY c.id DESC"
    )
    List<CustomerDTO> findAllCustomerDTOByDeletedIsFalse();


    //2.Kiểm tra điều kiện khi create
    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    //3.Kiểm tra điều kiện khi update
    Boolean existsByEmailAndIdIsNot(String email, Long id);

    Boolean existsByPhoneAndIdIsNot(String phone, Long id);

    //4.Tìm kiếm tất cả các trường theo từ khóa truyền vào
    @Query("SELECT NEW com.cg.domain.dto.CustomerDTO (" +
                "c.id," +
                "c.email," +
                "c.fullName," +
                "c.phone," +
                "c.locationRegion" +
            ") " +
            "FROM Customer AS c " +
            "WHERE c.deleted = false " +
            "AND CONCAT (" +
                "c.id, " +
                "c.email, " +
                "c.fullName, " +
                "c.phone , " +
                "c.locationRegion.provinceName, " +
                "c.locationRegion.districtName, " +
                "c.locationRegion.wardName" +
            ") LIKE ?1"
    )
    List<CustomerDTO> searchCustomerByAll(String keySearch);

    //Hiển thị customer theo id truyền vào
    @Query("SELECT NEW com.cg.domain.dto.CustomerDTO (" +
                "c.id, " +
                "c.email, " +
                "c.fullName, " +
                "c.phone, " +
                "c.locationRegion" +
            ") " +
            "FROM Customer AS c " +
            "WHERE c.id = ?1 "
    )
    Optional<CustomerDTO> getCustomerDTOById(Long id);


    //5.Tìm kiếm 2 trường fullname và email trong phần tạo đơn hàng, tìm kiếm khách hàng
    @Query("SELECT NEW com.cg.domain.dto.CustomerDTO(" +
                "c.id," +
                "c.email," +
                "c.fullName," +
                "c.phone," +
                "c.locationRegion" +
            ") " +
            "FROM Customer AS c " +
            "WHERE c.deleted = false " +
            "AND CONCAT(" +
                "c.email, " +
                "c.fullName " +
            ") LIKE ?1"
    )
    List<CustomerDTO> searchCustomerDTOByFullNameAndEmail(String keySearch);


    //7
    Optional<CustomerDTO> findCustomerById(Long id);
}

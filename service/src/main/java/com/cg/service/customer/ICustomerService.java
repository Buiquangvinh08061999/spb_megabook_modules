package com.cg.service.customer;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.entity.Customer;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;


public interface ICustomerService extends IGeneralService<Customer> {

    //1.Hiển thị tất cả danh sách
    List<CustomerDTO> findAllCustomerDTOByDeletedIsFalse();

    //2.Kiểm trả điều kiện khi create
    Boolean existsById(Long id);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    //3.Update dữ liệu theo Id, Kiểm tra điều kiện khi cập nhật
    Customer saveUpdate(Customer customer);

    Boolean existsByEmailAndIdIsNot(String email, Long id);

    Boolean existsByPhoneAndIdIsNot(String phone, Long id);

    //4.Tìm kiếm tất cả các trường theo từ khóa truyền vào
    List<CustomerDTO> searchCustomerByAll(String keySearch);

    Optional<CustomerDTO> getCustomerDTOById(Long id);


    //5.Tìm kiếm 2 trường fullname và email trong phần tạo đơn hàng, tìm kiếm khách hàng
    List<CustomerDTO> searchCustomerDTOByFullNameAndEmail(String keySearch);


    //7
    Optional<CustomerDTO> findCustomerById(Long id);
}

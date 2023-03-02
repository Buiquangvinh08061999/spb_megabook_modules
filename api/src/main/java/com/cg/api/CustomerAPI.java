package com.cg.api;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.dto.SearchDTO;
import com.cg.domain.entity.Customer;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICustomerService customerService;


    @GetMapping
    public ResponseEntity<?> findAllCustomerDTO() {
        List<CustomerDTO> customerDTOList = customerService.findAllCustomerDTOByDeletedIsFalse();

        if (customerDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của Customer rỗng, không có dữ liệu hiển thị");
        }

        return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {
        new CustomerDTO().validate(customerDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        customerDTO.setId(0L);
        customerDTO.getLocationRegion().setId(0L);
        customerDTO.setUrlImage("customer_icon.png");

        Boolean existsById = customerService.existsById(customerDTO.getId());
        if (existsById) {
            throw new EmailExistsException("ID đã tồn tại vui kiểm tra lại");
        }

        Boolean existsByEmail = customerService.existsByEmail(customerDTO.getEmail());
        if (existsByEmail) {
            throw new EmailExistsException("Email đã tồn tại vui lòng nhập lại!");
        }

        Boolean existsByPhone = customerService.existsByPhone(customerDTO.getPhone());
        if (existsByPhone) {
            throw new EmailExistsException("Phone đã tồn tại vui lòng nhập lại!");
        }

        try {
            Customer customer = customerDTO.toCustomer();

            Customer newCustomer = customerService.save(customer); //vào phương thức save để xử lí tiếp , lưu địa chỉ id vào customer(location_id)

            return new ResponseEntity<>(newCustomer.toCustomerDTO(), HttpStatus.CREATED);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }

    //Sổ tất cả dữ liệu để hiển thị phần edit theo id của customer truyền vào, dùng optional
    @GetMapping("/{id}")
    public ResponseEntity<?> displayCustomerById(@PathVariable long id) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy ID Customer, vui lòng kiểm tra lại");
        }

        try {

            return new ResponseEntity<>(customerOptional.get().toCustomerDTO(), HttpStatus.OK);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> doUpdate(@RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existsById = customerService.existsById(customerDTO.getId());
        if (!existsById) {
            throw new EmailExistsException("ID Customer không tồn tại vui lòng kiểm tra lại");
        }

        Optional<Customer> customerOptional = customerService.findById(customerDTO.getId()); // Lấy ra dữ liệu ngày giờ để gán lại
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy ID Customer, vui lòng kiểm tra lại");
        }

        Boolean existsByEmail = customerService.existsByEmailAndIdIsNot(customerDTO.getEmail(), customerDTO.getId());
        if (existsByEmail) {
            throw new EmailExistsException("Email đã tồn tại, Vui lòng nhập không nhập trùng email");
        }

        Boolean existsByPhone = customerService.existsByPhoneAndIdIsNot(customerDTO.getPhone(), customerDTO.getId());
        if (existsByPhone) {
            throw new EmailExistsException("Phone đã tồn tại, Vui lòng nhập không nhập trùng phone");
        }

        customerDTO.setCreateAt(customerOptional.get().getCreateAt());   //Lấy dữ liệu từ customerOptional ngày giờ ra set lại cho customerDTO, nếu không sẽ null

        try {
            Customer customer = customerDTO.toCustomer();

            Customer updateCustomer = customerService.saveUpdate(customer);

            return new ResponseEntity<>(updateCustomer.toCustomerDTO(), HttpStatus.ACCEPTED);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại");
        }

    }

    //Hàm xóa mềm Customer theo id truyền vào , dùng optional, rồi ta setDeleted(true) và dùng customerService save dữ liệu lại
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> doDeleteById(@PathVariable long id) {

        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy ID Customer, vui lòng kiểm tra lại");
        }

        customerOptional.get().setDeleted(true);

        customerService.save(customerOptional.get()); //set dữ liệu xong thì phải save lại

        try {

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại");
        }

    }

    //4.Tìm kiếm tất cả các trường theo từ khóa truyền vào(dùng Post với Search DTO truyền vào)
    @PostMapping("/search")
    public ResponseEntity<?> doSearchCustomerDTOByAll(@RequestBody SearchDTO searchDTO) {

        String keySearch = searchDTO.getKeySearch();
        keySearch = "%" + keySearch + "%";

        List<CustomerDTO> customerDTOListSearch = customerService.searchCustomerByAll(keySearch);

        if (customerDTOListSearch.isEmpty()) {
            throw new DataInputException("Không tìm thấy từ khóa tìm kiếm");
        }

        try {
            return new ResponseEntity<>(customerDTOListSearch, HttpStatus.OK);  // Trả về danh sách vừa tìm được
        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }

    }

    //5.Tìm kiếm 2 trường fullname và email trong phần tạo đơn hàng, tìm kiếm khách hàng
    @PostMapping("/search-fullName-and-email")
    public ResponseEntity<?> doSearchCustomerDTOByFullNameAndEmail(@RequestBody SearchDTO searchDTO) {

        String keySearch = searchDTO.getKeySearch();
        keySearch = "%" + keySearch + "%";

        List<CustomerDTO> customerDTOListSearch = customerService.searchCustomerDTOByFullNameAndEmail(keySearch);

        if (customerDTOListSearch.isEmpty()) {
            throw new DataInputException("Không tìm thấy từ khóa tìm kiếm");
        }

        try {
            return new ResponseEntity<>(customerDTOListSearch, HttpStatus.OK);  // Trả về danh sách vừa tìm được
        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }

}


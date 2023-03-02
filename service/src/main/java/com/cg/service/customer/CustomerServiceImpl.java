package com.cg.service.customer;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.entity.Customer;
import com.cg.domain.entity.LocationRegion;
import com.cg.repository.CustomerRepository;
import com.cg.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements ICustomerService{
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<Customer> findAll() {
        return null;
    }

    //Hiển thị tất cả danh sách khách hàng
    @Override
    public List<CustomerDTO> findAllCustomerDTOByDeletedIsFalse() {
        return customerRepository.findAllCustomerDTOByDeletedIsFalse();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }


    @Override
    public Optional<CustomerDTO> findCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer save(Customer customer) {

        LocationRegion locationRegion = locationRegionRepository.save(customer.getLocationRegion()); //Lưu tk location ở bảng cúa nó, rồi gán id của địa chỉ này vào thằng customer

        customer.setLocationRegion(locationRegion);

        return customerRepository.save(customer);

    }

    //Hàm save update Customer riêng, tránh dùng chung với create
    @Override
    public Customer saveUpdate(Customer customer) {

        LocationRegion locationRegion = locationRegionRepository.save(customer.getLocationRegion());

        customer.setLocationRegion(locationRegion);


        return customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {

    }

    //1.Kiểm tra điều kiện khi create
    @Override
    public Boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    //2.Kiểm tra điều kiện khi update
    @Override
    public Boolean existsByEmailAndIdIsNot(String email, Long id) {
        return customerRepository.existsByEmailAndIdIsNot(email, id);
    }

    @Override
    public Boolean existsByPhoneAndIdIsNot(String phone, Long id) {
        return customerRepository.existsByPhoneAndIdIsNot(phone, id);
    }

    //4.Tìm kiếm tất cả các trường theo từ khóa truyền vào
    @Override
    public List<CustomerDTO> searchCustomerByAll(String keySearch) {
        return customerRepository.searchCustomerByAll(keySearch);
    }


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
    public List<CustomerDTO> searchCustomerDTOByFullNameAndEmail(String keySearch) {
        return customerRepository.searchCustomerDTOByFullNameAndEmail(keySearch);
    }


    @Override
    public Optional<CustomerDTO> getCustomerDTOById(Long id) {
        return customerRepository.getCustomerDTOById(id);
    }
}

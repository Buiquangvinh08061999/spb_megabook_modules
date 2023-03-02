package com.cg.domain.dto;

import com.cg.domain.entity.Customer;
import com.cg.domain.entity.LocationRegion;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Valid;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO implements Validator {

    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String urlImage;
    private String note; //thông tin ghi chú

    @Valid
    private LocationRegionDTO locationRegion;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;


    public Customer toCustomer() {
        return (Customer) new Customer()
                .setId(id)
                .setEmail(email)
                .setFullName(fullName)
                .setPhone(phone)
                .setUrlImage(urlImage)
                .setNote(note)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }


    //1.Hiển thị tất cả các trường của Customer
    public CustomerDTO(Long id, String email, String fullName, String phone, String urlImage,String note, Date createAt, Date updateAt, LocationRegion locationRegion){
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.urlImage = urlImage;
        this.note = note;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    //2.Search tất cả các trường Customer
    public CustomerDTO(Long id, String email, String fullName, String phone,LocationRegion locationRegion){
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDTO customerDTO = (CustomerDTO) target;
        String email = customerDTO.getEmail();
        String fullName = customerDTO.getFullName();
        String phone = customerDTO.getPhone();
        String address = customerDTO.locationRegion.getAddress();

        if(email.trim().isEmpty()){
            errors.rejectValue("email", "email.isEmpty()","Vui lòng nhập vào Email, Email không được để trống");
            return;
        }
        if(!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$")){
            errors.rejectValue("email",  "email.matches" ,"Vui lòng nhập đúng định dạng email, (VD: txr@gmail.com)");
            return;
        }

        if(fullName.isEmpty()){
            errors.rejectValue("fullName", "fullName.isEmpty()","Vui lòng nhập vào fullName, fullName không được để trống");
            return;
        }
        if(fullName.trim().replaceAll("\\s+", "").length() < 3 || fullName.trim().replaceAll("\\s+", "").length() > 255){
            errors.rejectValue("fullName",  "fullName.length()" ,"Full Name phải nằm trong khoảng từ 3 đến 255 kí tự, Vui lòng nhập lại!");
            return;
        }
        if(!fullName.matches("^([a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+)$")){
            errors.rejectValue("fullName",  "fullName.matches" ,"Full Name không chứa kí tự đặt biệt, Vui lòng nhập lại theo đúng quy định!");
            return;
        }

        if(phone.trim().isEmpty()){
            errors.rejectValue("phone",  "phone.isEmpty" ,"Vui lòng nhập vào Phone, Phone không được để trống");
            return;
        }

        if(!phone.matches("^[0][1-9][0-9]{8,9}")){
            errors.rejectValue("phone",  "phone.matches" ,"Phone không chứa kí tự đặt biệt, nằm trong 10-11 số(VD: 0879983165");
            return;
        }

        if(address.trim().isEmpty()){
            errors.rejectValue("address",  "address.isEmpty" ,"Vui lòng nhập vào Address, Address không được để trống");
            return;
        }
        if(address.trim().replaceAll("\\s+", "").length() < 3 || address.trim().replaceAll("\\s+", "").length() > 100){
            errors.rejectValue("address",  "address.length()" ,"Address  phải nằm trong khoảng từ 3 đến 100 kí tự, Vui lòng nhập lại!");
            return;
        }
    }
}

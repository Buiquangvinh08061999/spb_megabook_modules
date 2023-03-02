package com.cg.api;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.dto.UserDTO;
import com.cg.domain.entity.Role;
import com.cg.domain.entity.User;
import com.cg.domain.enums.EUser;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.service.role.IRoleService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserAPI {
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<?> findAllUserDTO() {
        List<UserDTO> userDTOList = userService.findAllUserDTOByDeletedIsFalse();

        if (userDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của User rỗng, không có dữ liệu hiển thị");
        }

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody UserDTO userDTO, BindingResult bindingResult){

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        userDTO.setId("");
        userDTO.setUrlImage("avatar.png");

        Boolean existsByUsername = userService.existsByUsername(userDTO.getUsername());
        if(existsByUsername) {
            throw new DataInputException("Username "+ userDTO.getUsername()+ " đã tồn tại.Vui lòng nhập lại!");
        }

        Boolean existsByPhone = userService.existsByPhone(userDTO.getPhone());
        if(existsByPhone) {
            throw new DataInputException("Phone " + userDTO.getPhone() + " đã tồn tại.Vui lòng nhập lại");
        }


        Optional<Role> roleId = roleService.findById(userDTO.getRole().getId());
        if(!roleId.isPresent()){
            throw new EmailExistsException("Không tìm thấy Id role");
        }

        try{
            User newUser = userService.save(userDTO.toUser());

            return new ResponseEntity<>(newUser.toUserDTO(),  HttpStatus.CREATED);

        }catch (DataIntegrityViolationException e){
            throw new DataInputException("Lỗi ngoại lệ");
        }


    }


    @GetMapping("/list-admin")
    public ResponseEntity<?> findAllAdminDTO() {
        List<UserDTO> userDTOList = userService.findAllUserDTOByDeletedIsFalseRoleAdmin(EUser.ADMIN.getValue());

        if (userDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của Admin rỗng");
        }

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
    @GetMapping("/list-staff")
    public ResponseEntity<?> findAllStaffDTO() {
        List<UserDTO> userDTOList = userService.findAllUserDTOByDeletedIsFalseRoleStaff(EUser.STAFF.getValue());

        if (userDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của Staff rỗng");
        }

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }





}

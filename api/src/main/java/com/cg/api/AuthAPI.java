package com.cg.api;

import com.cg.domain.entity.JwtResponse;
import com.cg.domain.entity.User;
import com.cg.service.jwt.JwtService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())); /*Gửi tên username và password lên để so sánh với 2 trường này trong User user(gửi ở phần login.html)*/

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication); /*jwt tạo 1 chuỗi token để dùng ở json*/

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();/*trong UserDetails có phương thức String username và password sẳn */

        User currentUser = userService.getByUsername(user.getUsername()); /*phương thức đầu tiên của userRepo*/

        JwtResponse jwtResponse = new JwtResponse(
                jwt, /*chuổi token ở trên truyền vào*/
                currentUser.getId(), /*lấy id ở User ra*/
                userDetails.getUsername(), /*lấy tên ở UserDetails*/
                currentUser.getUsername(),  /*lấy username ở User ra*/
                userDetails.getAuthorities()
        );

        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(60 * 1000)
                .domain("localhost")
//                .domain("ajax-bank-location-jwt.herokuapp.com")
//                .domain("bank-transaction.azurewebsites.net")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);

    }

}

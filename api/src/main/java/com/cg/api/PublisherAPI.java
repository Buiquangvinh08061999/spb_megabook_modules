package com.cg.api;

import com.cg.domain.dto.PublisherDTO;
import com.cg.domain.entity.Publisher;
import com.cg.domain.entity.Supplier;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.service.publisher.IPublisherService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IPublisherService publisherService;

    @GetMapping()
    public ResponseEntity<?> findAllPubliserDTOS() {
        List<PublisherDTO> publisherDTOList = publisherService.findAllPublisherDTOSByDeletedIsFalse();

        if (publisherDTOList.isEmpty()) {
            throw new DataInputException("Danh sách nhà xuất bản trống!");
        }

        return new ResponseEntity<>(publisherDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody PublisherDTO publisherDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        publisherDTO.setId(0L);

        Boolean existsByTitle = publisherService.existsByTitle(publisherDTO.getTitle());
        if (existsByTitle) {
            throw new EmailExistsException("Tên nhà xuất bản đã tồn tại!");
        }

        try {
            Publisher publisher = publisherDTO.toPublisher();
            Publisher newPublisher = publisherService.save(publisher);
            return new ResponseEntity<>(newPublisher.toPublisherDTO(), HttpStatus.CREATED);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }
}

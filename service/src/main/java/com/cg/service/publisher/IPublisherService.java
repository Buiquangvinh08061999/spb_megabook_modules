package com.cg.service.publisher;

import com.cg.domain.dto.PublisherDTO;
import com.cg.domain.entity.Publisher;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IPublisherService extends IGeneralService<Publisher> {

    List<PublisherDTO> findAllPublisherDTOSByDeletedIsFalse();

    Boolean existsByTitle(String title);
}

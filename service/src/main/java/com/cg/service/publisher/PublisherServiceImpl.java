package com.cg.service.publisher;

import com.cg.domain.dto.PublisherDTO;
import com.cg.domain.entity.Publisher;
import com.cg.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements IPublisherService {

    @Autowired
    private PublisherRepository publisherRepository;


    @Override
    public List<Publisher> findAll() {
        return null;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    @Override
    public Publisher getById(Long id) {
        return null;
    }

    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<PublisherDTO> findAllPublisherDTOSByDeletedIsFalse() {
        return publisherRepository.findAllPublisherDTOSByDeletedIsFalse();
    }

    @Override
    public Boolean existsByTitle(String title) {
        return publisherRepository.existsByTitle(title);
    }
}

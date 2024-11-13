package kg.project.courseworkjava.service.impl;

import kg.project.courseworkjava.entity.University;
import kg.project.courseworkjava.exception.RecordNotFoundException;
import kg.project.courseworkjava.mapper.UniversityMapper;
import kg.project.courseworkjava.model.UniversityRequest;
import kg.project.courseworkjava.model.UniversityResponse;
import kg.project.courseworkjava.repos.UniversityRepos;
import kg.project.courseworkjava.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    UniversityRepos universityRepository;
    UniversityMapper universityMapper;

    @Override
    public UniversityResponse create(UniversityRequest universityRequest) {
        if (universityRepository.getByName(universityRequest.getName()) != null) {
            throw new RecordNotFoundException("Университет с таким названием уже существует!");
        }
        University university = universityMapper.requestToEntity(universityRequest);
        University savedUniversity = universityRepository.save(university);
        return universityMapper.entityToResponse(savedUniversity);
    }

    @Override
    public UniversityResponse findById(Long id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Университет с таким id не существует!"));
        return universityMapper.entityToResponse(university);
    }

    @Override
    public UniversityResponse update(UniversityRequest universityRequest, Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new RecordNotFoundException("Университет с таким id не существует"));
        universityMapper.update(university, universityRequest);
        University updatedUniversity = universityRepository.save(university);
        return universityMapper.entityToResponse(updatedUniversity);
    }

    @Override
    public List<UniversityResponse> findAll() {
        List<University> universities = universityRepository.findAll();
        return universities.stream().map(universityMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Университет с таким id не существует!"));
        universityRepository.deleteById(id);
    }
}
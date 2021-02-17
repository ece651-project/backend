package com.project.ece651.webapp.controllers;

        import com.project.ece651.webapp.entities.ApartmentEntity;
        import com.project.ece651.webapp.entities.UserEntity;
        import com.project.ece651.webapp.repositories.ApartmentRepository;
        import com.project.ece651.webapp.repositories.UserRepository;
        import com.project.ece651.webapp.shared.ApartmentDto;
        import com.project.ece651.webapp.shared.MsgDto;
        import com.project.ece651.webapp.utils.ApartmentUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.*;

        import java.util.ArrayList;
        import java.util.List;


@RestController
@RequestMapping("/apt")
public class ApartmentController {
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private UserRepository userRepository;
    /*
        Example request body:
        {
            "landlordId": "12601d30-b1f6-448f-b3bc-a9acc4802ad8",
            "type": null,
            "address": "Empty address",
            "uploadTime": null,
            "startMonth": null,
            "endMonth": "2014-02-24",
            "description": "Empty description",
            "price": 10086
        }
    */
    @PostMapping("/add_apt")
    @ResponseStatus(HttpStatus.CREATED)
    public MsgDto createApartment(@RequestBody ApartmentDto apartmentDto) {
        // sample url localhost:8080/apt/add_apt
        // add one new apartment according to the given apartment information
        MsgDto response = new MsgDto();
        // check whether the landlord is in the database
        UserEntity userEntity = userRepository.findByUid(apartmentDto.getLandlordId());
        if (userEntity != null) {
            // convert the apartmentEto to apartmentEntity
            ApartmentEntity apartmentEntity = ApartmentUtils.apartmentDtoToEntity(apartmentDto);
            // add the apartment into those owned by the user entity
            userEntity.addOwnedApartments(apartmentEntity);
            userRepository.save(userEntity);
            response.setSuccess(true);
        }
        else {
            response.setSuccess(false);
            response.setResponseMsg("Landlord of the apartment does not exist!");
        }
        return response;
    }

    @PutMapping("/update_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public MsgDto updateApartment(@PathVariable long aid, @RequestBody ApartmentDto apartmentDto) {
        // sample url localhost:8080/apt/update_apt/1
        // add one new apartment according to the given apartment information
        MsgDto response = new MsgDto();
        // check whether the landlord is in the database
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        if (apartmentEntity != null) {
            // update the apartment and persist to database
            ApartmentUtils.apartmentDtoToEntity(apartmentDto, apartmentEntity);
            apartmentRepository.save(apartmentEntity);
            response.setSuccess(true);
        }
        else {
            // apartment not in the database case
            response.setSuccess(false);
            response.setResponseMsg("Apartment not in database!");
        }
        return response;
    }

    @DeleteMapping("/delete_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public MsgDto deleteApartment(@PathVariable long aid) {
        // sample url localhost:8080/apt/delete_apt/1
        // add one new apartment according to the given apartment information
        MsgDto response = new MsgDto();
        // check whether the apartment is in the database
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        if (apartmentEntity != null) {
            UserEntity landlord = apartmentEntity.getLandlord();
            landlord.getOwnedApartments().remove(apartmentEntity);
            userRepository.save(landlord);
            // still need to delete the apartment from its repository
            // may do research on JPA to avoid that
            apartmentRepository.delete(apartmentEntity);
            response.setSuccess(true);
        }
        else {
            // apartment not in the database case
            response.setSuccess(false);
            response.setResponseMsg("Apartment not in database!");
        }
        return response;
    }

    @GetMapping("/get_all")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDto> getAllApartments() {
        // sample url localhost:8080/apt/get_all
        List<ApartmentEntity> apartmentEntities = apartmentRepository.findAll();
        List<ApartmentDto> apartmentDtos = new ArrayList<>();
        for (ApartmentEntity apartmentEntity : apartmentEntities) {
            apartmentDtos.add(ApartmentUtils.apartmentEntityToDto(apartmentEntity));
        }
        return apartmentDtos;
    }

    @GetMapping("/get_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto getApartment(@PathVariable long aid) {
        // sample url localhost:8080/apt/get_apt/1
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        ApartmentDto apartmentDto = ApartmentUtils.apartmentEntityToDto(apartmentEntity);
        return apartmentDto;
    }
}
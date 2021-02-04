package com.project.ece651.webapp.controllers;

        import com.project.ece651.webapp.entities.ApartmentEntity;
        import com.project.ece651.webapp.entities.UserEntity;
        import com.project.ece651.webapp.repositories.ApartmentRepository;
        import com.project.ece651.webapp.repositories.UserRepository;
        import com.project.ece651.webapp.shared.ApartmentDto;
        import com.project.ece651.webapp.shared.EmptyDto;
        import com.project.ece651.webapp.utils.ApartmentUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/apt")
public class ApartmentController {
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add_apt")
    @ResponseStatus(HttpStatus.CREATED)
    public EmptyDto createApartment(@RequestBody ApartmentDto apartmentDto) {
        // sample url localhost:8080/apt/add_apt
        // add one new apartment according to the given apartment information
        EmptyDto response = new EmptyDto();
        // check whether the landlord is in the database
        UserEntity userEntity = userRepository.findByUid(apartmentDto.getLandlordId());
        if (userEntity != null) {
            // convert the apartmentEto to apartmentEntity
            ApartmentEntity apartmentEntity = ApartmentUtils.apartmentDtoToEntity(apartmentDto);
            // add the apartment into those own by the user entity
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

    @PostMapping("/delete_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public EmptyDto deleteApartment(@PathVariable("aid") long aid) {
        // sample url localhost:8080/apt/delete_apt/1
        // add one new apartment according to the given apartment information
        EmptyDto response = new EmptyDto();
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

    @GetMapping("/get_apt/{aid}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto getApartment(@PathVariable long aid) {
        // sample url localhost:8080/apt/get_apt/1
        ApartmentEntity apartmentEntity = apartmentRepository.findByAid(aid);
        ApartmentDto apartmentDto;
        if (apartmentEntity == null) {
            // apartment not exist case
            apartmentDto = new ApartmentDto();
            apartmentDto.setSuccess(false);
            apartmentDto.setResponseMsg("Apartment not in database！");
        }
        else {
            // apartment in database case
            apartmentDto = ApartmentUtils.apartmentEntityToDto(apartmentEntity);
            apartmentDto.setSuccess(true);
        }
        return apartmentDto;
    }
}
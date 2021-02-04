package com.project.ece651.webapp.shared;

import java.io.Serializable;
import java.util.List;

public class ApartmentLstDto extends ResponseDto implements Serializable {
    private static final long serialVersionUID = 182756838819242L;
    List<ApartmentDto> apartmentDtos;

    public List<ApartmentDto> getApartmentDtos() {
        return apartmentDtos;
    }

    public void setApartmentDtos(List<ApartmentDto> apartmentDtos) {
        this.apartmentDtos = apartmentDtos;
    }
}

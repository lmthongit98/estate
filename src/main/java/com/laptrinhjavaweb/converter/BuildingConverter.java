package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;

@Component
public class BuildingConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO convertToDTOFromEntity(BuildingEntity entity) {
		BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class);
		dto.setAddress(dto.getStreet() +", "+ dto.getWard());
		return dto;
	}
}

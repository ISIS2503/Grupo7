/*
 * The MIT License
 *
 * Copyright 2017 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.minasamaca.model.dto.converter;

import co.edu.uniandes.isis2503.minasamaca.interfaces.IMeasurementConverter;
import co.edu.uniandes.isis2503.minasamaca.model.dto.model.MeasurementDTO;
import co.edu.uniandes.isis2503.minasamaca.model.entity.MeasurementEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ca.mendoza968
 * Modificaciones realizadas bajo el laboratorio de persistencia JPA Poliglota ISIS2503 2017-2
 */
public class MeasurementConverter implements IMeasurementConverter {

    public static IMeasurementConverter MCONVERTER = new MeasurementConverter();

    public MeasurementConverter() {
    }

    @Override
    public MeasurementDTO entityToDto(MeasurementEntity entity) {
        MeasurementDTO dto = new MeasurementDTO();
        
        dto.setId(entity.getId());
        dto.setSensetime(entity.getSensetime());
        dto.setType(entity.getType());
        dto.setDataValue(entity.getDataValue());
        dto.setUnit(entity.getUnit());
        
        return dto;
    }

    @Override
    public MeasurementEntity dtoToEntity(MeasurementDTO dto) {
        MeasurementEntity entity = new MeasurementEntity();
        
        entity.setId(dto.getId());
        entity.setSensetime(dto.getSensetime());
        entity.setType(dto.getType());
        entity.setDataValue(dto.getDataValue());
        entity.setUnit(dto.getUnit());
        
        return entity;
    }

    @Override
    public List<MeasurementDTO> listEntitiesToListDTOs(List<MeasurementEntity> entities) {
        ArrayList<MeasurementDTO> dtos = new ArrayList<>();
        
        for (MeasurementEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        
        return dtos;
    }

    @Override
    public List<MeasurementEntity> listDTOsToListEntities(List<MeasurementDTO> dtos) {
        ArrayList<MeasurementEntity> entities = new ArrayList<>();
        
        for (MeasurementDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        
        return entities;
    }
}

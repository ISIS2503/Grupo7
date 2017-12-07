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

import co.edu.uniandes.isis2503.minasamaca.interfaces.IAlarmConverter;
import co.edu.uniandes.isis2503.minasamaca.model.dto.model.AlarmDTO;
import co.edu.uniandes.isis2503.minasamaca.model.entity.AlarmEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ja.bermudez10
 */
public class AlarmConverter implements IAlarmConverter
{
    public static IAlarmConverter MCONVERTER = new AlarmConverter();

    public AlarmConverter()
    {
        
    }

    @Override
    public AlarmDTO entityToDto(AlarmEntity entity) {
        AlarmDTO dto = new AlarmDTO();
        
        dto.setId(entity.getId());
        dto.setAlarmtime(entity.getAlarmtime());
        dto.setType(entity.getType());
        dto.setDataValue(entity.getDataValue());
        dto.setUnit(entity.getUnit());
        dto.setDescripcion(entity.getDescripcion());
        dto.setUbicacion(entity.getUbicacion());
        return dto;
    }

    @Override
    public AlarmEntity dtoToEntity(AlarmDTO dto) {
        AlarmEntity entity = new AlarmEntity();
        
        entity.setId(dto.getId());
        entity.setAlarmtime(dto.getAlarmtime());
        entity.setType(dto.getType());
        entity.setDataValue(dto.getDataValue());
        entity.setUnit(dto.getUnit());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUbicacion(dto.getUbicacion());
        
        return entity;
    }

    @Override
    public List<AlarmDTO> listEntitiesToListDTOs(List<AlarmEntity> entities) {
        ArrayList<AlarmDTO> dtos = new ArrayList<>();
        
        for (AlarmEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        
        return dtos;
    }

    @Override
    public List<AlarmEntity> listDTOsToListEntities(List<AlarmDTO> dtos) {
        ArrayList<AlarmEntity> entities = new ArrayList<>();
        
        for (AlarmDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        
        return entities;
    }
}

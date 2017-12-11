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
package co.edu.uniandes.isis2503.minasamaca.logic;

import co.edu.uniandes.isis2503.minasamaca.interfaces.IMeasurementLogic;
import static co.edu.uniandes.isis2503.minasamaca.model.dto.converter.MeasurementConverter.MCONVERTER;
import co.edu.uniandes.isis2503.minasamaca.model.dto.model.MeasurementDTO;
import co.edu.uniandes.isis2503.minasamaca.persistence.MeasurementPersistence;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ca.mendoza968
 * Modificaciones realizadas bajo el laboratorio de persistencia JPA Poliglota ISIS2503 2017-2
 */
public class MeasurementLogic implements IMeasurementLogic {

    private final MeasurementPersistence persistence;

    public MeasurementLogic() {
        this.persistence = new MeasurementPersistence();
    }

    @Override
    public MeasurementDTO add(MeasurementDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        MeasurementDTO result = MCONVERTER.entityToDto(persistence.add(MCONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public MeasurementDTO update(MeasurementDTO dto) {
        MeasurementDTO result = MCONVERTER.entityToDto(persistence.update(MCONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public MeasurementDTO find(Long id) {
        return MCONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<MeasurementDTO> all() {
        return MCONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(Long id) {
        return persistence.delete(id);
    }
}

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
package co.edu.uniandes.isis2503.minasamaca.service;

import co.edu.uniandes.isis2503.minasamaca.auth.Secured;
import co.edu.uniandes.isis2503.minasamaca.interfaces.IAlarmLogic;
import co.edu.uniandes.isis2503.minasamaca.logic.AlarmLogic;
import co.edu.uniandes.isis2503.minasamaca.model.dto.model.AlarmDTO;
//import com.sun.istack.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ca.mendoza968
 * Modificaciones realizadas bajo el laboratorio de persistencia JPA Poliglota ISIS2503 2017-2
 */
@Secured
@Path("/alarms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlarmService {
    
    private final IAlarmLogic measurementLogic;

    public AlarmService() {
        this.measurementLogic = new AlarmLogic();
    }

    @POST
    public AlarmDTO add(AlarmDTO dto) {
        return measurementLogic.add(dto);
    }

    @GET
    public List<AlarmDTO> all() {
        return measurementLogic.all();
    }
    
    @GET
    @Path("/{id}")
    public AlarmDTO find(@PathParam("id") String id) {
        return measurementLogic.find(id);
    }    
    
    @PUT
    public AlarmDTO update(AlarmDTO dto) {
        return measurementLogic.update(dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            measurementLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Alarm was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(AlarmService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }    
}

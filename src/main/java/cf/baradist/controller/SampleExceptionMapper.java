/**
 * Copyright 2016 SmartBear Software
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cf.baradist.controller;


import cf.baradist.exception.ApiException;
import cf.baradist.exception.BadRequestException;
import cf.baradist.exception.NotFoundException;
import cf.baradist.model.ApiResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
public class SampleExceptionMapper implements ExceptionMapper<Exception> {
    public Response toResponse(Exception exception) {
        if (exception instanceof javax.ws.rs.WebApplicationException) {
            javax.ws.rs.WebApplicationException e = (javax.ws.rs.WebApplicationException) exception;
            return Response
                    .status(e.getResponse().getStatus())
                    .entity(new ApiResponse(e.getResponse().getStatus(),
                            exception.getMessage())).build();
        } else if (exception instanceof com.fasterxml.jackson.core.JsonParseException) {
            return Response.status(400)
                    .entity(new ApiResponse(400, "bad input")).build();
        } else if (exception instanceof NotFoundException) {
            return Response
                    .status(Status.NOT_FOUND)
                    .entity(new ApiResponse(ApiResponse.ERROR, exception
                            .getMessage())).build();
        } else if (exception instanceof BadRequestException) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(ApiResponse.ERROR, exception
                            .getMessage())).build();
        } else if (exception instanceof ApiException) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(ApiResponse.ERROR, exception
                            .getMessage())).build();
        } else if (exception instanceof SQLException) {
            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse(ApiResponse.ERROR, exception
                            .getMessage())).build();
        } else {
            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "something bad happened")).build();
        }
    }
}
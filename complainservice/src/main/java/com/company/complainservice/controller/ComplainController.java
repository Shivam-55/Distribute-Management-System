package com.company.complainservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.complainservice.dto.ComplainDto;
import com.company.complainservice.response.MessageResponse;
import com.company.complainservice.service.ComplainService;
import com.company.complainservice.utils.DecodeText;
import com.company.complainservice.utils.logger.LoggableClass;
import com.company.complainservice.utils.logger.LoggableMethod;

import java.util.List;

/**
 * Controller class for handling complaints related operations.
 */
@LoggableClass
@RestController
@RequestMapping("/connection")
public class ComplainController {

    private ComplainService complainService;

    @Autowired
    public  ComplainController(ComplainService complainService){
        this.complainService =complainService;
    }

    /**
     * Endpoint to fill a complaint form.
     *
     * @param complainDto        The complaint DTO object.
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<ComplainDto>> representing the response.
     */
    @LoggableMethod
    @PostMapping("/complain")
    public ResponseEntity<MessageResponse<ComplainDto>> fillComplainForm(@Valid @RequestBody ComplainDto complainDto, HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        ComplainDto filledComplain = complainService.fillComplainForm(userId, complainDto);
        MessageResponse<ComplainDto> response = MessageResponse.<ComplainDto>builder()
                .data(filledComplain)
                .message("complain filled successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to get all complaints.
     *
     * @return ResponseEntity<MessageResponse<List<ComplainDto>>> representing the response.
     */
    @LoggableMethod
    @GetMapping("/get/complain")
    public ResponseEntity<MessageResponse<List<ComplainDto>>> getAllComplains(){
        List<ComplainDto> complainDtoList = complainService.getAllComplains();
        MessageResponse<List<ComplainDto>> response = MessageResponse.<List<ComplainDto>>builder()
                .data(complainDtoList)
                .message("complain filled successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

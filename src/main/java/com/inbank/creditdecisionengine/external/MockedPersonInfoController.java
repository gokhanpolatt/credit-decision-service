package com.inbank.creditdecisionengine.external;

import com.inbank.creditdecisionengine.dto.PersonInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mockedService")
public class MockedPersonInfoController {

    @GetMapping("/personInfo")
    public ResponseEntity<PersonInfoDto> getPersonInfo(@RequestParam("identityNumber") final String identityNumber) {
        PersonInfoDto personInfoDto = PersonInfoDto.builder().build();

        switch (identityNumber) {
            case "49002010965":
                personInfoDto.setIdentityNumber(identityNumber);
                personInfoDto.setSegment("debt");
                personInfoDto.setCreditModifier(0);
                break;
            case "49002010976":
                personInfoDto.setIdentityNumber(identityNumber);
                personInfoDto.setSegment("segment 1");
                personInfoDto.setCreditModifier(50);
                break;
            case "49002010987":
                personInfoDto.setIdentityNumber(identityNumber);
                personInfoDto.setSegment("segment 2");
                personInfoDto.setCreditModifier(300);
                break;
            case "49002010998":
                personInfoDto.setIdentityNumber(identityNumber);
                personInfoDto.setSegment("segment 3");
                personInfoDto.setCreditModifier(1000);
                break;
            default:
                return null;
        }

        return ResponseEntity.ok(personInfoDto);
    }
}

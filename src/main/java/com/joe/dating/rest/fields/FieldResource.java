package com.joe.dating.rest.fields;

import com.joe.dating.domain.fields.ProfileFieldGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/fields")
public class FieldResource {

    private ResourceBundle resourceBundle;

    public FieldResource() {
        resourceBundle = ResourceBundle.getBundle("messages");
    }

    @GetMapping
    public ResponseEntity<Map<String, ProfileFieldDto>> findProfileFields() {
        Map<String, ProfileFieldDto> profileFieldDtos = new HashMap<>();

        for(ProfileFieldGroup profileFieldGroup : ProfileFieldGroup.values()) {
            ProfileFieldDto profileFieldDto = new ProfileFieldDto();
            profileFieldDto.setOptions(profileFieldGroup.getOptions(resourceBundle));
            profileFieldDto.setCategory(profileFieldGroup.getCategory());
            profileFieldDto.setFieldInputType(profileFieldGroup.getFieldInputType());
            profileFieldDtos.put(profileFieldGroup.getFieldName(), profileFieldDto);
        }

        return ResponseEntity.ok(profileFieldDtos);
    }


}

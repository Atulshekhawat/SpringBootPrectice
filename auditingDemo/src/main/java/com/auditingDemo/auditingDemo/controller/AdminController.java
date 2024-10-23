package com.auditingDemo.auditingDemo.controller;

import com.auditingDemo.auditingDemo.entity.User;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/audit")
public class AdminController {

    @Autowired
    private EntityManagerFactory  entityManagerFactory;




    // Assuming User is your entity class
    @GetMapping("/posts/{id}")
    List<Map<String, Object>> posts(@PathVariable String id) {
        AuditReader reader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());

        List<Number> revisions = reader.getRevisions(User.class, id);
        List<Map<String, Object>> changedFieldsList = new ArrayList<>();

        for (int i = 0; i < revisions.size(); i++) {
            Number currentRevisionNumber = revisions.get(i);
            User currentUser = reader.find(User.class, id, currentRevisionNumber);

            if (i > 0) { // Compare with the previous revision
                Number previousRevisionNumber = revisions.get(i - 1);
                User previousUser = reader.find(User.class, id, previousRevisionNumber);

                Map<String, Object> changedFields = new HashMap<>();
                // Use reflection to compare fields
                Field[] fields = User.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true); // Allow access to private fields
                    try {
                        Object currentValue = field.get(currentUser);
                        Object previousValue = field.get(previousUser);
                        if (!Objects.equals(currentValue, previousValue)) {
                            // Include both previous and current values
                            Map<String, Object> valueChange = new HashMap<>();
                            valueChange.put("previous", previousValue);
                            valueChange.put("current", currentValue);
                            changedFields.put(field.getName(), valueChange);
                        }
                    } catch (IllegalAccessException e) {
                        // Handle the exception according to your needs
                        e.printStackTrace();
                    }
                }

                if (!changedFields.isEmpty()) {
                    changedFieldsList.add(changedFields);
                }
            }
        }

        return changedFieldsList;
    }



}

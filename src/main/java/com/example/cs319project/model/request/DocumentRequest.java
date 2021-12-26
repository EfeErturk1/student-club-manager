package com.example.cs319project.model.request;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    protected int documentId;

    private String document_name;

    private Date submission_date;

    private int author;

    private int assignment_id;
}

package com.example.cs319project.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DocumentAssignmentIdHolder {
    List<Integer> documentIds;
    private int assignmentId;
}

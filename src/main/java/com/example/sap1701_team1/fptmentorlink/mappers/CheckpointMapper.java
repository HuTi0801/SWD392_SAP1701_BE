package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Checkpoint;
import com.example.sap1701_team1.fptmentorlink.models.response_models.CheckpointResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.CheckpointReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CheckpointMapper {
    public CheckpointResponse toCheckpointResponse(Checkpoint checkpoint) {
        return CheckpointResponse.builder()
                .id(checkpoint.getId())
                .cpName(checkpoint.getCpName())
                .deadline(checkpoint.getDeadline())
                .document(checkpoint.getDocument())
                .checkpointReviews(
                        checkpoint.getCheckpointReviewList().stream()
                                .map(review -> CheckpointReviewResponse.builder()
                                        .id(review.getId())
                                        .score(review.getScore())
                                        .feedback(review.getFeedback())
                                        .reviewedAt(review.getReviewedAt())
                                        .lecturerId(review.getLecturer().getId())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public List<CheckpointResponse> toCheckpointResponseList(List<Checkpoint> checkpoints) {
        return checkpoints.stream().map(this::toCheckpointResponse).collect(Collectors.toList());
    }
}

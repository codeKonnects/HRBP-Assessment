package io.davidabejirin.assessment.service;

import io.davidabejirin.assessment.dto.AddScoreDTO;
import io.davidabejirin.assessment.utils.ApiResponse;

public interface ScoreService {
     ApiResponse<?> addScore(Long studentId, AddScoreDTO addScoreDTO);
}

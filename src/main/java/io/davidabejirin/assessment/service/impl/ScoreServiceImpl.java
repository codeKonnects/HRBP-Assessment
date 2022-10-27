package io.davidabejirin.assessment.service.impl;

import io.davidabejirin.assessment.dto.AddScoreDTO;
import io.davidabejirin.assessment.entity.Clazz;
import io.davidabejirin.assessment.entity.Result;
import io.davidabejirin.assessment.entity.Student;
import io.davidabejirin.assessment.entity.Subject;
import io.davidabejirin.assessment.repository.ResultRepository;
import io.davidabejirin.assessment.repository.SchoolClassRepository;
import io.davidabejirin.assessment.repository.StudentRepository;
import io.davidabejirin.assessment.repository.SubjectRepository;
import io.davidabejirin.assessment.service.ScoreService;
import io.davidabejirin.assessment.utils.ApiResponse;
import io.davidabejirin.assessment.utils.StudentUtil;
import io.davidabejirin.assessment.utils.Term;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
@Data
@Builder
public class ScoreServiceImpl implements ScoreService {

    private final StudentUtil studentUtil;
    private final SubjectRepository subjectRepository;
    private  final StudentRepository studentRepository;
    private  final SchoolClassRepository schoolClassRepository;
    private final ResultRepository resultRepository;

    public ApiResponse<?> addScore(Long studentId, AddScoreDTO addScoreDTO){
        Student student = studentUtil.findStudentById(studentId);
        Clazz clazz = student.getClazz();
        Subject subject = subjectRepository.findById(addScoreDTO.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (addScoreDTO.getScore() < 100.0 && addScoreDTO.getScore() > 0.0){
            Result result = Result.builder()
                    .student(student)
                    .subject(subject)
                    .score(addScoreDTO.getScore())
                    .term(Term.valueOf(addScoreDTO.getTerm()))
                    .clazz(clazz)
                    .build();
            resultRepository.save(result);
            return new ApiResponse<>(CREATED, "Score added successfully", result);
        } else {
            return new ApiResponse<>(BAD_REQUEST, "Score must be between 0 and 100", null);
        }
    }


}

package demo.ai.service;

import demo.ai.service.dto.Answer;
import demo.ai.service.dto.Question;
import reactor.core.publisher.Flux;

public interface ACAService {
      Answer askQuestion(Question question);
      String askAnything(Question question);
      String askQuestionStr(Question question);
      Flux<String> askStreamQuestion(Question question);
}

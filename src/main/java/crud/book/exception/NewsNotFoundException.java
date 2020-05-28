package crud.book.exception;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.MessageUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice

public class NewsNotFoundException {


  @ExceptionHandler(Exception.class)
  public Map<String, String> badCredentialsExceptionHandler(HttpServletRequest request,
      HttpServletResponse response,
      Exception exception) {
//    log.warn("badCredentialsExceptionHandler: exception ={}, requestMenu={}, response={}",
//        exception.getMessage(), request.getPathInfo(), response.getContentType());

    return Map.of("status",exception.getMessage());
  }
}

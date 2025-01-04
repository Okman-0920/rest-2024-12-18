package com.ll.rest.global.globalExceptionHandlers;

import com.ll.rest.global.app.AppConfig;
import com.ll.rest.global.exceptions.ServiceException;
import com.ll.rest.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // // 데이터가 존재하지 않을 때 발생하는 예외에 대하여 404 응답을 반환
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RsData<Void>> handle(NoSuchElementException ex) {

        if (AppConfig.isNotProd()) ex.printStackTrace();
        // AppConfig : 임의로 만든거
        // 운영모드가 아니면 자세한 오류 내역을 출력해라

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RsData<>(
                        "404-1",
                        "해당 데이터가 존재하지 않습니다."
                ));
    }

    // 유효성 검사 오류를 처리하여 400 응답과 상세 오류 메시지를 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RsData<Void>> handle(MethodArgumentNotValidException ex) {

        if (AppConfig.isNotProd()) ex.printStackTrace();

        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData<>(
                        "400-1",
                        message
                ));
    }

    // 서비스 예외를 처리하여 해당 예외에 맞는 상태 코드와 데이터를 반환
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<RsData<Void>> handle(ServiceException ex) {

        if (AppConfig.isNotProd()) ex.printStackTrace();

        RsData<Void> rsData = ex.getRsData();

        return ResponseEntity
                .status(rsData.getStatusCode())
                .body(rsData);
    }
}
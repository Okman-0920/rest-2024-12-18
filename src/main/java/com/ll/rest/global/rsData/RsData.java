package com.ll.rest.global.rsData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
// null인 필드는 JSON에 포함되지 않음
@AllArgsConstructor
@Getter
public class RsData {
    private String resultCode;
    private String msg;
    private Long data;

    public RsData(String resultCode, String msg) {
        this(resultCode, msg, null);
    }
}

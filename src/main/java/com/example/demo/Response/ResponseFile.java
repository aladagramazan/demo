package com.example.demo.Response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFile {

    private String name;
    private String url;
    private String type;
    private long size;
}

package com.hybridlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDto {

    @NotNull
    private Long id;
    private Long bookId;
    private Long userId;
    private boolean rented;
    private LocalDate rentDate;
}

package com.hybridlibrary.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
public class BookRentalDto {
    @NotNull
    private Long id;
    @NotNull
    private Long bookId;
    @NotNull
    private Long userId;
    @NotNull
    private Long bookCopyId;
    @NotNull
    private LocalDate returnDate;
}

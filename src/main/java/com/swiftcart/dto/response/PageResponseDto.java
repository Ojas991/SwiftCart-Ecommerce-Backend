package com.swiftcart.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Generic paginated response containing records and pagination details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDto<T> {
	
	@Schema(description = "List of records in the current page")
	private List<T> content;
	
    @Schema(
            description = "Current page number (0-based index)",
            example = "0"
        )
	private int pageNumber;
    
    @Schema(
            description = "Number of records per page",
            example = "10"
        )
	private int pageSize;
    
    @Schema(
            description = "Total number of records",
            example = "125"
        )
	private long totalElements;
    
    @Schema(
            description = "Total number of pages",
            example = "13"
        )
	private int totalPages;
    
    @Schema(
            description = "Indicates whether this is the first page",
            example = "true"
        )
	private boolean first;
    
    @Schema(
            description = "Indicates whether this is the last page",
            example = "false"
        )
	private boolean last;
    
    @Schema(
            description = "Indicates whether a next page exists",
            example = "true"
        )
	private boolean hasNext;
    
    @Schema(
            description = "Indicates whether a previous page exists",
            example = "false"
        )
	private boolean hasPrevious;

	// Static method <----Static method ---->

	public static <T> PageResponseDto<T> fromPage(Page<T> page) {
		return PageResponseDto.<T>builder()
				.content(page.getContent())
				.pageNumber(page.getNumber())
				.pageSize(page.getSize())
				.totalElements(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.first(page.isFirst())
				.last(page.isLast())
				.hasNext(page.hasNext())
				.hasPrevious(page.hasPrevious())
				.build();
	}
}

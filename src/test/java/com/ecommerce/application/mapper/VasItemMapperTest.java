package com.ecommerce.application.mapper;

import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.vasItem.VasItemResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class VasItemMapperTest {

    @Test
    public void givenValidVasItemRequestDto_whenMappedToVasItem_thenFieldsShouldMatch() {
        // Given
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setVasItemId(1);
        requestDto.setPrice(100.0);
        requestDto.setQuantity(2);

        // When
        VasItem vasItem = VasItemMapper.toVasItem(requestDto);

        // Then
        assertNotNull(vasItem);
        assertEquals(requestDto.getVasItemId(), vasItem.getItemId());
        assertEquals(requestDto.getPrice(), vasItem.getPrice());
        assertEquals(requestDto.getQuantity(), vasItem.getQuantity());
        assertEquals(5003, vasItem.getSellerId()); // Default sellerId
        assertEquals(3442, vasItem.getCategoryId()); // Default categoryId
    }

    @Test
    public void givenInvalidQuantity_whenMappedToVasItem_thenQuantityShouldBeValid() {
        // Given
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setVasItemId(1);
        requestDto.setPrice(100.0);
        requestDto.setQuantity(5); // Invalid quantity as MAX_QUANTITY is 3

        // When
        VasItem vasItem = VasItemMapper.toVasItem(requestDto);

        // Then
        assertFalse(vasItem.isValidQuantity(requestDto.getQuantity()));
    }

    @Test
    public void givenNullVasItemRequestDto_whenMappedToVasItem_thenShouldReturnNull() {
        // Given
        ItemRequestDto requestDto = null;

        // When
        VasItem vasItem = VasItemMapper.toVasItem(requestDto);

        // Then
        assertNull(vasItem);
    }

    @Test
    public void givenValidVasItem_whenMappedToResponseDto_thenFieldsShouldMatch() {
        // Given
        VasItem vasItem = new VasItem(1, 100.0, 2);

        // When
        VasItemResponseDto responseDto = VasItemMapper.toResponseDto(vasItem);

        // Then
        assertNotNull(responseDto);
        assertEquals(vasItem.getItemId(), responseDto.getVasItemId());
        assertEquals(vasItem.getCategoryId(), responseDto.getCategoryId());
        assertEquals(vasItem.getSellerId(), responseDto.getSellerId());
        assertEquals(vasItem.getPrice(), responseDto.getPrice());
        assertEquals(vasItem.getQuantity(), responseDto.getQuantity());
    }

    @Test
    public void givenNullVasItem_whenMappedToResponseDto_thenShouldReturnNull() {
        // Given
        VasItem vasItem = null;

        // When
        VasItemResponseDto responseDto = VasItemMapper.toResponseDto(vasItem);

        // Then
        assertNull(responseDto);
    }
}

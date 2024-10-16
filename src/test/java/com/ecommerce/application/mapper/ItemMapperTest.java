package com.ecommerce.application.mapper;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.item.ItemResponseDto;
import com.ecommerce.application.dto.vasItem.VasItemResponseDto;
import com.ecommerce.application.factory.ItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemMapperTest {

    @Mock
    VasItemResponseDto vasItemResponseDto;
    @InjectMocks
    private ItemMapper itemMapper;
    @Mock
    private ItemFactory itemFactory;
    @Mock
    private VasItemMapper vasItemMapper;
    @Mock
    private DefaultItem defaultItem;
    @Mock
    private ItemRequestDto itemRequestDto;
    @Mock
    private VasItem vasItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        vasItem = new VasItem(2, 500, 1);
        vasItemResponseDto = new VasItemResponseDto();
        vasItemMapper = new VasItemMapper();


    }

    @Test
    public void toItemDto_shouldReturnItemResponseDto_whenItemIsValid1() {
        //Given
        when(defaultItem.getQuantity()).thenReturn(5);

        when(defaultItem.getItemId()).thenReturn(1);
        when(defaultItem.getCategoryId()).thenReturn(100);
        when(defaultItem.getSellerId()).thenReturn(200);
        when(defaultItem.getPrice()).thenReturn(50.0);

        //When
        ItemResponseDto result = itemMapper.toItemDto(defaultItem);

        // Then
        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        assertEquals(1, result.getItemId());
        assertEquals(100, result.getCategoryId());
        assertEquals(200, result.getSellerId());
        assertEquals(50.0, result.getPrice());
    }

    @Test
    public void toItemDto_shouldReturnItemResponseDto_whenItemIsValid() {
        // Given
        when(defaultItem.getQuantity()).thenReturn(5);
        when(defaultItem.getItemId()).thenReturn(1);
        when(defaultItem.getCategoryId()).thenReturn(100);
        when(defaultItem.getSellerId()).thenReturn(200);
        when(defaultItem.getPrice()).thenReturn(50.0);

        List<VasItem> vasItems = new ArrayList<>();
        vasItems.add(vasItem);
        when(defaultItem.getVasItems()).thenReturn(vasItems);

        try (MockedStatic<VasItemMapper> mockedStatic = mockStatic(VasItemMapper.class)) {
            mockedStatic.when(() -> VasItemMapper.toResponseDto(vasItem)).thenReturn(vasItemResponseDto);

            //When
            ItemResponseDto result = itemMapper.toItemDto(defaultItem);

            //Then
            assertNotNull(result);
            assertEquals(5, result.getQuantity());
            assertEquals(1, result.getItemId());
            assertEquals(100, result.getCategoryId());
            assertEquals(200, result.getSellerId());
            assertEquals(50.0, result.getPrice());
            assertNotNull(result.getVasItems());
            assertEquals(1, result.getVasItems().size());
            assertEquals(vasItemResponseDto, result.getVasItems().get(0));
        }
    }


    @Test
    public void toItemDto_shouldReturnNull_whenItemIsNull() {
        // When
        ItemResponseDto result = itemMapper.toItemDto(null);

        // Then
        assertNull(result);
    }

    @Test
    public void updateItemFromDto_shouldReturnItem_whenDtoIsValid() {
        // Given
        when(itemRequestDto.getItemId()).thenReturn(1);
        when(itemRequestDto.getCategoryId()).thenReturn(100);
        when(itemRequestDto.getSellerId()).thenReturn(200);
        when(itemRequestDto.getPrice()).thenReturn(50.0);
        when(itemRequestDto.getQuantity()).thenReturn(5);

        Item createdItem = mock(Item.class);
        when(itemFactory.createItem(1, 100, 200, 50.0, 5)).thenReturn(createdItem);

        // When
        Item result = itemMapper.updateItemFromDto(itemRequestDto);

        // Then
        assertNotNull(result);
        verify(itemFactory).createItem(1, 100, 200, 50.0, 5);
    }

    @Test
    public void updateItemFromDto_shouldReturnNull_whenDtoIsNull() {
        // When
        Item result = itemMapper.updateItemFromDto(null);

        // Then
        assertNull(result);
    }
}

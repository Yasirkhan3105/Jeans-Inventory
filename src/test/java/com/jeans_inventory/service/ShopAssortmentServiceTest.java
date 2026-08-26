package com.jeans_inventory.service;

import com.jeans_inventory.entity.ShopAssortment;
import com.jeans_inventory.entity.Style;
import com.jeans_inventory.repository.ShopAssortmentRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ShopAssortmentServiceTest {

    private final ShopAssortmentRepository shopAssortmentRepository = mock(ShopAssortmentRepository.class);
    private final ShopAssortmentService shopAssortmentService =
            new ShopAssortmentService(shopAssortmentRepository);

    @Test
    void addAssortmentIncreasesQuantityForExistingStyleAndSize() {
        Style style = styleWithId(1L);

        ShopAssortment existing = assortment(style, 32, 8);
        ShopAssortment incoming = assortment(style, 32, 4);

        when(shopAssortmentRepository.findByStyleIdAndSize(1L, 32))
                .thenReturn(Optional.of(existing));
        when(shopAssortmentRepository.save(existing)).thenReturn(existing);

        ShopAssortment result = shopAssortmentService.addAssortment(incoming);

        assertEquals(12, result.getQuantity());
        verify(shopAssortmentRepository).save(existing);
    }

    @Test
    void addAssortmentCreatesRecordWhenStyleAndSizeDoNotExist() {
        Style style = styleWithId(1L);
        ShopAssortment incoming = assortment(style, 34, 3);

        when(shopAssortmentRepository.findByStyleIdAndSize(1L, 34))
                .thenReturn(Optional.empty());
        when(shopAssortmentRepository.save(incoming)).thenReturn(incoming);

        ShopAssortment result = shopAssortmentService.addAssortment(incoming);

        assertEquals(34, result.getSize());
        assertEquals(3, result.getQuantity());
        verify(shopAssortmentRepository).save(incoming);
    }

    @Test
    void addAssortmentRejectsSizeOutsideConfirmedSizes() {
        ShopAssortment incoming = assortment(styleWithId(1L), 31, 2);

        assertThrows(
                IllegalArgumentException.class,
                () -> shopAssortmentService.addAssortment(incoming)
        );

        verifyNoInteractions(shopAssortmentRepository);
    }

    private Style styleWithId(Long id) {
        Style style = mock(Style.class);
        when(style.getId()).thenReturn(id);
        return style;
    }

    private ShopAssortment assortment(Style style, Integer size, Integer quantity) {
        ShopAssortment assortment = new ShopAssortment();
        assortment.setStyle(style);
        assortment.setSize(size);
        assortment.setQuantity(quantity);
        return assortment;
    }
}

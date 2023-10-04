package com.forcat.shop.repository;

import com.forcat.shop.dto.ItemSearchDto;
import com.forcat.shop.dto.MainItemDto;
import com.forcat.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    // ItemSearchDto (상품 조회 조건) 객체와 Pageable (페이징 정보 담음) 객체 파라미터로 받는 메소드

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}

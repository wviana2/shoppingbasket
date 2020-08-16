package com.shoppingbasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingbasket.model.Basket;

@Repository
public interface BasketRepository extends JpaRepository <Basket, Long> {

}

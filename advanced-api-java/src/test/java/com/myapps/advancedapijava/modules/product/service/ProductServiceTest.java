package com.myapps.advancedapijava.modules.product.service;

import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.product.dto.ProductDto;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.modules.product.repository.ProductRepository;
import com.myapps.advancedapijava.modules.product.util.ProductUtil;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
import com.myapps.advancedapijava.util.CryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository repository;
  private ProductService underTest;

  @BeforeEach
  void setUp() {
    underTest = new ProductService(repository);
  }

  @Test
  @DisplayName("Given id, when it searches for a product, it should find it.")
  void itShouldFindProductById() throws HandledException {
    // Given
    Long id = 1L;
    String name = "product";
    String desc = "product.description";
    Float price = 2.99F;
    Product product = Product.builder().id(id).name(name).description(desc).price(price).build();
    Optional<Product> optionalProduct = Optional.of(product);

    // When
    when(repository.findById(any(Long.class))).thenReturn(optionalProduct);
    Product foundProduct = underTest.findByIdOrException(id);

    // Then
    assertThat(foundProduct)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(foundProduct);
  }

  @Test
  @DisplayName("Given id, when it searches for a product, it should find it.")
  void itShouldFindProductDtoById() throws HandledException {
    // Given
    Long id = 1L;
    String name = "product";
    String desc = "product.description";
    Float price = 2.99F;
    Product product = Product.builder().id(id).name(name).description(desc).price(price).build();
    Optional<Product> optionalProduct = Optional.of(product);

    // When
    when(repository.findById(any(Long.class))).thenReturn(optionalProduct);
    ProductDto foundProductDto = underTest.readOne(id);
    Product foundProduct = ProductUtil.toEntity(foundProductDto);

    // Then
    assertThat(foundProduct)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .ignoringFields("id")
      .isEqualTo(foundProduct);
  }

  @Test
  @DisplayName("Given id, when it searches for a product which doesn't exists, it will throw an exception.")
  void willThrowWhenNotFindById() throws HandledException {
    // Given
    Long id = 1L;
    String name = "product";
    String desc = "product.description";
    Float price = 2.99F;
    Product product = Product.builder().id(id).name(name).description(desc).price(price).build();
    Optional<Product> optionalProduct = Optional.of(product);
    String msgProductNotFound = ExceptionType.PRODUCT_NOT_FOUND_BY_ID.getMessage().formatted(id);

    // When
    when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

    // Then
    assertThatThrownBy(() -> underTest.findByIdOrException(id))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(msgProductNotFound);
  }

  @Test
  @DisplayName("Given id, when it checks if a product exists, it should not throw exception.")
  void itShouldExistsProductById() throws HandledException {
    // Given
    Long id = 1L;

    // When
    when(repository.existsById(any(Long.class))).thenReturn(true);

    // Then
    assertThatNoException().isThrownBy(() -> underTest.existsByIdOrException(id));
  }

  @Test
  @DisplayName("Given id, when it checks if a product exists, it should throw exception.")
  void itShouldThrowOnExistsProductById() throws HandledException {
    // Given
    Long id = 1L;
    String msgProductNotFound = ExceptionType.PRODUCT_NOT_FOUND_BY_ID.getMessage().formatted(id);

    // When
    when(repository.existsById(any(Long.class))).thenReturn(false);

    // Then
    assertThatThrownBy(() -> underTest.existsByIdOrException(id))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(msgProductNotFound);
  }

  @Test
  @DisplayName("When it searches for all products, it should return them.")
  void canGetAllProducts() {
    // Given
    Product productA = Product.builder().id(1L).name("productA").description("product.descriptionA").price(2.99F).build();
    Product productB = Product.builder().id(2L).name("productB").description("product.descriptionB").price(3.99F).build();
    List<Product> productList = List.of(productA, productB);

    // When
    when(repository.findAll()).thenReturn(productList);

    // Then
    underTest.readAll();
    verify(repository).findAll();
  }

  @Test
  @DisplayName("Given name, description and price, when it creates a product, it must exist.")
  void canAddProduct() throws HandledException {
    // Given
    String name = "product";
    String desc = "product.description";
    Float price = 2.99F;
    ProductDto productDto = ProductDto.builder().id(1L).name(name).description(desc).price(price).build();
    Product product = ProductUtil.toEntity(productDto);

    // When
    when(repository.save(any())).thenReturn(product);
    underTest.create(productDto);

    // Then
    ArgumentCaptor<Product> userArgumentCaptor = ArgumentCaptor.forClass(Product.class);
    verify(repository).save(userArgumentCaptor.capture());
    Product capturedProduct = userArgumentCaptor.getValue();
    assertThat(capturedProduct)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .ignoringFields("id")
      .isEqualTo(product);
  }

  // TODO: Continuar desenvolvimento aqui, a partir do update do ProductService

}
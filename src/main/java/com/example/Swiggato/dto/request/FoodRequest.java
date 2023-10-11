package com.example.Swiggato.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodRequest {

    int requiredQuantity;

    String customerMobileNo;

    int menuItemId;
}

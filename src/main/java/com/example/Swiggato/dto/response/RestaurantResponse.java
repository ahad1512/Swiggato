package com.example.Swiggato.dto.response;

import com.example.Swiggato.Enum.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {

    String name;

    String location;

    String contactNumber;

    boolean opened;

    List<FoodResponse> menu;
}

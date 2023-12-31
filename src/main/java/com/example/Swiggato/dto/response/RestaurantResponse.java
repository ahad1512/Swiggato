package com.example.Swiggato.dto.response;

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

    List<MenuResponse> menu;
}

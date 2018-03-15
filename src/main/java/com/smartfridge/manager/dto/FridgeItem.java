package com.smartfridge.manager.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FridgeItem {
    private String itemUUID;
    private Long itemType;
    private String name;
    private Double fillFactor;
    private boolean forgetFlag;
}

package com.smartfridge.manager.dto;

import com.google.common.base.Objects;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FridgeItem {
    private String itemUUID;
    private Long itemType;
    private String name;
    private Double fillFactor;
    private boolean forgetFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FridgeItem that = (FridgeItem) o;
        return Objects.equal(itemUUID, that.itemUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), itemUUID);
    }
}

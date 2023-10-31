package forktex.SoccerManagerBELite.controllers.market.resources;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PlacePlayerOnMarketDTO {
    @NotNull(message = "requestedPrice field cannot be null!")
    @Min(value = 1, message = "requestedPrice must be a positive number!")
    Double requestedPrice;
}
